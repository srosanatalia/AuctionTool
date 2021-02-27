package br.uefs.ecomp.AuctionTool.controller;

import br.uefs.ecomp.AuctionTool.model.Alimento;
import br.uefs.ecomp.AuctionTool.model.Categoria;
import br.uefs.ecomp.AuctionTool.model.Lance;
import br.uefs.ecomp.AuctionTool.model.Pessoa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.uefs.ecomp.AuctionTool.util.Iterator;
import java.util.Calendar;

public class AuctionBCTest{

	private AuctionBC controller;
	
	@Before
	public void setUp() {
            controller = new AuctionBC();
	}
	
	@Test
	public void testCriarCategoriaComSucesso() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            Assert.assertEquals(0,codigoCadastrar);
            Categoria categoria = controller.buscarCategoriaPorNome("Automoveis");
            Assert.assertEquals("Automoveis", categoria.getNome());
	}
	
	@Test
	public void testCriarCategoriaDuplicada() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            Assert.assertEquals(1,codigoCadastrar);
	}
	
	@Test
	public void testAlterarCategoriaComSucesso() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoAlterar = controller.alterarCategoria("Automoveis","Avioes");
            Assert.assertEquals(0,codigoAlterar);
            Categoria categoria = controller.buscarCategoriaPorNome("Avioes");
            Assert.assertEquals("Avioes", categoria.getNome());
	}
	
	@Test
	public void testAlterarCategoriaParaNomeJaExistente() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            codigoCadastrar = controller.cadastrarCategoria("Avioes");
            int codigoAlterar = controller.alterarCategoria("Automoveis","Avioes");
            Assert.assertEquals(1,codigoAlterar);

            Categoria categoria = controller.buscarCategoriaPorNome("Automoveis");
            Assert.assertEquals("Automoveis", categoria.getNome());
            categoria = controller.buscarCategoriaPorNome("Avioes");
            Assert.assertEquals("Avioes", categoria.getNome());
	}
	
	@Test
	public void testAlterarCategoriaInexistente() { 
            int codigoAlterar = controller.alterarCategoria("Automoveis","Avioes");
            Assert.assertEquals(2,codigoAlterar);
	}
	
	@Test
	public void testRemoverCategoriaComSucesso() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoRemover = controller.removerCategoria("Automoveis");
            Assert.assertEquals(0,codigoRemover);
	}
	
	@Test
	public void testRemoverCategoriaInexistente() { 
            int codigoRemover = controller.removerCategoria("Automoveis");
            Assert.assertEquals(2,codigoRemover);
	}
	
	@Test
	public void testRemoverCategoriaComItem() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoRemover = controller.removerCategoria("Automoveis");
            Assert.assertEquals(3,codigoRemover);
	}
	
	@Test
	public void testCadastrarPessoaComSucesso() { 
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            Assert.assertEquals(0,codigoCadastrarPessoa);
            Pessoa pessoa = controller.buscarPessoaPorCpf("33333333333");
            Assert.assertEquals("Fulano", pessoa.getNome());
	}
	
	@Test
	public void testCadastrarPessoaComCpfJaCadastrado() { 
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            Assert.assertEquals(1,codigoCadastrarPessoa);
	}
	
	@Test
	public void testAlterarPessoaComSucesso() { 
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoAlterar = controller.alterarPessoa("33333333333","Fulano","fulano@fulano.com","7523421897");
            Assert.assertEquals(0,codigoAlterar);
            Pessoa pessoa = controller.buscarPessoaPorCpf("33333333333");
            Assert.assertEquals("fulano@fulano.com", pessoa.getEmail());
	}
	
	@Test
	public void testAlterarPessoaInexistente() { 
            int codigoAlterar = controller.alterarPessoa("33333333333","Fulano","fulano@fulano.com","7523421897");
            Assert.assertEquals(2,codigoAlterar);
	}
	
	@Test
	public void testRemoverPessoaComSucesso() { 
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoRemover = controller.removerPessoa("33333333333");
            Assert.assertEquals(0,codigoRemover);
	}
	
	@Test
	public void testRemoverPessoaInexistente() { 
            int codigoRemover = controller.removerPessoa("33333333333");
            Assert.assertEquals(2,codigoRemover);
	}

	@Test
	public void testRemoverPessoaDonaDeItem() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoRemover = controller.removerPessoa("33333333333");
            Assert.assertEquals(3,codigoRemover);
	}
	
	@Test
	public void testRemoverPessoaComLance() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            codigoCadastrarPessoa = controller.cadastrarPessoa("44444444444","Beltrano","beltrano@beltrano.com.br","7523421797");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoIniciarLeilao = controller.iniciarLeilaoDoProduto(1);
            int codigoCadastrarLance = controller.efetuarLance("44444444444",1, (float) 49700.34);
            int codigoRemover = controller.removerPessoa("44444444444");
            Assert.assertEquals(4,codigoRemover);
	}
	
	@Test
	public void testCadastrarItemComSucesso() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            Assert.assertEquals(0,codigoCadastrarItem);
	}
	
	@Test
	public void testCadastrarItemComCpfInexistente() { 
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            Assert.assertEquals(6,codigoCadastrarItem);
	}
	
	@Test
	public void testCadastrarItemComCategoriaInexistente() { 
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            Assert.assertEquals(5,codigoCadastrarItem);
	}
	
	@Test
	public void testAlterarItemComSucesso() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoAlterarItem = controller.alterarItem(1,"Toyota Corolla","Automoveis", (float) 49900.34,"33333333333");
            Assert.assertEquals(0,codigoAlterarItem);
	}
	
	@Test
	public void testAlterarItemInexistente() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoAlterarItem = controller.alterarItem(2,"Toyota Corolla","Automoveis", (float) 49900.34,"33333333333");
            Assert.assertEquals(2,codigoAlterarItem);
	}
	
	@Test
	public void testAlterarCategoriaDeItemParaCategoriaInexistente() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoAlterarItem = controller.alterarItem(1,"Toyota Corolla","Automoveis Novos", (float) 49900.34,"33333333333");
            Assert.assertEquals(5,codigoAlterarItem);
	}
	
	@Test
	public void testAlterarDonoDeItemParaPessoaInexistente() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoAlterarItem = controller.alterarItem(1,"Toyota Corolla","Automoveis", (float) 49900.34,"44444444444");
            Assert.assertEquals(6,codigoAlterarItem);
	}
	
	@Test
	public void testAlterarItemComStatusDiferenteDeCadastrado() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            controller.iniciarLeilaoDoProduto(1);
            int codigoAlterarItem = controller.alterarItem(1,"Toyota Corolla","Automoveis", (float) 49900.34,"33333333333");
            Assert.assertEquals(7,codigoAlterarItem);
	}
	
	@Test
	public void testRemoverItemComSucesso() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoRemoverItem = controller.removerItem(1);
            Assert.assertEquals(0,codigoRemoverItem);
	}
	
	@Test
	public void testRemoverItemInexistente() { 
            int codigoRemoverItem = controller.removerItem(1);
            Assert.assertEquals(2,codigoRemoverItem);
	}
	
	@Test
	public void testRemoverItemComStatusDiferenteDeCadastrado() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            controller.iniciarLeilaoDoProduto(1);
            int codigoRemoverItem = controller.removerItem(1);
            Assert.assertEquals(7,codigoRemoverItem);
	}
	
	@Test
	public void testListarLances() {
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            codigoCadastrarPessoa = controller.cadastrarPessoa("44444444444","Beltrano","beltrano@beltrano.com.br","7523421797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("77777777777","Cicrano","cicrano@cicrano.com.br","7523491797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("99999999999","Outra Pessoa","pessoa@pessoa.com.br","7523471797");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoIniciarLeilao = controller.iniciarLeilaoDoProduto(1);
            int codigoCadastrarLance = controller.efetuarLance("44444444444",1, (float) 49700.34);
            codigoCadastrarLance = controller.efetuarLance("77777777777",1, (float) 49980.34);
            codigoCadastrarLance = controller.efetuarLance("99999999999",1, (float) 49999.34);
            Iterator itrLances = controller.listarLancesProduto(1);
            Assert.assertEquals("99999999999",((Lance)itrLances.next()).getPessoa().getCpf());
            Assert.assertEquals("44444444444",((Lance)itrLances.next()).getPessoa().getCpf());
            Assert.assertEquals("77777777777",((Lance)itrLances.next()).getPessoa().getCpf());
	}
        
        public void testListarPessoa(){
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            codigoCadastrarPessoa = controller.cadastrarPessoa("44444444444","Beltrano","beltrano@beltrano.com.br","7523421797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("77777777777","Cicrano","cicrano@cicrano.com.br","7523491797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("99999999999","Outra Pessoa","pessoa@pessoa.com.br","7523471797");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            codigoCadastrarItem = controller.cadastrarItem("Bicicleta","Automoveis", (float) 700.34,"33333333333");
            codigoCadastrarItem = controller.cadastrarItem("Carro de mão","Automoveis", (float) 40700.34,"44444444444");
            int codigoIniciarLeilao = controller.iniciarLeilaoDoProduto(1);
            codigoIniciarLeilao = controller.iniciarLeilaoDoProduto(2);
            codigoIniciarLeilao = controller.iniciarLeilaoDoProduto(3);
            int codigoCadastrarLance = controller.efetuarLance("44444444444",1, (float) 49700.34);
            codigoCadastrarLance = controller.efetuarLance("77777777777",2, (float) 49980.34);
            codigoCadastrarLance = controller.efetuarLance("33333333333",3, (float) 49999.34);
            controller.encerrarLeilaoDoProduto(1);
            controller.encerrarLeilaoDoProduto(2);
            controller.encerrarLeilaoDoProduto(3);
            Iterator itrPessoas= controller.listarPessoas();
            Assert.assertEquals("33333333333", ((Pessoa)itrPessoas.next()).getCpf());
            Assert.assertEquals("44444444444", ((Pessoa)itrPessoas.next()).getCpf());
            Assert.assertEquals("77777777777", ((Pessoa)itrPessoas.next()).getCpf());
            Assert.assertEquals("99999999999", ((Pessoa)itrPessoas.next()).getCpf());
        }
        
        @Test
	public void testItemEspecialDoTipoAlimento() {
            int codigoCadastrar = controller.cadastrarCategoria("Alimento");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("pao","Alimento", (float) 49700.34,"33333333333");
            Assert.assertEquals(1,codigoCadastrarItem);
        }
        
        @Test
	public void testItemEspecialDoTipoRemedio() {
            int codigoCadastrar = controller.cadastrarCategoria("Remedio");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("pao","Remedio", (float) 49700.34,"33333333333");
            Assert.assertEquals(2,codigoCadastrarItem);
        }
        
        @Test
	public void testItemEspecialDoTipoImovel() {
            int codigoCadastrar = controller.cadastrarCategoria("Imovel");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("pao","Imovel", (float) 49700.34,"33333333333");
            Assert.assertEquals(3,codigoCadastrarItem);
        }
        
        @Test
        public void testLanceMenorQuePrecoInicial (){
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            codigoCadastrarPessoa = controller.cadastrarPessoa("44444444444","Beltrano","beltrano@beltrano.com.br","7523421797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("77777777777","Cicrano","cicrano@cicrano.com.br","7523491797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("99999999999","Outra Pessoa","pessoa@pessoa.com.br","7523471797");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoIniciarLeilao = controller.iniciarLeilaoDoProduto(1);
            int codigoCadastrarLance = controller.efetuarLance("44444444444",1, (float) 9700.34);
            codigoCadastrarLance = controller.efetuarLance("77777777777",1, (float) 9980.34);
            codigoCadastrarLance = controller.efetuarLance("99999999999",1, (float) 9999.34);
            Assert.assertEquals(3,codigoCadastrarLance);
            Assert.assertEquals(3,codigoCadastrarLance);
            Assert.assertEquals(3,codigoCadastrarLance);
        }
        
        @Test
        public void testDarLanceEmItemForaDoLeilao (){
            int codigoCadastrar = controller.cadastrarCategoria("Automoveis");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            codigoCadastrarPessoa = controller.cadastrarPessoa("44444444444","Beltrano","beltrano@beltrano.com.br","7523421797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("77777777777","Cicrano","cicrano@cicrano.com.br","7523491797");
            codigoCadastrarPessoa = controller.cadastrarPessoa("99999999999","Outra Pessoa","pessoa@pessoa.com.br","7523471797");
            int codigoCadastrarItem = controller.cadastrarItem("Toyota Corolla","Automoveis", (float) 49700.34,"33333333333");
            int codigoCadastrarLance = controller.efetuarLance("44444444444",1, (float) 9700.34);
            codigoCadastrarLance = controller.efetuarLance("77777777777",1, (float) 9980.34);
            codigoCadastrarLance = controller.efetuarLance("99999999999",1, (float) 9999.34);
            Assert.assertEquals(4,codigoCadastrarLance);
            Assert.assertEquals(4,codigoCadastrarLance);
            Assert.assertEquals(4,codigoCadastrarLance);
        }
        
        @Test
	public void testLanceProdutoVencido() {
            int codigoCadastrar = controller.cadastrarCategoria("Alimento");
            int codigoCadastrarPessoa = controller.cadastrarPessoa("33333333333","Fulano","fulano@fulano.com.br","7523421897");
            int codigoCadastrarItem = controller.cadastrarItem("pao","Alimento", (float) 4900.34,"33333333333");
            Alimento itemAlimento = (Alimento) controller.buscarItem(1);
            Calendar dataValidade =  Calendar.getInstance();
            dataValidade.set(Calendar.YEAR, 2000);
            itemAlimento.setValidade(dataValidade);
            int codigoIniciarLeilao = controller.iniciarLeilaoDoProduto(1);
            int codigoCadastrarLance = controller.efetuarLance("33333333333",1, (float) 9700.34);
            Assert.assertEquals(5,codigoCadastrarLance);
        }
	
}