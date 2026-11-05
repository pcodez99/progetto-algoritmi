import java.util.Stack;

//Ordinamento topologico con DFS: reverse postorder

public class DepthFirstOrder {
    private final boolean[] marked;
    private final Stack<Integer> reversePost = new Stack<Integer>(); // pila

    public DepthFirstOrder(Digraph G) {
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
        reversePost.push(v);
    }

    // Vertici in reverse postorder (dalla cima della pila)
    public Stack<Integer> reversePost() {
        return reversePost;
    }
}
