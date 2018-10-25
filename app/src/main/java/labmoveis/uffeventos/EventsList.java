package labmoveis.uffeventos;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventsList extends RecyclerView.Adapter<EventsList.MyViewHolder> {

    private eventItem[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mCardView;
        public MyViewHolder(LinearLayout v) {
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
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_eventscard, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // populando as informações de cada item no layout
        TextView nome = holder.mCardView.findViewById(R.id.nome);
        TextView horario = holder.mCardView.findViewById(R.id.horario);
        TextView local = holder.mCardView.findViewById(R.id.local);
        TextView data = holder.mCardView.findViewById(R.id.data);

        nome.setText(mDataset[position].nome);
        data.setText("Data: "+mDataset[position].data);
        horario.setText("Horário: "+mDataset[position].horario);
        local.setText("Local: "+mDataset[position].local);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
