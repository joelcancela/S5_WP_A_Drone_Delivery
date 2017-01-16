package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.poi.PointOfInterest;

import java.util.ArrayList;
import java.util.List;

/**
 * The Graph class is used to represent the graph of routes between the points of interest.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class Graph {
    private List<Node> nodes;

    /**
     * Constructs a default graph from a list of pois.
     * @param pois
     */
    public Graph(List<PointOfInterest> pois) {
        nodes = new ArrayList<>();

        for (PointOfInterest poi : pois) {
            Node node = new Node(poi);

            for (Node n : nodes) {
                int distance = poi.distance(n.value);
                node.connections.add(new Edge(node, n, distance));
                n.connections.add(new Edge(n, node, distance));
            }

            nodes.add(node);
        }
    }


    public int distance(List<PointOfInterest> pois){
       return 0;
    }

    private class Edge {
        Node nodeA;
        Node nodeB;
        int weight;

        public Edge(Node nodeA, Node nodeB, int weight) {
            this.nodeA = nodeA;
            this.nodeB = nodeB;
            this.weight = weight;
        }
    }

    private class Node {
        PointOfInterest value;
        List<Edge> connections;

        public Node(PointOfInterest value) {
            this.value = value;
        }
    }

}
