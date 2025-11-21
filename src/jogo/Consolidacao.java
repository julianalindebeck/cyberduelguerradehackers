package jogo;

import cartas.Ataque;
import cartas.Carta;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Jogador;

public class Consolidacao {
        public static void calcularDano(Jogador jogador1, Jogador jogador2){
            double ataqueJ1 = 0;
            double ataqueJ2 = 0;
            double defesaJ1 = 0;
            double defesaJ2 = 0;
            double dano = 0;

            System.out.println("\n*--------------------------------------------------------------------*");
            System.out.println("Todos os jogadores escolheram suas cartas! Hora de calcular os danos!");
            System.out.println("*--------------------------------------------------------------------*");


            for(Carta c : jogador1.cartasEmJogo){
                if(c instanceof Ataque){
                   ataqueJ1 +=c.getPoder();
                   System.out.println("\n" + jogador1.getNome() + " atacou!");
                }
                if(c instanceof Defesa){
                    defesaJ1+=c.getPoder();
                    System.out.println("\n" + jogador1.getNome() + " se defendeu!");
                }
            }
            for(Carta c : jogador2.cartasEmJogo){
                if(c instanceof Ataque){
                   ataqueJ2+=c.getPoder();
                   System.out.println("\n" + jogador2.getNome() + " atacou!");
                }
                if(c instanceof Defesa){
                    defesaJ2+=c.getPoder();
                    System.out.println("\n" + jogador2.getNome() + " se defendeu!");
                }
            }

            boolean temSuporte = false;
            Carta suporte = null;

            //jogador1
            for(Carta c : jogador1.cartasEmJogo){
                if(c instanceof Suporte){
                    temSuporte = true;
                    suporte = c;
                    break;
                }
            }

            if(temSuporte){

                System.out.println("\n" + jogador1.getNome() + " jogou uma carta de suporte!");

                if(suporte.getEfeito() == "AUMENTA_VIDA"){
                    jogador1.setVidaMais(suporte.getPoder());

                    System.out.println("\n" + jogador1.getNome() + " aumentou sua vida!\n" + "Vida: " + jogador1.getVida());

                }
                else if(suporte.getEfeito() == "AUMENTA_ATAQUE"){
                    if(ataqueJ1 == 0){
                        System.out.println("\nCarta de suporte inválida!");
                    }
                    else{
                        double maior = 0;
                        for(Carta c : jogador1.cartasEmJogo){
                            if(c.getPoder() > maior){
                                maior = c.getPoder();
                            }
                        }
                        ataqueJ1 = ataqueJ1 - maior + (maior*(1+suporte.getPoder()));

                        System.out.println("\n" + jogador1.getNome() + " aumentou seu ataque!\n" + "Ataque: " + ataqueJ1);
                    }
                }
                else{
                    if(ataqueJ2 == 0){
                        System.out.println("\nCarta de suporte inválida!");
                    }
                    else{
                        ataqueJ2 = ataqueJ2 - (ataqueJ2 * suporte.getPoder());
                        System.out.println("\n" + jogador1.getNome() + " enfraqueceu o adversário!");
                    }
                }
            }

            suporte = null;
            temSuporte = false;

            //jogador 2
            for(Carta c : jogador2.cartasEmJogo){
                if(c instanceof Suporte){
                    temSuporte = true;
                    suporte = c;
                    break;
                }
            }

            if(temSuporte){

                System.out.println("\n" + jogador2.getNome() + " jogou uma carta de suporte!");

                if(suporte.getEfeito() == "AUMENTA_VIDA"){
                    jogador2.setVidaMais(suporte.getPoder());

                    System.out.println("\n" + jogador2.getNome() + " aumentou sua vida!\n" + "Vida: " + jogador2.getVida());
                }
                else if(suporte.getEfeito() == "AUMENTA_ATAQUE"){
                    if(ataqueJ2 == 0){
                        System.out.println("\n Carta de suporte inválida!");
                    }
                    else{
                        double maior = 0;
                        for(Carta c : jogador2.cartasEmJogo){
                            if(c.getPoder() > maior){
                                maior = c.getPoder();
                            }
                        }
                        ataqueJ2 = ataqueJ2 - maior + (maior*(1+suporte.getPoder()));

                        System.out.println("\n" + jogador2.getNome() + " aumentou seu ataque!\n" + "Ataque: " + ataqueJ2);

                    }
                }
                else{
                    if(ataqueJ1 == 0){
                        System.out.println("\nCarta de suporte inválida!");
                    }
                    else{
                        ataqueJ1 = ataqueJ1 - (ataqueJ1 * suporte.getPoder());
                        System.out.println("\n" + jogador1.getNome() + " enfraqueceu o adversário!");
                    }
                }
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

            //limpar cartas em jogo
        }
}
