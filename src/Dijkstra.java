import IA.Comparticion.Usuario;
import com.sun.xml.internal.ws.wsdl.writer.UsingAddressing;

import java.util.*;

public class Dijkstra {
    private Vector<Node> listNodes;

    private Map<Node,Node> predecessor;

    private Vector<Integer> posFinal;

    public Dijkstra(Vector<Usuario> pasajeros, Usuario conductor) {
        listNodes = new Vector<Node> ();
        Node initial = new Node(conductor,pasajeros);
        listNodes.add(initial);

        posFinal = new Vector<Integer>(2);
        posFinal.set(0, conductor.getCoordDestinoX());
        posFinal.set(1, conductor.getCoordDestinoY());
    }

    public int executeDistance() {
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
