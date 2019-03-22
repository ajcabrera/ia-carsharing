import IA.Comparticion.Usuario;
import jdk.nashorn.internal.runtime.UserAccessorProperty;

import java.util.ArrayList;
import java.util.Vector;

import static java.lang.Math.abs;

public class Node {
    private Vector<Integer> pos;
    private short dist;
    private Vector<Usuario> car;
    private Vector<Usuario> remaining;

    public Node(Usuario cond, ArrayList<Usuario> pasajeros) {
        pos = new Vector<Integer>(2);
        pos.add(cond.getCoordOrigenX());
        pos.add(cond.getCoordOrigenY());
        dist = 0;
        car = new Vector<Usuario>();
        remaining = new Vector<Usuario>(pasajeros.size());
        for (int i = 0; i < pasajeros.size(); i++) {
            remaining.add(pasajeros.get(i));
        }
    }

    public Node(Vector<Integer> p, short d, Vector<Usuario> c, Vector<Usuario> r){
        this.pos = p;
        this.dist = d;
        this.car = c;
        this.remaining = r;
    }

    public Node(short oldDist, Vector<Integer> oldPos) {
        remaining = new Vector<>();
        car = new Vector<>();

        pos = new Vector<Integer>(2);
        pos.set(0,oldPos.get(0));
        pos.set(1,oldPos.get(1));
    }

    //Dejar pasajero
    private Node dejarPasajero(Vector<Usuario> oldCar, Vector<Usuario> oldRemaining, short oldDist, Vector<Integer> oldPos, int dejado) {
        Vector<Usuario> r = new Vector<Usuario>();
        for (int i = 0; i < oldRemaining.size(); i++)
            r.add(oldRemaining.get(i));

        Vector<Usuario> c = new Vector<Usuario>();
        for (int i = 0; i < oldCar.size(); i++)
            if (i != dejado) c.add(oldCar.get(i));


        Vector<Integer> p = new Vector<Integer>();
        p.add(oldCar.get(dejado).getCoordDestinoX());
        p.add(oldCar.get(dejado).getCoordDestinoY());

        short d = (short)(oldDist + computeDistance(p,oldPos));

        return new Node(p,d,c,r);

    }

    private Node cogerPasajero(Vector<Usuario> oldCar, Vector<Usuario> oldRemaining, short oldDist, Vector<Integer> oldPos, int cogido) {

        Vector<Usuario> r = new Vector<Usuario>();
        for (int i = 0; i < oldRemaining.size(); i++)
            if (i != cogido) r.add(oldRemaining.get(i));

        Vector<Usuario> c = new Vector<Usuario>();
        for (int i = 0; i < oldCar.size(); i++)
            c.add(oldCar.get(i));
        c.add(oldRemaining.get(cogido));


        Vector<Integer> p = new Vector<Integer>();
        p.add(oldRemaining.get(cogido).getCoordOrigenX());
        p.add(oldRemaining.get(cogido).getCoordOrigenY());

        short d = (short)(oldDist + computeDistance(p,oldPos));

        return new Node(p,d,c,r);
    }

    private Node destinoConductor(short oldDist, Vector<Integer> oldPos, Vector<Integer> posDestino) {
        Vector<Usuario> r = new Vector<Usuario> ();
        Vector<Usuario> c = new Vector<Usuario>();

        Vector<Integer> p = new Vector<Integer>();
        p.add(posDestino.get(0));
        p.add(posDestino.get(1));

        short d = (short)(oldDist + computeDistance(p,oldPos));

        return new Node(p,d,c,r);
    }

    public boolean isFinal(Vector<Integer> posFinal) {
        return posFinal.get(0) == pos.get(0) && posFinal.get(1) == pos.get(1);
    }

    public short getDistance() {
        return dist;
    }

    public Vector<Node> getNextState(Vector<Integer> posDestino) {
        Vector<Node> result = new Vector<Node>();
        for (int i = 0; i < car.size(); i++)
            result.add(dejarPasajero(car,remaining,dist,pos,i));

        if (car.size() < 2) {
            for (int i = 0; i < remaining.size(); i++)
                result.add(cogerPasajero(car,remaining,dist,pos,i));
        }

        if (result.size() == 0) {
                result.add(destinoConductor(dist,pos,posDestino));
        }
        return result;
    }

    private short computeDistance(Vector<Integer> p1, Vector<Integer> p2) {
        return (short)(abs(p1.get(0) - p2.get(0)) + abs(p1.get(1) - p2.get(1)));
    }

}