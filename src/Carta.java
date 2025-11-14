public abstract class Carta { //classe abstrata: não pode ser instanciada, apenas serve de modelo para outras
    protected String nome;
    protected int custo;

    public String getNome(){
        return nome;
    }
    
    public int getCusto(){
        return custo;
    }
}
