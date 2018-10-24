package labmoveis.uffeventos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CadastroEvento extends AppCompatActivity {

    private TextView titulo;
    private TextView local;
    private TextView data;
    private TextView hora;
    private TextView publico;
    private TextView desc;

    private Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        this.titulo = findViewById(R.id.title_id);
        this.local = findViewById(R.id.local_id);
        this.data = findViewById(R.id.data_id);
        this.hora = findViewById(R.id.horario_id);
        this.publico = findViewById(R.id.publico_id);
        this.desc = findViewById(R.id.descricao_id);

        //this.btnCadastro = findViewById(R.id.btn_cadastrar);

    }

    public void cadastrarEvento(View v){

    }
}
