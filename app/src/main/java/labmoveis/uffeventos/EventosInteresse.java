package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;
import labmoveis.uffeventos.Config.PreferenciasLogin;

public class EventosInteresse extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataSnapshot> myDataset = new ArrayList<>();
    private ArrayList<String> eventosIDS = new ArrayList<>();
    private AlertDialog carregando;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String id = new LoginAtual(this).getId();
        setContentView(R.layout.activity_nav_bar);

        criaCarregando();

        //coletando eventos criados pelo usuario
        ConfiguraçãoFirebase.getFirebase().child("usuarios").child(id).child("eventos interesse").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    eventosIDS.add(child.getKey());
                }
                pegaOsEventos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void pegaOsEventos() { //coletando os eventos relativos aos do usuario
        ConfiguraçãoFirebase.getFirebase().child("eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(eventosIDS.contains(child.getKey())){
                        myDataset.add(child);
                    }
                }
                carregaRecycleView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void carregaRecycleView(){

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new EventsList(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        apagaCarregando();
    }

    public void abrirInfo(View view) {
        EventsList tempList = (EventsList) mRecyclerView.getAdapter();

        View cdV = getParentCardView(view);
        int position = mRecyclerView.getChildAdapterPosition(cdV);

        DataSnapshot item = tempList.getItem(position);

        Intent abrirInformações = new Intent(EventosInteresse.this, InformacaoEvento.class);
        abrirInformações.putExtra("KEY", item.getKey());
        abrirInformações.putExtra("ID", item.child("id").getValue().toString());
        abrirInformações.putExtra("NOME", item.child("nome").getValue().toString());
        abrirInformações.putExtra("DATA", item.child("data").getValue().toString());
        abrirInformações.putExtra("HORARIO", item.child("duracao").getValue().toString());
        abrirInformações.putExtra("LOCAL", item.child("campus").getValue().toString());
        abrirInformações.putExtra("PUBLICO", item.child("publico").getValue().toString());
        abrirInformações.putExtra("RESPONSAVEL", item.child("responsavel").getValue().toString());
        abrirInformações.putExtra("INVESTIMENTO", item.child("investimento").getValue().toString());
        abrirInformações.putExtra("VAGAS", item.child("vagas").getValue().toString());
        abrirInformações.putExtra("DESCRICAO", item.child("descricao").getValue().toString());
        abrirInformações.putExtra("IMAGEM", item.child("codImagem").getValue().toString());
        abrirInformações.putExtra("URI", item.child("uri").getValue().toString());
        abrirInformações.putExtra("INTERESSE", false);


        startActivity(abrirInformações);
    }

    private View getParentCardView(View view){

        ViewParent parent = view.getParent();

        if(((View) parent).getId() == R.id.card_view) return (View) parent;

        return getParentCardView((View) parent);
    }

    public void onClickButton(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent it;
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_eventos_interesse) {
            carregaRecycleView();
        } else if (id == R.id.nav_eventos_cadastrados) {
            it = new Intent(EventosInteresse.this, EventosCadastrados.class);
            startActivity(it);
            finish();
        } else if (id == R.id.nav_eventos) {
            it = new Intent(EventosInteresse.this, Events.class);
            startActivity(it);
            finish();
        } else if(id == R.id.nav_sair){
            new LoginAtual(this).apagarLogin();
            new PreferenciasLogin(this).apagarPreferencia();
            it = new Intent(EventosInteresse.this, Login.class);
            startActivity(it);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cadastrarNovoEvento(View view) {
        Intent abrirCadastroEvento = new Intent(EventosInteresse.this, CadastraEvento.class);
        startActivity(abrirCadastroEvento);
    }

    private void criaCarregando() { //Cria a tela de carregamento
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Carregando eventos");
        //cria e instancia o inflater
        LayoutInflater li = getLayoutInflater();
        //inflamos o layout carregando.xml na view
        View view = li.inflate(R.layout.carregando, null);

        //associando a view ao carregando
        builder.setView(view);
        //criando o carregando com o builder
        carregando = builder.create();

        //instanciando o progressBar
        progressBar = (ProgressBar) view.findViewById(R.id.carregando_progressbar);
        //iniciando o progressBar
        progressBar.setVisibility(View.VISIBLE);
        //Exibe
        carregando.show();
    }

    private void apagaCarregando(){
        progressBar.setVisibility(View.INVISIBLE);
        carregando.dismiss();
    }
}
