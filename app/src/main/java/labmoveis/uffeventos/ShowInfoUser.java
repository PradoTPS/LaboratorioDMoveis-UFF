package labmoveis.uffeventos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    Usuário usuario;

    String teste;

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

        LoginAtual loginAtual = new LoginAtual(this);
        String id = loginAtual.getId();

        //ConfiguraçãoFirebase.getAutenticacao();
        //DatabaseReference db = ConfiguraçãoFirebase.getFirebase();
        DatabaseReference db = ConfiguraçãoFirebase.getFirebase();
        Query userAtual = db.child("usuarios").orderByValue().equalTo(id);
        userAtual.addValueEventListener(new ValueEventListener() {
        //ConfiguraçãoFirebase.getFirebase().child("usuarios").child(id).child("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //teste = dataSnapshot.child("nome").getValue(String.class);
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    teste = item.child("nome").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //usuario = new Usuário();
        //usuario.getUsuárioFireBase(this);
        nome_value.setText(teste);

    }

    public void EditarInfos(View view) {
        Intent it = new Intent(ShowInfoUser.this, EditUserInfo.class);
        startActivity(it);
    }
}
