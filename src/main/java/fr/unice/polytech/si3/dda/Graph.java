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
     *
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

    /**
     * Return the sum of the distance between the pois in the order of the list.
     *
     * @param pois the lists of pois.
     * @return the distance.
     */
    public static int distance(List<PointOfInterest> pois) {
        int distance = 0;
        PointOfInterest poi;
        PointOfInterest next;
        for (int i = 0; i < pois.size() - 1; i++) {
            poi = pois.get(i);
            next = pois.get(i + 1);
            distance += poi.distance(next);
        }
        return distance;
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
