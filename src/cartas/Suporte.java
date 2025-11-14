package cartas;

import java.util.Optional;

public class Suporte extends Carta {
    public Suporte(String nome, String tipo, double poder, double custo, String efeito, String descricao){
        this.nome = nome;
        this.tipo = tipo;
        this.poder = poder;
        this.custo = custo;
        this.efeito = Optional.of(efeito);
        this.descricao = descricao;
    }
}
