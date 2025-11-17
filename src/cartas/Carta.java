package cartas;

import java.util.Optional;

public abstract class Carta { //classe abstrata: não pode ser instanciada, apenas serve de modelo para outras
    protected String nome;
    protected String tipo;
    protected double poder;
    protected double custo;
    protected Optional<String> efeito;
    protected String descricao;

    public String getNome(){
        return nome;
    }

    public String getTipo(){
        return tipo;
    }

    public double getPoder(){
        return poder;
    }
    
    public double getCusto(){
        return custo;
    }

    public String getEfeito(){
        return nome;
    }

    public String getDescricao(){
        return descricao;
    }
}
