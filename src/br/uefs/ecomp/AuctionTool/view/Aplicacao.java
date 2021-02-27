/*******************************************************************************
Autor: NATÁLIA SILVA ROSA
Componente Curricular: Algoritmos II
Concluido em: 08/11/2017
Declaro que este codigo foi elaborado por mim de forma individual e nao contem nenhum
trecho de codigo de outro colega ou de outro autor, tais como provindos de livros e
apostilas, e paginas ou documentos eletronicos da Internet. Qualquer trecho de codigo
de outra autoria que não a minha esta destacado com uma citacao para o autor e a fonte
do codigo, e estou ciente que estes trechos nao serao considerados para fins de avaliacao.
******************************************************************************************/
package br.uefs.ecomp.AuctionTool.view;

import br.uefs.ecomp.AuctionTool.controller.AuctionBC;
import br.uefs.ecomp.AuctionTool.model.Alimento;
import br.uefs.ecomp.AuctionTool.model.Categoria;
import br.uefs.ecomp.AuctionTool.model.Imovel;
import br.uefs.ecomp.AuctionTool.model.Item;
import br.uefs.ecomp.AuctionTool.model.Lance;
import br.uefs.ecomp.AuctionTool.model.Pessoa;
import br.uefs.ecomp.AuctionTool.model.Remedio;
import br.uefs.ecomp.AuctionTool.util.Console;
import br.uefs.ecomp.AuctionTool.util.Iterator;
import java.io.IOException; 
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Aplicacao {
    public static void main (String [] args){
        
        AuctionBC objeto = new AuctionBC(); //Objeto que dará acesso a controller.
        
        // ** Criação automática das categorias para os itens especiais: **
        objeto.cadastrarCategoria("Alimento");
        objeto.cadastrarCategoria("Remedio");
        objeto.cadastrarCategoria("Imovel");
        
        int opcao = 0; //Controla as opções do menu.
        int modo = 0; //Controla o tipo de acesso. modo == 0: ADM ; modo == 1: usuário;
        boolean retorno = true; //Controla o retorno ao menu principal.
        boolean retornoModo = true; //Controla o retono as opções de modo de acesso (adm ou usuário);
        String codigoAcessoADM = null; //OBS: Para acessar em modo ADM o código deverá ser igual à "JamylleS";
        String cpfUsuario = null;
        
        do{ 
            // ** MENU INICIAL **
            System.out.println("** BEM VINDO AO AUCTION TOOL ** \n (0) Modo administrador; \n (1) Modo usuário;");
            do{
                try {modo = Console.readInt();} catch (IOException | NumberFormatException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                if((modo != 0) && (modo != 1)){ System.out.println("** Insira uma opção válida! **"); }
            }while ((modo != 0) && (modo != 1));

            //  ** MODO ADMINISTRADOR **
            if (modo == 0){ 
                do{
                    System.out.print("Insira o código de acesso: ");
                    try {codigoAcessoADM = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                    if (!(codigoAcessoADM.equalsIgnoreCase("JamylleS"))){
                        System.out.println("** Código de acesso incorreto! **");
                        int opcao_3 = 0;
                        do{
                            System.out.println("Deseja inserir um novo código?\n (0) SIM\n (1) NÃO");
                            try {opcao_3 = Console.readInt();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                            if ((opcao_3 != 0) && (opcao_3 != 1)){System.out.println("** Insira uma opção válida! **");}
                        }while ((opcao_3 != 0) && (opcao_3 != 1));
                        if(opcao_3 == 1){
                            System.out.println("** Muito obrigado por utilizar o Auction Tool! **");
                            System.exit(0); //Encerra a execução do programa;
                        }
                    }
                }while(!(codigoAcessoADM.equalsIgnoreCase("JamylleS"))); //Enquanto não houver uma entrada válida, o laço irá se repetir;
            }

            //  ** MODO USUÁRIO **
            else if (modo == 1) { 
                System.out.print("Insira o seu CPF: ");
                try {cpfUsuario = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                if (!(objeto.buscarPessoaPorCpf(cpfUsuario) != null)){
                    int opcao_2 = 0;
                    do{
                        System.out.println("** CPF não cadastrado no sistema! ** \nDeseja se cadastrar?\n (0) SIM\n (1) NÃO");
                        try {opcao_2 = Console.readInt();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        if ((opcao_2 != 0) && (opcao_2 != 1)){System.out.println("** Insira uma opção válida! **");}
                    }while ((opcao_2 != 0) && (opcao_2 != 1)); //Enquanto não houver uma entrada válida, o laço irá se repetir;

                    if(opcao_2 == 0){ //Caso o CPF não esteja cadastrado, é dado ao usuário a opção de realizar o cadastro.
                        String nomePessoa = null, emailPessoa = null, telefonePessoa = null;
                        System.out.print("CPF:");
                        try {cpfUsuario = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Nome:");
                        try {nomePessoa = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Email:");
                        try {emailPessoa = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Telefone:");
                        try {telefonePessoa = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        int retornoCadastrarPessoa = objeto.cadastrarPessoa(cpfUsuario, nomePessoa, emailPessoa, telefonePessoa);

                        switch (retornoCadastrarPessoa){
                            case 0:
                                System.out.println("** Cadastro realizado com sucesso! **\n");
                            break;

                            case 1:
                                System.out.println("** CPF já cadastrado! **");
                            break;
                        }
                    } 
                    else {
                        System.out.println("** Muito obrigado por utilizar o Auction Tool! **");
                        System.exit(0); //Programa é encerrado.
                    }
                }
            }

            do{
                if(modo == 0){menuAdm();} //Caso modo == 0, é exibido o menu administrador.
                else {menuUsuario();} //Senão, é exibido o menu usuário.
                try {opcao = Console.readInt();} catch (IOException | NumberFormatException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}

                switch( opcao )
                {    
                    // ** INSERIR CATEGORIA **
                    case 1: 
                        String nomeCategoriaCadastrar = null;
                        System.out.print("Insira o nome da categoria: ");
                        try {nomeCategoriaCadastrar = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        int retornoInserirCategoria = objeto.cadastrarCategoria(nomeCategoriaCadastrar); //Guarda o retorno do método "cadastrarCategoria". De acordo com o inteiro retornado, trata possíveis erros na aplicação.
                        switch (retornoInserirCategoria){
                            case 0:
                                System.out.println("** Categoria criada com sucesso! **");
                            break;
                            case 1:
                                System.out.println("** Esta categoria já existe! **");
                            break;
                        }
                    break;

                    // ** ALTERAR CATEGORIA **
                    case 2: 
                        String nomeCategoriaAlterar = null;
                        String nomeCategoriaNovo = null;
                        System.out.print("Insira o nome da categoria que deseja alterar: ");
                        try {nomeCategoriaAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira o novo nome da categoria: ");
                        try {nomeCategoriaNovo = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        int retornoAlterarCategoria = objeto.alterarCategoria(nomeCategoriaAlterar, nomeCategoriaNovo);
                        switch (retornoAlterarCategoria){
                            case 0:
                                System.out.println("** Todas as alterações foram feitas com sucesso! **");
                            break;
                            case 1:
                                System.out.println("** Já existe uma categoria registrada com este nome! **");
                            break;
                            case 2:
                                System.out.println("** A categoria escolhida para alterações, não existe! **");
                            break;
                        }
                    break;

                    // ** REMOVER CATEGORIA **
                    case 3: 
                        String nomeCategoriaRemover = null;
                        System.out.print("Insira o nome da categoria que deseja remover: ");
                        try {nomeCategoriaRemover = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        int retornoRemoverCategoria = objeto.removerCategoria(nomeCategoriaRemover);
                        switch (retornoRemoverCategoria){
                            case 0:
                                System.out.println("** Categoria removida com sucesso! **");
                            break;
                            case 2:
                                System.out.println("** Categoria inexistente! **");
                            break;
                            case 3:
                                System.out.println("** Esta categoria possui itens cadastrados. Não poderá ser removida! **");
                            break;
                        }
                    break;

                    // ** LISTAR CATEGORIAS **
                    case 4:

                        Iterator listarcategoria = objeto.listarCategorias();
                        while (listarcategoria.hasNext()){
                            Categoria atual = (Categoria) listarcategoria.next();
                            System.out.print("\nCategoria:"+atual.getNome());
                            System.out.print(" (cód.: "+atual.getCodigo()+")");
                        }
                    break;

                    // ** INSERIR ITEM **
                    case 5:
                        String nomeItemCadastrar = null,nomeCategoriaItem = null, cpfDonoItem = null;
                        float precoInicial = 0;

                        System.out.print("Insira o nome do Item: ");
                        try {nomeItemCadastrar = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira o nome da categoria que o item pertence: ");
                        try {nomeCategoriaItem = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira o valor inicial:");
                        try {precoInicial = Console.readFloat();} catch (IOException | NumberFormatException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira o CPF do dono do Item: ");
                        try {cpfDonoItem = Console.readString();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                        
                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            if (!(cpfDonoItem.equals(cpfUsuario))){
                                System.out.println("** O CPF inserido não corresponde ao CPF da conta em que você está logado. Item não cadastrado! **");
                                break;
                            }
                        }

                        int retornoInserirItem = objeto.cadastrarItem(nomeItemCadastrar, nomeCategoriaItem, precoInicial, cpfDonoItem);

                        switch (retornoInserirItem){
                            case 0:
                                System.out.println("** Item cadastrado com sucesso! **");
                            break;

                            /* 'getCodigoItem()' guarda o código do último item criado. Ao entrar nos casos 1, 2 ou 3, será feita uma busca pelo último objeto criado
                                para que se possa setar o estado de alguns dos seus atributos. Por exemplo, ao cadastrar um novo item e indicar que ele pertencerá a
                                categoria 'Alimento', inicialmente na Controller ele será criado da mesma forma que um item genérico (que não possui tipo especial),
                                pois os contrutores de um item normal e de um item especial são iguais.
                                Somente quando o método 'cadastrarItem' mandar um retorno para a view, poderemos saber qual tipo de objeto criado, e qual manipulação
                                será necessária. 
                            */

                            case 1:
                                Alimento itemAlimento = (Alimento) objeto.buscarItem(objeto.getCodigoItem());

                                int dia = 0, mes = 0, ano = 0;
                                System.out.print("Insira a data de validade do alimento: \n  Dia (formato: dd) - ");
                                try {dia = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                                System.out.print("  Mês (formato: MM) - ");
                                try {mes = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                                System.out.print("  Ano (formato: yyyy) - ");
                                try {ano = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                                Calendar dataValidade =  Calendar.getInstance(); //Instancia a data atual do sistema e modifica dia, mês e ano.
                                dataValidade.set(Calendar.DAY_OF_MONTH, dia); 
                                dataValidade.set(Calendar.MONTH, mes);
                                dataValidade.set(Calendar.YEAR, ano);

                                itemAlimento.setValidade(dataValidade);
                                System.out.println("** Item cadastrado com sucesso! **");
                            break;
                            case 2:
                                Remedio itemRemedio = (Remedio) objeto.buscarItem(objeto.getCodigoItem());
                                int dia_2 = 0, mes_2 = 0, ano_2 = 0, receita = 0;
                                boolean precisaReceita;
                                System.out.print("Insira a data de validade do remédio: \n  Dia (formato: dd) - ");
                                try {dia_2 = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                                System.out.print("  Mês (formato: MM) - ");
                                try {mes_2 = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                                System.out.print("  Ano (formato: yyyy) - ");
                                try {ano_2 = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                                Calendar dataValidade_2 =  Calendar.getInstance(); //Instancia a data atual do sistema e modifica dia,mês e ano.
                                dataValidade_2.set(Calendar.DAY_OF_MONTH, dia_2);
                                dataValidade_2.set(Calendar.MONTH, mes_2);
                                dataValidade_2.set(Calendar.YEAR, ano_2);

                                do{
                                    System.out.println("O medicamento precisa de receita? \n (0) - SIM; \n (1) - NÃO");
                                    try {receita = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                                    if ((receita != 0) && (receita != 1)){
                                        System.out.println("** Insira uma opção válida! **");
                                    }
                                } while ((receita != 0) && (receita != 1));
                                precisaReceita = receita == 0; //Se usuário inserir '0', significa que o medicamento precisa de receita. Variável 'precisaReceita' recebe true;

                                itemRemedio.setValidade(dataValidade_2);
                                itemRemedio.setPrecisaReceita(precisaReceita);

                                System.out.println("** Item cadastrado com sucesso! **");
                            break;
                            case 3:
                                Imovel itemImovel = (Imovel) objeto.buscarItem(objeto.getCodigoItem());
                                int ocupacao = 0;
                                boolean estaOcupado;
                                do{
                                    System.out.println("O imóvel está ocupado? \n (0) - SIM; \n (1) - NÃO");
                                    try {ocupacao = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                                    if ((ocupacao != 0) && (ocupacao != 1)){
                                        System.out.println("** Insira uma opção válida! **");
                                    }
                                } while ((ocupacao != 0) && (ocupacao != 1));    
                                estaOcupado = ocupacao == 0; //Se usuário inserir '0', significa que o imóvel está ocupado. Variável 'estaOcupado' recebe true;

                                itemImovel.setOcupado(estaOcupado);

                                System.out.println("** Item cadastrado com sucesso! **");
                            break;
                            case 5:
                                System.out.println("** A categoria escolhida não existe! **");
                            break;
                            case 6:
                                System.out.println("** Este CPF não está cadastrado no sistema! **");
                            break;
                        }
                    break;

                    // ** ALTERAR ITEM **
                    case 6:
                        int idItemAlterar = 0;
                        String nomeItemAlterar = null, cpfDonoItemAlterar = null, nomeCategoriaItemAlterar = null;
                        float precoInicialAlterar = 0;
                        System.out.print("Insira o id do Item:");
                        try {idItemAlterar = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira o novo nome do Item:");
                        try {nomeItemAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira a nova categoria do Item:");
                        try {nomeCategoriaItemAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira o novo preço do Item:");
                        try {precoInicialAlterar = Console.readFloat();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Insira o CPF do novo dono do Item:");
                        try {cpfDonoItemAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            Item verificacaoDonoItem = objeto.buscarItem(idItemAlterar);
                            if (!(verificacaoDonoItem.getDonoItem().getCpf().equals(cpfUsuario))){
                                System.out.println("** Você não é o atual dono deste item. As alterações não foram realizadas! **");
                                break;
                            }
                        }

                        int retornoAlterarItem = objeto.alterarItem(idItemAlterar, nomeItemAlterar, nomeCategoriaItemAlterar, precoInicialAlterar, cpfDonoItemAlterar);

                        switch (retornoAlterarItem){
                            case 0:
                                System.out.println("** Todas as alterações foram feitas com sucesso! **");
                            break;
                            case 2:
                                System.out.println("** Item inexistente! **");
                            break;
                            case 5:
                                System.out.println("** Categoria inexistente! **");
                            break;
                            case 6:
                                System.out.println("** Este CPF não está cadastrado no sistema! **");
                            break;
                            case 7:
                                System.out.println("** Este item não pode ser alterado pois seu status não consta como 'Cadastrado'! **");
                            break;
                        }
                    break;

                    // ** REMOVER ITEM **
                    case 7:
                        int idItemRemover = 0;     
                        System.out.print("Insira o id do Item:");
                        try {idItemRemover = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        int retornoRemoverItem = objeto.removerItem(idItemRemover);

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            Item verificacaoDonoItem = objeto.buscarItem(idItemRemover);
                            if (verificacaoDonoItem != null){
                                if (!(verificacaoDonoItem.getDonoItem().getCpf().equals(cpfUsuario))){
                                    System.out.println("** Você não é o atual dono deste item. O item não poderá ser removido! **");
                                    break;
                                }
                            } else {
                                System.out.println("** Item inexistente! **");
                                break;
                            }
                        }

                        switch (retornoRemoverItem){
                            case 0:
                                System.out.println("** O item foi removido com sucesso! **");
                            break;
                            case 2:
                                System.out.println("** Item inexistente! **");
                            break;
                            case 7:
                                System.out.println("** Este item não pode ser removido pois seu status não consta como 'Cadastrado'! **");
                            break;
                        }
                    break;
                    
                    // ** ALTERAR PESSOA **
                    case 8:
                        String cpfPessoaAlterar = null, nomePessoaAlterar = null, emailPessoaAlterar = null, telefonePessoaAlterar = null;
                        System.out.print("CPF:");
                        try {cpfPessoaAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Novo Nome:");
                        try {nomePessoaAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Novo Email:");
                        try {emailPessoaAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Novo Telefone:");
                        try {telefonePessoaAlterar = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            if (!(cpfPessoaAlterar.equals(cpfUsuario))){
                                System.out.println("** O CPF inserido não corresponde ao CPF da conta em que você está logado. Alterações não realizadas! **");
                                break;
                            }
                        }

                        int retornoAlterarPessoa = objeto.alterarPessoa(cpfPessoaAlterar, nomePessoaAlterar, emailPessoaAlterar, telefonePessoaAlterar);

                        switch (retornoAlterarPessoa){
                            case 0:
                                System.out.println("** Todas as alterações foram feitas com sucesso! **");
                            break;

                            case 2:
                                System.out.println("** Este CPF não está cadastrado no sistema! **");
                            break;
                        }
                    break;

                    // ** REMOVER PESSOA **
                    case 9:
                        String cpfPessoaRemover = null;
                        System.out.print("CPF:");
                        try {cpfPessoaRemover = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        int retornoRemoverPessoa = objeto.removerPessoa(cpfPessoaRemover);

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            if (!(cpfPessoaRemover.equals(cpfUsuario))){
                                System.out.println("** O CPF inserido não corresponde ao CPF da conta em que você está logado. Pessoa não removida! **");
                                break;
                            }
                        }

                        switch(retornoRemoverPessoa){
                            case 0:
                                System.out.println("** Pessoa removida com sucesso! **");
                                if(modo ==1){ //Se modo usuário, ao excluir conta, o usuário é automaticamente desconectado do sistema;
                                    System.out.println("** Muito obrigado por utilizar o Auction Tool! **");
                                    retorno = false;
                                }
                            break;
                            case 2:
                                System.out.println("** Este CPF não está cadastrado no sistema! **");
                            break;
                            case 3:
                                System.out.println("** Esta pessoa possui itens cadastrados. Não poderá ser removida! **");
                            break;
                            case 4:
                                System.out.println("** Esta pessoa possui lances ofertados. Não poderá ser removida! **");
                            break;
                        }
                    break;

                    // ** DAR LANCE **
                    case 10:
                        String cpfLance = null;
                        int idItemLance = 0;
                        float valorLance = 0;
                        System.out.print("CPF:");
                        try {cpfLance = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Id do Item:");
                        try {idItemLance = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("Valor do Lance:");
                        try {valorLance = Console.readFloat();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            if (!(cpfLance.equals(cpfUsuario))){
                                System.out.println("** O CPF inserido não corresponde ao CPF da conta em que você está logado. Lance não registrado! **");
                                break;
                            }
                        }

                        int retornoDarLance = objeto.efetuarLance(cpfLance, idItemLance, valorLance);

                        switch (retornoDarLance){
                            case 0:
                               System.out.println("** Lance efetuado com sucesso! **"); 
                            break;
                            case 1:
                                System.out.println("** Item Inexistente! **");
                            break;
                            case 2:
                                System.out.println("** Este CPF não está cadastrado no sistema! **");
                            break;
                            case 3:
                                System.out.println("** Valor ofertado é inferior ao valor mínimo do item. Lance não registrado! **");
                            break;
                            case 4:
                                System.out.println("** Este item não está em leilão. Lance não registrado! **");
                            break;
                            case 5:
                                System.out.println("** Este item possui a data de validade vencida. Lance não registrado! **");
                            break;
                        }

                    break;

                    // ** COLOCAR ITEM EM LEILÃO **
                    case 11:
                        int idItemLeilao = 0;
                        System.out.print("Id do Item:");
                        try {idItemLeilao = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            Item buscarItem = objeto.buscarItem(idItemLeilao);
                            if (buscarItem != null){
                                if (!(buscarItem.getDonoItem().getCpf().equals(cpfUsuario))){
                                    System.out.println("** Você não é o atual dono deste item. Leilão do produto não foi iniciado! **");
                                    break;
                                }
                            } else {
                                System.out.println("** Item inexistente! **");
                                break;
                            }
                        }

                        int retornoIniciarLeilao = objeto.iniciarLeilaoDoProduto(idItemLeilao);

                        switch (retornoIniciarLeilao){
                            case 0:
                                System.out.println("** Leilão iniciado com sucesso! **"); 
                            break;
                            case 1:
                                System.out.println("** Item Inexistente! **"); 
                            break;
                            case 2:
                                System.out.println("** Este item não pode ser colocado em leilão pois seu status não consta como 'Cadastrado'! **"); 
                            break;
                        }
                    break;

                    // ** ENCERRAR LEILÃO **
                    case 12:
                        int idItemEncerrarLeilao = 0;
                        System.out.print("Id do Item:");
                        try {idItemEncerrarLeilao = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            Item buscarItem = objeto.buscarItem(idItemEncerrarLeilao);
                            if (buscarItem != null){
                                if (!(buscarItem.getDonoItem().getCpf().equals(cpfUsuario))){
                                    System.out.println("** Você não é o atual dono deste item. Leilão do produto não foi encerrado! **");
                                    break;
                                }
                            } else {
                                System.out.println("** Item inexistente! **");
                                break;
                            }
                        }

                        int retornoEncerrarLeilao = objeto.encerrarLeilaoDoProduto(idItemEncerrarLeilao);

                        switch (retornoEncerrarLeilao){
                            case 0:
                                System.out.println("** Leilão encerrado com sucesso! **"); 
                            break;
                            case 1:
                                System.out.println("** Item Inexistente! **"); 
                            break;
                            case 2:
                                System.out.println("** Este item não recebeu nenhum lance. Leilão não pode ser encerrado! **"); 
                            break;
                            case 3:
                                System.out.println("** Este item não está em leilão! **"); 
                            break;
                        }
                    break;

                    // ** DESISTIR DO ITEM **
                    case 13:
                        int idItemDesistencia = 0;
                        String cpfDonoItemDesistencia = null;
                        System.out.print("Id do Item:");
                        try {idItemDesistencia = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        System.out.print("CPF:");
                        try {cpfDonoItemDesistencia = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        //Verificação para modo usuário: O cpf inserido deverá sempre ser igual ao cpf do usuário conectado ao sistema no momento da execução.
                        if (modo ==1){
                            if (!(cpfDonoItemDesistencia.equals(cpfUsuario))){
                                System.out.println("** O CPF inserido não corresponde ao CPF da conta em que você está logado. Desistencia não realizada! **");
                                break;
                            }
                        }

                        int retornoDesistirItem = objeto.desistirItem(idItemDesistencia, cpfDonoItemDesistencia);

                        switch (retornoDesistirItem){
                            case 0:
                                System.out.println("** Desistência efetuada com sucesso! **");
                            break;
                            case 1:
                                System.out.println("** Este CPF não corresponde ao CPF do vencedor do leilão deste item! **");
                            break;
                            case 2:
                                System.out.println("** Este item ainda não foi vendido! **");
                            break;
                            case 3:
                                System.out.println("** Item inexistente! **");
                            break;
                        }
                    break;

                    // ** EXIBIR VENCEDOR **
                    case 14: 
                        int idItemVencedor = 0;
                        System.out.print("Id do Item:");
                        try {idItemVencedor = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        int retornoExibirVencedor = objeto.verificacaoVencedorLeilao(idItemVencedor);
                        switch (retornoExibirVencedor){
                            case 0:
                                Pessoa vencedorLeilao = objeto.vencedorLeilao(idItemVencedor);
                                Item itemComprado = objeto.buscarItem(idItemVencedor);
                                Lance lanceGanhador = (Lance) itemComprado.getLance().peek();
                                System.out.print("\nNome: "+vencedorLeilao.getNome());
                                System.out.print("; CPF: "+vencedorLeilao.getCpf());
                                System.out.print("; Email: "+vencedorLeilao.getEmail());
                                System.out.print("; Telefone: "+vencedorLeilao.getTelefone());
                                System.out.print("\nValor do lance: R$"+lanceGanhador.getValor());
                            break;
                            case 1:
                                System.out.println("** Item inexistente! **");
                            break;
                            case 2:
                                System.out.println("** Não há vencedores para o leilão deste item! **");
                            break;
                        }
                    break;

                    // ** LISTAR ITENS POR CATEGORIA **
                    case 15: 
                        String nomeCategoria = null;
                        System.out.print("Insira o nome da categoria: ");
                        try {nomeCategoria = Console.readString();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}

                        int retornoVerificacaoCategoria = objeto.verificacaoListarItensPorCategoria(nomeCategoria);
                        switch (retornoVerificacaoCategoria){
                            case 0:
                                Iterator listarItensPorCategoria = objeto.listarItens();
                                while (listarItensPorCategoria.hasNext()){
                                    Item atual = (Item) listarItensPorCategoria.next();
                                    if(atual.getCategoriaItem().getNome().equals(nomeCategoria)){
                                        System.out.println(atual.getNome()+"(cód.:"+atual.getCodigo()+"): Valor inicial: R$"+atual.getPrecoInicial()+"; Status: "+atual.getStatus());
                                    }
                                }
                            break;

                            case 1:
                                System.out.println("** Esta categoria não possui itens! **");
                            break;

                            case 2:
                                System.out.println("** Esta categoria não existe! **");
                            break;
                        }
                    break;

                    // ** LISTAR PESSOAS **
                    case 16:
                        Iterator listarPessoas = objeto.listarPessoas();
                        while (listarPessoas.hasNext()){
                            Pessoa atual = (Pessoa) listarPessoas.next();
                            System.out.print("\nNome: "+atual.getNome());
                            System.out.print("; CPF: "+atual.getCpf());
                            System.out.print("; Email: "+atual.getEmail());
                            System.out.print("; Telefone: "+atual.getTelefone());
                            System.out.print("; Pontuação: "+atual.getPontuacao());
                        }
                    break;

                    // ** LISTAR LANCES **
                    case 17: 
                        int idItemLanceListar = 0;
                        System.out.print("Id do Item:");
                        try {idItemLanceListar = Console.readInt();} catch (IOException ex) {Logger.getLogger(AuctionBC.class.getName()).log(Level.SEVERE, null, ex);}
                        int retornoVerificacaoListarLances =  objeto.verificacaoListarLancesProduto (idItemLanceListar);
                        switch (retornoVerificacaoListarLances){
                            case 0:
                                Iterator listarLances = objeto.listarLancesProduto(idItemLanceListar);
                                while (listarLances.hasNext()){
                                    Lance atual = (Lance) listarLances.next();
                                    System.out.println("\nPessoa: "+atual.getPessoa().getNome());
                                    System.out.println("Valor do lance: "+atual.getValor());
                                }
                            break;
                            case 1:
                                System.out.println("** Item inexistente! **");
                            break;
                            case 2:
                                System.out.println("** Este item não possui lances! **");
                            break;
                            case 3:
                                System.out.println("** Este item não está em leilão! **");
                            break;
                        }
                    break;

                    // ** SAIR **
                    case 18:
                        retorno = false;
                    break;

                    default:
                        System.out.println("**Insira uma opção válida!**");
                }       
            } while (retorno); //Retorna ao menu modo administrador ou usuário, caso retorno == true;
            int opcao_4 = 0;
            do{
                System.out.println("Deseja entrar em outro modo de acesso? \n (0) SIM \n (1) NÃO");
                try {opcao_4 = Console.readInt();} catch (IOException ex) {Logger.getLogger(Aplicacao.class.getName()).log(Level.SEVERE, null, ex);}
                if ((opcao_4 != 0) && (opcao_4 != 1)){System.out.println("** Insira uma opção válida! **");}
            }while ((opcao_4 != 0) && (opcao_4 != 1));
            if(opcao_4 == 1){
                System.out.println("** Muito obrigado por utilizar o Auction Tool! **");
                retornoModo = false;
            }
            
        } while (retornoModo); //Enquanto retorno for true, o laço irá se repetir, dando ao usuário a opção de acessar em outro modo;
    }
    
    public static void menuAdm(){ //MENU ADMINISTRADOR
        System.out.println ("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n"
                                + "*  (1) Inserir categoria;                               *\n"                 
                                + "*  (2) Alterar categoria;                               *\n"
                                + "*  (3) Remover categoria;                               *\n"
                                + "*  (4) Listar categorias;                               *\n"  
                                + "*  (7) Remover Item;                                    *\n"
                                + "*  (9) Remover Pessoa;                                  *\n" 
                                + "*  (14) Exibir Vencedor;                                *\n" 
                                + "*  (15) Listar itens por categoria;                     *\n" 
                                + "*  (16) Listar pessoas;                                 *\n" 
                                + "*  (17) Listar lances;                                  *\n" 
                                + "*  (18) Sair;                                           *\n"
                                + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        System.out.print("Insira uma opção: ");
    }
    public static void menuUsuario(){ //MENU USUÁRIO
        System.out.println ("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n"
                                + "*  (4) Listar categorias;                               *\n"
                                + "*  (5) Inserir Item;                                    *\n"
                                + "*  (6) Alterar Item;                                    *\n"   
                                + "*  (7) Remover Item;                                    *\n"        
                                + "*  (8) Alterar dados pessoais;                          *\n" 
                                + "*  (9) Excluir conta;                                  *\n" 
                                + "*  (10) Dar Lance;                                      *\n" 
                                + "*  (11) Colocar item em leilão;                         *\n" 
                                + "*  (12) Encerrar leilão;                                *\n" 
                                + "*  (13) Desistir do item;                               *\n" 
                                + "*  (14) Exibir Vencedor de item;                        *\n" 
                                + "*  (15) Listar itens por categoria;                     *\n" 
                                + "*  (17) Listar lances;                                  *\n" 
                                + "*  (18) Sair;                                           *\n"
                                + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        System.out.print("Insira uma opção: ");
    }
}
