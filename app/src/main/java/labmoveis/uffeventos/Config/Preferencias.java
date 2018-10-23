package labmoveis.uffeventos.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String nomeArquivo = "UFFEventos.preferencias";
    private int mode = 0;
    private SharedPreferences.Editor editor;

    private final String chave_identificador = "identificarUsuarioLogado";
    private final String chave_nome = "nomeUsuarioLogado";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(nomeArquivo, mode);
        editor = preferences.edit();
    }

    public void salvarPreferencias(String identificador, String Nome){
        editor.putString(chave_identificador, identificador);
        editor.putString(chave_nome, Nome);
        editor.commit();
    }

    public String getIdentificador(){
        return preferences.getString(chave_identificador, null);
    }

    public String getNome(){
        return preferences.getString(chave_nome, null);
    }
}
