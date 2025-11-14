package leituraDeArquivos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import cartas.Ataque;
import cartas.Defesa;
import cartas.Suporte;

public class Leitor {
    public static List<String[]> lerCSV(String caminho){
        List<String[]> linhas = new ArrayList<>(); //lê as linhas do arquivo

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;
            br.readLine(); //ignora o cabeçalho

            while ((linha = br.readLine()) != null){
                String[] partes = linha.split(",");
                linhas.add(partes);
            } //lê todas as linhas separadas por vírgula

        } catch (Exception e){
            System.out.println("Erro ao ler: " + caminho);
            e.printStackTrace();
        }

        return linhas;
    }

    public static List<Ataque> listaDeAtaques(){
        List<Ataque> lista = new ArrayList<>();

        for (String[] c : lerCSV("arquivos/ataque.csv")){
            String nome = c[0];
            String tipo = c[1];
            double poder = Double.parseDouble(c[2]);
            double custo = Double.parseDouble(c[3]);
            String descricao = c[4];

            lista.add(new Ataque(nome, tipo, poder, custo, descricao));
        }

        return lista;
    }

    public static List<Defesa> listaDeDefesas(){
        List<Defesa> lista = new ArrayList<>();

        for (String[] c : lerCSV("arquivos/defesa.csv")){
            String nome = c[0];
            String tipo = c[1];
            double poder = Double.parseDouble(c[2]);
            double custo = Double.parseDouble(c[3]);
            String descricao = c[4];

            lista.add(new Defesa(nome, tipo, poder, custo, descricao));
        }

        return lista;
    }

    public static List<Suporte> listaDeSuportes(){
        List<Suporte> lista = new ArrayList<>();

        for (String[] c : lerCSV("arquivos/suporte.csv")){
            String nome = c[0];
            String tipo = c[1];
            double poder = Double.parseDouble(c[2]);
            double custo = Double.parseDouble(c[3]);
            String efeito = c[4];
            String descricao = c[5];

            lista.add(new Suporte(nome, tipo, poder, custo, efeito, descricao));
        }

        return lista;
    }
}
