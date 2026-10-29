
/**
 * Simulated Annealing
 *
 * O programa utiliza Simulated Annealing para gerar encontrar uma solução para
 * um tabuleiro contendo n rainhas.
 *
 * Utiliza o algoritmo clássico para encontrar L soluções.
 *
 */
import java.util.Random;

public class Principal {

    /**
     * Atributo do numero de soluções encontradas ao final do algoritmo
     */
    private static int solucoes;

    //Gerador de número aleatórios
    private static final Random RANDOMICO = new Random();

    //Habilita ou desabilita a saida dos dados de impressão
    private static final boolean IMPRIMIRTABULEIRO = false;

    /**
     * Imprime as soluções: tabuleiro e o posicionamento das rainhas.
     *
     * @param R vetor das rainhas.
     */
    private static void imprimeSolucao(int[] R) {

        //Tamanho do Problema
        int n = R.length;

        if (IMPRIMIRTABULEIRO) {

            System.out.println(" Solução número " + solucoes + ":");

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //Posição ocupada
                    if (R[j] == i) {
                        System.out.print(" " + i + " ");
                    } else {
                        System.out.print(" . ");
                    }
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
    }

    /**
     * Gera um indivíduo com n posições de forma aleatória.
     *
     * Gera n rainhas aleatórias(com repetição) para um tabuleiro.
     *
     * @param n Tamanho do indivíduo
     * @return um indivíduo da populacao com repetição de rainhas
     */
    private static int[] geraIndividuo(int n) {
        //Inicializa o vetor de retorno
        int[] novoIndividuo = new int[n];
        int i = 0;
        //Gera as rainhas aleatóriamente de acordo com o tamanho especificado em n
        while (i < n) {
            //Gera um uma rainha aleatória
            novoIndividuo[i] = RANDOMICO.nextInt(n);
            i = i + 1;
        }
        return novoIndividuo;
    }

    /**
     * Função de avaliação do indivíduo, retorna a quantidade de rainhas
     * atacadas.
     *
     * Complexidade O(n^2)
     *
     * @param R vetor das rainhas posicionadas. O elemento corresponde à coluna
     * e seu respectivo conteúdo corresponde à linha.
     *
     * @return A quantidade de rainhas atacadas em R.
     */
    public static int fitness(int[] R) {
        //Recupera a quantidade de rainhas de R.
        int n = R.length;                                           // Theta(1)
        //Variável para acumular a quantidade de rainhas atacadas.
        int cont = 0;                                               // Theta(1)
        //Verifica se todas as rainhas estão sendo atacads
        for (int k = 0; k < n; k++) {                               // Theta(1)
            //Verifica se a rainha na posição k esta atacada em R
            if (validaPosicao(R, k) == false) {                       // n * O(n)
                cont = cont + 1;                                    // O(n)
            }
        }
        return cont;                                                // Theta(1)
    }

    /**
     * Valida se a k-ésima rainha posicionada está sob ataque.
     *
     * Uma rainha está sob ataque se há outra rainha na mesma linha, coluna ou
     * diagonal onde esta se encontra.
     *
     * Como as rainhas são adicionadas sempre na coluna seguinte, não há
     * necessi- dade de validar conflitos na mesma coluna.
     *
     * Complexidade Theta(k)
     *
     * @param R vetor das rainhas posicionadas. O elemento corresponde à coluna
     * e seu respectivo conteúdo corresponde à linha.
     *
     * @param k linha do vetor a ser analisada
     *
     * @return true se a k-ésima rainha não estiver sob ataque das demais já
     * posicionadas
     */
    public static boolean validaPosicao(int[] R, int k) {

        // Rainhas anteriormente posicionadas:
        for (int i = 0; i < k; i++) {                               // Theta(k)
            // Se sob ataque na linha
            if (R[i] == R[k]) {                                   // Theta(k)
                return false;                                   // O(1)
            }

            // Se sob ataque na diagonal
            if (Math.abs(R[i] - R[k]) == (k - i)) {                   // Theta(k)
                return false;                                      // O(1)
            }
        }
        // Posição válida
        return true;                                            // Theta(1)
    }

    /**
     * Avalia todas as rainhas posicionadas.
     *
     * Chamada o método valida posição para avaliação cada posição do vetor de
     * rainhas.
     *
     * Complexidade O(n^2)
     *
     * @param R vetor das rainhas posicionadas. O elemento corresponde à coluna
     * e seu respectivo conteúdo corresponde à linha.
     *
     * @return True ou False se existe alguma rainha em posição inválida.
     */
    public static boolean valida(int[] R) {
        //Recupera a quantidade de rainhas
        int n = R.length;                                           // Theta(1)
        int cont = 0;                                               // Theta(1)
        //Verifica se todas as rainhas estão em posições validas
        for (int k = 0; k < n; k++) {                               // Theta(n)
            if (validaPosicao(R, k) == false) {                       // n * O(n)
                cont = cont + 1;                                    // O(1)    
            }
        }
        return (cont == 0);                                           // Theta(1)
    }

    /**
     * Move uma rainha de uma coluna segundo a quantidade de delocamentos.
     *
     * @param n Quantidade de rainhas.
     * @param coluna Coluna da rainha.
     * @param deslocamento Número de posições que deve ser deslocada a rainha.
     * @return A nova posição da rainha de acordo com o deslocamento.
     */
    public static int moverParaBaixo(int n, int coluna, int deslocamento) {
        coluna = coluna + deslocamento;

        //Verifica se está acima dos limites
        if (coluna >= n) {
            coluna = coluna % n;
        }
        return coluna;
    }

    /**
     * Gera um um vizinho a partir do indivíduo atual.
     *
     * Aplica uma pertubação no indivíduo atual gerando um novo(vizinho).
     *
     * @param R Um indíviuo que deve ser modificado.
     * @return Um novo indivíduo vizinho a R.
     */
    public static int[] geraVizinho(int R[]) {

        int n = R.length;
        int coluna = RANDOMICO.nextInt(n);
        int linha = RANDOMICO.nextInt(n);

        //Inicializa o vetor de retorno
        int[] novoIndividuo = new int[n];
        int i = 0;

        //Copia o tabuleiro
        while (i < n) {
            novoIndividuo[i] = R[i];
            i = i + 1;
        }
        novoIndividuo[coluna] = moverParaBaixo(n, novoIndividuo[coluna], linha);
        //novoIndividuo[coluna] = linha;       

        return novoIndividuo;
    }

    /**
     * Executa as iterações do Simulated Annealing.
     *
     * Complexidade O(n^2)
     *
     * @param n Quantidade de rainhas.
     * @param M Número máximo de iterações (Entrada).
     * @param P Número máximo de Perturbações por iteração (Entrada).
     * @param L Número máximo de sucessos por iteração (Entrada).
     * @param alfa Fator de redução da temperatura.
     * @param TO Temperatura inicial
     * @return Retorna o melhor indivíduo encontrado nas geracões
     */
    public static int[] simulatedAnnealing(int n, int M, int P, int L, double alfa, double T0) {

        //ORIGINAL WIKI
        //https://pt.wikipedia.org/wiki/Simulated_annealing
        //Define o maior fitness pela quantidade rainhas posicionadas corretamente
        int melhorFitness = n;

        //Gera o solução inicial
        int[] S = geraIndividuo(n);

        //temperatura inicial        
        double T = T0;

        //Calcula o custo do candidato inicial          
        //Inicialização das variáveis
        int C = fitness(S);

        //Contador de sucessos
        int nSucesso;
        //Iterador de pertubações em uma iteração
        int j = 0;
        /*Loop principal – Verifica se foram atendidas as condições de termino do algoritmo*/
        do {
            //Iterador de pertubações em uma iteração
            int i = 0;
            nSucesso = 0;
            /*Loop Interno – Realização de perturbação em uma iteração*/
            do {
                //Gera o vizinho Si a partir de S
                int[] Si = geraVizinho(S);

                //Calcula o custo do vizinho Si 
                int Ci = fitness(Si);

                //Calcula o delta do fitness
                int delta = Ci - C;

                //Se o vizinho tem custo menor
                if (delta <= 0) {
                    // Escolhe o vizinho(Si)
                    S = Si;
                    C = Ci;
                    nSucesso = nSucesso + 1;
                } else {
                    if (Math.exp(-delta / T) > Math.random()) {
                        // Escolhe o vizinho(Si)
                        S = Si;
                        C = Ci;
                        nSucesso = nSucesso + 1;
                    }
                }
                i = i + 1;
                //Enquanto não encontrar o melhor fitness ou atingir o limite de iterações
            } while ((C != melhorFitness) && (i < P));
            //} while((nSucesso < L) && ( i < P));

            // System.out.println("i="+i); 
            //Atualização da temperatura
            T = T * alfa;
            j = j + 1;
        } while ((nSucesso >= 0) && (j < M));
        // System.out.println("j="+j); 
        //Retorna o melhor indivíduo encontrado nas gerações   
        return S;
    }

    /**
     * Faz a chamada do algoritmo Simulated Annealing e apresenta as
     * estatísticas.
     *
     * @param n Quantidade de rainhas.
     * @param iteracoes Quantidade de iterações a ser executado na simulação.
     * @param temperaturaInicial Percentual de probabilidade de mutação dos
     * indivíduos.
     */
    public static void executaSimulatedAnnealing(int n, int M, int P, int L, double alfa, double T0) {

        //Guarda o melhor indivíduo
        //Procura o menor indivíduo
        int[] melhorIndividuo = simulatedAnnealing(n, M, P, L, alfa, T0);

        if (valida(melhorIndividuo)) {
            //Incrementa o contador de soluções
            solucoes = solucoes + 1;
            //System.out.println("Solucao encontrada em " + geracao + " geracoes");
            //System.out.println("Solucao = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness = " + fitness(melhorIndividuo));
            //System.out.println("Solucao");
            imprimeSolucao(melhorIndividuo);
        } else {
            //System.out.println("Solucao nao encontrada após " + geracao + " geracoes");
            //System.out.println("Melhor Individuo = " + vetorToString(melhorIndividuo));
            //System.out.println("Fitness = " + fitness(melhorIndividuo));
        }
    }

    /**
     * Chamada do algoritmo.
     *
     * Provê o resultado da execução para cada problema resolvido, imprimindo em
     * tela detalhes estatísticos para cada caso e tempo global.
     *
     * @param listaProblemasASolucionar vetor contendo os problemas a serem
     * executados.
     * @param repeticoesTeste quantidade de repetições para cada problema.
     */
    private static void nRainhas(int[] listaProblemasASolucionar, int repeticoesTeste) {

        double tempoTotalDeTeste = 0;
        long tempoAcumulado = 0;
        long solucoesAcumulado = 0;

        //Realiza os testes para as quantidades das rainhas especificadas no vetor
        for (int problemaAtual = 0; problemaAtual < listaProblemasASolucionar.length; problemaAtual++) {

            int n = listaProblemasASolucionar[problemaAtual];

            System.out.println("-----------------------------------------------------------");
            System.out.println("Para " + n + " Rainhas \n");

            //Zera o tempo da execução da iteração
            tempoAcumulado = 0;
            //Zera o contador de solucoes da iteração
            solucoesAcumulado = 0;

            //Repete o teste para as vezes especificadas no vetor
            for (int testeAtual = 1; testeAtual <= repeticoesTeste; testeAtual++) {
                //Zera o contador de solucoes
                solucoes = 0;

                //Executa o garbage collector (gc) antes de cada teste
                System.gc();

                //Início da execução
                long tempo = System.currentTimeMillis();

                //Parâmetros do Simulated Annealing                
                //Número máximo de iterações (Entrada);
                int M = 2500;
                //Número máximo de Perturbações por iteração
                int P = 1;

                //Número máximo de sucessos por iteração
                int L = 1;

                //Temperatura inicial
                double T0 = n;

                double alfa = 0.01;

                //Executa a solução da simulação
                executaSimulatedAnnealing(n, M, P, L, alfa, T0);

                //Pega o tempo final do processamento da vez
                tempo = System.currentTimeMillis() - tempo;

                //Acumula o tempo do teste ao tempo final
                tempoAcumulado = tempoAcumulado + tempo;

                //Acumula a soluções do teste
                solucoesAcumulado = solucoesAcumulado + solucoes;
                System.out.println("Resultado da " + testeAtual + "a execucao: " + tempo + " milisegundos" + " com " + solucoes + " solucoes");
            }
            //Calcula a média do tempo
            double mediaTempo = tempoAcumulado / (double) repeticoesTeste;

            //Calcula a média de solucoes
            double mediaSolucoes = solucoesAcumulado / (double) repeticoesTeste;

            System.out.println("\nSolucoes Media: " + mediaSolucoes + " solucoes");
            System.out.println("Tempo Medio...: " + mediaTempo + " milisegundos");
            System.out.println("Acumulado.....: " + tempoAcumulado + " milisegundos");

            tempoTotalDeTeste = tempoTotalDeTeste + tempoAcumulado;
        }
        System.out.println("===========================================================");
        System.out.println("O tempo total do teste e " + tempoTotalDeTeste + " milisegundos.");
        System.out.println("===========================================================");
    }

    public static void main(String[] args) {

        System.out.println("Simulated Annealing");

        // Vetor contendo os problemas a serem processados.
        // Cada elemento define a ordem do tabuleiro e, consequentemente, a 
        // quantidade de rainhas a serem posicionadas.
        int[] listaProblemasASolucionar = {4, 6, 8, 10, 12, 14, 16, 18, 20, 22};
        //int[] listaProblemasASolucionar = {22, 50, 100};

        // Quantidade de repetições do processamento
        // Útil para fins estatísticos.
        int repeticoesTeste = 10;

        System.out.println("Executando N-Rainhas com " + repeticoesTeste + " repetições.\n\n");
        nRainhas(listaProblemasASolucionar, repeticoesTeste);
    }
}
