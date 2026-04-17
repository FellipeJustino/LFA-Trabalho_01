package functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FunctionList {

    private static final String CAMINHO_CSV = "teste.csv";

    /**
     * Agora linha sem texto é tratada como vazio
     */
    public static void lerPalavra(String caminhoAutomato) {
        System.out.println("\n[Autômato: " + caminhoAutomato + "] Lendo palavras do arquivo: " + CAMINHO_CSV);

        FunctionsAutomatos1 automato;
        try {
            automato = new FunctionsAutomatos1(caminhoAutomato);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo do autômato: " + e.getMessage());
            return;
        }

        System.out.println("  Autômato: " + automato.getNumEstados() + " estados, "
                + automato.getNumSimbolos() + " símbolos, alfabeto: " + automato.getAlfabeto()
                + ", estados finais: " + automato.getEstadosFinais());

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_CSV))) {
            String linha;
            int numLinha = 0;

            while ((linha = br.readLine()) != null) {
                numLinha++;
                String entrada = linha.trim();

                List<Character> listaLetras = new ArrayList<>();
                for (char c : entrada.toCharArray()) {
                    listaLetras.add(c);
                }

                boolean aceita = automato.validar(listaLetras);

                String palavraExibida = entrada.isEmpty() ? "ε " : "\"" + entrada + "\"";
                System.out.println("  Linha " + numLinha + ": " + palavraExibida + " >>> " + (aceita ? "ACEITA" : "REJEITADA"));
            }

            System.out.println("Leitura do arquivo concluída.\n");

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }
}
