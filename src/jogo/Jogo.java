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
        esperar(500);
        System.out.println("*------------------*\nO jogo vai iniciar!\n*------------------*");

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

            esperar(500);
            System.out.println("\nVez de: " + primeiroJogador.getNome() + "\nEnergia: " + primeiroJogador.getEnergia() + " | Vida: " + primeiroJogador.getVida());

            Replay.registrar("\nVez de: " + primeiroJogador.getNome());
            Replay.registrar("Energia: " + primeiroJogador.getEnergia() + " | Vida: " + primeiroJogador.getVida());

            if(verificaDesistencia(primeiroJogador, segundoJogador, leitura)){
                break;
            }

            boolean ehBot = (primeiroJogador instanceof Bot);

            primeiroJogador.jogada(ehBot);

            Replay.registrar("\nCartas jogadas por " + primeiroJogador.getNome() + ": ");
            for(Carta c : primeiroJogador.cartasEmJogo){
                Replay.registrar(c.getNome());
            }
                    
            esperar(500);
            System.out.println("\nVez de: " + segundoJogador.getNome() + "\nEnergia: " + segundoJogador.getEnergia() + " | Vida: " + segundoJogador.getVida());

            Replay.registrar("\nVez de: " + segundoJogador.getNome());
            Replay.registrar("Energia: " + segundoJogador.getEnergia() + " | Vida: " + segundoJogador.getVida());

            if(verificaDesistencia(segundoJogador, primeiroJogador, leitura)){
                break;
            }

            ehBot = (segundoJogador instanceof Bot);
            
            segundoJogador.jogada(ehBot);

            Replay.registrar("\nCartas jogadas por " + segundoJogador.getNome() + ": ");
            for(Carta c : segundoJogador.cartasEmJogo){
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
            esperar(500);
            System.out.println("\n" + jogador1.getNome() + " foi derrotada(o)!\n");

            esperar(500);
            System.out.println("*-------------------------*\nVENCEDOR: " + jogador2.getNome() + "\n*-------------------------*");

            Replay.registrar("\n" + jogador1.getNome() + " foi derrotada(o)!");
            Replay.registrar("\nVENCEDOR: " + jogador2.getNome());

            return true;
        }
        else if(jogador2.getVida() <= 0){
            esperar(500);
            System.out.println("\n" + jogador2.getNome() + " foi derrotada(o)!\n");

            esperar(500);
            System.out.println("*-------------------------*\nVENCEDOR: " + jogador1.getNome() + "\n*-------------------------*");

            Replay.registrar("\n" + jogador2.getNome() + " foi derrotada(o)!");
            Replay.registrar("\nVENCEDOR: " + jogador1.getNome());

            return true;
        }
        else if(jogador1.getVida() <= 0 && jogador2.getVida() <= 0){
            esperar(500);
            System.out.println("\nOs jogadores usaram seus últimos suspiros para se derrotarem!\n");

            esperar(500);
            System.out.println("*-------------------------*\nEMPATE!\n*-------------------------*");

            Replay.registrar("\nOs jogadores usaram seus últimos suspiros para se derrotarem!");
            Replay.registrar("\nEMPATE!");

            return true;
        }
        return false;
    }

    private static boolean verificaDesistencia(Jogador jogador, Jogador jogador2, Scanner leitura){
        if(!(jogador instanceof Bot)){
            esperar(500);
            System.out.println("\nVocê deseja desistir?\n(1) Sim\n(2) Não");
            int escolha = leitura.nextInt();

            while(escolha != 1 && escolha != 2){
                System.out.println("\nOpção inválida! Deseja desistir?");
                System.out.println("(1) Sim");
                System.out.println("(2) Não");
                escolha = leitura.nextInt();
            }

            if(escolha == 1){
                esperar(500);
                System.out.println("\n" + jogador.getNome() + " desistiu!\n");

                esperar(500);
                System.out.println("*-------------------------*\nVENCEDOR: " + jogador2.getNome() + "\n*-------------------------*");

                Replay.registrar("\n" + jogador.getNome() + " desistiu!");
                Replay.registrar("\nVENCEDOR: " + jogador2.getNome());

                return true;
            }
        }
        return false;
    }

    private static void esperar(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
