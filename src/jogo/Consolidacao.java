package jogo;

import java.util.ArrayList;
import java.util.List;

import cartas.Ataque;
import cartas.Carta;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Jogador;
import replay.Replay;

public class Consolidacao {
    public static void calcularDano(Jogador jogador1, Jogador jogador2){
        System.out.println("\n*--------------------------------------------------------------------*");
        System.out.println("Todos os jogadores escolheram suas cartas! Hora de calcular os danos!");
        System.out.println("*--------------------------------------------------------------------*");

        Replay.registrar("\nCartas escolhidas! Hora de calcular os danos!");

        double dano = 0;

        boolean temSuporte1 = false, temSuporte2 = false;
        List<Carta> suporte1 = new ArrayList<>();
        List<Carta> suporte2 = new ArrayList<>();

        verificaTipos(jogador1, temSuporte1, suporte1);
        verificaTipos(jogador2, temSuporte2, suporte2);

        for(int i = 0; i < suporte1.size(); i++){
            verificaSuporte(temSuporte1, jogador1, jogador2, suporte1.get(i));
        }

        for(int i = 0; i < suporte2.size(); i++){
            verificaSuporte(temSuporte2, jogador2, jogador1, suporte2.get(i));
        }

        if(jogador1.ataque != 0 && jogador2.defesa != 0){
            dano = jogador1.ataque - jogador2.defesa;

            if(dano > jogador2.defesa){
                jogador2.setVidaMenos(dano);
                esperar(500);
                System.out.println("\n" + jogador2.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador2.getVida());

                Replay.registrar("\n" + jogador2.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador2.getVida());
            }
            else{
                esperar(500);
                System.out.println("\n" + jogador2.getNome() + " conseguiu se defender!");

                Replay.registrar("\n" + jogador2.getNome() + " conseguiu se defender!");
            }
        }
        if(jogador2.ataque != 0 && jogador1.defesa != 0){
            dano = jogador2.ataque - jogador1.defesa;

            if(dano > jogador1.defesa){
                jogador1.setVidaMenos(dano);
                esperar(500);
                System.out.println("\n" + jogador1.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador1.getVida());

                Replay.registrar("\n" + jogador1.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador1.getVida());
            }
            else{
                esperar(500);
                System.out.println("\n" + jogador1.getNome() + " conseguiu se defender!");

                Replay.registrar("\n" + jogador1.getNome() + " conseguiu se defender!");
            }
        }
        if(jogador1.ataque !=0 && jogador2.defesa == 0){
            dano = jogador1.ataque;
            jogador2.setVidaMenos(dano);

            esperar(500);
            System.out.println("\n" + jogador2.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador2.getVida());

            Replay.registrar("\n" + jogador2.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador2.getVida());
        }
        if(jogador2.ataque !=0 && jogador1.defesa == 0){
            dano = jogador2.ataque;
            jogador1.setVidaMenos(dano);

            esperar(500);
            System.out.println("\n" + jogador1.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador1.getVida());

            Replay.registrar("\n" + jogador1.getNome() + " recebeu " + dano + " de dano!\nVida: " + jogador1.getVida());
        }
        if(jogador1.ataque != 0 && jogador2.ataque != 0 && jogador1.defesa == 0 && jogador2.defesa == 0){
            jogador1.setVidaMenos(jogador2.ataque);
            jogador2.setVidaMenos(jogador1.ataque);

            esperar(500);
            System.out.println("\n" + jogador1.getNome() + " recebeu " + jogador2.ataque + " de dano!\nVida: " + jogador1.getVida());

            Replay.registrar("\n" + jogador1.getNome() + " recebeu " + jogador2.ataque + " de dano!\nVida: " + jogador1.getVida());

            esperar(500);
            System.out.println("\n" + jogador2.getNome() + " recebeu " + jogador1.ataque + " de dano!\nVida: " + jogador2.getVida());

            Replay.registrar("\n" + jogador2.getNome() + " recebeu " + jogador1.ataque + " de dano!\nVida: " + jogador2.getVida());
        }
        if(jogador1.defesa !=0 && jogador2.defesa !=0 && jogador1.ataque == 0 && jogador2.ataque == 0){
            esperar(500);
            System.out.println("\nNenhum dano foi causado nesse turno!");

            Replay.registrar("\nNenhum dano foi causado nesse turno!");
        }

        jogador1.arredondarVida();
        jogador2.arredondarVida();
    }

    public static void verificaTipos(Jogador jogador, boolean temSuporte, List<Carta> suporte){
        for(Carta c : jogador.cartasEmJogo){
            if(c instanceof Ataque){
                jogador.ataque += c.getPoder();
                esperar(500);
                System.out.println("\n" + jogador.getNome() + " atacou!");
            }

            if(c instanceof Defesa){
                jogador.defesa += c.getPoder();
                esperar(500);
                System.out.println("\n" + jogador.getNome() + " se defendeu!");
            }

            if(c instanceof Suporte){
                temSuporte = true;
                suporte.add(c);
            }
        }
    }

    public static void verificaSuporte(boolean temSuporte, Jogador jogador, Jogador jogador2, Carta suporte){
        if(temSuporte){
            esperar(500);
            System.out.println("\n" + jogador.getNome() + " jogou uma carta de suporte!");

            if("AUMENTA_VIDA".equals(suporte.getEfeito())){
                jogador.setVidaMais(suporte.getPoder());
                esperar(500);
                System.out.println("\n" + jogador.getNome() + " aumentou sua vida!\n" + "Vida: " + jogador.getVida());

            }
            else if("AUMENTA_ATAQUE".equals(suporte.getEfeito())){
                if(jogador.ataque == 0){
                    esperar(500);
                    System.out.println("\nCarta de suporte inválida!");
                }
                else{
                    double maior = 0;
                    for(Carta c : jogador.cartasEmJogo){
                        if(c.getPoder() > maior){
                            maior = c.getPoder();
                        }
                    }
                    jogador.ataque = jogador.ataque - maior + (maior*(1+suporte.getPoder()));
                    esperar(500);
                    System.out.println("\n" + jogador.getNome() + " aumentou seu ataque!\n" + "Ataque: " + jogador.ataque);
                }
            }
            else{
                if(jogador2.ataque == 0){
                    esperar(500);
                    System.out.println("\nCarta de suporte inválida!");
                }
                else{
                    jogador2.ataque = jogador2.ataque - (jogador2.ataque * suporte.getPoder());
                    esperar(500);
                    System.out.println("\n" + jogador.getNome() + " enfraqueceu o adversário!");
                }
            }
        }
    }

    private static void esperar(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
