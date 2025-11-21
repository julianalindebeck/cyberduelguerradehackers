package jogo;

import cartas.Ataque;
import cartas.Carta;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Jogador;

public class Consolidacao {
        public void calcularDano(Jogador jogador1, Jogador jogador2){
            double ataqueJ1 = 0;
            double ataqueJ2 = 0;
            double defesaJ1 = 0;
            double defesaJ2 = 0;
            double dano = 0;
            for(Carta c : jogador1.cartasEmJogo){
                if(c instanceof Ataque){
                   ataqueJ1 +=c.getPoder(); 
                }
                if(c instanceof Defesa){
                    defesaJ1+=c.getPoder();
                }
            }
            for(Carta c : jogador2.cartasEmJogo){
                if(c instanceof Ataque){
                   ataqueJ2+=c.getPoder(); 
                }
                if(c instanceof Defesa){
                    defesaJ2+=c.getPoder();
                }
            }
            boolean temSuporte = false;
            Carta suporte = null;
            for(Carta c : jogador1.cartasEmJogo){
                if(c instanceof Suporte){
                    temSuporte = true;
                    suporte = c;
                    break;
                }
            }
            if(suporte.getEfeito() == "AUMENTA_VIDA"){
                jogador1.setVida(suporte.getPoder());
                if(jogador1.getVida() > 100){
                    jogador1.setVida(100);
                }
            }
            else if(suporte.getEfeito() == "AUMENTA_ATAQUE"){
                if(ataqueJ1 == 0){
                    System.out.println("\n Carta de suporte inválida!");
                }
                else{
                    double maior = 0;
                    for(Carta c : jogador1.cartasEmJogo){
                        if(c.getPoder() > maior){
                            maior = c.getPoder();
                        }
                    }
                    ataqueJ1 = ataqueJ1 - maior + (maior*(1+suporte.getPoder()));

                }
            }
            else{
               if(ataqueJ2 == 0){
                System.out.println("\nCarta de suporte inválida!");
               }
               else{
                ataqueJ2 = ataqueJ2 - (ataqueJ2 * suporte.getPoder());
               }
            }
            suporte = null;
            temSuporte = false;

            //Jogador 2
            for(Carta c : jogador2.cartasEmJogo){
                if(c instanceof Suporte){
                    temSuporte = true;
                    suporte = c;
                    break;
                }
            }
            if(suporte.getEfeito() == "AUMENTA_VIDA"){
                jogador2.setVida(suporte.getPoder());
                if(jogador2.getVida() > 100){
                    jogador2.setVida(100);
                }
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

                }
            }
            else{
               if(ataqueJ1 == 0){
                System.out.println("\nCarta de suporte inválida!");
               }
               else{
                ataqueJ1 = ataqueJ1 - (ataqueJ1 * suporte.getPoder());
               }
            }

            if(ataqueJ1 == 0 && ataqueJ2 == 0){
                System.out.println("\nNenhum dano causado!");
            }
            else if(ataqueJ1 == 0){
                dano = ataqueJ2 - defesaJ1;
                if(dano > defesaJ1){
                    jogador1.setVida(jogador1.getVida() - dano);
                }
                else{
                    System.out.println("\nNenhum dano causado!");
                }
            }
            else if(ataqueJ2 == 0){
                dano = ataqueJ1 - defesaJ2;
                if(dano > defesaJ2){
                    jogador2.setVida(jogador2.getVida() - dano);
                }
                else{
                    System.out.println("\nNenhum dano causado!");
                }
            }
            else{
                jogador1.setVida(jogador1.getVida()- ataqueJ2);
                jogador2.setVida(jogador2.getVida()- ataqueJ1);
            }

        }

}
