import IA.Comparticion.Usuario;

import java.util.*;

public class Dijkstra {
    private PriorityQueue listNodes;

    private Map<Node,Node> predecessor;

    private Vector<Integer> posFinal;

    public Dijkstra(ArrayList<Usuario> pasajeros, Usuario conductor) {

        Comparator<Node> comp = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getDistance() - o2.getDistance();
            }
        };

        listNodes = new PriorityQueue(comp);
        Node initial = new Node(conductor,pasajeros);
        listNodes.add(initial);

        posFinal = new Vector<Integer>(2);
        posFinal.add(conductor.getCoordDestinoX());
        posFinal.add(conductor.getCoordDestinoY());
    }

    public short executeDistance() {
        int fin = -1;
        while (fin == -1) {
            Node actual = (Node)listNodes.get();
            listNodes.remove();
            if (actual.isFinal(posFinal)) fin = actual.getDistance();
            if (actual.getDistance() > 400) fin = actual.getDistance();
            addNeighbors(actual);
        }

        return (short)fin;
    }

    public int executePath() {
        int fin = -1;
        while (fin == -1) {
            Node actual = (Node)listNodes.get();
            listNodes.remove();
            if (actual.isFinal(posFinal)) fin = actual.getDistance();
            addNeighbors(actual);
        }

        return fin;
    }

    private void addNeighbors(Node actual) {
        Vector<Node> neighbors = actual.getNextState(posFinal);
        for (Node n:neighbors)
            listNodes.add(n);
    }
}
