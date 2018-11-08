package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;

public class Events extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private eventItem aux = new eventItem("Apreciação da praia", "Campus Gragoatá", "descricao","responsavel", "fundo_praia.jpg", "20/10/2018","14h às 17h", "100");
    private List<DataSnapshot> myDataset = new ArrayList<>();
    public ImageButton btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
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

        btn = (ImageButton) findViewById(R.id.nav_drawer_btn);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        System.out.println(myDataset.size());
        mAdapter = new EventsList(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void abrirInfo(View view) {
        EventsList tempList = (EventsList) mRecyclerView.getAdapter();

        View cdV = getParentCardView(view);
        System.out.println(cdV);
        int position = mRecyclerView.getChildAdapterPosition(cdV);

        DataSnapshot item = tempList.getItem(position);
        System.out.println(item.child("nome").getValue().toString());

        Intent abrirInformações = new Intent(Events.this, InformacaoEvento.class);
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
                it = new Intent(this, EventosInteresse.class);
                startActivity(it);
            } else if (id == R.id.nav_eventos_cadastrados) {
                it = new Intent(this, EventosCadastrados.class);
                startActivity(it);

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
    }
}
