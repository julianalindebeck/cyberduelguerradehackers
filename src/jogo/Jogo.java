package jogo;

import java.util.Scanner;

import jogadores.Bot;
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

    public void iniciaJogo(){
        System.out.println("\nInício de jogo!");
        boolean verificaJogo = true;

        while(verificaJogo){
            System.out.println("\nVez de: " + primeiroJogador.getNome());

            if(!(primeiroJogador instanceof Bot)){
                System.out.println(primeiroJogador.getNome() + " deseja desistir?");
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                int escolha = leitura.nextInt();

                if(escolha == 1){
                    System.out.println("\n" + primeiroJogador.getNome() + " desistiu!");
                    System.out.println("\nVENCEDOR: " + segundoJogador.getNome());
                    verificaJogo = false;
                    break;
                }
            }

            boolean ehBot = (primeiroJogador instanceof Bot);
            primeiroJogador.jogada(!ehBot);

            //inverte o turno
            Jogador aux = primeiroJogador;
            primeiroJogador = segundoJogador;
            segundoJogador = aux;
        }
    }
}
