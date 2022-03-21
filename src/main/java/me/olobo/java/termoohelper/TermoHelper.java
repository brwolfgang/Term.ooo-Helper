package me.olobo.java.termoohelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TermoHelper {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Formato de uso: TermoHelper.jar <palavra>. Cada letra desconhecida deve ser trocada por ponto como em r.dio");
        }

        if (args[0].length() != 5) {
            throw new IllegalArgumentException("Formato de uso: TermoHelper.jar <palavra>. A palavra consultada deve conter 5 caracteres");
        }

        TermoHelper termoHelper = new TermoHelper();

        String patternBusca = termoHelper.gerarPatternConsulta(args[0]);

        termoHelper.consultarListaPalavras(patternBusca);
    }

    public String gerarPatternConsulta(String palavra) {
        return palavra.replaceAll("\\.", "\\\\D");
    }

    private void consultarListaPalavras(String patternBusca) {
        ArrayList<String> arrayPalavrasCarregadas = new TermoHelper().carregarListaPalavras(5, "br-utf8.txt");

        System.out.println(String.format("Qtde palavras carregadas do dicionário: %s", arrayPalavrasCarregadas.size()));

        ArrayList<String> palavrasFiltradas = arrayPalavrasCarregadas.stream()
                .filter(v -> v.matches(patternBusca))
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("-----------------------------------");
        System.out.println("-- Lista de palavras encontradas --");
        System.out.println("-----------------------------------");

        palavrasFiltradas.forEach(System.out::println);

        System.out.println(String.format("Quantidade de palavras filtradas: %s", palavrasFiltradas.size()));
    }

    public ArrayList<String> carregarListaPalavras(int qtdeCaracteres, String arquivoDicionario) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStream fileFromResourceAsStream = getFileFromResourceAsStream(arquivoDicionario);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileFromResourceAsStream, StandardCharsets.UTF_8));
            
            while (reader.ready()) {
                stringBuilder.append(reader.readLine()).append("\n");
            }
            
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String megaListaPalavrasEncontradas = stringBuilder.toString();

        return Arrays.stream(megaListaPalavrasEncontradas.split("\n"))
                .filter(v -> v.length() == qtdeCaracteres)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("Arquivo não encontrado! " + fileName);
        } else {
            return inputStream;
        }
    }
}
