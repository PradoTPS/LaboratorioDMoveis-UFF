package labmoveis.uffeventos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import labmoveis.uffeventos.Objetos.Usuário;

public class EditUserInfo extends AppCompatActivity {

    private EditText nome;
    private EditText campus;
    private EditText email;
    private EditText senha;
    private EditText confirmaSenha;

    private Usuário usuário;

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
        //this.senha.setText(usuário.getSenha());
        //this.confirmaSenha.setText();
    }
}
