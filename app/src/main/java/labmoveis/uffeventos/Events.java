package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;

public class Events extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private eventItem aux = new eventItem("Apreciação da praia", "Campus Gragoatá", "descricao","responsavel", "fundo_praia.jpg", "20/10/2018","14h às 17h", "100");
    private List<DataSnapshot> myDataset = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference firebase = ConfiguraçãoFirebase.getFirebase();
        firebase.child("eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                        myDataset.add(child);
                }
                setContentView(R.layout.activity_events);
                carregaRecycleView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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

        CardView cdV = (CardView) getParentCardView(view);
        System.out.println(cdV);
        //int position = mRecyclerView.getChildAdapterPosition(cdV);

        DataSnapshot item = tempList.getItem(1);
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

        if(parent instanceof CardView) return (View) parent;

        return getParentCardView((View) parent);
    }
}
