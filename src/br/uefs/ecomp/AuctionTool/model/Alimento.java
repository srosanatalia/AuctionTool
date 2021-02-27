package br.uefs.ecomp.AuctionTool.model;

import java.util.Calendar;

public class Alimento extends Item{
    private Calendar validade; //Data de validade do alimento;

    public Alimento(int codigo, String nome, Calendar dataCriacao, float precoInicial, Enumeracao status, Pessoa donoItem, Categoria categoriaItem) {
        super(codigo, nome, dataCriacao, precoInicial, status, donoItem, categoriaItem);
        this.validade = validade;
    }

    public Calendar getValidade() {
        return validade;
    }

    public void setValidade(Calendar validade) {
        this.validade = validade;
    }

   
}
