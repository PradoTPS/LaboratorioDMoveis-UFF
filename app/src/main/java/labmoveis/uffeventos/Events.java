package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Events extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private eventItem aux = new eventItem("Apreciação da praia", "Campus Gragoatá", "descricao","responsavel", "fundo_praia.jpg", "20/10/2018","14h às 17h", "100");
    private eventItem[] myDataset = {aux, aux, aux, aux, aux, aux, aux, aux};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        carregaRecycleView();
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
    }

    public void abrirInfo(View view) {
        Intent abrirInformações = new Intent(Events.this, InformacaoEvento.class);
        startActivity(abrirInformações);
    }
}
