package jogo;

import java.util.Scanner;

import jogadores.Jogador;

public class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador primeiroJogador;
    private Jogador segundoJogador;
    private Scanner leitura;
    
    public Jogo(Jogador jogador1, Jogador jogador2, Scanner leitura){
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.leitura = leitura;
        this.primeiroJogador = jogador1;
        this.segundoJogador = jogador2;
    }
}
