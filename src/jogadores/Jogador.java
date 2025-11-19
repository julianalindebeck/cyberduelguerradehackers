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
    protected List<Carta> maoOriginal = new ArrayList<>();
    protected List<Carta> cartasEmJogo = new ArrayList<>();
    protected Scanner leitura = new Scanner(System.in);

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
        maoOriginal.clear();
        maoOriginal.addAll(mao);
    }
    
    //verifica se existe alguma carta na mão que possa ser jogada com a energia atual
    public boolean verificaJogada(){ 
        for (Carta c : mao){
            if (c.getCusto() <= energia){
                return true;
            } 
        }
        return false;
    }
    
    private void imprimeCartas(int inicio, int fim){
        int j = 1;
        for(int i = inicio; i < fim; i++){
            System.out.println(j + " - " + mao.get(i).getNome());
            j++;
        }
    }
    
    public void jogada(){
        if(!verificaJogada()){
            System.out.println("Energia insuficiente! Sua vez será pulada.");
            energia++;
            return;
        }

        boolean jogadaValida = false;
        while(!jogadaValida){
            System.out.println("\nEscolha o tipo de cartas que deseja jogar:");
            System.out.println("1 - Ataque");
            System.out.println("2 - Defesa");
            System.out.println("3 - Suporte");

            int escolha = leitura.nextInt();
            int inicio = 0, fim = 0;

            if(escolha == 1){
                inicio = 0;
                fim = 4;
            }
            else if(escolha == 2){
                inicio = 4;
                fim = 8;
            }
            else if(escolha == 3){
                inicio = 8; 
                fim = 10;
            }
            else{
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            System.out.println("Suas cartas: ");
            imprimeCartas(inicio, fim);

            System.out.print("Quantas cartas deseja jogar?");
            int qtd = leitura.nextInt();
            List<Integer> indices = new ArrayList<>(); //indices das cartas selecionadas pelo jogador

            for(int i = 0; i < qtd; i++){
                System.out.print("Escolha a carta " + (i + 1) + ": ");
                indices.add(leitura.nextInt());
            }
            Collections.sort(indices, Collections.reverseOrder()); //ordena em ordem decrescente

            //calcula o custo total de energia das cartas selecionadas
            int custoTotal = 0;
            for(int i : indices){
                custoTotal += mao.get(inicio + i - 1).getCusto();
            }

            if(custoTotal > energia){
                System.out.println("Energia insuficiente para jogar essas cartas!");
                continue;
            }
            //diminui a energia do jogador
            energia -= custoTotal;

            //move cartas para cartasEmJogo e tira da mão
            for(int i : indices){
                Carta c = mao.get(inicio + i - 1);
                cartasEmJogo.add(c);
                mao.remove(inicio + i - 1);
            }

            System.out.println("Cartas jogadas!");
            jogadaValida = true;
        }

        //se a mão esvaziou, restaura exatamente como era no início
        if(mao.size() == 0){
            mao.clear();
            mao.addAll(maoOriginal);
            System.out.println("Sua mão foi restaurada!");
        }
        energia++;
    }
}
