package br.uefs.ecomp.AuctionTool.util;

public class PriorityQueue implements IPriorityQueue{
   
    private Comparable[] data;   
    private int size; 
    
    public PriorityQueue(){ //Construtor da fila;
        data = new Comparable[50]; 
    } 
    
    @Override
    public boolean isEmpty(){ //Se tamanho da fila for igual a zero, a fila está vazia;
        return size==0;
    }

    @Override
    public int size(){ //Retorna o tamanho da fila.
        return size; 
    }

    @Override
    public void add(Comparable obj){
        if(size == data.length){ //Duplica o tamanho da fila caso necessário;
            Comparable[] temp = new Comparable[size*2];
            System.arraycopy(data, 0, temp, 0, size);
            data = temp;
        } 
        data[size] = obj; //Insere o objeto recebido em uma posição do array;    
        int parent = (size-1)/2;  //Procura o nó pai do objeto inserido, de acordo com o sei index (size).   
        int child = size; //Atribui ao nó 'filho' a posição do array criado.
        while(child>0 && data[parent].compareTo(data[child])<0){ //São realizadas comparações entre o nó pai e o nó filho. O processo é repetido até que não hajam mais trocas.
            swap(parent, child); //Caso nó filho seja maior, é feita a troca de posições.
            child = parent; //Nó filho recebe a posição do nó pai;
            parent = (parent-1)/2; //Nó pai, passa a ser filho;
        }     
        size++; //Tamanho é incrementado.
    }

    @Override
    public Comparable remove(){
        Comparable ret = data[0]; //Remove o item que esta no topo da fila.
        data[0] = data[size-1]; // Faz com o topo, receba o último elemento da esquerda para a direita.
        data[size-1] = null; //Faz com que a última posição agora aponte para null.
        size--; //Decrementa o tamanho.
        int parent = 0; //Declara index do nó 'pai'.
        int child = max(parent*2+1, parent*2+2); //Iguala o nó 'filho' ao retorno do método 'max'.
        
        while(child<size && data[child].compareTo(data[parent])>0){ //São realizadas comparações entre o nó pai e o nó filho. O processo é repetido até que não hajam mais trocas.
            swap(parent, child); //Pai e filho trocam de posição.
            parent = child; //Pai recebe endereço do nó filho.
            child = max(parent*2+1, parent*2+2); //Iguala o nó 'filho' ao retorno do método 'max'.
        }     
        return ret;
    }
    
    
    @Override
    public Comparable peek(){ //Retorna a cabeça da fila.
        return data[0];
    }  
    
    private int max(int i, int j){ /*É chamado no método 'remove()'. Consiste em, após remover o topo da fila 
                                     (posição que guarda o maior valor), atribuir a cabeça um novo valor, realizando 
                                     comparações entre os nós a fim reposicionar os nós da fila. 
                                     */
        if(i<size && j<size){
            return data[i].compareTo(data[j])>0 ? i : j;
        } 
        else if(i<size){
            return i;
        } 
        else if(j<size){
            return j;
        }
        else
            return data.length; 
    } 
    
    private void swap(int p1, int p2){ //Realiza a troca do conteúdo entre duas posições do array.
        Comparable temp = data [p1];
        data[p1] = data[p2];
        data[p2] = temp;
    } 
    
    public Iterator iterator() {
        return new PriorityQueueIterator();
    }
    
    private class PriorityQueueIterator implements Iterator { //Classe iterador.
        private int posicao = 0; //Inicializa posição
        @Override
        public boolean hasNext() { //Se objeto associado a uma posição for diferente de null, significa que tem próximo.
            return data [posicao] != null;
        }
        
        @Override
        public Comparable next() { //Retorna nó de determinada posição
            Comparable atual = data [posicao];
            posicao++;
            return  atual;
        }
    } 

}
