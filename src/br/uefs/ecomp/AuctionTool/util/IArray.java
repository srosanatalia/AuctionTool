package br.uefs.ecomp.AuctionTool.util;

public interface IArray {
    public void add(Comparable data);
    public void set(int index, Comparable data);
    public Comparable get(int index);
    public int contains(Comparable obj);
    public void remove(int index);
    public void remove(Comparable data);
    public int size();
    public boolean isEmpty();
    public Iterator iterator(); 
}
