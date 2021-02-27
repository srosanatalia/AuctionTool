package br.uefs.ecomp.AuctionTool.model;

public class Pessoa implements Comparable{
    private String cpf; //CPF
    private String nome; //Nome da pessoa
    private String email; //Email
    private String telefone; //Telefone
    private int pontuacao; //Pontuação 
    private int quantidade_lances; //Indica quantidade de lances feitos por tal pessoa;
    private int quantidade_itens; //Indica quantidade de itens que tal pessoa possui;

    public Pessoa(String cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getQuantidade_lances() {
        return quantidade_lances;
    }

    public void setQuantidade_lances(int quantidade_lances) {
        this.quantidade_lances = quantidade_lances;
    }

    public int getQuantidade_itens() {
        return quantidade_itens;
    }

    public void setQuantidade_itens(int quantidade_itens) {
        this.quantidade_itens = quantidade_itens;
    }
       
    @Override
    public int compareTo(Object o) {  //Utilizado para fazer a comparação dos objetos desejados.
        /*
         Por padrão java: 
            se obj1 > obj2 => return > 0; 
            se obj1 < obj2 => return < 0;
            se obj1 == obj2 => return = 0;
        */
        
        Pessoa temp = (Pessoa) o;
        if (this.pontuacao > temp.getPontuacao()){
            return 1;
        }
        if (this.pontuacao < temp.getPontuacao()){
            return -1;
        }
        return 0;
    }
}
