package labmoveis.uffeventos.Config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguraçãoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticaçãoFirebase;

    public static DatabaseReference getFirebase(){
        if(referenciaFirebase == null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    public static FirebaseAuth getAutenticacao(){
        if(autenticaçãoFirebase == null){
            autenticaçãoFirebase = FirebaseAuth.getInstance();
        }
        return autenticaçãoFirebase;
    }
}
