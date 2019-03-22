import IA.Comparticion.Usuario;

import java.util.*;

public class Dijkstra {
    private Vector<Node> listNodes;

    private Map<Node,Node> predecessor;

    private Vector<Integer> posFinal;

    public Dijkstra(ArrayList<Usuario> pasajeros, Usuario conductor) {
        listNodes = new Vector<Node> ();
        Node initial = new Node(conductor,pasajeros);
        listNodes.add(initial);

        posFinal = new Vector<Integer>(2);
        posFinal.add(conductor.getCoordDestinoX());
        posFinal.add(conductor.getCoordDestinoY());
    }

    public short executeDistance() {
        int fin = -1;
        while (fin == -1) {
            Node actual = getMinimum();
            listNodes.remove(actual);
            if (actual.isFinal(posFinal)) fin = actual.getDistance();
            addNeighbors(actual);
        }

        return (short)fin;
    }

    public int executePath() {
        int fin = -1;
        while (fin == -1) {
            Node actual = getMinimum();
            listNodes.remove(actual);
            if (actual.isFinal(posFinal)) fin = actual.getDistance();
            addNeighbors(actual);
        }

        return fin;
    }

    private Node getMinimum() {
        int index = 0;
        int min = listNodes.get(0).getDistance();
        for (int i = 1; i < listNodes.size(); i++) {
            if (listNodes.get(i).getDistance() < min) {
                index = i;
                min = listNodes.get(i).getDistance();
            }
        }

        return listNodes.get(index);
    }

    private void addNeighbors(Node actual) {
        Vector<Node> neighbors = actual.getNextState(posFinal);
        for (Node n:neighbors)
            listNodes.add(n);
    }
}
