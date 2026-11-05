import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// Ordinamento topologico con gradi entranti e coda FIFO (slide lab 44).
public class TopologicalKahn {
    private final ArrayList<Integer> ordine;
    private final int numeroAttivita;

    public TopologicalKahn(Digraph G) {
        numeroAttivita = G.V();
        ordine = new ArrayList<Integer>();
        int[] gradiEntranti = new int[G.V()];
        Queue<Integer> coda = new LinkedList<Integer>();

        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                gradiEntranti[w]++;
            }
        }

        for (int v = 0; v < G.V(); v++) {
            if (gradiEntranti[v] == 0) {
                coda.add(v);
            }
        }

        while (!coda.isEmpty()) {
            int v = coda.remove();
            ordine.add(v);
            for (int w : G.adj(v)) {
                gradiEntranti[w]--;
                if (gradiEntranti[w] == 0) {
                    coda.add(w);
                }
            }
        }
    }

    public boolean isComplete() {
        return ordine.size() == numeroAttivita;
    }

    public ArrayList<Integer> order() {
        return ordine;
    }
}
