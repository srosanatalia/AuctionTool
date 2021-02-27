package br.uefs.ecomp.AuctionTool.util;


public interface IList {

    public boolean isEmpty();

    public int size();

    public void addBegin(Comparable o);

    public void addEnd(Comparable o);

    public Comparable removeBegin();

    public Comparable removeEnd();

    public Comparable get(int index);

    public Iterator iterator();
}
