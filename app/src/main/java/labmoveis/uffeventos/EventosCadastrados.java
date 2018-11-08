package labmoveis.uffeventos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import labmoveis.uffeventos.Config.LoginAtual;

public class EventosCadastrados extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DataSnapshot> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference firebase = ConfiguraçãoFirebase.getFirebase();
        final String id = new LoginAtual(this).getId();
        System.out.println("ver filhos");
        firebase.child("usuarios").child(id).child("eventos cadastrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String dataID = child.getKey();

                    System.out.println(dataID);
                    ConfiguraçãoFirebase.getFirebase().child("eventos").child(dataID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                myDataset.add(snapshot);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                setContentView(R.layout.activity_eventos_cadastrados);
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

    public void abrirInfo(View view) {
        EventsList tempList = (EventsList) mRecyclerView.getAdapter();

        CardView cdV = (CardView) getParentCardView(view);
        System.out.println(cdV);
        int position = mRecyclerView.getChildAdapterPosition(cdV);

        DataSnapshot item = tempList.getItem(1);
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
}
