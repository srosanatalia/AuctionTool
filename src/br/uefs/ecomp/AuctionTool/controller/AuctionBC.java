package br.uefs.ecomp.AuctionTool.controller;

import br.uefs.ecomp.AuctionTool.model.Alimento;
import br.uefs.ecomp.AuctionTool.model.Categoria;
import br.uefs.ecomp.AuctionTool.model.Enumeracao;
import br.uefs.ecomp.AuctionTool.model.Imovel;
import br.uefs.ecomp.AuctionTool.model.Item;
import br.uefs.ecomp.AuctionTool.model.Lance;
import br.uefs.ecomp.AuctionTool.model.Pessoa;
import br.uefs.ecomp.AuctionTool.model.Remedio;
import br.uefs.ecomp.AuctionTool.util.Array;
import br.uefs.ecomp.AuctionTool.util.IPriorityQueue;
import br.uefs.ecomp.AuctionTool.util.Iterator;
import br.uefs.ecomp.AuctionTool.util.ListaEncadeada;
import br.uefs.ecomp.AuctionTool.util.PriorityQueue;
import java.util.Calendar;

public class AuctionBC {

    private final ListaEncadeada categoria; //Atributo do tipo ListaEncadeada
    private final Array pessoa; //Atributo do tipo Array
    private final ListaEncadeada item; //Atributo do tipo ListaEncadeada

    private int codigoCategoria; //Gera automaticamente os códigos das categorias;
    private int codigoItem; //Gera autmaticamente os códigos dos itens (Id);

    public int getCodigoItem() { 
        return codigoItem; 
        /*A cada vez que é criado um item, a variável "codigoItem" é incrementada. Desta forma, com este get, 
        é possível saber o código do último item criado. Isto será útil durante a criação dos itens especiais, 
        pois nesta aplicação, após ser criado um item, caso ele tenha um tipo especial, os valores dos 
        atributos adicionais precisarão ser setados, e para isto,  precisaremos fazer uma busca para descobrir qual último item criado.*/
    }
    
    public AuctionBC (){
        this.categoria = new ListaEncadeada ();
        this.pessoa = new Array ();
        this.item = new ListaEncadeada ();
    }
    
    //** CADASTRAR CATEGORIA: Insere objetos do tipo 'categoria' em uma lista encadeada. **
    public int cadastrarCategoria(String nome) { 
        if (verificaCategoriaPorNome(nome) == false){ //Se não existir nenhuma outra categoria com o nome passado como parâmetro, a nova categoria é criada.
            codigoCategoria ++; //Incrementa código
            Calendar dataCriacao =  Calendar.getInstance(); //Instancia a data atual do sistema
            //Cria uma nova categoria e insere no final da lista do tipo Categoria.
            Categoria cadastrarCategoria = new Categoria (codigoCategoria, nome, dataCriacao);
            this.categoria.addEnd(cadastrarCategoria);
            
            return 0; //Se categoria for criada com sucesso, retornará 0;
        }
        return 1; //Se o teste acusar 'true', significa que tal categoria já existe e não poderá ser cadastrada novamente. Retornará 1;
    }
    
    //** VERIFICAR CATEGORIA POR NOME: Utiliza o iterator da lista encadeada para buscar uma categoria pelo nome. **
    private Boolean verificaCategoriaPorNome(String nome) { 
        Iterator procurarCategoria = this.categoria.iterator(); 
        while (procurarCategoria.hasNext()){ //Percorre a lista de categoria.
            Categoria atual = (Categoria) procurarCategoria.next();
            return (atual.getNome().equals(nome)); //Se a categoria existir, retorna true.
        }
        return false;
    }

    //** BUSCAR CATEGORIA POR NOME: Busca uma categoria pelo nome, e caso seja encontrada, é retornado o objeto correspondente. **
    public Categoria buscarCategoriaPorNome(String nome) {  
        Iterator procurarCategoria_2 = this.categoria.iterator();
        while (procurarCategoria_2.hasNext()){ //Percorre a lista de categoria.
            Categoria atual = (Categoria) procurarCategoria_2.next();
            if (atual.getNome().equals(nome)){
                return atual; //Se a categoria existir, retorna um objeto do tipo Categoria.
            }  
        }
        return null;
    }

    //** LISTAR CATEGORIAS: Retorna o iterator da lista encadeada de categoria. **
    public Iterator listarCategorias(){
        return this.categoria.iterator(); 
    }

    //** LISTAR PESSOAS: Retorna o iterator do array de pessoas. **
    public Iterator listarPessoas(){
        pessoa.setArray(sort(pessoa.getArray())); /*Envia o array de pessoas para que o método 'sort()' o ordene. Feito isso, o antigo array 
                                                    de pessoasé subtituido pelo array agora ordneado.*/
        return this.pessoa.iterator(); 
    }

    //** LISTAR ITENS: Retorna o iterator da lista encadeada de itens. **
    public Iterator listarItens(){
        return this.item.iterator();
    }
    
    //** VERIFICACAO - LISTAR LANCES DO PRODUTO: Verifica se um determinado item possui lances para serem listados. **
    public int verificacaoListarLancesProduto (int idItem){
        Item atual = buscarItem (idItem);
        if (atual != null) {
            if (atual.getStatus() != Enumeracao.EM_LEILAO){
                return 3; /*Se o item não estiver em leilão, ainda que já tenha sido vendido, não é viável exibir ao 
                            usuário os lances recebidos. Isto acontece pois quando um lance é vendido, pode ser que 
                            hajam desistência. Ou seja, o histórico de lances não será mostrado integralmente.
                          */
            }
            if (atual.getLancesRecebidos() != 0){
                return 0; //O item possui pelo menos 1 lance.
            }
            return 2; //O item não possui lances.
        }
        return 1; //Item inexistente
    }
    
    //** LISTAR LANCES DE UM PRODUTO: Retorna um iterator da fila de lances. **
    public Iterator listarLancesProduto (int idItem){
        if (buscarItem (idItem) != null) { 
            Item listarItem = buscarItem (idItem);
            return listarItem.getLance().iterator();
        }
        return null;
    }

    //** ALTERAR CATEGORIA: Altera os dados de um determinado item. **
    public int alterarCategoria(String nomeAtual, String novoNome) {
        if(buscarCategoriaPorNome(nomeAtual) != null){
            if (buscarCategoriaPorNome(novoNome) == null) {
               Categoria atual = buscarCategoriaPorNome(nomeAtual);
                atual.setNome(novoNome);
                return 0; /*Se a categoria que o usuário deseja alterar o nome existir, assim como, o novo nome escolhido para ela ainda não estiver 
                            cadastrado, a categoria poderá ser alterada com sucesso.*/
            }
            return 1; //Caso já exista uma categoria com o novo nome escolhido pelo usuário, a categoria não poderá ser alterada.           
        }
        return 2; //Caso a categoria que o usuário deseja alterar o nome, não existir, o retorno 2 indicará tal erro.
    }

    //** REMOVER CATEGORIA: Remove da lista, um objeto do tipo categoria, **
    public int removerCategoria(String nomeCategoria) {
        if (buscarCategoriaPorNome (nomeCategoria) != null){ 
            Categoria categoriaItem = buscarCategoriaPorNome (nomeCategoria);
            if (categoriaItem.getQuantidade_itens() == 0) { 
                this.categoria.remove(categoriaItem);
                return 0; //Se a categoria escolhida existir e não possuir nenhum item cadastrado, será excluida.    
            }
            return 3; //Se a categoria escolhida existir, porém tiver pelo menos um item cadastrado, não será excluida.
        }        
        return 2; //Se a categoria escolhida não existir, retornará 2.
    }

    //** CADASTRAR PESSOA: Insere um novo objeto do tipo pessoa, em um array. **
    public int cadastrarPessoa(String cpf, String nome, String email, String telefone) {
        if (buscarPessoaPorCpf(cpf) == null) {
            Pessoa cadastrarPessoa = new Pessoa (cpf, nome, email, telefone);
            this.pessoa.add(cadastrarPessoa);
            
            return 0; //Se não for encontrada nenhuma pessoa cadastrada no sistema, que possua o cpf informado, o cadastro será feito com sucesso.
        }
        return 1; //Caso o cpf fornecido já estiver cadastrado no sistema, o retorno 1 indicará tal erro.
    }

    //** BUSCAR PESSOA POR CPF: Utliza o iterador de array para buscar uma pessoa através do seu cpf. **
    public Pessoa buscarPessoaPorCpf(String cpf) {
        Iterator buscarPessoa = this.pessoa.iterator();
        while (buscarPessoa.hasNext()){ //Percorre o array de pessoa;
            Pessoa atual = (Pessoa) buscarPessoa.next();
            if (atual.getCpf().equals(cpf)){ //Caso o cpf da posição atual, seja igual ao passado como parâmetro, retorna o objeto atual;
                return atual;
            }
        }
        return null;
    }

    //** CADASTRAR ITEM: Insere novos itens em uma lista encadeada. **
    public int cadastrarItem(String nomeItem, String nomeCategoria, float precoInicial, String cpfDono) {
        if (buscarPessoaPorCpf(cpfDono) != null){
            if (buscarCategoriaPorNome (nomeCategoria) == null){ //Se a categoria escolhida não existir, o item não poderá ser cadastrado e retornará 5;
               return 5;
            }
            codigoItem++;
            Calendar dataCriacao =  Calendar.getInstance(); //Instancia a data atual do sistema
            Pessoa donoItem = buscarPessoaPorCpf(cpfDono);
            donoItem.setQuantidade_itens(donoItem.getQuantidade_itens() + 1); //Incrementa a quantidade de itens que tal pessoa possui;
            Enumeracao status = Enumeracao.CADASTRADO;
            Categoria categoriaItem = buscarCategoriaPorNome (nomeCategoria);
            categoriaItem.setQuantidade_itens(categoriaItem.getQuantidade_itens() + 1); //Incrementa a quantidade de itens que tal categoria possui;
            if (nomeCategoria.equalsIgnoreCase("Alimento")) {
                Alimento novoItem = new Alimento (codigoItem,  nomeItem, dataCriacao, precoInicial, status, donoItem, categoriaItem);
                this.item.addEnd(novoItem);
                return 1; //Se o item do tipo Alimento, for cadastrado com sucesso, retornará 1;
            }
            else if (nomeCategoria.equalsIgnoreCase("Remedio")) {
                Remedio novoItem = new Remedio (codigoItem,  nomeItem, dataCriacao, precoInicial, status, donoItem, categoriaItem);
                this.item.addEnd(novoItem);
                return 2; //Se o item do tipo Remedio, for cadastrado com sucesso, retornará 2;
            }
            else if (nomeCategoria.equalsIgnoreCase("Imovel")) {
                Imovel novoItem = new Imovel (codigoItem,  nomeItem, dataCriacao, precoInicial, status, donoItem, categoriaItem);
                this.item.addEnd(novoItem);
                return 3; //Se o item do tipo Imovel, for cadastrado com sucesso, retornará 3;
            }
            else{ //Cria item genérico.
                Item novoItem = new Item (codigoItem,  nomeItem, dataCriacao, precoInicial, status, donoItem, categoriaItem);
                this.item.addEnd(novoItem);
                return 0; //Se o item genérico (que não pertence a cotegoria especial), for cadastrado com sucesso, retornará 0;
            }
        }
        return 6; //Se o cpf for nulo, o item não será cadastrado. Retornará 6;
    }
    
    //** ALTERAR PESSOA: Altera os dados pessoais de uma determinada pessoa. **
    public int alterarPessoa(String cpf, String nome, String email, String telefone) {
        if (buscarPessoaPorCpf(cpf) != null){
            Pessoa atual = buscarPessoaPorCpf(cpf);
            atual.setNome(nome);
            atual.setEmail(email);
            atual.setTelefone(telefone);
            return 0; //Se pessoa correspondente ao cpf fornecido, existir, poderá ser alterada.
        }
        return 2; //Se pessoa correspondente ao cpf fornecido, não existir, retorno 2 indicará tal erro.
    }

    //** REMOVER PESSOA: Remove um objeto do array de pessoas. **
    public int removerPessoa(String cpf) {
        if (buscarPessoaPorCpf(cpf) != null){
            Pessoa atual = buscarPessoaPorCpf(cpf);
            if (atual.getQuantidade_lances() != 0) {
                return 4; //Se a pessoa já tiver ofertado algum lance, não poderá ser removida; 
            }
            if (atual.getQuantidade_itens() != 0){
                return 3; //Se a pessoa possuir itens cadastrados, não poderá ser removida;
            }
            
            this.pessoa.remove(atual);
            return 0; //Se pessoa não possuir lances ou itens cadastrados, poderá ser removida;
        }
        return 2; //Se pessoa inexistente, retorno 2 indicará tal erro;
    }

    //** EFETUAR LANCE: Insere novos lances em uma fila de prioridades. **
    public int efetuarLance(String cpfPessoa, int idItem, float valorLance) {
        if (buscarPessoaPorCpf(cpfPessoa) != null){
            if (buscarItem (idItem) == null){
                return 1; //Indica que o item não existe;
            }
            Item itemLance = buscarItem (idItem);
            if (itemLance.getStatus() != Enumeracao.EM_LEILAO) {
                return 4; //Se o item não estiver em leilão, não poderá receber lances.
            }
            if (itemLance.getPrecoInicial() > valorLance) {
                return 3; //Indica que o valor ofertado é inferior ao valor mínimo;
            }
            if (itemLance.getCategoriaItem().getNome().equalsIgnoreCase("Alimento")) {
                Alimento itemAlimento = (Alimento) itemLance;
                Calendar dataAtual = Calendar.getInstance();
                if ((itemAlimento.getValidade().compareTo(dataAtual)) < 0){ /*Compara a data atual com a data de validade do alimento. Se compareTo retrnar um valor menor que 0, 
                                                                                significa que a data de validade é inferior a data atual;*/
                    return 5; //Se a data de validade do alimento for menor que a atual, retorna 5, indicando que o alimento está vencido.
                }
            }
            if (itemLance.getCategoriaItem().getNome().equalsIgnoreCase("Remedio")) {
                Remedio itemRemedio = (Remedio) itemLance;
                Calendar dataAtual = Calendar.getInstance();
                if ((itemRemedio.getValidade().compareTo(dataAtual)) < 0){ /*Compara a data atual com a data de validade do alimento. Se compareTo retrnar um valor menor que 0, 
                                                                                significa que a data de validade é inferior a data atual;*/
                    return 5; //Se a data de validade do remedio for menor que a atual, retorna 5, indicando que o remedio está vencido.
                }
            }
            Pessoa pessoaLance = buscarPessoaPorCpf(cpfPessoa);
            pessoaLance.setQuantidade_lances(pessoaLance.getQuantidade_lances()+1); //Incrementa quantidade de lances efetuados por determinada pessoa;
            itemLance.setLancesRecebidos(itemLance.getLancesRecebidos()+1); //Incrementa quantidade de lances que determinado item recebeu;
            itemLance.novoLance(valorLance, pessoaLance);
            return 0; //Lance efetuado com sucesso.
        }
        return 2; //Indica que a pessoa não existe.
    }
    
    //** BUSCAR ITEM: Utiliza o iterator para buscar um item através do seu id. **
    public Item buscarItem (int idItem){
        Iterator buscarItem = this.item.iterator();
        while (buscarItem.hasNext()){ //Percorre a lista de itens;
            Item atual = (Item) buscarItem.next(); 
            if (atual.getCodigo() == idItem){ //Se o código do nó atual for igual ao passado como parâmetro, retorna o objeto atual;
                return atual;
            }
        }
        return null;
    }

    //** ALTERAR ITEM: Altera os dados de um determinado item. **
    public int alterarItem(int idItem, String nomeItem, String nomeCategoria, float precoInicial, String cpfDono) {
        if (buscarItem (idItem) != null){
            Item atual = buscarItem (idItem);
            if (buscarCategoriaPorNome (nomeCategoria) == null){ //Se a categoria escolhida não existir, retornará 5.
               return 5;
            }
            if ((buscarPessoaPorCpf(cpfDono) == null)){
                return 6; //Se não existirem pessoas associadas ao cpf informado, retornará 6.
            }
            if (atual.getStatus() != Enumeracao.CADASTRADO) {
                return 7; //Se status do item for diferente de 'cadastrado', não poderá ser alterado.
            }
            
            atual.setNome(nomeItem);
            
            Pessoa donoItem = buscarPessoaPorCpf(cpfDono);
            atual.setDonoItem(donoItem); 
                
            Categoria categoriaItem = buscarCategoriaPorNome (nomeCategoria);
            atual.setCategoriaItem(categoriaItem);   
            atual.setPrecoInicial(precoInicial);  
            
            return 0; //Indica que todas as alteraçõe foram feitas com sucesso;
        }
        
        return 2; //Caso o item não exista, retornará 2.
    }

    //** INICIAR LEILÃO DO PRODUTO: Altera o status de um item de 'cadastrado' para 'em leilão'. **
    public int iniciarLeilaoDoProduto(int idItem) {
        Item atual = buscarItem (idItem);
        if (atual != null) {
            if (atual.getStatus() != Enumeracao.CADASTRADO) {
                return 2; //Se o item possuir status diferente de cadastrado, não poderá entrar em leilão.
            }
            atual.setStatus(Enumeracao.EM_LEILAO);
            return 0; //Indica que leilão de item foi iniciado com sucesso.
        }
        return 1; //Se item inexistente, retorna 1;
    }
    
    //** ENCERRAR LEILÃO DO PRODUTO: Altera o status de um item de 'em lailão' para 'vendido'. **
    public int encerrarLeilaoDoProduto(int idItem) {
        Item atual = buscarItem (idItem);
        if (atual != null) {
            if (atual.getStatus() != Enumeracao.EM_LEILAO) {
                return 3; //Se o item possuir status diferente de 'em leilao', significa que nem entrou em leilão.
            }
            if (atual.getLancesRecebidos() == 0) {
                return 2; //Se o item não tiver recebido pelo menos 1 lance, o leilão não poderá ser encerrado. 
            }
            Lance vencedorLeilao = (Lance) atual.getLance().peek(); //Pega o objeto que está na cabeça da fila de prioridades (lance mais alto);
            atual.setVencedorLeilao(vencedorLeilao.getPessoa()); //Pessoa presente na cabeça da fila será vencedora do leilão.
            vencedorLeilao.getPessoa().setPontuacao(vencedorLeilao.getPessoa().getPontuacao() + 1); //Incrementa pontuação do vencedor do leilão.
            atual.getDonoItem().setPontuacao(atual.getDonoItem().getPontuacao() + 1); //Incrementa pontuação do dono do item.
            atual.setStatus(Enumeracao.VENDIDO);
            return 0; //Indica que leilão de item foi encerrado com sucesso.
        }
        return 1; //Se item inexistente, retorna 1;
    }

    //** REMOVER ITEM: Remove um objeto da lista de itens. **
    public int removerItem(int idItem) { //Remover item
        Item atual = buscarItem (idItem);
        if (atual!= null){
            if (atual.getStatus() != Enumeracao.CADASTRADO) {
                return 7; //Se status de item for diferente de cadastrado, ele não poderá ser removido; 
            }
            atual.getDonoItem().setQuantidade_itens(atual.getDonoItem().getQuantidade_itens()- 1); //Decrementa a quantidade de itens que o dono do item possui;
            this.item.remove(atual);
            return 0; //Indica que o item foi removido com sucesso;
        }
        return 2; //Se item inexistente, retorna 2;
    }
    
    //** DESISTIR DE ITEM: Permite que o vencedor de um leilão possa desistir do produto se assim desejar. **
    public int desistirItem (int idItem, String cpfCompradorDesistente){ //Desistir de item
        Item atual = buscarItem(idItem);
        if (atual != null) {
            if ((atual.getStatus() == Enumeracao.VENDIDO) || (atual.getStatus() == Enumeracao.DESISTENCIA)) {
                if(atual.getVencedorLeilao().getCpf().equals(cpfCompradorDesistente)){
                    atual.getVencedorLeilao().setPontuacao(atual.getVencedorLeilao().getPontuacao() - 1); //Decrementa pontuação do comprador desistente;
                    atual.setStatus(Enumeracao.DESISTENCIA); //Altera status de item para 'desistencia';
                    atual.getLance().remove(); //Remove a primeira posição da fila de lances (que guarda o atual vencedor). Ao remover, peek passara a guardar o segundo maior lance;
                    if (!atual.getLance().isEmpty()){ //Se a fila não estiver vazia, atribui um novo vencedor ao item. 
                        Lance novoLanceVencedor = (Lance) atual.getLance().peek(); //Pega o segundo maior lance, que agora ocupa a primeira posição da fila.
                        atual.setVencedorLeilao(novoLanceVencedor.getPessoa()); //Atualiza o vencedor do leilão.
                        atual.getVencedorLeilao().setPontuacao(atual.getVencedorLeilao().getPontuacao() + 1); //Incrementa pontuação do novo dono.
                    } 
                    else{ //Se a fila estiver vazia, vencedor do leilão apontará pra null.
                        atual.setVencedorLeilao(null);
                        atual.getDonoItem().setPontuacao(atual.getDonoItem().getPontuacao()-1); //Se não houveram vencedores, então significa que o item não foi vendido. Desta forma a pontuação do dono do item será decrementada;
                    }
                    return 0; //Se desistencia feita com sucesso, retorna 0;
                }
                return 1; //Se o cpf recebido não corresponder ao cpf do vencedor atual de determinado item, retorna 1;
            }
            return 2; //Se o item possuir status diferente de "vendido", retorna 2;
        }
        return 3; //Se item inexistente, retorna 3;
    }
    
    //** VERIFICAÇÃO VENCEDOR LEILÃO: Verifica se houveram vencedores para o leilão. **
    public int verificacaoVencedorLeilao(int idItem){
        Item atual = buscarItem(idItem);
        if (atual != null) {
            if (atual.getVencedorLeilao() != null) {
                return 0; //Se houver vencedores, retorna 0;
            }
            return 2; //Se não houver vencedores, retorna 2;
        }
        return 1; //Se item inexistente, retorna 1;
    }
    
    //** VENCEDOR LEILÃO: Retorna o vencedor do leilão de um determinado item. **
    public Pessoa vencedorLeilao (int idItem){ 
        Item atual = buscarItem(idItem);
        if (atual != null) {
            return atual.getVencedorLeilao();
        }
        return null;
    }
    
    //** VERIFICAÇÃO LISTAR ITENS POR CATEGORIA: Verifica se uma determinada categoria possui itens para listar. **
    public int verificacaoListarItensPorCategoria (String nomeCategoria){
        Categoria categoriaEscolhida = buscarCategoriaPorNome(nomeCategoria);
        if (categoriaEscolhida != null){
            if(categoriaEscolhida.getQuantidade_itens()<1){
                return 1; //Categoria não possui itens para listar;
            }
            return 0; //Categoria possui pelo menos 1 item;
        }
        return 2; //Categoria inexistente;
    }
    
    /*IMPLEMENTAÇÃO DO SORT PARA ORDENAÇÃO DO ARRAY:
        Copia um array para a fila de prioridades, afim de utilizar o método de ordenação da própria fila. 
        Após isso, é devolvido um array já ordenado.
    */

    public Comparable[] sort(Comparable[] data){
        IPriorityQueue queue;
        queue = new PriorityQueue();
        for (Comparable data1 : data) {
            queue.add(data1);
        }
        for(int i=0; i < data.length; i++){
            data[i] = queue.remove();
        }
        return data;
    }
}
