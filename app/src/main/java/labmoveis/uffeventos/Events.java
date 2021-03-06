package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;
import labmoveis.uffeventos.Config.PreferenciasLogin;
import labmoveis.uffeventos.Objetos.Usuário;

public class Events extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataSnapshot> myDataset = new ArrayList<>();
    public ImageButton btn;
    private AlertDialog carregando;
    private ProgressBar progressBar;

    private SwipeRefreshLayout pullToRefresh;

    Usuário usuário;
    public TextView nomeUser;
    public TextView emailUser;
    public ImageView imagemUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        criaCarregando(); //cria a tela de carregamento

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(true);
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

        populaRecyclerView(); //chama a criação dos cards da list

        btn = (ImageButton) findViewById(R.id.nav_drawer_btn);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        nomeUser = (TextView) navHeader.findViewById(R.id.nav_nome_user_id);
        emailUser = (TextView) navHeader.findViewById(R.id.nav_email_uer_id);
        imagemUser = (ImageView) navHeader.findViewById(R.id.nav_imagem_user_id);


        LoginAtual loginAtual = new LoginAtual(this);
        final String idUser = loginAtual.getId();

        ConfiguraçãoFirebase.getAutenticacao();
        DatabaseReference db = ConfiguraçãoFirebase.getFirebase();
        Query userAtual = db.child("usuarios").child(idUser);
        userAtual.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nome = dataSnapshot.child("nome").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String uri = dataSnapshot.child("uri").getValue().toString();
                if(!uri.equals("")){
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(imagemUser);
                }else{
                    imagemUser.setImageResource(R.drawable.no_image);
                    Toast.makeText(Events.this, "Teste", Toast.LENGTH_SHORT).show();
                }

                nomeUser.setText(nome);
                emailUser.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void populaRecyclerView() {
        DatabaseReference firebase = ConfiguraçãoFirebase.getFirebase();
        firebase.child("eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    myDataset.add(child);
                }
                //setContentView(R.layout.activity_nav_bar);
                carregaRecycleView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void refreshData() {
        myDataset.clear();
        mAdapter.notifyDataSetChanged();
        populaRecyclerView();
        Toast.makeText(this, "Atualizado", Toast.LENGTH_SHORT).show();
    }

    public void cadastrarNovoEvento(View view) {
        Intent abrirCadastroEvento = new Intent(Events.this, CadastraEvento.class);
        startActivity(abrirCadastroEvento);
        carregaRecycleView();
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
        System.out.println(cdV);
        int position = mRecyclerView.getChildAdapterPosition(cdV);

        DataSnapshot item = tempList.getItem(position);


        Intent abrirInformações = new Intent(Events.this, InformacaoEvento.class);
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

        startActivityForResult(abrirInformações, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshData();
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

        if (id == R.id.nav_alterar_cadastro) {
            it = new Intent(Events.this, ShowInfoUser.class);
            startActivity(it);
        } else if (id == R.id.nav_eventos_interesse) {
            it = new Intent(Events.this, EventosInteresse.class);
            startActivity(it);
            finish();
        } else if (id == R.id.nav_eventos_cadastrados) {
            it = new Intent(Events.this, EventosCadastrados.class);
            startActivity(it);
            finish();
        } else if (id == R.id.nav_eventos) {
            carregaRecycleView();
        } else if(id == R.id.nav_sair){
            new LoginAtual(this).apagarLogin();
            new PreferenciasLogin(this).apagarPreferencia();
            it = new Intent(Events.this, Login.class);
            startActivity(it);
            finish();
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
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
