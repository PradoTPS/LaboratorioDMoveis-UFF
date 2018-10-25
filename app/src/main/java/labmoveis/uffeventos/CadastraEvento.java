package labmoveis.uffeventos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;

import labmoveis.uffeventos.Config.Base64Custom;
import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;
import labmoveis.uffeventos.Objetos.Evento;

public class CadastraEvento extends AppCompatActivity {

    private EditText nome;
    private EditText campus;
    private EditText complementoLocal;
    private EditText resposnavel;
    private EditText data;
    private EditText duracao;
    private EditText vagas;
    private EditText publico;
    private EditText valor;
    private TextView mensagemErro;
    private EditText descricao;


    private ImageButton imgbutton;

    private String cod_imagem;

    private LoginAtual loginAtual;

    private Uri uri;
    private ProgressBar progressBar;
    private DatePicker calendario;
    private StorageReference firebaseStorage;
    private static final int galery_intent = 2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_evento);

        nome = (EditText) findViewById(R.id.cadastro_evento_nome);
        campus = (EditText) findViewById(R.id.cadastro_evento_Campus);
        complementoLocal = (EditText) findViewById(R.id.cadastro_evento_complemento);
        resposnavel = (EditText) findViewById(R.id.cadastro_evento_responsavel);
        duracao = (EditText) findViewById(R.id.cadastro_evento_duracao);
        vagas = (EditText) findViewById(R.id.cadastro_evento_vagas);
        publico = (EditText) findViewById(R.id.cadastro_evento_publico);
        valor = (EditText) findViewById(R.id.cadastro_evento_investimento);
        descricao = (EditText) findViewById(R.id.cadastro_evento_descricao);
        mensagemErro = (TextView) findViewById(R.id.cadastra_evento_mensagem_erro);
        progressBar = (ProgressBar) findViewById(R.id.cadastro_evento_progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        imgbutton = (ImageButton) findViewById(R.id.cadastra_evento_adiciona);


        loginAtual = new LoginAtual(CadastraEvento.this);

        firebaseStorage = FirebaseStorage.getInstance().getReference();

        calendario = findViewById(R.id.calendario_evento_cadastro);

        data = findViewById(R.id.cadastro_evento_data);
    }
    public void calendario_visivel(View view){
        calendario.setVisibility(View.VISIBLE);
    }
    public void calendario_clicked(View view){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int dia = calendario.getDayOfMonth();
        int mes = calendario.getMonth();
        int ano = calendario.getYear();
        data.setText(sdf.format(new Date(ano, mes, dia)));
        calendario.setVisibility(View.GONE);
    }

    public void mudarFoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, galery_intent); //chama a galeria do android

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == galery_intent && resultCode == RESULT_OK){
            uri = data.getData(); //pega o caminho para a imagem no telefone do usuario
            imgbutton.setImageResource(R.drawable.checked);
        }
    }

    public void cadastrar(View view) {
        if(nome.getText().toString().equals("") ||
                complementoLocal.getText().toString().equals("") ||
                campus.getText().toString().equals("") ||
                resposnavel.getText().toString().equals("") ||
                data.getText().toString().equals("") ||
                duracao.getText().toString().equals("") ||
                vagas.getText().toString().equals("") ||
                publico.getText().toString().equals("") ||
                valor.getText().toString().equals("") ||
                descricao.getText().toString().equals("")){ //verifica os campos
            mensagemErro.setText("Preencha todos os campos!");
        }else{
            if(uri != null) {
                progressBar.setVisibility(View.VISIBLE); //mensagem na tela informando o carregamento
                cod_imagem = Base64Custom.codifica(uri.toString()); //codifica o uri
                StorageReference referencia = firebaseStorage.child("ImagensEventos").child(cod_imagem);
                referencia.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { //carrega o arquivo para o Storage
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //verifica se foi corretamente
                        mensagemErro.setText(" ");
                        Toast.makeText(CadastraEvento.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        cadastrarEvento();
                        progressBar.setVisibility(View.INVISIBLE);
                        finish();
                    }

                });
            }else{
                mensagemErro.setText("Escolha uma imagem");
            }
        }

    }

    private void cadastrarEvento() { //cria um evento e o cadastra no BD
        Evento evento = new Evento();
        evento.setNome(nome.getText().toString());
        evento.setCampus(campus.getText().toString());
        evento.setComplementoLocal(complementoLocal.getText().toString());
        evento.setData(data.getText().toString());
        evento.setDescricao(descricao.getText().toString());
        evento.setDuracao(duracao.getText().toString());
        evento.setInvestimento(valor.getText().toString());
        evento.setVagas(vagas.getText().toString());
        evento.setResponsavel(resposnavel.getText().toString());
        evento.setCodImagem(cod_imagem);
        evento.setPublico(publico.getText().toString());
        evento.setId();

        evento.salvar();
        DatabaseReference referencia = ConfiguraçãoFirebase.getFirebase();
        referencia.child("usuarios").child(loginAtual.getId()).child("eventos cadastrados").child(evento.getId()).setValue(evento.getNome()); //coloca a referencia do evento no cadastro do usuario

    }

    public void home(View view) {
        finish();
    }
}
