package br.uefs.ecomp.AuctionTool.model;

import java.util.Calendar;

public class Remedio extends Item {
    private boolean precisaReceita; //Indica se o remédio precisa ou não de receita médica
    private Calendar validade; //Data de validade do medicamento

    public Remedio(int codigo, String nome, Calendar dataCriacao, float precoInicial, Enumeracao status, Pessoa donoItem, Categoria categoriaItem) {
        super(codigo, nome, dataCriacao, precoInicial, status, donoItem, categoriaItem);
        this.validade = validade;
        this.precisaReceita = precisaReceita;
    }

    public boolean isPrecisaReceita() {
        return precisaReceita;
    }

    public void setPrecisaReceita(boolean precisaReceita) {
        this.precisaReceita = precisaReceita;
    }

    public Calendar getValidade() {
        return validade;
    }

    public void setValidade(Calendar validade) {
        this.validade = validade;
    }
    

}
