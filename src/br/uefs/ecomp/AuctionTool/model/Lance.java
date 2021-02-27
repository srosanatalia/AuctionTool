package br.uefs.ecomp.AuctionTool.model;

import java.util.Calendar;

public class Lance implements Comparable{
    private Calendar dataHora; //Data e hora em que um lance foi efetuado
    private float valor; //Valor ofertado
    private Pessoa pessoa; //Pessoa responsável por ofertar determinado lance.

    public Lance(Calendar dataHora, float valor, Pessoa pessoa) {
        this.dataHora = dataHora;
        this.valor = valor;
        this.pessoa = pessoa;
    }
    
    public float getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    } 
    
    @Override
    public int compareTo(Object o) {  //Utilizado para fazer a comparação dos objetos desejados.
        /*
         Por padrão java: 
            se obj1 > obj2 => return > 0; 
            se obj1 < obj2 => return < 0;
            se obj1 == obj2 => return = 0;
        */
        
        Lance temp = (Lance) o;
        if (this.valor > temp.getValor()){
            return 1;
        }
        if (this.valor < temp.getValor()){
            return -1;
        }
        return 0;
    }

}
