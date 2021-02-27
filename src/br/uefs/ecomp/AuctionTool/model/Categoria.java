package br.uefs.ecomp.AuctionTool.model;

import java.util.Calendar;

public class Categoria implements Comparable{
    private int quantidade_itens; //Indica quantidade de itens cadastrados em tal categoria;
    private int codigo; //Código de uma categoria;
    private String nome; //Nome da categoria
    private Calendar dataCriacao; //Data de criação

    public Categoria(int codigo, String nome, Calendar dataCriacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Calendar getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Calendar dataCriacao) {
        this.dataCriacao = dataCriacao;
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
              
        Categoria temp = (Categoria) o;
        return temp.nome.compareTo((String) o);
    }   
}
