# Term.ooo Helper
 
Um programa para facilitar a vida dos jogadores do jogo de palavras Term.ooo e semelhantes, disponível [aqui](https://term.ooo).

## Download
Consulte os releases [aqui](https://github.com/brwolfgang/Term.ooo-Helper/releases).

## Modo de uso

No terminal, digite o seguinte comando:
```
java -jar TermoooHelper.jar <palavra>
```

O parâmetro `palavra` deve ser substituído pelas letras atualmente conhecidas e que estão na posição correta. As letras desconhecidas ou que não têm a posição confirmada devem ser substituiídas pelo caractere ponto `.`. Exemplo:

- Term.ooo: V⬛Z🟨O
- Parâmetro: V.Z.O
- Linha de comando completa: `java -jar TermoooHelper.jar V.Z.O`

Ao pressionar Enter será exibida uma lista de palavras de 5 letras que se encaixam na informação passada.
