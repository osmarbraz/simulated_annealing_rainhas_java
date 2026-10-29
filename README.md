# N-Rainhas com Simulated Annealing em Java

## 1. Descrição do projeto

Este programa implementa uma abordagem baseada no algoritmo **Simulated Annealing** (*Recozimento Simulado*) para procurar soluções para o problema das **N-Rainhas**. O problema consiste em posicionar `N` rainhas em um tabuleiro de xadrez de dimensão `N × N`, de modo que nenhuma rainha ataque outra. O programa foi implementado em Java e executa experimentos para diferentes tamanhos de tabuleiro. Para cada tamanho, realiza várias execuções e apresenta o tempo de processamento, a quantidade de soluções encontradas e as médias estatísticas. 

## 2. Objetivos

-   Aplicar o algoritmo Simulated Annealing a um problema de busca combinatória.
-   Representar as posições das rainhas por meio de um vetor de inteiros.
-   Avaliar configurações do tabuleiro por uma função de *fitness*.
-   Gerar soluções vizinhas por perturbações aleatórias.
-   Observar o tempo de execução e a frequência de soluções válidas em diferentes tamanhos de problema.

## 3. O problema das N-Rainhas

O objetivo é posicionar `N` rainhas em um tabuleiro `N × N` sem que duas rainhas compartilhem a mesma linha, coluna ou diagonal.

### 3.1 Representação da solução

O programa representa o tabuleiro por um vetor `R` de tamanho `N`:

-   O índice do vetor representa a **coluna** do tabuleiro.
-   O valor armazenado representa a **linha** em que a rainha daquela coluna está posicionada.

Por exemplo, o vetor `[1, 3, 0, 2]` representa quatro rainhas em um tabuleiro `4 × 4`. Cada coluna possui exatamente uma rainha. Essa configuração é uma solução válida para o problema das quatro rainhas.

A representação evita conflitos entre rainhas na mesma coluna, pois há apenas uma posição por índice do vetor. Por isso, a validação precisa verificar conflitos de linha e diagonal.

## 4. Conceito de Simulated Annealing

O **Simulated Annealing** é uma meta-heurística de busca inspirada no processo físico de recozimento. O algoritmo começa com uma solução candidata e gera vizinhos por pequenas alterações. Em geral: 
1.  Uma solução inicial é gerada aleatoriamente.
2.  Uma função de avaliação calcula o custo da solução.
3.  Uma solução vizinha é criada por uma perturbação.
4.  Se o vizinho tem custo menor ou igual, ele é aceito.
5.  Se o custo aumenta, o algoritmo pode aceitar o vizinho com uma probabilidade que depende da temperatura.
6.  A temperatura é reduzida ao longo das iterações, diminuindo a probabilidade de aceitar soluções piores.
7.  O processo termina quando uma condição de parada é alcançada.

No código, a probabilidade de aceitar um vizinho de custo maior é calculada pela expressão:

\[ P(`\text{aceitar}`{=tex}) = e\^{-`\Delta`{=tex}/T} \]

Em que:

-   `Δ` é a diferença entre o custo do vizinho e o custo atual (`Ci - C`);
-   `T` é a temperatura atual;
-   `e` é a base do logaritmo natural.

Quanto maior a temperatura, maior tende a ser a possibilidade de aceitar um movimento que aumenta o custo. À medida que a temperatura diminui, essa possibilidade também diminui.

## 5. Função de avaliação (*fitness*)

O método `fitness(int[] R)` percorre todas as rainhas e conta quantas estão em posições consideradas inválidas pelo método `validaPosicao`.

O método `validaPosicao(int[] R, int k)` compara a rainha da coluna `k` com as rainhas nas colunas anteriores. A posição é considerada inválida se outra rainha estiver:

-   na mesma linha: `R[i] == R[k]`;
-   na mesma diagonal: `Math.abs(R[i] - R[k]) == (k - i)`.

Como cada rainha está em uma coluna diferente pela representação do vetor, não é necessário verificar conflito na mesma coluna.

O método `valida(int[] R)` utiliza essa verificação para determinar se o tabuleiro completo é válido. Uma solução válida deve ter todas as rainhas sem conflitos.

**Interpretação importante:** no código atual, `fitness` conta rainhas atacadas, portanto valores menores representam configurações melhores. Uma solução sem conflitos deve ter `fitness(R) == 0`.

## 6. Como o programa funciona

O método `main` define os tamanhos dos problemas e a quantidade de repetições. Em seguida, chama `nRainhas`, que executa o experimento para cada tamanho.

Para cada tamanho `N`, o programa:

1.  Reinicia os acumuladores de tempo e de soluções.
2.  Executa o algoritmo o número de vezes configurado.
3.  Registra o tempo de cada execução.
4.  Conta se a configuração final é válida.
5.  Calcula a média de soluções e a média de tempo.
6.  Exibe os resultados e o tempo total dos testes.

### 6.1 Parâmetros utilizados

No código fornecido, os parâmetros são definidos em `nRainhas`:

 | Parâmetro | Valor utilizado | Significado |
|---|---:|---|
| `M` | `2500` | Limite de iterações externas do algoritmo. |
| `P` | `1` | Limite de perturbações no ciclo interno por iteração. |
| `L` | `1` | Parâmetro declarado para limitar sucessos; a condição que o utilizaria está comentada. |
| `T0` | `N` | Temperatura inicial, igual ao tamanho do tabuleiro. |
| `alfa` | `0.01` | Fator multiplicativo de redução da temperatura. |
| `repeticoesTeste` | `10` | Número de execuções para cada tamanho de tabuleiro. |

Os tamanhos configurados em `listaProblemasASolucionar` são`4, 6, 8, 10, 12, 14, 16, 18, 20` e `22`.

A quantidade de soluções e os tempos apresentados podem variar de uma execução para outra, pois o algoritmo utiliza números aleatórios.

## 7. Principais métodos

| Método | Responsabilidade |
|---|---|
| `main(String[] args)` | Ponto de entrada do programa. Define os tamanhos dos tabuleiros e a quantidade de repetições dos testes. |
| `nRainhas(int[] listaProblemasASolucionar, int repeticoesTeste)` | Executa os experimentos para cada tamanho de tabuleiro, mede os tempos de execução e calcula as médias estatísticas. |
| `executaSimulatedAnnealing(int n, int M, int P, int L, double alfa, double T0)` | Executa o algoritmo para um tabuleiro, verifica se a configuração final é válida e contabiliza as soluções encontradas. |
| `simulatedAnnealing(int n, int M, int P, int L, double alfa, double T0)` | Implementa o Simulated Annealing: gera uma solução inicial, avalia vizinhos, decide quais movimentos aceitar e reduz a temperatura. |
| `geraIndividuo(int n)` | Gera aleatoriamente uma configuração inicial com `n` rainhas, representada por um vetor de inteiros. |
| `geraVizinho(int[] R)` | Cria uma cópia da configuração atual e modifica aleatoriamente a posição de uma rainha para gerar uma solução vizinha. |
| `moverParaBaixo(int n, int coluna, int deslocamento)` | Calcula a nova posição da rainha considerando o deslocamento e o retorno ao início do intervalo quando o limite do tabuleiro é ultrapassado. |
| `fitness(int[] R)` | Calcula o custo da configuração contando as rainhas que estão sob ataque. Quanto menor o valor, melhor a configuração; uma solução válida deve ter custo zero. |
| `validaPosicao(int[] R, int k)` | Verifica se a rainha da coluna `k` está sob ataque de alguma rainha posicionada nas colunas anteriores, considerando conflitos de linha e diagonal. |
| `valida(int[] R)` | Verifica se todas as rainhas estão em posições válidas, sem conflitos entre si. |
| `imprimeSolucao(int[] R)` | Imprime a representação do tabuleiro quando a constante `IMPRIMIRTABULEIRO` está habilitada. |

### 7.1 Tecnologias e ferramentas

| Tecnologia ou ferramenta | Utilização |
|---|---|
| **Java 8 (JDK 8)** | Linguagem e ambiente de desenvolvimento utilizados para implementar e executar o algoritmo. |
| **Apache Maven** | Gerencia a estrutura do projeto, as configurações de compilação e a execução de tarefas definidas no `pom.xml`. O código fornecido não depende de bibliotecas externas explícitas. |
| `java.util.Random` | Biblioteca padrão do Java utilizada para gerar números aleatórios na criação de indivíduos e vizinhos. |

## 8. Requisitos

Antes de executar o projeto, instale e configure:

-   **JDK 8**.
-   **Apache Maven** compatível com o ambiente Java utilizado.

Verifique as instalações no terminal:

``` bash
java -version
javac -version
mvn -version
```

Confirme que o Java utilizado pelo Maven é compatível com Java 8.

## 9. Como compilar e executar

Execute os comandos a partir do diretório raiz do projeto, onde está o arquivo `pom.xml`.

### 9.1 Compilar o projeto

``` bash
mvn clean compile
```

O Maven remove os arquivos de compilação anteriores e compila o código-fonte localizado em `src/main/java`.

### 9.2 Executar o programa

``` bash
mvn exec:java -Dexec.mainClass="Principal"
```

Esse comando executa o método `main` da classe `Principal`. Se o projeto ainda não tiver o plugin `exec-maven-plugin` configurado no `pom.xml`, será necessário adicioná-lo à seção `<build><plugins>`.

## 10. Saída do programa

A saída apresenta, para cada tamanho de tabuleiro:

-   o tamanho do problema;
-   o tempo de cada execução, em milissegundos;
-   a quantidade de soluções válidas encontrada em cada execução;
-   a média de soluções;
-   o tempo médio;
-   o tempo acumulado;
-   o tempo total do experimento.

A impressão do tabuleiro está desabilitada por padrão porque `IMPRIMIRTABULEIRO` está definido como `false`. Alterar essa constante para `true` habilita a impressão das configurações consideradas soluções válidas.

Os valores exatos não são fixos, porque a inicialização e a geração de vizinhos são aleatórias.

## 11. Observações sobre a implementação

Há alguns pontos importantes a considerar ao interpretar os resultados do código atual.

### 11.1 Condição de parada baseada no *fitness*

O método `fitness` conta quantas rainhas estão sob ataque. Logo, o custo ideal é `0`. Entretanto, o código define:

``` java
int melhorFitness = n;
```

e utiliza a condição:

``` java
} while ((C != melhorFitness) && (i < P));
```

Essa condição compara o custo com `n`, e não com `0`. Portanto, ela não representa corretamente a condição de parada esperada para encontrar uma solução sem conflitos. Para que a busca tenha uma condição de parada coerente com a função de avaliação, é necessário revisar essa lógica. 

### 11.2 Parâmetro `L`

O parâmetro `L` é recebido pelo método `simulatedAnnealing`, mas a condição que o utiliza está comentada. Assim, alterar `L` não muda o comportamento atual do ciclo interno.

### 11.3 Contador de sucessos

A condição externa usa `nSucesso >= 0`. Como `nSucesso` é inicializado em zero e incrementado sem ser negativo, essa parte da condição não interrompe o ciclo antecipadamente. Na prática, o limite `j < M` controla a quantidade máxima de iterações externas.

### 11.4 Temperatura

A temperatura é atualizada por `T = T * alfa`. Com `alfa = 0.01`, a temperatura diminui muito rapidamente. Como a expressão de aceitação divide por `T`, convém revisar o tratamento de temperaturas muito pequenas para evitar comportamento numérico indesejado.

### 11.5 Busca estocástica

O algoritmo utiliza números aleatórios e, por isso, não garante que encontrará uma solução em todas as execuções. A quantidade de soluções válidas deve ser interpretada como resultado experimental dos parâmetros e da implementação utilizada.

## 12. Análise de complexidade

Considere `N` como a quantidade de rainhas.

-   `geraIndividuo(N)` percorre o vetor uma vez: **O(N)**.
-   `validaPosicao(R, k)` pode comparar a rainha com até `k` rainhas anteriores: **O(N)** no pior caso.
-   `valida(R)` percorre as `N` posições e chama `validaPosicao`: **O(N²)**.
-   `fitness(R)` também percorre as `N` posições e chama `validaPosicao`: **O(N²)**.
-   `geraVizinho(R)` copia o vetor e altera uma posição: **O(N)**.
-   `simulatedAnnealing(...)` pode executar até `M` iterações externas e até `P` perturbações por iteração; como cada avaliação de *fitness*  custa **O(N²)**, o limite superior esperado para essa parte é **O(M × P × N²)**, desconsiderando os custos constantes e a validação final.

Com os valores usados no programa (`M = 2500` e `P = 1`), a estimativa para o trabalho de avaliação por execução é **O(M × N²)**. Essa estimativa descreve o custo potencial dos ciclos; o comportamento efetivo depende das condições de parada e da implementação. 

## 13. Limitações

-   A busca é probabilística e pode não encontrar uma solução em determinada execução.
-   Os resultados dependem dos parâmetros e dos números aleatórios.
-   O código não fixa uma semente para o gerador aleatório, dificultando reproduzir exatamente uma execução.
-   A condição de parada compara o *fitness* com `N`, embora uma solução sem conflitos tenha custo `0`.
-   O parâmetro `L` não é utilizado na condição ativa do ciclo interno.
-   O desenho do tabuleiro está desativado por padrão.
-   O tempo medido inclui a execução do algoritmo e parte do processamento associado à verificação e contabilização da solução, conforme o fluxo do método `nRainhas`.


