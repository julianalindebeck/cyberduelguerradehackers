package cartas;

public class Ataque extends Carta {
    public Ataque(String nome, String tipo, double poder, double custo, String descricao){
        this.nome = nome;
        this.tipo = tipo;
        this.poder = poder;
        this.custo = custo;
        this.descricao = descricao;
    }
}
