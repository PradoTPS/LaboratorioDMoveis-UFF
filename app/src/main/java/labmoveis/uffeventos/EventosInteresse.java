package labmoveis.uffeventos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class EventosInteresse extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_interesse);
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
            //TODO: atualizar a página
        } else if (id == R.id.nav_eventos_cadastrados) {
            it = new Intent(EventosInteresse.this, EventosCadastrados.class);
            startActivity(it);
            finish();
        } else if (id == R.id.nav_eventos) {
            it = new Intent(EventosInteresse.this, Events.class);
            startActivity(it);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cadastrarNovoEvento(View view) {
        Intent abrirCadastroEvento = new Intent(EventosInteresse.this, CadastraEvento.class);
        startActivity(abrirCadastroEvento);
    }
}
