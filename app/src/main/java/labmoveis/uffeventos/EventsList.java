package labmoveis.uffeventos;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EventsList extends RecyclerView.Adapter<EventsList.MyViewHolder> {

    private eventItem[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public MyViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }

    //Construtor - myDataset é o array de dados q vão popular a view. É populada em Events.java
    public EventsList(eventItem[] myDataset) {
        mDataset = myDataset;
    }

    // cria a view e o manipulador da mesma
    @Override
    public EventsList.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_eventscard, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // populando as informações de cada item no layout
        TextView nome = holder.mCardView.findViewById(R.id.nome);
        TextView descricao = holder.mCardView.findViewById(R.id.descricao);
        TextView local = holder.mCardView.findViewById(R.id.local);

        nome.setText(mDataset[position].nome);
        descricao.setText(mDataset[position].descricao);
        local.setText(mDataset[position].local);

        //definindo acao do botao mais detalhes
        Button bt_mais = holder.mCardView.findViewById(R.id.maisdetalhes);
        bt_mais.setOnClickListener(new View.OnClickListener() {
            //Adicionar atividade que será executada.
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                //it.setAction("");
                //startActivity(it);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
