public class Ataque extends Carta {
    private int ataque;

    public Ataque(String nome, int custo, int ataque){
        this.nome = nome;
        this.custo = custo;
        this.ataque = ataque;
    }

    public int getAtaque(){
        return ataque;
    }
}
