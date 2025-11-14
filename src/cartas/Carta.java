package cartas;

import java.util.Optional;

public abstract class Carta { //classe abstrata: não pode ser instanciada, apenas serve de modelo para outras
    protected String nome;
    protected String tipo;
    protected int poder;
    protected int custo;
    protected Optional<String> efeito;
    protected String descricao;

    public String getNome(){
        return nome;
    }
    
    public int getCusto(){
        return custo;
    }
}
