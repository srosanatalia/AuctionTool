package br.uefs.ecomp.AuctionTool.model;

import br.uefs.ecomp.AuctionTool.util.PriorityQueue;
import java.util.Calendar;

public class Item implements Comparable{
    private int codigo; //Código do item
    private String nome; //Nomedo item
    private Calendar dataCriacao; //Data de criação
    private float precoInicial; //Preço inicial
    private Enumeracao status; //Status do item
    private Pessoa donoItem; //Dono do item (pessoa que efetuou o cadastro do item)
    private Pessoa vencedorLeilao; //Vencedor do leilão do item
    private Categoria categoriaItem; //Categoria que o item pertence
    private int lancesRecebidos; //Quantidade de lances recebidos pelo item
    private PriorityQueue lance; //Atributo do tipo PriorityQueue

    public Item(int codigo, String nome, Calendar dataCriacao, float precoInicial, Enumeracao status, Pessoa donoItem, Categoria categoriaItem) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.precoInicial = precoInicial;
        this.status = status;
        this.donoItem = donoItem;
        this.categoriaItem = categoriaItem;
        this.lance = new PriorityQueue (); //Ao criar item, é criada uma fila de lances.
    }
    
    public void novoLance(float valor, Pessoa donoLance){
        Calendar dataHora =  Calendar.getInstance(); //Instancia a data e hora atual do sistema
        Lance novoLance = new Lance (dataHora, valor, donoLance);
        lance.add(novoLance);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Calendar dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public float getPrecoInicial() {
        return precoInicial;
    }

    public void setPrecoInicial(float precoInicial) {
        this.precoInicial = precoInicial;
    }

    public Enumeracao getStatus() {
        return status;
    }

    public void setStatus(Enumeracao status) {
        this.status = status;
    }

    public Pessoa getDonoItem() {
        return donoItem;
    }

    public void setDonoItem(Pessoa donoItem) {
        this.donoItem = donoItem;
    }

    public Pessoa getVencedorLeilao() {
        return vencedorLeilao;
    }

    public void setVencedorLeilao(Pessoa vencedorLeilao) {
        this.vencedorLeilao = vencedorLeilao;
    }
    
    public Categoria getCategoriaItem() {
        return categoriaItem;
    }

    public void setCategoriaItem(Categoria categoriaItem) {
        this.categoriaItem = categoriaItem;
    }

    public PriorityQueue getLance() {
        return lance;
    }  

    public int getLancesRecebidos() {
        return lancesRecebidos;
    }

    public void setLancesRecebidos(int lancesRecebidos) {
        this.lancesRecebidos = lancesRecebidos;
    }

    @Override
    public int compareTo(Object o) {
                /*
         Por padrão java: 
            se obj1 > obj2 => return > 0; 
            se obj1 < obj2 => return < 0;
            se obj1 == obj2 => return = 0;
        */
              
        Item temp = (Item) o;
        return temp.nome.compareTo((String) o);
    }
            
}
