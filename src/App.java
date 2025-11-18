import java.util.List;
import java.util.Scanner;

import cartas.Ataque;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Bot;
import jogadores.Hacker;
import leituraDeArquivos.Leitor;

public class App {
    public static void main(String[] args) throws Exception{
        //lê e cria as listas
        List<Ataque> ataques = Leitor.listaDeAtaques();
        List<Defesa> defesas = Leitor.listaDeDefesas();
        List<Suporte> suportes = Leitor.listaDeSuportes();

        //realiza a leitura do modo de jogo
        Scanner leitura = new Scanner(System.in);
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1. Hacker vs Bot");
        System.out.println("2. Hacker vs Hacker");
        int modoDeJogo = leitura.nextInt(); //passa o valor lido para modoDeJogo
        leitura.nextLine();
        
        //criação do hacker 1
        System.out.println("Hacker 1 - Escolha seu nome: ");
        String nome = leitura.nextLine();
        System.out.println("Hacker 1 - Escolha seu ID: ");
        int id = leitura.nextInt();
        leitura.nextLine();
        Hacker hacker1 = new Hacker(nome, id);   
        System.out.println("Hacker 1: " + hacker1.getNome());
        System.out.println("ID: " + hacker1.getId());

        //criação da mão do hacker 1
        System.out.println("Hacker 1 - Faça sua escolha: ");
        System.out.println("1. Desejo cartas aleatórias");
        System.out.println("2. Desejo escolher minhas cartas");
        int desejo = leitura.nextInt();
        leitura.nextLine(); //? transformar em função ?

        if(desejo == 1){
            hacker1.selecionaCartas(leitura, ataques, defesas, suportes, true);
        }
        else {
            hacker1.selecionaCartas(leitura, ataques, defesas, suportes, false);
        }

        //verifica se jogo é contra bot ou outro hacker
        if(modoDeJogo == 1){
            Bot bot = new Bot();
            bot.selecionaCartas(leitura, ataques, defesas, suportes, true);
        }
        else{
            //criação do hacker 2
            System.out.println("Hacker 2 - Escolha seu nome: ");
            nome = leitura.nextLine();
            System.out.println("Hacker 2 - Escolha seu ID: ");
            id = leitura.nextInt();
            leitura.nextLine();
            Hacker hacker2 = new Hacker(nome, id);   
            System.out.println(hacker2.getNome());
            System.out.println(hacker2.getId());

            //criação da mão do hacker 2
            System.out.println("Hacker 2 - Faça sua escolha: ");
            System.out.println("1. Desejo cartas aleatórias");
            System.out.println("2. Desejo escolher minhas cartas");
            desejo = leitura.nextInt();
            leitura.nextLine();

            if(desejo == 1){
                hacker2.selecionaCartas(leitura, ataques, defesas, suportes, true);
            }
            else {
                hacker2.selecionaCartas(leitura, ataques, defesas, suportes, false);
            }
        }
    }
}
