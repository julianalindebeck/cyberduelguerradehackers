package jogo;

import java.util.Scanner;

import jogadores.Bot;
import jogadores.Jogador;

public class Jogo {
    private Jogador primeiroJogador;
    private Jogador segundoJogador;
    private Scanner leitura;
    
    public Jogo(Jogador jogador1, Jogador jogador2, Scanner leitura){
        this.primeiroJogador = jogador1;
        this.segundoJogador = jogador2;
        this.leitura = leitura;
    }

    public void iniciaJogo(){
        System.out.println("\n*------------------*");
        System.out.println("O jogo vai iniciar!");
        System.out.println("*------------------*");

        boolean verificaJogo = true;

        while(verificaJogo){
            //reseta os atributos dos jogadores
            primeiroJogador.resetaTurno();
            segundoJogador.resetaTurno();
            
            System.out.println("\nVez de: " + primeiroJogador.getNome());
            System.out.println("Energia: " + primeiroJogador.getEnergia() + " | Vida: " + primeiroJogador.getVida());

            if(!(primeiroJogador instanceof Bot)){
                System.out.println("\n" + primeiroJogador.getNome() + " deseja desistir?");
                System.out.println("(1) Sim");
                System.out.println("(2) Não");
                int escolha = leitura.nextInt();

                if(escolha == 1){
                    System.out.println("\n" + primeiroJogador.getNome() + " desistiu!");
                    System.out.println("\nVENCEDOR: " + segundoJogador.getNome());
                    verificaJogo = false;
                    break;
                }
            }

            boolean ehBot = (primeiroJogador instanceof Bot);
            primeiroJogador.jogada(ehBot);
            
            System.out.println("\nVez de: " + segundoJogador.getNome());
            System.out.println("Energia: " + segundoJogador.getEnergia() + " | Vida: " + segundoJogador.getVida());

            if(!(segundoJogador instanceof Bot)){
                System.out.println("\n" + segundoJogador.getNome() + " deseja desistir?");
                System.out.println("(1) Sim");
                System.out.println("(2) Não");
                int escolha = leitura.nextInt();

                if(escolha == 1){
                    System.out.println("\n" + segundoJogador.getNome() + " desistiu!");
                    System.out.println("\nVENCEDOR: " + primeiroJogador.getNome());
                    verificaJogo = false;
                    break;
                }
            }
            
            ehBot = (segundoJogador instanceof Bot);
            segundoJogador.jogada(ehBot);

            Consolidacao.calcularDano(primeiroJogador, segundoJogador);

            //inverte o turno
            Jogador aux = primeiroJogador;
            primeiroJogador = segundoJogador;
            segundoJogador = aux;
        }
    }
}
