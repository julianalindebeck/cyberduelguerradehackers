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
        esperar(700);
        System.out.println("\n*--------------------------------------------------------------------*\nTodos os jogadores escolheram suas cartas! Hora de calcular os danos!\n*--------------------------------------------------------------------*");

        Replay.registrar("\nCartas escolhidas! Hora de calcular os danos!");

        double dano = 0;

        List<Carta> suporte1 = new ArrayList<>();
        List<Carta> suporte2 = new ArrayList<>();

        verificaTipos(jogador1, suporte1);
        verificaTipos(jogador2, suporte2);

        //verifica se o jogador jogou uma carta de suporte e aplica seu efeito
        for(Carta c : suporte1){
            verificaSuporte(jogador1, jogador2, c);
        }

        for(Carta c : suporte2){
            verificaSuporte(jogador2, jogador1, c);
        }

        //verifica se algum jogador passou a vez
        passouAVez(jogador1);
        passouAVez(jogador2);

        //jogador 1 ataca e jogador 2 defende
        if(jogador1.ataque != 0 && jogador2.defesa != 0){
            dano = jogador1.ataque - jogador2.defesa;

            if(dano > 0){
                jogador2.setVidaMenos(dano);
                esperar(800);
                System.out.println("\n" + jogador2.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador2.getVida());

                Replay.registrar("\n" + jogador2.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador2.getVida());
            }
            else{
                esperar(800);
                System.out.println("\n" + jogador2.getNome() + " conseguiu se defender!");

                Replay.registrar("\n" + jogador2.getNome() + " conseguiu se defender!");
            }
        }
        
        //jogador 2 ataca e jogador 1 defende
        if(jogador2.ataque != 0 && jogador1.defesa != 0){
            dano = jogador2.ataque - jogador1.defesa;

            if(dano > 0){
                jogador1.setVidaMenos(dano);
                esperar(800);
                System.out.println("\n" + jogador1.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador1.getVida());

                Replay.registrar("\n" + jogador1.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador1.getVida());
            }
            else{
                esperar(800);
                System.out.println("\n" + jogador1.getNome() + " conseguiu se defender!");

                Replay.registrar("\n" + jogador1.getNome() + " conseguiu se defender!");
            }
        }
        
        //jogador 1 ataca e jogador 2 não defende
        if(jogador1.ataque !=0 && jogador2.defesa == 0){
            dano = jogador1.ataque;
            jogador2.setVidaMenos(dano);

            esperar(800);
            System.out.println("\n" + jogador2.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador2.getVida());

            Replay.registrar("\n" + jogador2.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador2.getVida());
        }
        
        //jogador 2 ataca e jogador 1 não defende
        if(jogador2.ataque !=0 && jogador1.defesa == 0){
            dano = jogador2.ataque;
            jogador1.setVidaMenos(dano);

            esperar(800);
            System.out.println("\n" + jogador1.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador1.getVida());

            Replay.registrar("\n" + jogador1.getNome() + " recebeu " + dano + " de dano! | Vida: " + jogador1.getVida());
        }

        //jogadores se defendem mas não atacam
        if(jogador1.defesa !=0 && jogador2.defesa !=0 && jogador1.ataque == 0 && jogador2.ataque == 0){
            esperar(800);
            System.out.println("\nNenhum dano foi causado nesse turno!");

            Replay.registrar("\nNenhum dano foi causado nesse turno!");
        }
        if(jogador1.vida > 100){
            jogador1.vida = 100;
        }
        if(jogador2.vida > 100){
            jogador2.vida = 100;
        }
    }

    //verifica quais tipos de cartas os jogadores jogaram
    public static void verificaTipos(Jogador jogador, List<Carta> suporte){
        for(Carta c : jogador.cartasEmJogo){
            if(c instanceof Ataque){
                jogador.ataque += c.getPoder();
                esperar(800);
                System.out.println("\n" + jogador.getNome() + " atacou!");
            }

            if(c instanceof Defesa){
                jogador.defesa += c.getPoder();
                esperar(800);
                System.out.println("\n" + jogador.getNome() + " se defendeu!");
            }

            if(c instanceof Suporte){
                suporte.add(c);
            }
        }
    }

    //aplica efeitos da carta de suporte
    public static void verificaSuporte(Jogador jogador, Jogador jogador2, Carta suporte){
        esperar(800);
        System.out.println("\n" + jogador.getNome() + " jogou uma carta de suporte!");

        if("Aumenta vida".equals(suporte.getEfeito())){
            jogador.setVidaMais(suporte.getPoder());
            esperar(800);
            System.out.println("\n" + jogador.getNome() + " aumentou sua vida! | + Vida: " + jogador.getVida());
            return;
        }

        if("Aumenta ataque".equals(suporte.getEfeito())){
            if(jogador.ataque == 0){
                esperar(800);
                System.out.println("\nCarta de suporte de " + jogador.getNome() + " para aumentar seu ataque inválida! | Motivo: Nenhuma carta de ataque jogada.");
                return;
            }
        double maior = 0;
        for(Carta c : jogador.cartasEmJogo){
            if("ATAQUE".equals(c.getTipo())){ //pega a maior carta somente dos ataques
                if(c.getPoder() > maior){
                    maior = c.getPoder();
                }
            }
        }

        jogador.ataque -= maior;
        jogador.ataque = jogador.ataque + (maior * (1 + suporte.getPoder()));

        esperar(800);
        System.out.println("\n" + jogador.getNome() + " aumentou seu ataque! | + Ataque: " + jogador.ataque);
        return;
}

        if("Diminui ataque".equals(suporte.getEfeito())){
            if(jogador2.ataque == 0){
                esperar(800);
                System.out.println("\nCarta de suporte de " + jogador.getNome() + " para diminuir ataque inimigo inválida! | Motivo: O adversário não atacou.");
                return;
            }
            double reducaoSuporte = jogador2.ataque * suporte.getPoder();
            jogador2.ataque -= reducaoSuporte;

            if(jogador2.ataque < 0){
                jogador2.ataque = 0;
            }

            esperar(800);
            System.out.println("\n" + jogador.getNome() + " enfraqueceu o adversário!");
        }
    }

    //método para verificar se algum jogador passou a vez
    private static void passouAVez(Jogador jogador){
        if(jogador.cartasEmJogo.isEmpty()){
            System.out.println("\n"+jogador.getNome() + " não jogou nesse turno!");
            Replay.registrar("\n"+jogador.getNome() + " não jogou nesse turno!");
        }
    }

    private static void esperar(long ms){
        try{
            Thread.sleep(ms);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
