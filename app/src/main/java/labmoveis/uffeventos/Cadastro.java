package labmoveis.uffeventos;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import labmoveis.uffeventos.Config.Base64Custom;
import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Objetos.Usuário;

public class Cadastro extends AppCompatActivity {
    private EditText nome;
    private EditText campus;
    private EditText email;
    private EditText senha;
    private EditText confirmasenha;
    private TextView mensagemerro;
    private Usuário usuario;
    private String id;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome = (EditText) findViewById(R.id.cadastro_nome);
        campus = (EditText) findViewById(R.id.cadastro_campus);
        email = (EditText) findViewById(R.id.cadastro_email);
        senha = (EditText) findViewById(R.id.cadastro_senha);
        confirmasenha = (EditText) findViewById(R.id.cadastro_confirma_senha);
        mensagemerro = (TextView) findViewById(R.id.cadastro_mensagem_erro);
        mensagemerro.setText("");
        usuario = new Usuário();
    }

    public void cadastrar(View view) {
        if(nome.getText().toString().equals("") ||
                campus.getText().toString().equals("") ||
                email.getText().toString().equals("") ||
                senha.getText().toString().equals("") ||
                confirmasenha.getText().toString().equals("")){
            mensagemerro.setText("Preencha todos os campos!");
        }else {
            if(email.getText().toString().substring(
                    email.getText().toString().length()-6, //confirma se é terminado em "uff.br"
                    email.getText().toString().length()).toLowerCase().equals("uff.br")){

                if (senha.getText().toString().equals(confirmasenha.getText().toString())) {
                    usuario.setNome(nome.getText().toString());
                    usuario.setCampus(campus.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    id = Base64Custom.codifica(email.getText().toString()); //cria um código a partir do email
                    usuario.setId(id);

                    cadastraInfos();

                } else {
                    mensagemerro.setText("As senhas não são compatíveis!");
                }
            }else{
                mensagemerro.setText("Utilize uma conta da UFF!");
            }
        }
    }

    private void cadastraInfos() {
        autenticacao = ConfiguraçãoFirebase.getAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),
                usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mensagemerro.setText("Cadastrado com sucesso!");
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.salvar();
                }else{
                    if(task.getException().getMessage().substring(0, 9).equals("The given")){
                        mensagemerro.setText("Esse email já está em uso!");
                    }else if(task.getException().getMessage().substring(0, 17).equals("The email address")) {
                        mensagemerro.setText("A senha deve ter, ao menos, 6 caracteres!");
                    }else{
                        mensagemerro.setText(task.getException().getMessage());
                    }
                }
            }
        });
    }
}
