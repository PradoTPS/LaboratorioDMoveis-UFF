package labmoveis.uffeventos;

class eventItem {

    //objeto de dados personalizado - deve corresponder com o padrão do BD
    protected String nome, local, descricao, foto, responsavel, data, horario, lotacao;

    protected eventItem(String nome, String local, String descricao,
                        String responsavel, String foto, String data,
                        String horario, String lotacao) {
        try {
            this.nome = nome;
            this.local = local;
            this.lotacao = lotacao;
            this.descricao = descricao;
            this.foto = foto;
            this.data = data;
            this.responsavel = responsavel;
            this.horario = horario;
        }catch (Exception e){
            System.out.println("Erro ao criar item: "+e);
        }
    }
}
