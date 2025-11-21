package jogo;

import java.util.ArrayList;
import java.util.List;

import cartas.Ataque;
import cartas.Carta;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Jogador;

public class Consolidacao {
        public static void calcularDano(Jogador jogador1, Jogador jogador2){
            System.out.println("\n*--------------------------------------------------------------------*");
            System.out.println("Todos os jogadores escolheram suas cartas! Hora de calcular os danos!");
            System.out.println("*--------------------------------------------------------------------*");

            double dano = 0;

            boolean temSuporte1 = false, temSuporte2 = false;
            List<Carta> suporte1 = new ArrayList<>();
            List<Carta> suporte2 = new ArrayList<>();

            verificaTipos(jogador1, temSuporte1, suporte1);
            verificaTipos(jogador2, temSuporte2, suporte2);

            for(int i = 0; i < suporte1.size(); i++){
                verificaSuporte(temSuporte1, jogador1, jogador2, suporte1.get(i));
            }

            for(int i = 0; i < suporte1.size(); i++){
                verificaSuporte(temSuporte2, jogador2, jogador1, suporte2.get(i));
            }

            if(ataqueJ1 == 0 && ataqueJ2 == 0){
                System.out.println("\nNenhum dano causado!");
            }
            else if(ataqueJ1 == 0){
                dano = ataqueJ2 - defesaJ1;
                if(dano > defesaJ1){
                    jogador1.setVidaMenos(dano);
                    System.out.println("\n" + jogador2.getNome() + " atacou e feriu " + jogador1.getNome() + ".");
                }
                else{
                    System.out.println("\nNenhum dano causado!");
                }
            }
            else if(ataqueJ2 == 0){
                dano = ataqueJ1 - defesaJ2;
                if(dano > defesaJ2){
                    jogador2.setVidaMenos(dano);
                    System.out.println("\n" + jogador1.getNome() + " atacou e feriu " + jogador2.getNome() + ".");
                }
                else{
                    System.out.println("\nNenhum dano causado!");
                }
            }
            else{
                jogador1.setVidaMenos(ataqueJ2);
                jogador2.setVidaMenos(ataqueJ1);
                System.out.println("\nAmbos os jogadores saíram feridos!");
            }

            jogador1.arredondarVida();
            jogador2.arredondarVida();

            //limpar cartas em jogo?
        }

        public static void verificaTipos(Jogador jogador, boolean temSuporte, List<Carta> suporte){
            int i = 0;

            for(Carta c : jogador.cartasEmJogo){
                if(c instanceof Ataque){
                    jogador.ataque += c.getPoder();
                    System.out.println("\n" + jogador.getNome() + " atacou!");
                }

                if(c instanceof Defesa){
                    jogador.defesa += c.getPoder();
                    System.out.println("\n" + jogador.getNome() + " se defendeu!");
                }

                if(c instanceof Suporte){
                    temSuporte = true;
                    suporte.add(c);
                    i++;
                }
            }
        }

        public static void verificaSuporte(boolean temSuporte, Jogador jogador, Jogador jogador2, Carta suporte){
                if(temSuporte){
                    System.out.println("\n" + jogador.getNome() + " jogou uma carta de suporte!");

                    if(suporte.getEfeito() == "AUMENTA_VIDA"){
                        jogador.setVidaMais(suporte.getPoder());

                        System.out.println("\n" + jogador.getNome() + " aumentou sua vida!\n" + "Vida: " + jogador.getVida());

                    }
                    else if(suporte.getEfeito() == "AUMENTA_ATAQUE"){
                        if(jogador.ataque == 0){
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

                            System.out.println("\n" + jogador.getNome() + " aumentou seu ataque!\n" + "Ataque: " + jogador.ataque);
                        }
                    }
                    else{
                        if(jogador2.ataque == 0){
                            System.out.println("\nCarta de suporte inválida!");
                        }
                        else{
                            jogador2.ataque jogador2.ataque - (jogador2.ataque * suporte.getPoder());
                            System.out.println("\n" + jogador.getNome() + " enfraqueceu o adversário!");
                        }
                    }
                }
            }
}
