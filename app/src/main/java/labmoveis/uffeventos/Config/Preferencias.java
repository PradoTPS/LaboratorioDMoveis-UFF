package labmoveis.uffeventos.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String nomeArquivo = "UFFEventos.preferencias";
    private int mode = 0;
    private SharedPreferences.Editor editor;

    private final String chave_email = "emailUsuarioLogado";
    private final String chave_senha = "senhaUsuarioLogado";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(nomeArquivo, mode);
        editor = preferences.edit();
    }

    public void salvarPreferencias(String email, String senha){
        editor.putString(chave_email, email);
        editor.putString(chave_senha, senha);
        editor.commit();
    }

    public String getEmail(){
        return preferences.getString(chave_email, null);
    }

    public String getSenha(){
        return preferences.getString(chave_senha, null);
    }
}
