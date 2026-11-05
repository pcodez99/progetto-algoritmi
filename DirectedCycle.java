import java.util.Stack;


//Rilevamento di un ciclo orientato con DFS

public class DirectedCycle {
    private final boolean[] marked;
    private final int[] edgeTo;
    private final boolean[] onStack;  // onStack[v] = v e' nello stack della ricorsione?
    private Stack<Integer> cycle;     // ciclo orientato trovato (null se il grafo e' un DAG)

    public DirectedCycle(Digraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        // Si avvia una DFS da ogni vertice non ancora marcato:
        // così si esplorano anche le componenti non raggiungibili tra loro. (CASO ATTIVITA ISOLATA)
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v] && cycle == null) dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (cycle != null) return;               // ciclo già trovato: si interrompe
            else if (!marked[w]) {                   // arco dell'albero DFS
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {                 // arco all'indietro: ciclo
                cycle = new Stack<Integer>();   // pila del ciclo
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;                          // v esce dallo stack della ricorsione
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Stack<Integer> cycle() {
        return cycle;
    }
}
