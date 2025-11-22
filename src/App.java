import java.io.File;
import java.util.List;
import java.util.Scanner;

import cartas.Ataque;
import cartas.Carta;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Bot;
import jogadores.Hacker;
import jogadores.Jogador;
import jogo.Jogo;
import leituraDeArquivos.Leitor;
import replay.Replay;

public class App {
    static Scanner leitura = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
        System.out.println("\n*--------------------------------------------------*");
        System.out.println("Seja bem-vinda(o) ao CYBER DUELS: GUERRA DE HACKERS!");
        System.out.println("*--------------------------------------------------*\n");

        //lê e cria as listas
        List<Ataque> ataques = Leitor.listaDeAtaques();
        List<Defesa> defesas = Leitor.listaDeDefesas();
        List<Suporte> suportes = Leitor.listaDeSuportes();

        //realiza a leitura do modo de jogo
        esperar(500);
        System.out.println("Escolha o modo de jogo:\n(1) Hacker vs Bot\n(2) Hacker vs Hacker");
        int modoDeJogo = lerInteiro(); //passa o valor lido para modoDeJogo

        while(modoDeJogo != 1 && modoDeJogo != 2){
            System.out.println("\nOpção inválida! Digite (1) para Hacker vs Bot ou (2) para Hacker vs Hacker.");
            modoDeJogo = lerInteiro();
        } //verifica se o modo de jogo escolhido é válido

        //criação do hacker 1
        Hacker hacker1 = criarHacker(1);
        escolhaDeCartas(hacker1, ataques, defesas, suportes);

        //imprime mão do hacker 1
        esperar(500);
        System.out.println("\nMão de " + hacker1.getNome() + ": ");
        int i = 1;
        for(Carta c : hacker1.getMao()){
            esperar(700);
            System.out.print(i + " - " + c.getNome() + "\nTIPO: " + c.getTipo() + " | PODER: " + c.getPoder() + " | CUSTO: " + c.getCusto());
            
            if(c instanceof Suporte){
                System.out.print(" | EFEITO: " + c.getEfeito());
            }
           
            System.out.println("\n");

            i++;
        }

        Jogador jogador1 = hacker1;
        Jogador jogador2;

        esperar(500);
        System.out.println("*----------------------*\nHora do segundo jogador!\n*----------------------*");

        //verifica se jogo é contra bot ou outro hacker
        if(modoDeJogo == 1){
            Bot bot = new Bot();
            bot.selecionaCartas(leitura, ataques, defesas, suportes, true);
            jogador2 = bot;

            esperar(500);
            System.out.println("\n* Hacker 2 criado: " + bot.getNome() + " | ID: " + bot.getId() + " *");

            //imprime mão do bot
            esperar(500);
            System.out.println("\nMão do Bot:");
            i = 1;
            for(Carta c : jogador2.getMao()){
                esperar(700);
                System.out.print(i + " - " + c.getNome() + "\nTIPO: " + c.getTipo() + " | PODER: " + c.getPoder() + " | CUSTO: " + c.getCusto());
                
                if(c instanceof Suporte){
                    System.out.print(" | EFEITO: " + c.getEfeito());
                }
           
                System.out.println("\n");
                
                i++;
            }

            Replay.registrar("\nO jogo será contra um Bot!\nBot | ID: 202565001");
        }
        else{
            //criação do hacker 2
            Hacker hacker2 = criarHacker(2);
            escolhaDeCartas(hacker2, ataques, defesas, suportes);
            jogador2 = hacker2;

            //imprime mão do hacker2
            esperar(1000);
            System.out.println("\nMão de " + hacker2.getNome() + ": ");
            i = 1;
            for(Carta c : hacker1.getMao()){
                esperar(700);
                System.out.print(i + " - " + c.getNome() + "\nTIPO: " + c.getTipo() + " | PODER: " + c.getPoder() + " | CUSTO: " + c.getCusto());
                
                if(c instanceof Suporte){
                    System.out.print(" | EFEITO: " + c.getEfeito());
                }
           
                System.out.println("\n");
                
                i++;
            }

            Replay.registrar("\nO jogo será contra um hacker humano!\n" + jogador2.getNome() + " | ID: " + jogador2.getId());
        }

        Jogo jogo = new Jogo(jogador1, jogador2, leitura);
        jogo.iniciaJogo();

        Replay.salvarEmArquivo("replay.txt");

        esperar(500);
        System.out.println("\n* Deseja ver o replay completo? *\n(1) Sim\n(2) Não");

        int opcao = lerInteiro();
        while(opcao != 1 && opcao != 2){
            System.out.println("Opção inválida! Escolha (1) para ver o replay e (2) para encerrar o jogo.");
            opcao = lerInteiro();
        }

        if (opcao == 1) {
            System.out.println("\n* Replay *\n");
            try (Scanner sc = new Scanner(new File("replay.txt"))){
                while (sc.hasNextLine()) {
                    System.out.println(sc.nextLine());
                    Thread.sleep(400);
                }
            }
        }

        System.out.println("\n*----------------------*");
        System.out.println("Obrigada por jogar!");
        System.out.println("*----------------------*");

        System.out.print("\n<");
        esperar(500);
        System.out.print("3 \n");

        Replay.limpar();
    }

    //método para criar os hackers
    public static Hacker criarHacker(int n){
        System.out.println("\n* Hacker " + n + " *");
        
        esperar(500);
        System.out.print("Escolha seu nome: ");
        String nome = leitura.nextLine();
        
        esperar(500);
        System.out.print("Escolha seu ID: ");
        int id = lerInteiro();
        while(id <=0){
            System.out.print("\nID inválido! \nEscolha um número positivo para o ID: ");
            id = lerInteiro();
        }

        Hacker hacker = new Hacker(nome, id);
        esperar(500);
        System.out.println("\n* Hacker criado: " + hacker.getNome() + " | ID: " + hacker.getId() + " *");

        Replay.registrar("Hacker: " + hacker.getNome() + " | ID: " + hacker.getId());

        return hacker;
    }

    //método para decidir se a escolha de cartas é aleatória ou não
    public static void escolhaDeCartas(Jogador jogador, List<Ataque> atq, List<Defesa> def, List<Suporte> sup){
        esperar(500);
        System.out.println("\n" + jogador.getNome() + " faça sua escolha de cartas:\n(1) Desejo cartas aleatórias\n(2) Desejo escolher minhas cartas");
        
        int desejo = lerInteiro();
        while(desejo != 1 && desejo != 2){
            System.out.println("\nOpção inválida! Escolha (1) para cartas aleatórias e (2) para escolher as próprias cartas.");
            desejo = lerInteiro();
        } //verifica se escolha para seleção de cartas é válida

        if (desejo == 1){
            jogador.selecionaCartas(leitura, atq, def, sup, true);

            Replay.registrar(jogador.getNome() + " escolheu cartas aleatórias!");
        }
        else{
            jogador.selecionaCartas(leitura, atq, def, sup, false);

            Replay.registrar(jogador.getNome() + " escolheu suas próprias cartas!");
        }
    }

    //método para ler inteiros
    public static int lerInteiro(){
        int valor = leitura.nextInt();
        leitura.nextLine();
        return valor;
    }

    private static void esperar(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
