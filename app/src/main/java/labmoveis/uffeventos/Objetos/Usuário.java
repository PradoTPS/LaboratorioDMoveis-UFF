package labmoveis.uffeventos.Objetos;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import labmoveis.uffeventos.Config.Base64Custom;
import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;
import labmoveis.uffeventos.Config.LoginAtual;

public class Usuário implements Serializable {

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

        hashmapUsuario.put("nome", getNome());
        hashmapUsuario.put("campus", getCampus());
        hashmapUsuario.put("email", getEmail());
        hashmapUsuario.put("senha", getSenha());

        return hashmapUsuario;
    }


    public String getId() { return id; }

    public void setId(String v) {this.id = v;}

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
        this.id = Base64Custom.codifica(email.toLowerCase());
        this.email = email.toLowerCase();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
