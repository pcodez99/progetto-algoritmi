/*
 Compilazione:  javac *.java
 Esecuzione:    java Main < test/esempioInputProfessoeessa.txt

 Il programma:
 1. modella le attività con un grafo orientato (Digraph);
 2. calcola un ordine con gradi entranti e coda FIFO (TopologicalOrder);
 3. rileva un ciclo se l'ordine contiene meno di V attivita';
 4. se esiste un ciclo, lo ricostruisce con DFS e segnala la pianificazione impossibile.
 */

import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.Stack;
import java.util.NoSuchElementException;

public class Main {
    private static String[] nomi;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Digraph G;
        
        try {
            G = leggiGrafo(scanner);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            String input = e.getMessage();
            if (input == null) {
                input = "dati mancanti";
            }
            System.out.println("Errore nell'input: " + input);
            scanner.close();
            System.exit(1);
            return;
        }
        scanner.close();

        TopologicalOrder pianificazione = new TopologicalOrder(G);

        if (pianificazione.isComplete()) {
            System.out.println("Ordine di esecuzione possibile:");
            stampa(pianificazione.order());
            System.out.println("Pianificazione valida.");
        } else {
            DirectedCycle controllo = new DirectedCycle(G);
            Stack<Integer> ciclo = controllo.cycle();
            System.out.print("Ciclo rilevato: ");
            // Stack si legge dalla cima per seguire gli archi del ciclo.
            for (int i = ciclo.size() - 1; i >= 0; i--) {
                System.out.print(nomi[ciclo.get(i)]);
                if (i > 0) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
            System.out.println("Pianificazione non valida: non e' possibile eseguire tutte le attivita'.");
        }
    }

    private static Digraph leggiGrafo(Scanner in) {
        int V = leggiIntero(in, "V");
        int E = leggiIntero(in, "E");

        // Tabella dei simboli: identificativo -> indice del vertice.
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
                throw new IllegalArgumentException("attività non indicata nell'elenco attività " + a + " " + b + ": " + a);
            }
            if (w == null) {
                throw new IllegalArgumentException("attività non indicata nell'elenco attività " + a + " " + b + ": " + b);
            }
            G.addEdge(v, w);
        }
        if (in.hasNext()) {
            throw new IllegalArgumentException("ci sono più di E = " + E + " dipendenze");
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

    // Stampa le attivita' nell'ordine prodotto.
    private static void stampa(List<Integer> vertici) {
        if (vertici.isEmpty()) {
            System.out.print("(nessuna)");
        }
        for (int v : vertici) {
            System.out.print(nomi[v] + " ");
        }
        System.out.println();
    }
}
