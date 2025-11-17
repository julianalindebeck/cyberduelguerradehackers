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

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

    public static List<Carta> selecionaCartas(
        Scanner escolha,
        List<Ataque> ataques,
        List<Defesa> defesas,
        List<Suporte> suportes,
        boolean ehAleatorio
    ){
        List<Carta> mao = new ArrayList<>();

        if(ehAleatorio){
            Collections.shuffle(ataques);
            Collections.shuffle(defesas);
            Collections.shuffle(suportes);

            mao.addAll(ataques.subList(0, 4));
            mao.addAll(defesas.subList(0, 4));
            mao.addAll(suportes.subList(0, 2));

            return mao;
        }

        System.out.println("Selecione as cartas de ataque: ");

        for(int i = 0; i < ataques.size(); i++){
            System.out.println((i + 1) + " -> " + ataques.get(i).getNome());
        }

        for(int i = 0; i < 4; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            mao.add(ataques.get(cartaEscolhida));
        }

        System.out.println("Selecione as cartas de defesa: ");

        for(int i = 0; i < defesas.size(); i++){
            System.out.println((i + 1) + " -> " + defesas.get(i).getNome());
        }

        for(int i = 0; i < 4; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            mao.add(defesas.get(cartaEscolhida));
        }

        System.out.println("Selecione as cartas de suporte: ");

        for(int i = 0; i < suportes.size(); i++){
            System.out.println((i + 1) + " -> " + suportes.get(i).getNome());
        }

        for(int i = 0; i < 2; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            mao.add(suportes.get(cartaEscolhida));
        }

        return mao;
    }
}
