package replay;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Replay {

    private static List<String> eventos = new ArrayList<>();

    public static void registrar(String texto){
        eventos.add(texto);
    }

    public static void salvarEmArquivo(String nomeArquivo){
        try (FileWriter fw = new FileWriter(nomeArquivo)){ 
            for (String e : eventos){
                fw.write(e + "\n");
            }
        }
        catch(IOException e){
            System.out.println("Erro ao salvar replay: " + e.getMessage());
        }
    }

    public static void limpar(){
        eventos.clear();
    }
}
