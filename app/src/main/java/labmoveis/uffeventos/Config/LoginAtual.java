package labmoveis.uffeventos.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginAtual {

    private Context context;
    private SharedPreferences preferences;
    private String nomeArquivo = "UFFEventos.loginAtual";
    private int mode = 0;
    private SharedPreferences.Editor editor;

    private final String chave_id = "idUsuárioLogado";

    public LoginAtual(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(nomeArquivo, mode);
        editor = preferences.edit();
    }

    public void salvarLogin(String id){
        editor.putString(chave_id, id);
        editor.commit();
    }

    public String getId(){
        return preferences.getString(chave_id, null);
    }


}
