package labmoveis.uffeventos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.BreakIterator;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Objetos.Usuário;

public class EditUserInfo extends AppCompatActivity {

    public EditText nome;
    public EditText campus;
    public EditText email;
    public EditText senha;
    public EditText confirmaSenha;
    private ProgressBar progressBar;

    public Usuário usuário;
    public BreakIterator mensagemerro;

    public FirebaseAuth autenticacao;
    public DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        this.nome = findViewById(R.id.edit_nome_id);
        this.campus = findViewById(R.id.edit_campus_id);
        this.email = findViewById(R.id.edit_email_id);
        this.senha = findViewById(R.id.edit_senha_id);
        this.confirmaSenha = findViewById(R.id.edit_confirmaSenha_id);

        this.usuário = (Usuário) getIntent().getSerializableExtra("usuario");

        this.nome.setText(this.usuário.getNome().toString());
        this.campus.setText(this.usuário.getCampus().toString());
        this.email.setText(this.usuário.getEmail().toString());
        this.senha.setText(usuário.getSenha());
        this.confirmaSenha.setText(usuário.getSenha());

        progressBar = (ProgressBar) findViewById(R.id.edit_user_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
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
                    atualizaInfos();

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
}
