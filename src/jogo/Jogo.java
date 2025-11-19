package jogo;

import java.util.Scanner;

import jogadores.Bot;
import jogadores.Jogador;

public class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador primeiroJogador;
    private Jogador segundoJogador;
    private Scanner leitura;
    
    public Jogo(Jogador jogador1, Jogador jogador2, Scanner leitura){
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.leitura = leitura;
        this.primeiroJogador = jogador1;
        this.segundoJogador = jogador2;
    }

    public void iniciaJogo(){
        System.out.println("Início de jogo!");
        boolean verificaJogo = true;

        while(verificaJogo){
            if(!(primeiroJogador instanceof Bot)){
                System.out.println(primeiroJogador.getNome() + " deseja desistir?");
                Scanner opcao = new Scanner(System.in);
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                int escolha = opcao.nextInt();
                opcao.nextLine();
                if(escolha == 1){
                    verificaJogo = false;
                    break;
                }               
            }
        }
    }

}
