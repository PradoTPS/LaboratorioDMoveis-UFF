package labmoveis.uffeventos.Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;

public class Usuário {

    private String nome;
    private String campus;
    private String email;
    private String senha;
    //o Firebase não aceita um monte de coisas para colocar como nome de um filho
    //por isso coloquei o email numa base, que dai ele vai aceitar
    private String id;

    public Usuário() {
    }

    public void salvar(){ //coloca o hashmap do usuário como um filho de usuarios no BD
        DatabaseReference referencia = ConfiguraçãoFirebase.getFirebase();
        referencia.child("usuarios").child(id).setValue(this);
    }

    @Exclude //cria o hashmap para alocar as informações no BD
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashmapUsuario = new HashMap<>();

        hashmapUsuario.put("Nome", getNome());
        hashmapUsuario.put("Campus", getCampus());
        hashmapUsuario.put("Email", getEmail());
        hashmapUsuario.put("Senha", getSenha());

        return hashmapUsuario;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
