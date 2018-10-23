package labmoveis.uffeventos.Config;

import android.util.Base64;

public class Base64Custom {

    public static String codifica(String texto){ //codifica para base 64
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodifica(String texto){ //decodifica da base 64
        return new String(Base64.decode(texto, Base64.DEFAULT));
    }

}
