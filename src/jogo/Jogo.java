package jogo;

import java.util.Scanner;

import cartas.Carta;
import jogadores.Bot;
import jogadores.Jogador;
import replay.Replay;

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

        Replay.registrar("\nMão de " + primeiroJogador.getNome() + ":");
        for(Carta c : primeiroJogador.getMao()){
            Replay.registrar(c.getNome());
        }

        Replay.registrar("\nMão de " + segundoJogador.getNome() + ":");
        for(Carta c : segundoJogador.getMao()){
            Replay.registrar(c.getNome());
        }

        boolean verificaJogo = true;

        while(verificaJogo){
            //reseta os atributos dos jogadores
            primeiroJogador.resetaTurno();
            segundoJogador.resetaTurno();

            System.out.println("\nVez de: " + primeiroJogador.getNome());
            System.out.println("Energia: " + primeiroJogador.getEnergia() + " | Vida: " + primeiroJogador.getVida());

            Replay.registrar("\nVez de: " + primeiroJogador.getNome());
            Replay.registrar("Energia: " + primeiroJogador.getEnergia() + " | Vida: " + primeiroJogador.getVida());

            if(verificaDesistencia(primeiroJogador, segundoJogador, leitura)){
                break;
            }

            boolean ehBot = (primeiroJogador instanceof Bot);
            primeiroJogador.jogada(ehBot);

            Replay.registrar("\nCartas jogadas por " + primeiroJogador.getNome() + ": ");
            for(Carta c : primeiroJogador.cartasEmJogo){//corrigido
                Replay.registrar(c.getNome());
            }
            
            System.out.println("\nVez de: " + segundoJogador.getNome());
            System.out.println("Energia: " + segundoJogador.getEnergia() + " | Vida: " + segundoJogador.getVida());

            Replay.registrar("\nVez de: " + segundoJogador.getNome());
            Replay.registrar("Energia: " + segundoJogador.getEnergia() + " | Vida: " + segundoJogador.getVida());

            if(verificaDesistencia(segundoJogador, primeiroJogador, leitura)){
                break;
            }
            
            ehBot = (segundoJogador instanceof Bot);
            segundoJogador.jogada(ehBot);

            Replay.registrar("\nCartas jogadas por " + segundoJogador.getNome() + ": ");
            for(Carta c : segundoJogador.cartasEmJogo){//corrigido
                Replay.registrar(c.getNome());
            }

            Consolidacao.calcularDano(primeiroJogador, segundoJogador);

            if(verificaStatus(primeiroJogador, segundoJogador)){
                verificaJogo = false;
            }

            //inverte o turno
            Jogador aux = primeiroJogador;
            primeiroJogador = segundoJogador;
            segundoJogador = aux;
        }
    }

    private static boolean verificaStatus(Jogador jogador1, Jogador jogador2){
        if(jogador1.getVida() <= 0){
            System.out.println("\n" + jogador1.getNome() + " foi derrotado!");
            System.out.println("\nVENCEDOR: " + jogador2.getNome());

            Replay.registrar("\n" + jogador1.getNome() + " foi derrotado!");
            Replay.registrar("\nVENCEDOR: " + jogador2.getNome());

            return true;
        }
        else if(jogador2.getVida() <= 0){
            System.out.println("\n" + jogador2.getNome() + " foi derrotado!");
            System.out.println("\nVENCEDOR: " + jogador1.getNome());

            Replay.registrar("\n" + jogador2.getNome() + " foi derrotado!");
            Replay.registrar("\nVENCEDOR: " + jogador1.getNome());

            return true;
        }
        return false;
    }

    private static boolean verificaDesistencia(Jogador jogador, Jogador jogador2, Scanner leitura){
        if(!(jogador instanceof Bot)){
            System.out.println("\nVocê deseja desistir?");
            System.out.println("(1) Sim");
            System.out.println("(2) Não");
            int escolha = leitura.nextInt();

            while(escolha != 1 && escolha != 2){
                System.out.println("\nOpção inválida! Deseja desistir?");
                System.out.println("(1) Sim");
                System.out.println("(2) Não");
                escolha = leitura.nextInt();
            }

            if(escolha == 1){
                System.out.println("\n" + jogador.getNome() + " desistiu!");
                System.out.println("\nVENCEDOR: " + jogador2.getNome());

                Replay.registrar("\n" + jogador.getNome() + " desistiu!");
                Replay.registrar("\nVENCEDOR: " + jogador2.getNome());

                return true;
            }
        }
        return false;
    }
}
