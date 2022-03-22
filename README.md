# Term.ooo Helper
 
Um programa para facilitar a vida dos jogadores do jogo de palavras Term.ooo e semelhantes, disponível [aqui](https://term.ooo).

## Download
Consulte os releases [aqui](https://github.com/brwolfgang/Term.ooo-Helper/releases).

## Modo de uso

### Usando letras com posição conhecida
No terminal, digite o seguinte comando:
```
java -jar TermoooHelper.jar -l <palavra>
```

O parâmetro `palavra` deve ser substituído pelas letras atualmente conhecidas e que estão na posição correta. As letras desconhecidas ou que não têm a posição confirmada devem ser substituídas pelo caractere ponto `.`. Exemplo:

- Term.ooo: V⬛Z🟨O
- Parâmetro: `-l V.Z.O`
- Linha de comando completa: `java -jar TermoooHelper.jar -l V.Z.O`

### Usando letras existentes na palavra, mas com posição ainda desconhecida
O parâmetro `-a` é usado para definir letras que sabidamente existem na palavra a ser adivinhada, mas a posição correta ainda não é conhecida. As letras devem ser colocadas todas juntas sem espacó. Exemplo.

- Term.ooo: V⬛Z🟨O
- Parâmetro: `-a a`
- Linha de comando completa: `java -jar TermoooHelper.jar -l V.Z.O -a a`

Ao pressionar Enter será exibida uma lista de palavras que se encaixam no padrão passado usando `-l` e que também contêm a letra `a`.

### Removendo letras que não existem na palavra
O parâmetro `-e` é usado para definir letras que sabidamente não existem na palavra, ajudando a filtrar a lista de de candidatos. Exemplo:

- Term.ooo: V⬛Z🟨O
- Parâmetro: `-e xyz`
- Linha de comando completa: `java -jar TermoooHelper.jar -l V.Z.O -e xyz`
  
Ao pressionar Enter será exibida uma lista de palavras que se encaixam no padrão passado usando `-l` e que *não* contêm as letras x, y ou z.

### Tudo junto e misturado
Você pode combinar todos os parâmetros para obter uma lista de palavras super específica:

`java -jar TermoooHelper-0.3.jar -l ..l.... -e poar -a ulst`

Significado do comando:
- `-l ..l....`
  - A palavra a ser encontrada tem um l na terceira posição.
- `-e poar`
  - A palavra a ser encontrada não possui as letras p, o, a ou r.
- `-a ulst`
  - A palavra a ser encontrada deve ter as letrs u, l, s e t.

Saída do programa com os parâmetros acima:
```
java -jar TermoooHelper-0.3.jar -l ..l.... -e poar -a ulst
Qtde palavras carregadas do dicionário: 22226
-- Parâmetros utilizados: --
-- Letras: ..l....
-- Tamanho da palavra: 7
-- Letras inexistentes: poar
-- Letras existentes: ulst
-----------------------------------
-- Lista de palavras encontradas --
-----------------------------------
buliste
cultues
multeis
Quantidade de palavras filtradas: 3
```