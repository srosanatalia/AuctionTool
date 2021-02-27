package br.uefs.ecomp.AuctionTool.util;


public interface IPriorityQueue {
  
    public boolean isEmpty();

    public int size();

    public void add(Comparable obj);

    public Comparable remove();

    public Comparable peek();        
}
