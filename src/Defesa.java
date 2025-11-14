public class Defesa extends Carta {
    private int defesa;

    public Defesa(String nome, int custo, int defesa){
        this.nome = nome;
        this.custo = custo;
        this.defesa = defesa;
    }

    public int getDefesa(){
        return defesa;
    }
}
