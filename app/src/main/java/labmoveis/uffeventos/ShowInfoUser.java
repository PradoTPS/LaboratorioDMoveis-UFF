package labmoveis.uffeventos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;
import labmoveis.uffeventos.Objetos.Usuário;

public class ShowInfoUser extends AppCompatActivity {

    TextView nome_title;
    TextView nome_value;
    TextView campus_title;
    TextView campus_value;
    TextView email_title;
    TextView email_value;
    ImageView imagem_value;

    Usuário usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_user);

        nome_title = findViewById(R.id.nome_title_id);
        nome_value = findViewById(R.id.nome_user_id);
        campus_title = findViewById(R.id.campus_title_id);
        campus_value = findViewById(R.id.campus_user_id);
        email_title = findViewById(R.id.email_title_id);
        email_value = findViewById(R.id.email_user_id);
        imagem_value = findViewById(R.id.imagem_user_id);

        LoginAtual loginAtual = new LoginAtual(this);
        final String id = loginAtual.getId();

        DatabaseReference db = ConfiguraçãoFirebase.getFirebase();
        Query userAtual = db.child("usuarios").child(id);
        userAtual.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nome = dataSnapshot.child("nome").getValue().toString();
                String campus = dataSnapshot.child("campus").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String senha = dataSnapshot.child("senha").getValue().toString();
                String uri = dataSnapshot.child("uri").getValue().toString();
                nome_value.setText(nome);
                campus_value.setText(campus);
                email_value.setText(email);
                if(!uri.equals("")){
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(imagem_value);
                }else{
                    imagem_value.setImageResource(R.drawable.no_image);
                    Toast.makeText(ShowInfoUser.this, "Teste", Toast.LENGTH_SHORT).show();
                }

                salvaUser(id, nome, campus, email, senha, uri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void EditarInfos(View view) {
        Intent it = new Intent(ShowInfoUser.this, EditUserInfo.class);
        it.putExtra("usuario", this.usuario);
        startActivity(it);
    }

    public void salvaUser(String id, String nm, String cmps, String email, String pssd, String uri) {
        this.usuario = new Usuário();
        this.usuario.setId(id);
        this.usuario.setNome(nm);
        this.usuario.setCampus(cmps);
        this.usuario.setEmail(email);
        this.usuario.setSenha(pssd);
        this.usuario.setUri(uri);
        Log.i("lucas", usuario.getNome());
    }

    public void back(View view) {
        finish();
    }
}
