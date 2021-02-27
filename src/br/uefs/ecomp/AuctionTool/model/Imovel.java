package br.uefs.ecomp.AuctionTool.model;

import java.util.Calendar;

public class Imovel extends Item {
    private boolean ocupado; //Indica se o imóvel está ou não ocupado

    public Imovel(int codigo, String nome, Calendar dataCriacao, float precoInicial, Enumeracao status, Pessoa donoItem, Categoria categoriaItem) {
        super(codigo, nome, dataCriacao, precoInicial, status, donoItem, categoriaItem);
        this.ocupado = ocupado;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
        
}
