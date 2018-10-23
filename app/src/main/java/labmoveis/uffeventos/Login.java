package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Objetos.Usuário;


public class Login extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button btlogin;
    private TextView mensagemErro;
    private Usuário usuario;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.login_email);
        senha = (EditText) findViewById(R.id.login_senha);
        btlogin = (Button) findViewById(R.id.login_login);
        mensagemErro = (TextView) findViewById(R.id.login_mensagem_erro);
        usuario = new Usuário();
    }

    public void fazerLogin(View view) {
        if(email.getText().toString().equals("") && senha.getText().toString().equals("")){
            mensagemErro.setText("Preencha todos os campos!");
        }else{
            usuario.setEmail(email.getText().toString());
            usuario.setSenha(senha.getText().toString());
            AutenticarLogin();
        }
    }

    public void AutenticarLogin(){
        autenticacao = ConfiguraçãoFirebase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mensagemErro.setText("As informações estão corretas!");
                    Intent TelaInicial = new Intent(Login.this, MainActivity.class);
                    startActivity(TelaInicial);
                }else{
                    mensagemErro.setText("As informações estão incorretas!");
                }
            }
        });
    }

    public void cadastrar(View view) {
        Intent abrirCadastro = new Intent(Login.this, Cadastro.class);
        startActivity(abrirCadastro);
    }
}
