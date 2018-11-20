
package labmoveis.uffeventos;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.List;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;

public class EventsList extends RecyclerView.Adapter<EventsList.MyViewHolder> {

    private List<DataSnapshot> mDataset;
    private ProgressBar progressBar;
    private List<Uri> mImageset;
    DatabaseReference firebase = ConfiguraçãoFirebase.getFirebase();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child("ImagensEventos");


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mCardView;

        public MyViewHolder(LinearLayout v) {
            super(v);
            mCardView = v;
        }

    }

    //Construtor - myDataset é o array de dados q vão popular a view. É populada em Events.java
    protected EventsList(List<DataSnapshot> myDataset) {
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

    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DataSnapshot item = mDataset.get(position);
        // populando as informações de cada item no layout
        TextView nome = holder.mCardView.findViewById(R.id.nome);
        TextView horario = holder.mCardView.findViewById(R.id.horario);
        TextView local = holder.mCardView.findViewById(R.id.local);
        TextView data = holder.mCardView.findViewById(R.id.data);


        nome.setText(item.child("nome").getValue().toString());
        data.setText("Data: "+item.child("data").getValue().toString());
        horario.setText("Horário: "+item.child("duracao").getValue().toString());
        local.setText("Local: "+item.child("campus").getValue().toString());

        System.out.println("\n\n\n\n"+item.getKey().toString());

        Glide.with(holder.mCardView.getContext())
                .load(item.child("uri").getValue().toString())
                .into((ImageView) holder.mCardView.findViewById(R.id.imagem_evento));
        }

    public DataSnapshot getItem(int position){
        return mDataset.get(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
