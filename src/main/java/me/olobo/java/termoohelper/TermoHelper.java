package me.olobo.java.termoohelper;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(name = "Term.ooo Helper", mixinStandardHelpOptions = true, version = "0.2")
public class TermoHelper implements Callable<Integer> {

    @Option(names = "-l", required = true, description = "Letras com posi\u00E7\u00F5es conhecidas, colocando . (ponto) no lugar das desconhecidas. Exemplo: v.z.o")
    String letras;

    @Option(names = "-e", description = "Letras que sabidamente n\u00E3o existem na palavra, todas juntas sem espa\u00E7o.", defaultValue = "")
    String letrasInexistentes;

    @Option(names = "-a", description = "Letras que sabidamente existem na palavra, mas de posi\u00E7\u00E3o desconhecida. Todas juntas sem espa\u00E7o.", defaultValue = "")
    String letrasExistentes;

    @Option(names = "-t", description = "Tamanho da palavra a ser consultada, o padr\u00E3o \u00E9 5", defaultValue = "5")
    Integer tamanhoPalavra;

    public static void main(String[] args) {
        System.exit(new CommandLine(new TermoHelper()).execute(args));
    }

    public void validarInput() {
        if (letras.length() != tamanhoPalavra) {
            throw new IllegalArgumentException(String.format("Formato de uso: TermoHelper.jar <letras>. A palavra consultada deve conter %d caracteres. Foi usada a palavra '%s' que cont\u00E9m %d caracteres", tamanhoPalavra, letras, letras.length()));
        }
    }

    public String gerarPatternConsulta(String palavra) {
        return palavra.toLowerCase(Locale.ROOT).replaceAll("\\.", "\\\\D");
    }

    private void consultarListaPalavras(String patternBusca) {
        ArrayList<String> arrayPalavrasCarregadas = new TermoHelper().carregarListaPalavras(tamanhoPalavra, "br-utf8.txt");

        System.out.println(String.format("Qtde palavras carregadas do dicion\u00E1rio: %s", arrayPalavrasCarregadas.size()));

        List<String> listaLetrasExistentes = Arrays.stream(letrasExistentes.split("")).map(String::toLowerCase).collect(Collectors.toList());
        List<String> listaLetrasInexistentes = Arrays.stream(letrasInexistentes.split("")).map(String::toLowerCase).collect(Collectors.toList());

        ArrayList<String> palavrasFiltradas = arrayPalavrasCarregadas.stream()
                .filter(v -> v.matches(patternBusca))
                .filter(v -> {
                    // Mantém apenas palavras que contêm todas as letras conhecidas
                    for (String letraExistente : listaLetrasExistentes) {
                        if (!v.contains(letraExistente)) {
                            return false;
                        }
                    }

                    return true;
                })
                .filter(v -> {
                    // Remove palavras que contém letras sabidamente inexistentes
                    for (String letraInexistente : listaLetrasInexistentes) {
                        if (v.contains(letraInexistente)) {
                            return false;
                        }
                    }

                    return true;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("-- Par\u00E2metros utilizados: --");
        System.out.println("-- Letras: " + letras);
        System.out.println("-- Tamanho da palavra: " + tamanhoPalavra);

        if (!letrasInexistentes.isEmpty()) {
            System.out.println("-- Letras inexistentes: " + letrasInexistentes);
        }
        if (!letrasExistentes.isEmpty()) {
            System.out.println("-- Letras existentes: " + letrasExistentes);
        }

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

    @Override
    public Integer call() {
        validarInput();

        String patternBusca = gerarPatternConsulta(letras);

        consultarListaPalavras(patternBusca);

        return 123;
    }
}
