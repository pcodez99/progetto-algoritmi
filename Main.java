/*
 Compilazione:  javac *.java
 Esecuzione:    java Main < test/esempioInputProfessoeessa.txt

 Il programma:
 1. modella le attivita' con un grafo orientato (Digraph);
 2. rileva un'eventuale dipendenza circolare con DFS (DirectedCycle);
 3. se il grafo e' un DAG, calcola il reverse postorder (DepthFirstOrder);
 4. se esiste un ciclo, segnala che la pianificazione completa e' impossibile.
 */

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static String[] nomi;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Digraph G;
        
        try {
            G = leggiGrafo(scanner);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            String messaggio = e.getMessage();
            if (messaggio == null) {
                messaggio = "dati mancanti";
            }
            System.out.println("Errore nell'input: " + messaggio);
            scanner.close();
            System.exit(1);
            return;
        }
        scanner.close();

        DirectedCycle controlloCicli = new DirectedCycle(G);

        if (!controlloCicli.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            System.out.println("Ordine di esecuzione possibile:");
            stampa(dfs.reversePost(), " ");
            if (!rispettaDipendenze(G, dfs.reversePost())) {
                throw new IllegalStateException("Ordine prodotto non valido");
            }
            System.out.println("Pianificazione valida.");
        } else {
            System.out.print("Dipendenza circolare rilevata: ");
            stampa(controlloCicli.cycle(), " -> ");
            System.out.println("Pianificazione non valida: non e' possibile eseguire tutte le attivita'.");
        }
    }

    // Legge V, E, gli identificativi e gli E archi; valida l'input.
    private static Digraph leggiGrafo(Scanner in) {
        int V = leggiIntero(in, "V");
        int E = leggiIntero(in, "E");

        // Tabella dei simboli: identificativo -> indice del vertice.
        // HashMap: ricerca e inserimento in O(1) atteso.
        HashMap<String, Integer> indice = new HashMap<String, Integer>();
        nomi = new String[V];
        for (int v = 0; v < V; v++) {
            String id = in.next();
            if (indice.containsKey(id)) {
                throw new IllegalArgumentException("attivita' duplicata: " + id);
            }
            indice.put(id, v);
            nomi[v] = id;
        }

        Digraph G = new Digraph(V);
        for (int i = 0; i < E; i++) {
            String a = in.next();
            String b = in.next();
            Integer v = indice.get(a);
            Integer w = indice.get(b);
            if (v == null) {
                throw new IllegalArgumentException("attivita' sconosciuta nella dipendenza " + a + " " + b + ": " + a);
            }
            if (w == null) {
                throw new IllegalArgumentException("attivita' sconosciuta nella dipendenza " + a + " " + b + ": " + b);
            }
            G.addEdge(v, w);
        }
        if (in.hasNext()) {
            throw new IllegalArgumentException("ci sono piu' di E = " + E + " dipendenze");
        }
        return G;
    }

    private static int leggiIntero(Scanner in, String nome) {
        if (!in.hasNextInt()) {
            throw new IllegalArgumentException(nome + " deve essere un intero");
        }
        int x = in.nextInt();
        if (x < 0) {
            throw new IllegalArgumentException(nome + " non puo' essere negativo");
        }
        return x;
    }

    // Verifica che per ogni arco v -> w, v preceda w nell'ordine. Tempo O(V + E).
    private static boolean rispettaDipendenze(Digraph G, Stack<Integer> ordine) {
        int[] pos = new int[G.V()];
        int i = 0;
        for (int j = ordine.size() - 1; j >= 0; j--) {
            int v = ordine.get(j);
            pos[v] = i;
            i++;
        }
        if (i != G.V()) {
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (pos[v] >= pos[w]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Stampa i nomi separati da uno spazio oppure da una freccia.
    private static void stampa(Stack<Integer> vertici, String separatore) {
        boolean primo = true;
        // Stack si legge dalla cima: il for-each partirebbe dal fondo.
        for (int i = vertici.size() - 1; i >= 0; i--) {
            int v = vertici.get(i);
            if (!primo) {
                System.out.print(separatore);
            }
            System.out.print(nomi[v]);
            primo = false;
        }
        if (primo) {
            System.out.print("(nessuna)");
        }
        System.out.println();
    }
}
