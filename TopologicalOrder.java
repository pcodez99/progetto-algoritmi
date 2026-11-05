import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//Modello: Grafo orientato in cui 
// - i vertici sono associati ai task
// - gli archi rappresentano i vincoli di precedenza
//mappa le attività con un grafo orientato

public class TopologicalOrder {

    private final ArrayList<Integer> topologicalOrder; //risultato
    private final int numAttivita;

    public TopologicalOrder(Digraph G) {
        numAttivita = G.V();

        topologicalOrder = new ArrayList<Integer>();

        //per ogni vertice, avremo un numero di gradi entranti:
        int[] gradiEntranti = new int[G.V()];

        //la coda gestisce i vertici con grado entrante 0 
        // (attività che possono essere svolte perchè non dipendono più da nessuno)
        Queue<Integer> coda = new LinkedList<Integer>();

        //INIZIALIZZAZIONE

        //inzializza l'array con il numero di gradi entranti per ogni vertice
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                gradiEntranti[w]++;
            }
        }

        //inizializza la coda: nella coda mettiamo tutti e soli i vertici che non hanno grado entrante
        for (int v = 0; v < G.V(); v++) {
            if (gradiEntranti[v] == 0) {
                coda.add(v);
            }
        }

        while (!coda.isEmpty()) {
            //rimuovi gli elementi che non hanno più dipendenze in ordine
            int v = coda.remove();
            topologicalOrder.add(v);
            for (int w : G.adj(v)) {
                gradiEntranti[w]--;
                if (gradiEntranti[w] == 0) { //se il nodo che abbiamo rimosso rimuove qualche dipendenza
                    coda.add(w);  //aggiungiamolo alla coda
                }
            }
        }
    }

    public boolean isComplete() {
        return topologicalOrder.size() == numAttivita;
    }

    public ArrayList<Integer> order() {
        return topologicalOrder;
    }
}
