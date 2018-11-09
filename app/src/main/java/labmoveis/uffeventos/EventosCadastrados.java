package labmoveis.uffeventos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;

public class EventosCadastrados extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataSnapshot> myDataset = new ArrayList<>();
    private ArrayList<String> eventosIDS = new ArrayList<>();
    private int fim = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String id = new LoginAtual(this).getId();
        setContentView(R.layout.activity_eventos_cadastrados);

        System.out.println("ver filhos");
        //coletando eventos criados pelo usuario
        ConfiguraçãoFirebase.getFirebase().child("usuarios").child(id).child("eventos cadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    eventosIDS.add(child.getKey());
                    System.out.println("filho: "+child.getKey());
                }
                pegaOsEventos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void pegaOsEventos() { //coletando os eventos relativos aos do usuario
        System.out.println("ver eventos");
        ConfiguraçãoFirebase.getFirebase().child("eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    System.out.println("filhos"+eventosIDS.toString());
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
        mRecyclerView = (RecyclerView) findViewById(R.id.cadastado_recycle_view);

        mRecyclerView.setHasFixedSize(true);

        // definindo o layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //colocando o adapter
        System.out.println(myDataset.size());
        mAdapter = new EventsList(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void abrirInfoCadastrado(View view) { //TODO: implementar
        EventsList tempList = (EventsList) mRecyclerView.getAdapter();

        View cdV = getParentCardView(view);
        System.out.println(cdV);
        int position = mRecyclerView.getChildAdapterPosition(cdV);

        DataSnapshot item = tempList.getItem(position);
        System.out.println(item.child("nome").getValue().toString());

        Intent abrirInformações = new Intent(EventosCadastrados.this, InformacaoEvento.class);
        abrirInformações.putExtra("NOME", item.child("nome").getValue().toString());
        abrirInformações.putExtra("DATA", item.child("data").getValue().toString());
        abrirInformações.putExtra("HORARIO", item.child("duracao").getValue().toString());
        abrirInformações.putExtra("LOCAL", item.child("campus").getValue().toString());
        abrirInformações.putExtra("PUBLICO", item.child("publico").getValue().toString());
        abrirInformações.putExtra("RESPONSAVEL", item.child("responsavel").getValue().toString());
        abrirInformações.putExtra("INVESTIMENTO", item.child("investimento").getValue().toString());
        abrirInformações.putExtra("VAGAS", item.child("vagas").getValue().toString());
        abrirInformações.putExtra("DESCRICAO", item.child("descricao").getValue().toString());

        startActivity(abrirInformações);
    }

    private View getParentCardView(View view){
        ViewParent parent = view.getParent();

        if(parent instanceof CardView) return (View) parent;

        return getParentCardView((View) parent);
    }

    public void cadastrarNovoEvento(View view) {
        Intent abrirCadastroEvento = new Intent(EventosCadastrados.this, CadastraEvento.class);
        startActivity(abrirCadastroEvento);
        carregaRecycleView();
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
            it = new Intent(EventosCadastrados.this, EventosInteresse.class);
            startActivity(it);
            finish();
        } else if (id == R.id.nav_eventos_cadastrados) {
            carregaRecycleView();
        } else if (id == R.id.nav_eventos) {
            it = new Intent(EventosCadastrados.this, Events.class);
            startActivity(it);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
