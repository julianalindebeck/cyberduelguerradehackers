import java.util.List;
import java.util.Scanner;

import cartas.Ataque;
import cartas.Defesa;
import cartas.Suporte;
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
    }
}
