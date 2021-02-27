package br.uefs.ecomp.AuctionTool.util;

public interface IStack {
    

    public int size();

    public boolean isEmpty();
    
    public Comparable removeTop();
    
    public void addTop(Comparable obj);
    
    public Comparable getTop();
}

