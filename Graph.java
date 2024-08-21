import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int size;
    private final int[][] adjMatrix;

    public Graph(int size) {
        this.size = size;
        adjMatrix = new int[size][size];
    }

    public void addEdge(int source, int destination) {
        adjMatrix[source][destination] = 1;
    }

    private boolean dfsUtil(int v, boolean[] visited, boolean[] recStack, List<int[]> cycleEdges, List<Integer> cycleVertices) {
        visited[v] = true;
        recStack[v] = true;

        for (int i = 0; i < size; i++) {
            if (adjMatrix[v][i] == 1) {
                if (!visited[i]) {
                    if (dfsUtil(i, visited, recStack, cycleEdges, cycleVertices)) {
                        cycleEdges.add(new int[]{v, i});
                        cycleVertices.add(i);
                        return true;
                    }
                } else if (recStack[i]) {
                    cycleEdges.add(new int[]{v, i});
                    cycleVertices.add(i);
                    cycleVertices.add(v);
                    return true;
                }
            }
        }

        recStack[v] = false;
        return false;
    }

    public Result isCyclic() {
        boolean[] visited = new boolean[size];
        boolean[] recStack = new boolean[size];
        List<int[]> cycleEdges = new ArrayList<>();
        List<Integer> cycleVertices = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (!visited[i]) {
                if (dfsUtil(i, visited, recStack, cycleEdges, cycleVertices)) {
                    return new Result(true, cycleEdges, cycleVertices);
                }
            }
        }
        return new Result(false, cycleEdges, cycleVertices);
    }

    public static class Result {
        public final boolean hasCycle;
        public final List<int[]> cycleEdges;
        public final List<Integer> cycleVertices;

        public Result(boolean hasCycle, List<int[]> cycleEdges, List<Integer> cycleVertices) {
            this.hasCycle = hasCycle;
            this.cycleEdges = cycleEdges;
            this.cycleVertices = cycleVertices;
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);

        Result result = graph.isCyclic();
        if (result.hasCycle) {
            System.out.println("Graph contains a cycle.");
            System.out.println("Cycle edges: " + result.cycleEdges);
            System.out.println("Cycle vertices: " + result.cycleVertices);
        } else {
            System.out.println("Graph does not contain a cycle.");
        }
    }
}
