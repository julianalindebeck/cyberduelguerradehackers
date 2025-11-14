public class Suporte extends Carta {
    private String efeito;
    private double valor; 

    public Suporte(String nome, int custo, String efeito, double valor){
        this.nome = nome;
        this.custo = custo;
        this.efeito = efeito;
        this.valor = valor;
    }

    public String getEfeito(){
        return efeito;
    }

    public double getValor(){
        return valor;
    }
}
