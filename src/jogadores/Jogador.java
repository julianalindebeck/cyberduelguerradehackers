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
    protected Scanner leitura = new Scanner(System.in);

    public List<Carta> cartasEmJogo = new ArrayList<>();
    public double ataque = 0;
    public double defesa = 0;

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

    public double getEnergia(){
        return energia;
    }

    public double getVida(){
        return vida;
    }

    public void setVidaMais(double poder){
        this.vida += poder;

        if(this.vida > 100){
            this.vida = 100;
        }
    }

    public void setVidaMenos(double poder){
        this.vida -= poder;

        if(this.vida < 0){
            this.vida = 0;
        }
    }

    public void arredondarVida(){
        if(this.vida % 10 != 0){
            int resto = (int)(this.vida % 10);

            if (resto >= 5) {
                this.vida += (10 - resto);
            }

            this.vida -= resto;
        }

        if(this.vida > 100){
            this.vida = 100;
        }
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
            System.out.println((i + 1) + " - " + ataques.get(i).getNome() + " - Poder: " + ataques.get(i).getPoder() + " - Custo: " + ataques.get(i).getCusto());
        }
        System.out.print("\n");
        for(int i = 0; i < 4; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            while(cartaEscolhida < 1 || cartaEscolhida > ataques.size()){
                System.out.print("\nCarta inválida! Escolha novamente a carta " + (i+1) + ": " + "\n");
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

        cartasEscolhidas.clear();

        //defesa
        System.out.println("\nSelecione as cartas de defesa: ");
        for(int i = 0; i < defesas.size(); i++){
            System.out.println((i + 1) + " - " + defesas.get(i).getNome() + " - Poder: " + defesas.get(i).getPoder() + " (Custo: " + defesas.get(i).getCusto() + ")");
        }
        System.out.print("\n");
        for(int i = 0; i < 4; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            while(cartaEscolhida < 1 || cartaEscolhida > defesas.size()){
                System.out.print("\nCarta inválida! Escolha novamente a carta " + (i+1) + ": " + "\n");
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

        cartasEscolhidas.clear();

        //suporte
        System.out.println("\nSelecione as cartas de suporte: ");
        for(int i = 0; i < suportes.size(); i++){
            System.out.println((i + 1) + " - " + suportes.get(i).getNome() + " - Poder: " + suportes.get(i).getPoder() + " (Custo: " + suportes.get(i).getCusto() + ")");
        }
        System.out.print("\n");
        for(int i = 0; i < 2; i++){
            System.out.print("Escolha a carta " + (i+1) + ": ");
            int cartaEscolhida = escolha.nextInt();
            while(cartaEscolhida < 1 || cartaEscolhida > suportes.size()){
                System.out.print("\nCarta inválida! \nEscolha novamente a carta " + (i+1) + ": " + "\n");
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
            System.out.println("\nCarta já escolhida! \nEscolha outra carta.\n ");
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
    
    private void imprimeCartas(){
        for(int i = 0; i < mao.size(); i++){
            System.out.println((i + 1) + " - " + mao.get(i).getNome());
            System.out.print("TIPO: " + mao.get(i).getTipo() + " | PODER: " + mao.get(i).getPoder() + " | CUSTO: " + mao.get(i).getCusto());

            if(mao.get(i) instanceof Suporte){
                System.out.print(" | EFEITO: " + mao.get(i).getEfeito());
            }
           
            System.out.println("\n");
        }   
    }

    //reseta os atributos do jogador
    public void resetaTurno(){
        this.ataque = 0;
        this.defesa = 0;
        this.cartasEmJogo.clear();
    }

    public void jogada(boolean ehBot){

        //verifica se o jogador pode jogar alguma carta
        if(!verificaJogada()){
            System.out.println("\nEnergia insuficiente! Sua vez será pulada.");
            energia++;
            return;
        }

        boolean jogadaValida = false;

        if(!ehBot){ //verifica se jogador é bot ou não
            System.out.println("\nVocê deseja passar sua vez? \n(1) Sim \n(2) Não");
            int passarVez = leitura.nextInt();
            while(passarVez != 1 && passarVez != 2){
                System.out.println("\nOpção inválida! Você deseja passar sua vez? \n(1) Sim \n(2) Não");
                passarVez = leitura.nextInt();
            }
            if(passarVez == 1){
                System.out.println("\nVocê passou sua vez!");
                energia++;
                return;
            }
            while(!jogadaValida){
                System.out.println("\nHora de fazer sua jogada! Escolha suas cartas:\n");
                imprimeCartas();

                System.out.print("Quantas cartas deseja jogar?\n");
                int qtd = leitura.nextInt();
                
                //verifica se a quantidade de cartas escolhidas pelo jogador é válida
                while(qtd <= 0 || qtd > mao.size()){
                    System.out.print("\nOpção inválida! Quantas cartas deseja jogar?\n");
                    qtd = leitura.nextInt();
                }

                List<Integer> indices = new ArrayList<>(); //indices das cartas selecionadas pelo jogador

                for(int i = 0; i < qtd; i++){                    
                    System.out.print("Escolha a carta " + (i + 1) + ": ");
                    int escolhaCarta = leitura.nextInt();

                    while(escolhaCarta <= 0 || escolhaCarta > mao.size() || indices.contains(escolhaCarta)){
                        System.out.print("\nCarta inválida! Escolha novamente a carta " + (i + 1) + ": ");
                        escolhaCarta = leitura.nextInt();
                    }
                
                    indices.add(escolhaCarta);
                }

                Collections.sort(indices, Collections.reverseOrder()); //ordena em ordem decrescente

                //calcula o custo total de energia das cartas selecionadas
                int custoTotal = 0;
                for(int i : indices){
                    custoTotal += mao.get(i-1).getCusto();
                }

                if(custoTotal > energia){
                    System.out.println("\nEnergia insuficiente para jogar essas cartas!");
                    continue;
                }

                //diminui a energia do jogador
                energia -= custoTotal;

                //move cartas para cartasEmJogo e tira da mão
                for(int i : indices){
                    cartasEmJogo.add(mao.get(i-1));
                    mao.remove(i-1);
                }

                System.out.println("\n*---------------*");
                System.out.println("Cartas jogadas!");
                System.out.println("*---------------*");

                jogadaValida = true;
            }

            //se a mão esvaziou, restaura exatamente como era no início
            if(mao.isEmpty()){
                mao.clear();
                mao.addAll(maoOriginal);
                System.out.println("\nSua mão foi restaurada!");
            }
        }
        else{
            Random rng = new Random();

            while(!jogadaValida){
                List<Carta> cartasValidas = new ArrayList<>();
                for(Carta c : mao){
                    if(c.getCusto() <= energia){
                        cartasValidas.add(c);
                    }
                }
                //faz escolha aleatória de quantas cartas o bot vai jogar
                int qtd = rng.nextInt(cartasValidas.size()) + 1;

                List<Carta> cartasEscolhidas = new ArrayList<>();
                while(cartasEscolhidas.size() < qtd){
                    Carta carta = cartasValidas.get(rng.nextInt(cartasValidas.size()));
                    if(!cartasEscolhidas.contains(carta)){
                        cartasEscolhidas.add(carta);
                    }
                }

                // calcula custo total
                int custoTotal = 0;
                for(Carta c : cartasEscolhidas){
                    custoTotal += c.getCusto();
                }

                if(custoTotal > energia){
                    esperar(500);
                    System.out.println("\nEnergia insuficiente para jogar essas cartas!");
                    continue;
                }

                // gasta energia e move cartas para cartasEmJogo
                energia -= custoTotal;
                for(Carta c : cartasEscolhidas){
                    cartasEmJogo.add(c);
                    mao.remove(c);
                }
                
                esperar(500);
                System.out.println("\n*----------------------------------*");
                System.out.println("Cartas do Bot jogadas: ");

                for(Carta c : cartasEmJogo){
                    esperar(500);
                    System.out.println(c.getNome());
                    System.out.print("TIPO: " + c.getTipo() + " | PODER: " + c.getPoder() + " | CUSTO: " + c.getCusto());
                    if(c instanceof Suporte){
                        System.out.print(" | EFEITO: " + c.getEfeito());
                    }
                    System.out.println("\n");
                }

                System.out.println("*----------------------------------*");

                jogadaValida = true;
                cartasValidas.clear();
            }

            if(mao.isEmpty()){
                mao.clear();
                mao.addAll(maoOriginal);
                esperar(500);
                System.out.println("\nA mão do Bot foi restaurada!");
            }
        }

        //aumenta a energia
        energia++;
        if(energia > 10){
            energia = 10;
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
