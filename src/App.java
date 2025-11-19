import java.util.List;
import java.util.Scanner;

import cartas.Ataque;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Bot;
import jogadores.Hacker;
import jogadores.Jogador;
import jogo.Jogo;
import leituraDeArquivos.Leitor;

public class App {
    static Scanner leitura = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
        //lê e cria as listas
        List<Ataque> ataques = Leitor.listaDeAtaques();
        List<Defesa> defesas = Leitor.listaDeDefesas();
        List<Suporte> suportes = Leitor.listaDeSuportes();

        //realiza a leitura do modo de jogo
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1. Hacker vs Bot");
        System.out.println("2. Hacker vs Hacker");
        int modoDeJogo = lerInteiro(); //passa o valor lido para modoDeJogo

        while(modoDeJogo != 1 && modoDeJogo != 2){
            System.out.println("\nOpção inválida! Digite 1 para Hacker vs Bot ou 2 para Hacker vs Hacker");
            modoDeJogo = lerInteiro();
        } //verifica se o modo de jogo escolhido é válido

        //criação do hacker 1
        Hacker hacker1 = criarHacker(1);
        escolhaDeCartas(hacker1, ataques, defesas, suportes);

        Jogador jogador1 = hacker1;
        Jogador jogador2;

        //verifica se jogo é contra bot ou outro hacker
        if(modoDeJogo == 1){
            Bot bot = new Bot();
            bot.selecionaCartas(leitura, ataques, defesas, suportes, true);
            jogador2 = bot;
        }
        else{
            //criação do hacker 2
            Hacker hacker2 = criarHacker(2);
            escolhaDeCartas(hacker2, ataques, defesas, suportes);
            jogador2 = hacker2;
        }

        Jogo jogo = new Jogo(jogador1, jogador2, leitura);
        jogo.iniciaJogo();
    }

    //método para criar os hackers
    public static Hacker criarHacker(int n){
        System.out.println("\nHacker " + n);
        
        System.out.print("Escolha seu nome: ");
        String nome = leitura.nextLine();
        
        System.out.print("Escolha seu ID: ");
        int id = lerInteiro();

        Hacker hacker = new Hacker(nome, id);
        System.out.println("\nHacker criado: " + hacker.getNome() + " (ID: " + hacker.getId() + ")");
        return hacker;
    }

    //método para decidir se a escolha de cartas é aleatória ou não
    public static void escolhaDeCartas(Jogador jogador, List<Ataque> atq, List<Defesa> def, List<Suporte> sup){
        System.out.println("\n" + jogador.getNome() + " faça sua escolha de cartas:");
        System.out.println("1. Desejo cartas aleatórias");
        System.out.println("2. Desejo escolher minhas cartas");
        
        int desejo = lerInteiro();
        while(desejo != 1 && desejo != 2){
            System.out.println("\nOpção inválida! Escolha 1 para cartas aleatórias e 2 para escolher as próprias cartas.");
            desejo = lerInteiro();
        } //verifica se escolha para seleção de cartas é válida

        if (desejo == 1){
            jogador.selecionaCartas(leitura, atq, def, sup, true);
        }
        else{
            jogador.selecionaCartas(leitura, atq, def, sup, false);
        }
    }

    //método para ler inteiros
    public static int lerInteiro(){
        int valor = leitura.nextInt();
        leitura.nextLine();
        return valor;
    }
}
