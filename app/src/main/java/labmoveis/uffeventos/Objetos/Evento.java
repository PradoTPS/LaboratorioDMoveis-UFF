package labmoveis.uffeventos.Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import labmoveis.uffeventos.Config.ConfiguraçãoFirebase;

public class Evento {
    private String id;
    private String nome;
    private String complementoLocal;
    private String campus;
    private String descricao;
    private String responsavel;
    private String data;
    private String duracao;
    private String vagas;
    private String publico;
    private String investimento;
    private String codImagem;

    public Evento() {
    }


    public void salvar(){ //coloca o hashmap do usuário como um filho de usuarios no BD
        DatabaseReference referencia = ConfiguraçãoFirebase.getFirebase();
        referencia.child("eventos").child(id).setValue(this);
    }

    @Exclude //cria o hashmap para alocar as informações no BD
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashmapEvento = new HashMap<>();

        hashmapEvento.put("id", getId());
        hashmapEvento.put("nome", getNome());
        hashmapEvento.put("complementoLocal", getComplementoLocal());
        hashmapEvento.put("campus", getCampus());
        hashmapEvento.put("descricao", getDescricao());
        hashmapEvento.put("resposavel", getResponsavel());
        hashmapEvento.put("data", getData());
        hashmapEvento.put("duracao", getDuracao());
        hashmapEvento.put("vagas", getVagas());
        hashmapEvento.put("publico", getPublico());
        hashmapEvento.put("investimento", getInvestimento());
        hashmapEvento.put("codigoImagem", getCodImagem());
        return hashmapEvento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodImagem() {
        return codImagem;
    }

    public void setCodImagem(String codImagem) {
        this.codImagem = codImagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComplementoLocal() {
        return complementoLocal;
    }

    public void setComplementoLocal(String complementoLocal) {
        this.complementoLocal = complementoLocal;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getVagas() {
        return vagas;
    }

    public void setVagas(String vagas) {
        this.vagas = vagas;
    }

    public String getPublico() {
        return publico;
    }

    public void setPublico(String publico) {
        this.publico = publico;
    }

    public String getInvestimento() {
        return investimento;
    }

    public void setInvestimento(String investimento) {
        this.investimento = investimento;
    }
}