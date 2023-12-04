import java.util.*;

class Graph {
    private int vertices;
    private LinkedList<Integer>[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            this.adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination) {
        this.adjacencyList[source].add(destination);
    }

    public void depthFirstTraversal(int startVertex) {
        boolean[] visited = new boolean[vertices];
        System.out.println("Depth-First Traversal (starting from vertex " + startVertex + "):");
        depthFirstTraversalUtil(startVertex, visited);
        System.out.println();
    }

    private void depthFirstTraversalUtil(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (Integer adjacentVertex : adjacencyList[vertex]) {
            if (!visited[adjacentVertex]) {
                depthFirstTraversalUtil(adjacentVertex, visited);
            }
        }
    }

    public void breadthFirstTraversal(int startVertex) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();

        System.out.println("Breadth-First Traversal (starting from vertex " + startVertex + "):");

        visited[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            System.out.print(currentVertex + " ");

            for (Integer adjacentVertex : adjacencyList[currentVertex]) {
                if (!visited[adjacentVertex]) {
                    visited[adjacentVertex] = true;
                    queue.add(adjacentVertex);
                }
            }
        }

        System.out.println();
    }
}

public class GraphTraversal {
    public static void main(String[] args) {
        // Create a graph
        int vertices = 5;
        Graph graph = new Graph(vertices);

        // Add edges
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);

        // Perform depth-first traversal
        graph.depthFirstTraversal(0);

        // Perform breadth-first traversal
        graph.breadthFirstTraversal(0);
    }
}

