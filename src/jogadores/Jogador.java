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
            System.out.println((i + 1) + " - " + ataques.get(i).getNome() + " - Poder: " + ataques.get(i).getPoder() + " (Custo: " + ataques.get(i).getCusto() + ")");
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
                System.out.print("\nCarta inválida! Escolha novamente a carta " + (i+1) + ": " + "\n");
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
    
    private void imprimeCartas(int inicio, int fim, int val){
        int cont = val;

        for(int i = inicio; i < fim; i++){
            if (i < mao.size()){
                System.out.println(cont + " - " + mao.get(i).getNome());
                cont++;
            }
        }   
    }
    
    public void jogada(boolean ehBot){
        if(!verificaJogada()){
            System.out.println("\nEnergia insuficiente! Sua vez será pulada.");
            energia++;
            return;
        }

        boolean jogadaValida = false;

        if(!ehBot){ //verifica se jogador é bot ou não
            while(!jogadaValida){
                System.out.println("\nEscolha o tipo de cartas que deseja jogar:");
                System.out.println("(1) Ataque");
                System.out.println("(2) Defesa");

                int escolha = leitura.nextInt();
                int inicio = 0, fim = 0;

                if(escolha == 1){
                    inicio = 0;
                    fim = 4;
                    System.out.println("\nVocê escolheu cartas de ataque!");
                }
                else if(escolha == 2){
                    inicio = 4;
                    fim = 8;
                    System.out.println("\nVocê escolheu cartas de defesa!");
                }
                else{
                    System.out.println("\nOpção inválida! Tente novamente.");
                    continue;
                }

                System.out.println("\nDeseja também jogar cartas de suporte? \n(1) Sim \n(2) Não");
                int desejaSuporte = leitura.nextInt();

                int inicio2 = 8, fim2 = 10;
                boolean incluirSuporte = (desejaSuporte == 1);

                System.out.println("\nCartas do tipo escolhido: ");
                imprimeCartas(inicio, fim, 1);
                if(incluirSuporte){
                    System.out.println("\nCartas de suporte: ");
                    imprimeCartas(inicio2, fim2, (fim - inicio) + 1);
                }

                int maxCartas = (fim - inicio) + (incluirSuporte?2:0);
                int qtdVerifica = 1;

                do{
                    if(qtdVerifica <= 0 || qtdVerifica > maxCartas){
                        System.out.print("\nOpção inválida! Quantas cartas deseja jogar? \n");
                    }
                    else{
                        System.out.print("\nQuantas cartas deseja jogar? \n");
                    }
                    int qtd = leitura.nextInt();
                    qtdVerifica = qtd;
                }while(qtdVerifica < 0 || qtdVerifica > maxCartas);

                List<Integer> indices = new ArrayList<>(); //indices das cartas selecionadas pelo jogador

                for(int i = 0; i < qtdVerifica; i++){
                int escolhaCarta = 1;
                    do{
                        if(escolhaCarta <= 0 || escolhaCarta > qtdVerifica){
                            System.out.print("\nOpção inválida! Escolha novamente a carta " + (i + 1) + ": ");
                        }
                        else{
                            System.out.print("Escolha a carta " + (i + 1) + ": ");
                        }
                        escolhaCarta = leitura.nextInt();
                    }while(escolhaCarta < 0 || escolhaCarta > maxCartas);
                
                    indices.add(escolhaCarta);
                }
                Collections.sort(indices, Collections.reverseOrder()); //ordena em ordem decrescente

                //calcula o custo total de energia das cartas selecionadas
                int custoTotal = 0;
                for(int i : indices){
                    int j;

                    if(i <= (fim - inicio)){
                        j = inicio + i - 1;
                    }
                    else{
                        j = inicio2 + (i - (fim - inicio)) - 1;
                    }

                    custoTotal += mao.get(j).getCusto();
                }

                if(custoTotal > energia){
                    System.out.println("\nEnergia insuficiente para jogar essas cartas!");
                    continue;
                }

                //diminui a energia do jogador
                energia -= custoTotal;

                //move cartas para cartasEmJogo e tira da mão
                for(int i : indices){
                    int j;

                    if(i <= (fim - inicio)){
                        j = inicio + i - 1;
                    }
                    else{
                        j = inicio2 + (i - (fim - inicio)) - 1;
                    }

                    cartasEmJogo.add(mao.get(j));
                    mao.remove(j);
                }
                System.out.println("\n*---------------*");
                System.out.println("Cartas jogadas!");
                System.out.println("*---------------*");

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
                int escolha = rng.nextInt(2) + 1;

                int inicio = 0, fim = 0;

                if(escolha == 1){
                    inicio = 0;
                    fim = 4;
                    System.out.println("\nBot jogando cartas de ataque!");
                }
                else{
                    inicio = 4;
                    fim = 8;
                    System.out.println("\nBot jogando cartas de defesa!");
                }
                
                boolean incluirSuporte = rng.nextBoolean();
                int inicio2 = 8, fim2 = 10;

                if(incluirSuporte){
                    System.out.println("\nBot decidiu incluir cartas de suporte!");
                }
                else{
                    System.out.println("\nBot decidiu não incluir suporte!");
                }

                int qtdTipo = 0;
                for(int i = inicio; i < fim; i++){
                    if(i < mao.size()){
                        qtdTipo++;
                    }
                }

                int qtdSuporte = 0;
                if(incluirSuporte){
                    for(int i = inicio2; i < fim2; i++){
                        if(i < mao.size()) qtdSuporte++;
                    }
                }

                int maxCartas = qtdTipo + qtdSuporte;
                if(maxCartas == 0) continue;

                int qtdVerifica = rng.nextInt(maxCartas) + 1;

                List<Integer> opcoes = new ArrayList<>();

                //guarda os índices das cartas que o bot pode jogar
                while(opcoes.size() < qtdVerifica){
                    int escolhaCarta = rng.nextInt(maxCartas) + 1;

                    if(!opcoes.contains(escolhaCarta)){
                        opcoes.add(escolhaCarta);
                    }
                }

                //ordena decrescente
                Collections.sort(opcoes, Collections.reverseOrder());

                int custoTotal = 0;
                for(int i : opcoes){
                    int j;

                    if(i <= qtdTipo){  
                        j = inicio + i - 1; 
                    }
                    else{  
                        j = inicio2 + (i - qtdTipo) - 1;
                    }

                    custoTotal += mao.get(j).getCusto();
                }

                if(custoTotal > energia){
                    System.out.println("\nEnergia insuficiente para jogar essas cartas!");
                    continue;
                }

                //diminui a energia do bot
                energia -= custoTotal;

                for(int i : opcoes){
                    int j;

                    if(i <= qtdTipo){
                        j = inicio + i - 1;
                    }
                    else{
                        j = inicio2 + (i - qtdTipo) - 1;
                    }
                    
                    cartasEmJogo.add(mao.get(j));
                    mao.remove(j);
                }

                System.out.println("\n*--------------------*");
                System.out.println("Cartas do Bot jogadas: ");

                for(Carta c : cartasEmJogo){
                    System.out.println(c.getNome());
                }

                System.out.println("*--------------------*");

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
