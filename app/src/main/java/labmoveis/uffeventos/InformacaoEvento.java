package labmoveis.uffeventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;

public class InformacaoEvento extends AppCompatActivity {
    private FloatingActionButton btn_marcaInteresse;

    private boolean interesse;
    private int salvoBanco;
    private String userId;
    private String id;
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
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_evento);
        i = getIntent();
        ctx = this;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("ImagensEventos");

        userId = new LoginAtual(this).getId();
        id = i.getStringExtra("ID");
        btn_marcaInteresse = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        salvoBanco = 0;
        btn_marcaInteresse.setImageResource(R.drawable.adicionarfavorito);

        interesse  = i.getBooleanExtra("INTERESSE", false);
        if(interesse){
            salvoBanco = 1;
            btn_marcaInteresse.setImageResource(R.drawable.clear);
        }else{
            System.out.println(i.getStringExtra("KEY"));
            ConfiguraçãoFirebase.getFirebase().child("usuarios").child(userId).child("eventos interesse").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        if(child.getKey().equals(i.getStringExtra("KEY"))){
                            interesse = true;
                            salvoBanco = 1;
                            btn_marcaInteresse.setImageResource(R.drawable.clear);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }

        loginAtual = new LoginAtual(InformacaoEvento.this);

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

        Glide.with(ctx)
                .load(i.getStringExtra("URI"))
                .into((ImageView) findViewById(R.id.imageView));
    }

    public void marcaInteresse(View view) {
        DatabaseReference referencia = ConfiguraçãoFirebase.getFirebase();
        if(!interesse){
            interesse = true;
            btn_marcaInteresse.setImageResource(R.drawable.clear);
            referencia.child("usuarios").child(userId).child("eventos interesse").child(i.getStringExtra("KEY")).setValue(i.getStringExtra("NOME")); //coloca a referencia do evento no cadastro do usuario
            Toast.makeText(this, "Adicionado aos interesses", Toast.LENGTH_SHORT).show();
        }else{
            interesse = false;
            btn_marcaInteresse.setImageResource(R.drawable.adicionarfavorito);
            referencia.child("usuarios").child(userId).child("eventos interesse").child(i.getStringExtra("KEY")).removeValue();
            Toast.makeText(this, "Removido dos interesses", Toast.LENGTH_SHORT).show();
        }
    }

    public void home(View view) {
        finish();
    }

    @Override
    protected void onDestroy() { //salva a decisão de marcar interesse ou não ao sair
        super.onDestroy();
    }
}
