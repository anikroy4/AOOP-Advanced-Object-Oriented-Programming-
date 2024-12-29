package Answer2;
import java.util.*;

class Node {
    private String Name;
    private int cost;
    private List<Node> neighbors;

    public Node(String name, int cost) {
        this.Name = name;
        this.cost = cost;
        this.neighbors = new ArrayList<>();
    }

    public String getName() {
        return Name;
    }

    public int getCost() {
        return cost;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    @Override
    public String toString() {
        return Name + " (" + cost + ")";
    }
}

class Graph {
    private Map<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addNode(String name, int cost) {
        nodes.put(name, new Node(name, cost));
    }

    public void addEdge(String from, String to) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);
        if (fromNode != null && toNode != null) {
            fromNode.addNeighbor(toNode);
        }
    }

    public void displayGraph() {
        for (Node node : nodes.values()) {
            System.out.print(node + " -> ");
            for (Node neighbor : node.getNeighbors()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }
}

public class graphsimulation {
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Add nodes based on the provided graph
        graph.addNode("5", 5);
        graph.addNode("4", 4);
        graph.addNode("3", 3);
        graph.addNode("1", 1);
        graph.addNode("-6", -6);
        graph.addNode("0", 0);
        graph.addNode("7", 7);
        graph.addNode("-4", -4);
        graph.addNode("2", 2);
        graph.addNode("9", 9);
        graph.addNode("8", 8);

        // Add edges based on the provided graph
        graph.addEdge("5", "4");
        graph.addEdge("5", "3");
        graph.addEdge("4", "1");
        graph.addEdge("4", "-6");
        graph.addEdge("3", "0");
        graph.addEdge("3", "7");
        graph.addEdge("1", "2");
        graph.addEdge("-6", "9");
        graph.addEdge("0", "8");
        graph.addEdge("7", "-4");

        // Display the graph
        graph.displayGraph();
    }
}

