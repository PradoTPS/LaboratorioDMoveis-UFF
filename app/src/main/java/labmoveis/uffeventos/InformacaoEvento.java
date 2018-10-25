package labmoveis.uffeventos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class InformacaoEvento extends AppCompatActivity {
    private FloatingActionButton btn_marcaInteresse;
    private int interesse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_evento);
        btn_marcaInteresse = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        interesse = 0;
    }

    public void marcaInteresse(View view) {
        if(interesse == 0){
            interesse = 1;
            btn_marcaInteresse.setImageResource(R.drawable.clear);
            Toast.makeText(this, "Adicionado aos interesses", Toast.LENGTH_SHORT).show();
        }else{
            interesse = 0;
            btn_marcaInteresse.setImageResource(R.drawable.checked);
            Toast.makeText(this, "Removido dos interesses", Toast.LENGTH_SHORT).show();
        }


    }

    public void home(View view) {
        finish();
    }
}
