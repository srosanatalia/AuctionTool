package br.uefs.ecomp.AuctionTool.util;

public class Array implements IArray{
    
    private Comparable[] array;
    private int next; 
    
    public Array (){
        array = new Comparable [50]; 
        next = 0;
    }

    public Comparable[] getArray() {
        return array;
    }

    public void setArray(Comparable[] array) {
        this.array = array;
    }

    @Override
    public void add(Comparable data) { //Adiciona um item ao array;
        if(next==array.length){
            int size = array.length * 2; //Duplica o tamanho do array caso o tamanho inicial não seja o suficiente.
            Comparable[] temp = new Comparable[size];
            System.arraycopy(array, 0, temp, 0, next);
            array = temp;
        }
        array [next] = data;
        next = next + 1; 
    }

    @Override
    public void set(int index, Comparable data) { //Modifica o conteúdo de uma determinada posição do array;
        if (array[index] != null) {
            array[index] = data;
        }
    }

    @Override
    public Comparable get(int index) { //Retorna o conteúdo de uma determinada posição do array.
        if (array[index] != null) {
            return array [index];
        }
        return null;
    }

    @Override
    public int contains(Comparable obj) { //Retorna index do objeto procurado. Caso não o encontre, retorna -1.
        for (int i = 0; i < next; i++) {
            if (obj.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(int index) { //Remove através de um index.
        if(index >=0 && index < next){
            array[index] = array[next-1]; //Última posição do array assume a posição do objeto a ser removido.
            array[next-1] = null; //Última posição passa a apontar pra null.
            next--; //Next é decrementado, pois o array passa a ter um objeto a menos.
        } 
    }

    @Override
    public void remove(Comparable data) { //Remove através de um objeto.
        int j  = contains (data); //Procura index correspondente ao objeto recebido.
        if (j >= 0) { //Caso o index exista, ele será maior ou igual a zero.
            remove(j); //Chama o método que remove através do index.
        }
    }

    @Override
    public int size() { //Retorna o tamanho
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array[0] == null; //Se primeira posição do array apontar pra null, significa que o vetor está vazio.
    }

    @Override
    public Iterator iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator { //Classe iterador.
        private int posicao = 0; //Inicializa posição
        @Override
        public boolean hasNext() { //Se objeto associado a uma posição for diferente de null, significa que tem próximo.
            return array [posicao] != null;
        }
        
        @Override
        public Comparable next() { //Retorna nó de determinada posição
            Comparable data = array [posicao];
            posicao++;
            return  data;
        }
    } 
    
}
