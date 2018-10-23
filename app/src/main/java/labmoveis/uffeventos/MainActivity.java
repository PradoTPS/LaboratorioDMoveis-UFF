package labmoveis.uffeventos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent it = new Intent();
        System.out.printf("%d", item.getItemId());
        switch (item.getItemId()){
            case(R.id.login):
                it.setAction("OPEN_LOGIN");
                startActivity(it);
                break;
        }
        return true;
    }*/
}
