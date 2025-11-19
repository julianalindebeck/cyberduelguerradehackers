package jogadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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

            this.maoOriginal.clear();
            this.maoOriginal.addAll(mao);

            return;
        }

        List<Integer> cartasEscolhidas = new ArrayList<>();

        //ataque
        System.out.println("\nSelecione as cartas de ataque: ");
        for(int i = 0; i < ataques.size(); i++){
            System.out.println((i + 1) + " - " + ataques.get(i).getNome() + " - Poder: " + ataques.get(i).getPoder());
        }
        for(int i = 0; i < 4; i++){
            System.out.print("\nEscolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            while(cartaEscolhida < 1 || cartaEscolhida > ataques.size()){
                System.out.print("\nCarta inválida! Escolha novamente a carta " + (i+1) + ": ");
                cartaEscolhida = escolha.nextInt();
            }

            cartasEscolhidas.add(cartaEscolhida);
            //verifica se a carta já foi escolhida
            boolean ehRepetido = verificaDuplicidade(cartaEscolhida, cartasEscolhidas);
            if(ehRepetido){
                i--;
                continue;
            }

            mao.add(ataques.get(cartaEscolhida - 1));
        }

        //defesa
        System.out.println("\nSelecione as cartas de defesa: ");
        for(int i = 0; i < defesas.size(); i++){
            System.out.println((i + 1) + " - " + defesas.get(i).getNome() + " - Poder: " + defesas.get(i).getPoder());
        }
        for(int i = 0; i < 4; i++){
            System.out.print("\nEscolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            while(cartaEscolhida < 1 || cartaEscolhida > defesas.size()){
                System.out.print("\nCarta inválida! Escolha novamente a carta " + (i+1) + ": ");
                cartaEscolhida = escolha.nextInt();
            }

            cartasEscolhidas.add(cartaEscolhida);
            boolean ehRepetido = verificaDuplicidade(cartaEscolhida, cartasEscolhidas);
            if(ehRepetido){
                i--;
                continue;
            }

            mao.add(defesas.get(cartaEscolhida - 1));
        }

        //suporte
        System.out.println("\nSelecione as cartas de suporte: ");
        for(int i = 0; i < suportes.size(); i++){
            System.out.println((i + 1) + " - " + suportes.get(i).getNome() + " - Poder: " + suportes.get(i).getPoder());
        }
        for(int i = 0; i < 2; i++){
            System.out.print("\nEscolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            while(cartaEscolhida < 1 || cartaEscolhida > suportes.size()){
                System.out.print("\nCarta inválida! Escolha novamente a carta " + (i+1) + ": ");
                cartaEscolhida = escolha.nextInt();
            }

            cartasEscolhidas.add(cartaEscolhida);
            boolean ehRepetido = verificaDuplicidade(cartaEscolhida, cartasEscolhidas);
            if(ehRepetido){
                i--;
                continue;
            }

            mao.add(suportes.get(cartaEscolhida - 1));
        }

        this.maoOriginal.clear();
        this.maoOriginal.addAll(mao);
    }
    
    //verifica se o jogador já escolheu a carta
    public boolean verificaDuplicidade(int i, List<Integer> cartasEscolhidas){
        int contador = 0;
        for(int j : cartasEscolhidas){
            if(j == i){
                contador++;
            }
        }
        if(contador > 1){
            System.out.println("\nCarta já escolhida! Escolha outra carta.");
            return true;
        }
        return false;
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
    
    public void jogada(boolean ehBot){
        if(!verificaJogada()){
            System.out.println("\nEnergia insuficiente! Sua vez será pulada.");
            energia++;
            return;
        }

        boolean jogadaValida = false;

        if(ehBot){ //verifica se jogador é bot ou não
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
                    System.out.println("\nSuas cartas de ataque: ");
                }
                else if(escolha == 2){
                    inicio = 4;
                    fim = 8;
                    System.out.println("\nSuas cartas de defesa: ");
                }
                else if(escolha == 3){
                    inicio = 8; 
                    fim = 10;
                    System.out.println("\nSuas cartas de suporte: ");
                }
                else{
                    System.out.println("\nOpção inválida! Tente novamente.");
                    continue;
                }

                imprimeCartas(inicio, fim);

                System.out.print("\nQuantas cartas deseja jogar? \n");
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
                    System.out.println("\nEnergia insuficiente para jogar essas cartas!");
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

                System.out.println("\nCartas jogadas!");
                jogadaValida = true;
            }

            //se a mão esvaziou, restaura exatamente como era no início
            if(mao.size() == 0){
                mao.clear();
                mao.addAll(maoOriginal);
                System.out.println("\nSua mão foi restaurada!");
            }
        }
        else{
            Random rng = new Random();

            while(!jogadaValida){
                //faz escolha aleatória de tipo de carta
                int escolha = rng.nextInt(3) + 1;

                int inicio = 0, fim = 0, limite = 0;

                if(escolha == 1){
                    inicio = 0;
                    fim = 4;
                    limite = 4;
                    System.out.println("\nBot jogando cartas de ataque!");
                }
                else if(escolha == 2){
                    inicio = 4;
                    fim = 8;
                    limite = 4;
                    System.out.println("\nBot jogando cartas de defesa!");
                }
                else{
                    inicio = 8;
                    fim = 10;
                    limite = 2;
                    System.out.println("\nBot jogando cartas de suporte!");
                }

                List<Integer> opcoes = new ArrayList<>();

                //guarda os índices das cartas que o bot pode jogar
                for(int i = 0; i < limite; i++){
                    if(mao.get(inicio + i).getCusto() <= energia){
                        opcoes.add(i + 1);
                    }
                }

                if (opcoes.isEmpty()) {
                    continue;
                }
                
                //faz escolha aleatória de quantidade de cartas a serem jogadas
                int quantidade = rng.nextInt(opcoes.size()) + 1;

                //guarda os índices selecionados
                List<Integer> selecionadas = opcoes.subList(0, quantidade);

                int custoTotal = 0;
                for(int i : selecionadas){
                    custoTotal += mao.get(inicio + i - 1).getCusto();
                }

                if(custoTotal > energia){
                    System.out.println("\nEnergia insuficiente para jogar essas cartas!");
                    continue;
                }

                //diminui a energia do bot
                energia -= custoTotal;

                List<Integer> ordenadas = new ArrayList<>(selecionadas);
                Collections.sort(ordenadas, Collections.reverseOrder()); //ordena decrescente

                for(int i : ordenadas){
                    Carta c = mao.get(inicio + i - 1);
                    cartasEmJogo.add(c);
                    mao.remove(inicio + i - 1);
                }

                System.out.println("\nCartas do Bot jogadas:");
                for(Carta c : cartasEmJogo){
                    System.out.println(c.getNome());
                }

                jogadaValida = true;
            }

            if(mao.size() == 0){
                mao.clear();
                mao.addAll(maoOriginal);
                System.out.println("\nA mão do Bot foi restaurada!");
            }
        }

        energia++;
        if(energia > 10){
            energia = 10;
        }
    }
}
