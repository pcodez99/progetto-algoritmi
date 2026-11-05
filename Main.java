/*
 Compilazione:  javac *.java
 Esecuzione:    java Main < test/esempioInputProfessoeessa.txt

 Il programma:
 1. modella le attività con un grafo orientato (Digraph);
 2. calcola un ordine con gradi entranti e coda FIFO (TopologicalKahn);
 3. rileva un ciclo se l'ordine contiene meno di V attivita';
 4. se esiste un ciclo, segnala che la pianificazione completa e' impossibile.
 */

import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static String[] nomi;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Digraph G;
        
        try {
            G = leggiGrafo(scanner);
        } catch (Error e) {
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
            System.out.println("Cliclo rilevato.");
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
        for (int v : vertici) {
            System.out.print(nomi[v]);
        }
        System.out.println();
    }
}
