package br.uefs.ecomp.AuctionTool.util;

public class ListaEncadeada implements IList{
    
    private No cabeca; //Primeira célula da fila

    private class No{ //Classe referente aos nós da fila;
        private Comparable dado;
        private No proximo;  
        
        public No(Comparable dado){
            this.dado = dado;
        }
        
        public No getProximo(){
            return proximo;
        }
        
        public void setProximo(No proximo){
            this.proximo = proximo;
        }
        
        public Object getDado(){
            return dado;
        }
        
        public void setDado(Comparable dado){
            this.dado = dado;
        }
    } 
    
    private No getNo(int index) { //Método auxiliar retorna o nó associado a posição		

        if (index>= 0 && index< size()) { //Se posição maior ou igual a zero e menor que tamanho da lista.
            No n = cabeca;
            for (int i = 0; i < index; i++) {
                n = n.getProximo();
            }
            return n; 
        }
        return null;
    } 
    @Override
    public boolean isEmpty() {
        return size() == 0; //Se tamanho == 0, lista vazia.
    }

    @Override
    public int size() {
        int contador = 0;
        for (No i = cabeca; i != null; i = i.getProximo()){
        contador++; //obterTamanho tem sempre 1+ tamanho da lista
        }
        return contador; 
    }

    @Override
    public void addBegin(Comparable dado) {
        No temp = cabeca;
        cabeca = new No(dado);
        cabeca.setProximo(temp);
    }

    @Override
    public void addEnd(Comparable dado) {
        if (isEmpty()) { //Se vazia, adiciona o primeiro elemento
            cabeca = new No(dado);
        } 
        else {
        No n = getNo(size() - 1); //Como obterTamanho tem sempre 1+, decrementa em 1 ao adicionar no final da lista.
        n.setProximo(new No(dado)); 
        } 
    }

    @Override
    public Comparable removeBegin() { //Não implementado
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comparable removeEnd() { //Não implemetado
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void remove(Comparable dado) { //Remove um dado realizando uma busca na lista pelo nó que possui o conteúdo igual ao objeto passado como parâmetro.
        No n = cabeca;
        for (int i = 0; n != null; i++) {
            if (n.getDado() != null && n.getDado().equals(dado)){
                remove(i);
                return;
            }
            n = n.getProximo();
        }
    } 

    @Override
    public Comparable get(int index) { //Retorna um objeto de acordo com o index,
        No n = getNo(index);
        if (n != null) {
            return (Comparable) n.getDado();
        }
        return null;
    }
    
    public void set(int index, Comparable dado) { //Altera conteúdo de um determinado nó.
        No n = getNo(index);
        if (n != null) {
            n.setDado(dado);
        }
    }

    @Override
    public Iterator iterator() { //Iterator
        return new ListIterator();
    }
    
    
    private class ListIterator implements Iterator { //Classe iterador.
        private int posicao = 0; //Inicializa posição
        @Override
        public boolean hasNext() { //Se nó acossiado a uma posição for diferente de null, significa que tem próximo.
            return getNo(posicao) != null;
        }
        
        @Override
        public Comparable next() { //Retorna nó de determinada posição
            Comparable data = get(posicao);
            posicao++;
            return  data;
        }
    } 
}
