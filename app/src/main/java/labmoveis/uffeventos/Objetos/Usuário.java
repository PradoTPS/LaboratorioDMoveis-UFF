package labmoveis.uffeventos.Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import labmoveis.uffeventos.Config.Base64Custom;
import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;

public class Usuário {

    private String nome;
    private String campus;
    private String email;
    private String senha;
    private ArrayList<Evento> eventosCadastrados;
    private ArrayList<Evento> eventosInteresse;
    //o Firebase não aceita um monte de coisas para colocar como nome de um filho
    //por isso coloquei o email numa base, que dai ele vai aceitar
    private String id;

    public Usuário() {
        eventosCadastrados = new ArrayList<>();
        eventosInteresse = new ArrayList<>();
        Evento evento = new Evento();
        evento.setNome("Evento1");
        eventosInteresse.add(evento);
        eventosCadastrados.add(evento);
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
        hashmapUsuario.put("eventos cadastrados", getEventosCadastrados());
        hashmapUsuario.put("eventos interesses", getEventosInteresse());

        return hashmapUsuario;
    }

    public ArrayList<Evento> getEventosInteresse() {
        return eventosInteresse;
    }

    public void adicionarEventoInteresse(Evento evento) {
        if(!this.eventosInteresse.contains(evento)) {
            this.eventosInteresse.add(evento);
        }
    }

    public ArrayList<Evento> getEventosCadastrados() {
        return eventosCadastrados;
    }

    public void adicionarEventoCadastrado(Evento evento) {
        if(!this.eventosCadastrados.contains(evento)) {
            this.eventosCadastrados.add(evento);
        }
    }

    public String getId() { return id; }

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
