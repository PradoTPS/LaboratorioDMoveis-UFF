package labmoveis.uffeventos;

import java.util.Date;

class eventItem {

    //objeto de dados personalizado - deve corresponder com o padrão do BD
    protected String nome, local, descricao, foto, responsavel;
    protected Date data;
    protected int duracao, lotacao;

    protected eventItem(String nome, String local, String descricao,
                        String responsavel, String foto, Date data,
                        int duracao, int lotacao) {
        try {
            this.nome = nome;
            this.local = local;
            this.lotacao = lotacao;
            this.descricao = descricao;
            this.foto = foto;
            this.data = data;
            this.responsavel = responsavel;
            this.duracao = duracao;
        }catch (Exception e){
            System.out.println("Erro ao criar item: "+e);
        }
    }
}
