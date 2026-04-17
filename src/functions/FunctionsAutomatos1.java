package functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FunctionsAutomatos1 {

    private int[][] matrizTransicao;
    private int estadoInicial;
    private List<Integer> estadosFinais;
    private List<String> alfabeto;
    private int numEstados;
    private int numSimbolos;

    public FunctionsAutomatos1(String caminhoArquivo) throws IOException {
        lerAutomato(caminhoArquivo);
    }

    private void lerAutomato(String caminhoArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            // estado inicial
            String linhaInicial = br.readLine().trim();
            String[] partesInicial = linhaInicial.split(";");
            estadoInicial = Integer.parseInt(partesInicial[0].replace("q", "").trim());

            // estado final ou estados finais
            String linhaFinais = br.readLine().trim();
            String[] partesFinais = linhaFinais.split(";");
            estadosFinais = new ArrayList<>();
            for (String parte : partesFinais) {
                parte = parte.trim();
                if (!parte.isEmpty()) {
                    estadosFinais.add(Integer.parseInt(parte.replace("q", "").trim()));
                }
            }

            List<int[]> linhasDaMatriz = new ArrayList<>();
            String linhaLida;
            while ((linhaLida = br.readLine()) != null) {
                linhaLida = linhaLida.trim();
                if (linhaLida.isEmpty()) continue;

                String[] celulas = linhaLida.split(";");
                int[] transicoesDaLinha = new int[celulas.length];
                for (int coluna = 0; coluna < celulas.length; coluna++) {
                    transicoesDaLinha[coluna] = Integer.parseInt(celulas[coluna].trim());
                }
                linhasDaMatriz.add(transicoesDaLinha);
            }

            numEstados = linhasDaMatriz.size();
            numSimbolos = linhasDaMatriz.get(0).length;

            // cria a matriz de transição
            matrizTransicao = new int[numEstados][numSimbolos];
            for (int i = 0; i < numEstados; i++) {
                matrizTransicao[i] = linhasDaMatriz.get(i);
            }

            // gera o alfabeto dinamicamente -> coluna 0 = 'a', coluna 1 = 'b' e etc.
            alfabeto = new ArrayList<>();
            for (int i = 0; i < numSimbolos; i++) {
                alfabeto.add(String.valueOf((char) ('a' + i)));
            }
        }
    }

    public boolean validar(List<Character> listaLetras) {
        int estadoAtual = estadoInicial;

        if (listaLetras.isEmpty()) {
            return estadosFinais.contains(estadoAtual);
        }

        for (int i = 0; i < listaLetras.size(); i++) {
            char c = listaLetras.get(i);

            int indiceLetra = alfabeto.indexOf(String.valueOf(c));

            if (indiceLetra == -1) {
                return false;
            }

            estadoAtual = matrizTransicao[estadoAtual][indiceLetra];

            if (estadoAtual == -1) {
                return false;
            }
        }

        return estadosFinais.contains(estadoAtual);
    }

    public int getNumEstados() {
        return numEstados;
    }

    public int getNumSimbolos() {
        return numSimbolos;
    }

    public List<Integer> getEstadosFinais() {
        return estadosFinais;
    }

    public List<String> getAlfabeto() {
        return alfabeto;
    }

    public int getEstadoInicial() {
        return estadoInicial;
    }
}