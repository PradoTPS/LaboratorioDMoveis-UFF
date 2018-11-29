package labmoveis.uffeventos;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.BreakIterator;

import labmoveis.uffeventos.Config.Base64Custom;
import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Objetos.Usuário;

public class EditUserInfo extends AppCompatActivity {

    public EditText nome;
    public EditText campus;
    public EditText email;
    public EditText senha;
    public EditText confirmaSenha;
    private ProgressBar progressBar;
    private ImageButton salvaImagem;
    private Uri uri;

    public Usuário usuário;
    public BreakIterator mensagemerro;

    private StorageReference firebaseStorage;
    public FirebaseAuth autenticacao;
    public DatabaseReference dbUser;
    private static final int galery_intent = 2;
    private String cod_imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        this.nome = findViewById(R.id.edit_nome_id);
        this.campus = findViewById(R.id.edit_campus_id);
        this.email = findViewById(R.id.edit_email_id);
        this.senha = findViewById(R.id.edit_senha_id);
        this.confirmaSenha = findViewById(R.id.edit_confirmaSenha_id);
        salvaImagem = findViewById(R.id.salva_imagem_user_id);

        this.usuário = (Usuário) getIntent().getSerializableExtra("usuario");

        this.nome.setText(this.usuário.getNome().toString());
        this.campus.setText(this.usuário.getCampus().toString());
        this.email.setText(this.usuário.getEmail().toString());
        this.senha.setText(usuário.getSenha());
        this.confirmaSenha.setText(usuário.getSenha());

        progressBar = (ProgressBar) findViewById(R.id.edit_user_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        firebaseStorage = FirebaseStorage.getInstance().getReference();
    }

    public void confirma(View view) {
        if (nome.getText().toString().equals("") ||
            campus.getText().toString().equals("") ||
            email.getText().toString().equals("") ||
            senha.getText().toString().equals("") ||
            confirmaSenha.getText().toString().equals("")) {

        } else {
            if (email.getText().toString().substring(
                email.getText().toString().length() - 6, //confirma se é terminado em "uff.br"
                email.getText().toString().length()).toLowerCase().equals("uff.br")) {

                if (senha.getText().toString().equals(confirmaSenha.getText().toString())) {
                    usuário.setNome(nome.getText().toString());
                    usuário.setCampus(campus.getText().toString());
                    usuário.setEmail(email.getText().toString());
                    usuário.setSenha(senha.getText().toString());
                    //progressBar.setVisibility(View.VISIBLE);
                    Log.i("lucas", "validou as infos");
                    if(uri != null) {
                        progressBar.setVisibility(View.VISIBLE); //mensagem na tela informando o carregamento
                        cod_imagem = Base64Custom.codifica(uri.toString()); //codifica o uri
                        StorageReference referencia = firebaseStorage.child("ImagensUsuarios").child(cod_imagem);
                        referencia.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { //carrega o arquivo para o Storage
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //verifica se foi corretamente
                            Toast.makeText(EditUserInfo.this, "Imagem salva", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                usuário.setUri(uri.toString());
                                usuário.setCodImagem(cod_imagem);
                                atualizaInfos();
                                }
                            });
                            progressBar.setVisibility(View.INVISIBLE);
                            }

                        });
                    }else{
                        atualizaInfos();
                    }


                } else {
                    mensagemerro.setText("As senhas não são compatíveis!");
                    //Log.i("lucas", "As senhas não são compatíveis!");
                }
            } else {
                mensagemerro.setText("Utilize uma conta da UFF!");
                //Log.i("lucas", "Utilize uma conta da UFF!");
            }

        }
    }



    public void atualizaInfos() {
        autenticacao = ConfiguraçãoFirebase.getAutenticacao();
        dbUser = ConfiguraçãoFirebase.getFirebase().child("usuarios").child(usuário.getId().toString());
        dbUser.updateChildren(usuário.toMap());
        this.finish();
    }

    public void carregarFoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, galery_intent); //chama a galeria do android
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == galery_intent && resultCode == RESULT_OK){
            uri = data.getData(); //pega o caminho para a imagem no telefone do usuario
            salvaImagem.setImageResource(R.drawable.checked);
        }
    }

    public void back(View view) {
        finish();
    }
}
