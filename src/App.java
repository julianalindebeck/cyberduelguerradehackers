import java.util.List;
import java.util.Scanner;

import cartas.Ataque;
import cartas.Defesa;
import cartas.Suporte;
import jogadores.Hacker;
import jogadores.Jogador;
import leituraDeArquivos.Leitor;

public class App {
    public static void main(String[] args) throws Exception{
        List<Ataque> ataques = Leitor.listaDeAtaques();
        List<Defesa> defesas = Leitor.listaDeDefesas();
        List<Suporte> suportes = Leitor.listaDeSuportes(); //lê e cria as listas

        Scanner leitura = new Scanner(System.in);
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1. Hacker vs Bot");
        System.out.println("2. Hacker vs Hacker");
        int modoDeJogo = leitura.nextInt(); //passa o valor lido para modoDeJogo
        leitura.nextLine();
        System.out.println(modoDeJogo);
        
        System.out.println("Hacker 1 - Escolha seu nome: ");
        String nome = leitura.next();
        leitura.nextLine();

        System.out.println("Hacker 1 - Escolha seu ID: ");
        int id = leitura.nextInt();
        leitura.nextLine();

        Hacker hacker1 = new Hacker(nome, id);   
        System.out.println(hacker1.getNome());
        System.out.println(hacker1.getId());

        System.out.println("Hacker 1 - Faça sua escolha: ");
        System.out.println("1. Desejo cartas aleatórias");
        System.out.println("2. Desejo escolher minhas cartas");
        int desejo = leitura.nextInt(); //passa o valor lido para modoDeJogo
        leitura.nextLine();

        if(desejo == 1){
            Jogador.selecionaCartas(leitura, ataques, defesas, suportes, true);
        }
        else {
            Jogador.selecionaCartas(leitura, ataques, defesas, suportes, false);
        }
    }
}
