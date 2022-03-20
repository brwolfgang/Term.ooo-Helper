package me.olobo.java.termoohelper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TermoHelper {
    public static void main(String[] args) throws Exception {
        ArrayList<String> arrayPalavrasCarregadas = new TermoHelper().carregarListaPalavras(5, "br-utf8.txt");

        tratarInput(args);
        
        System.out.println(String.format("Qtde palavras encontradas: %s", arrayPalavrasCarregadas.size()));

        ArrayList<String> palavrasFiltradas = arrayPalavrasCarregadas.stream()
                .filter(v -> v.matches("pra\\D\\D"))
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("-----------------------------------");
        System.out.println("-- Lista de palavras encontradas --");
        System.out.println("-----------------------------------");

        palavrasFiltradas.forEach(System.out::println);
        
        System.out.println(String.format("Quantidade de palavras filtradas: %s", palavrasFiltradas.size()));
    }

    private static void tratarInput(String[] args) throws Exception {
        System.out.println(args.length);
    }

    public ArrayList<String> carregarListaPalavras(int qtdeCaracteres, String arquivoDicionario) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URI uriArquivoDicionario = getClass().getClassLoader().getResource(arquivoDicionario).toURI();

            Stream<String> lines = Files.lines(Path.of(uriArquivoDicionario), StandardCharsets.UTF_8);

            lines.forEach(s -> stringBuilder.append(s).append("\n"));

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        String megaListaPalavrasEncontradas = stringBuilder.toString();

        return Arrays.stream(megaListaPalavrasEncontradas.split("\n"))
                .filter(v -> v.length() == qtdeCaracteres)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
