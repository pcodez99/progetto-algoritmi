import java.util.ArrayList;
import java.util.List;

/*
 Grafo orientato implementazione con array delle liste di adiacenza
 */
public class Digraph {
    private final int V;
    private final List<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        adj = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // Vertici adiacenti (successori) di v
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

}
