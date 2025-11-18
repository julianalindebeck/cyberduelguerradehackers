package jogadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import cartas.Ataque;
import cartas.Carta;
import cartas.Defesa;
import cartas.Suporte;

public abstract class Jogador {
    protected String nome;
    protected int id;
    protected double vida = 100;
    protected double energia = 10;
    protected List<Carta> mao = new ArrayList<>();

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

    public void selecionaCartas(
        Scanner escolha,
        List<Ataque> ataques,
        List<Defesa> defesas,
        List<Suporte> suportes,
        boolean ehAleatorio
    ){
        if(ehAleatorio){ //embaralha as cartas e tira as primeiras
            Collections.shuffle(ataques);
            Collections.shuffle(defesas);
            Collections.shuffle(suportes);

            this.mao.addAll(ataques.subList(0, 4));
            this.mao.addAll(defesas.subList(0, 4));
            this.mao.addAll(suportes.subList(0, 2));

            return;
        }

        //ataque
        System.out.println("Selecione as cartas de ataque: ");
        for(int i = 0; i < ataques.size(); i++){
            System.out.println((i + 1) + " - " + ataques.get(i).getNome() + " - Poder: " + ataques.get(i).getPoder());
        }
        for(int i = 0; i < 4; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            mao.add(ataques.get(cartaEscolhida));
        }

        //defesa
        System.out.println("Selecione as cartas de defesa: ");
        for(int i = 0; i < defesas.size(); i++){
            System.out.println((i + 1) + " - " + defesas.get(i).getNome() + " - Poder: " + defesas.get(i).getPoder());
        }
        for(int i = 0; i < 4; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            mao.add(defesas.get(cartaEscolhida));
        }

        //suporte
        System.out.println("Selecione as cartas de suporte: ");
        for(int i = 0; i < suportes.size(); i++){
            System.out.println((i + 1) + " - " + suportes.get(i).getNome() + " - Poder: " + suportes.get(i).getPoder());
        }
        for(int i = 0; i < 2; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            mao.add(suportes.get(cartaEscolhida));
        }
    }
}
