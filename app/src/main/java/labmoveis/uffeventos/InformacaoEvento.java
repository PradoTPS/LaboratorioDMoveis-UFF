package labmoveis.uffeventos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;

public class InformacaoEvento extends AppCompatActivity {
    private FloatingActionButton btn_marcaInteresse;

    private int interesse;

    private TextView tvNome;
    private TextView tvData;
    private TextView tvHorario;
    private TextView tvLocal;
    private TextView tvPublico;
    private TextView tvResponsavel;
    private TextView tvInvestimento;
    private TextView tvVagas;
    private TextView tvDescricao;
    private LoginAtual loginAtual;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_evento);
        i = getIntent();
        interesse = 0;
        loginAtual = new LoginAtual(InformacaoEvento.this);
        btn_marcaInteresse = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        //TODO: VERIFICAR O INTERESSE NO BANCO DE DADOS


        String nome = i.getStringExtra("NOME");
        tvNome = (TextView) findViewById(R.id.titleView);
        tvNome.setText(nome);

        String data = i.getStringExtra("DATA");
        tvData = (TextView) findViewById(R.id.dataView);
        tvData.setText(data);

        String horario = i.getStringExtra("HORARIO");
        tvHorario = (TextView) findViewById(R.id.hourView);
        tvHorario.setText(horario);

        String local = i.getStringExtra("LOCAL");
        tvLocal = (TextView) findViewById(R.id.localView);
        tvLocal.setText(local);

        String publico = i.getStringExtra("PUBLICO");
        tvPublico = (TextView) findViewById(R.id.publicView);
        tvPublico.setText(publico);

        String responsavel = i.getStringExtra("RESPONSAVEL");
        tvResponsavel = (TextView) findViewById(R.id.responsavelView);
        tvResponsavel.setText(responsavel);

        String investimento = i.getStringExtra("INVESTIMENTO");
        tvInvestimento = (TextView) findViewById(R.id.investimentoView);
        tvInvestimento.setText(investimento);

        String vagas = i.getStringExtra("VAGAS");
        tvVagas = (TextView) findViewById(R.id.vagasView);
        tvVagas.setText(vagas);

        String descricao = i.getStringExtra("DESCRICAO");
        tvDescricao = (TextView) findViewById(R.id.descriptionView);
        tvDescricao.setText(descricao);
    }

    public void marcaInteresse(View view) {
        if(interesse == 0){
            interesse = 1;
            btn_marcaInteresse.setImageResource(R.drawable.clear);
            Toast.makeText(this, "Adicionado aos interesses", Toast.LENGTH_SHORT).show();
        }else{
            interesse = 0;
            btn_marcaInteresse.setImageResource(R.drawable.adicionarfavorito);
            Toast.makeText(this, "Removido dos interesses", Toast.LENGTH_SHORT).show();
        }


    }

    public void home(View view) {
        finish();
    }

    @Override
    protected void onDestroy() { //salva a decisão de marcar interesse ou não ao sair
        super.onDestroy();
        if(interesse == 1) {
            DatabaseReference referencia = ConfiguraçãoFirebase.getFirebase();
            referencia.child("usuarios").child(loginAtual.getId()).child("eventos interesse").child(i.getStringExtra("ID")).setValue(i.getStringExtra("NOME")); //coloca a referencia do evento no cadastro do usuario
        }
            //TODO: REMOVER O INTERESSE DA LISTA

    }
}
