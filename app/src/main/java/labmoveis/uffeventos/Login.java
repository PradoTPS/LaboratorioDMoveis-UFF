package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import labmoveis.uffeventos.Config.Base64Custom;
import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;
import labmoveis.uffeventos.Config.PreferenciasLogin;
import labmoveis.uffeventos.Objetos.Usuário;


public class Login extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private TextView mensagemErro;
    private Usuário usuario;
    private CheckBox checkbox;
    private PreferenciasLogin preferencias;
    private LoginAtual loginAtual;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.login_email);
        senha = (EditText) findViewById(R.id.login_senha);
        mensagemErro = (TextView) findViewById(R.id.login_mensagem_erro);
        checkbox = (CheckBox) findViewById(R.id.login_checkbox);
        usuario = new Usuário();
        preferencias = new PreferenciasLogin(Login.this);
        loginAtual = new LoginAtual(Login.this);
        email.setText(preferencias.getEmail());
        senha.setText(preferencias.getSenha());
    }


    public void fazerLogin(View view) {
        if(email.getText().toString().equals("") || senha.getText().toString().equals("")){
            mensagemErro.setText("Preencha todos os campos!");
        }else{
            usuario.setEmail(email.getText().toString());
            usuario.setSenha(senha.getText().toString());

            if(checkbox.isChecked()){
                preferencias.salvarPreferencias(usuario.getEmail(), usuario.getSenha());
            }

            AutenticarLogin();
        }
    }

    public void AutenticarLogin(){
        autenticacao = ConfiguraçãoFirebase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mensagemErro.setText(" ");
                    loginAtual.salvarLogin(usuario.getId());
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
