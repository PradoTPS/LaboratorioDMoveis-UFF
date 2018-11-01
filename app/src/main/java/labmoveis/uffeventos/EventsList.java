package labmoveis.uffeventos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class EventsList extends RecyclerView.Adapter<EventsList.MyViewHolder> {

    private List<DataSnapshot> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mCardView;
        public MyViewHolder(LinearLayout v) {
            super(v);
            mCardView = v;
        }
    }

    //Construtor - myDataset é o array de dados q vão popular a view. É populada em Events.java
    public EventsList(List<DataSnapshot> myDataset) {
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
        DataSnapshot item = mDataset.get(position);
        // populando as informações de cada item no layout
        TextView nome = holder.mCardView.findViewById(R.id.nome);
        TextView horario = holder.mCardView.findViewById(R.id.horario);
        TextView local = holder.mCardView.findViewById(R.id.local);
        TextView data = holder.mCardView.findViewById(R.id.data);


        nome.setText("Nome: "+item.child("nome").getValue().toString());
        data.setText("Data: "+item.child("data").getValue().toString());
        horario.setText("Horário: "+item.child("duracao").getValue().toString());
        local.setText("Local: "+item.child("campus").getValue().toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
