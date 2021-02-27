package br.uefs.ecomp.AuctionTool.model;

public enum Enumeracao { //Estes status serão utilizados para objetos do tipo Item.
    CADASTRADO, EM_LEILAO, VENDIDO , DESISTENCIA;
    
    /* Cadastrado - Primeiro status do item assim que ele é cadastrado pelo usuário.
       Em_leilao - O item recebe este status quando seu leilão é iniciado.
       Vendido - O item recebe este status quando é encerrado o seu leilão.
       Desistencia - Status dado ao item que possua pelo menos uma desistência após encerrado o leilão.
    */
}
