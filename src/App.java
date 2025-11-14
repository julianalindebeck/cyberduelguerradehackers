import java.util.List;

import cartas.Ataque;
import cartas.Defesa;
import cartas.Suporte;
import leituraDeArquivos.Leitor;

public class App {
    public static void main(String[] args) throws Exception{
        List<Ataque> ataques = Leitor.listaDeAtaques();
        List<Defesa> defesas = Leitor.listaDeDefesas();
        List<Suporte> suportes = Leitor.listaDeSuportes(); //lê e cria as listas
    }
}
