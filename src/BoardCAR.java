import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;
import IA.Comparticion.Usuario;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

public class BoardCAR {

    private BitSet schedule;
    private Vector<Short> distance;

    public static String SWAP = "swap";
    public static String MOVE = "move";

    private static Vector<Usuario> conductors;
    private static Vector<Usuario> passengers;


    public BoardCAR(Vector<Usuario> pasajeros, Vector<Usuario> conductores, Vector<Usuario> pas) {
        conductors = conductores;
        passengers = pas;

        schedule = new BitSet(conductores.size()*(1+pasajeros.size()+conductores.size()));
        schedule.clear();
        distance = new Vector<Short>();
        distance.setSize(conductores.size());
        EquitableRandomInit();

        for (int i = 0; i < conductors.size(); i++) {
            Dijkstra algo = new Dijkstra(getPasAssigned(i),conductores.get(i));
            distance.set(i,algo.executeDistance());
        }
    }

    public BoardCAR(BitSet oldSchedule, Vector<Short> oldDistance) {
        schedule = (BitSet)oldSchedule.clone();
        distance = new Vector<Short>();
        distance.setSize(oldDistance.size());

        for (int i = 0; i < oldDistance.size(); i++) {
            distance.set(i,oldDistance.get(i));
        }
    }

    private void EquitableRandomInitTrue() {
        Random rand = new Random();
        Vector<Integer> aux = new Vector<Integer> ();

        int each = passengers.size()/conductors.size();

        for (int i = 0; i < passengers.size(); i++) aux.add(i);
        for (int i = 0; i < conductors.size(); i++) {
            for (int j = 0; j < each; j++) {
                int r = rand.nextInt(aux.size());
                int idx = aux.get(r);
                aux.remove(r);
                idx = getIndex(i, idx);
                schedule.set(idx);
            }
        }
        int c = 0;
        while (aux.size() > 0) {
            int r = rand.nextInt(aux.size());
            int idx = aux.get(r);
            aux.remove(r);
            idx = getIndex(c, idx);
            schedule.set(idx);
            c++;
        }
    }

    private void EquitableRandomInit() {
        Random rand = new Random();
        Vector<Integer> aux = new Vector<Integer> ();

        int each = passengers.size()/conductors.size() + 1;

        for (int i = 0; i < passengers.size(); i++) aux.add(i);
        for (int i = 0; i < conductors.size()/2; i++) {
            for (int j = 0; j < each; j++) {
                int r = rand.nextInt(aux.size());
                int idx = aux.get(r);
                aux.remove(r);
                idx = getIndex(i, idx);
                schedule.set(idx);
            }
        }
        int c = 0;
        while (aux.size() > 0) {
            int r = rand.nextInt(aux.size());
            int idx = aux.get(r);
            aux.remove(r);
            idx = getIndex(c, idx);
            schedule.set(idx);
            c++;
        }
    }

    public void swapPassajero(int condI, int pasI, int condJ, int pasJ) {

        System.out.println("Swap C" + condI + " P" + pasI + " with C" + condJ + " P" + pasJ);

        schedule.clear(getIndex(condI,pasI));
        schedule.set(getIndex(condJ,pasI));

        schedule.clear(getIndex(condJ,pasJ));
        schedule.set(getIndex(condI,pasJ));

        Dijkstra algo = new Dijkstra(getPasAssigned(condI),conductors.get(condI));
        distance.set(condI,algo.executeDistance());
        algo = new Dijkstra(getPasAssigned(condJ),conductors.get(condJ));
        distance.set(condJ,algo.executeDistance());

        System.out.println("Heuristic value: " + this.heuristicValue());
    }

    public void movePasajero(int condI, int pasI, int condJ) {

        System.out.println("Move P" + pasI + " from C" + condI + "  to C" + condJ);

        schedule.clear(getIndex(condI,pasI));
        schedule.set(getIndex(condJ,pasI));

        Dijkstra algo = new Dijkstra(getPasAssigned(condI),conductors.get(condI));
        distance.set(condI,algo.executeDistance());
        algo = new Dijkstra(getPasAssigned(condJ),conductors.get(condJ));
        distance.set(condJ,algo.executeDistance());


        System.out.println("Heuristic value: " + this.heuristicValue());
    }

    public double heuristicValue() {
        return abusivo();
    }

    private double abusivo() {
        double sum = 0;
        for (int i = 0; i < distance.size(); i++) {
            sum += distance.get(i);
            if (distance.get(i) > 300) sum+= distance.get(i)*5;
        }
        return sum/3.79;
    }

    private double hf() {
        double sum = 0;
        for (int i = 0; i < distance.size(); i++) {
            sum += Math.pow(distance.get(i)/100, distance.get(i) > 300 ? 2 : 1);
        }
        return Math.pow(sum,getNumCond()/getNumCond());
    }

    private double hfSumPow3() {
        double sum = 0;
        for (int i = 0; i < distance.size(); i++) {
            sum += Math.pow(distance.get(i),3);
        }
        return sum;
    }

    private double hfSumPow2() {
        double sum = 0;
        for (int i = 0; i < distance.size(); i++) {
            sum += Math.pow(distance.get(i),2);
        }
        return sum;
    }

    private double hfThreshold30() {
        double sum = 0;
        for (int i = 0; i < distance.size(); i++) {
            sum += Math.pow(distance.get(i), distance.get(i) > 300 ? 3 : 1);
        }
        return sum;
    }


    // Auxiliar functions

    private ArrayList<Usuario> getPasAssigned (int cond) {
        ArrayList<Usuario> res = new ArrayList<>();
        for (int i = 0; i < rowSize(); i++) {
            if (schedule.get(cond*rowSize()+1+i)) {
                res.add(passengers.get(i));
            }
        }
        return res;
    }
    private int getIndex(int c, int p) {
        return c*rowSize()+1+p;
    }

    public BitSet getPassengers(int c) {
        return (BitSet)schedule.get(c*rowSize(),(c+1)*rowSize());
    }

    public boolean isPassenger(int c, int p) {
        return schedule.get(c*rowSize()+1+p);
    }

    public int rowSize() {
        return 1+passengers.size()+conductors.size();
    }

    public int getNumCond() {
        return conductors.size();
    }

    public BitSet getSchedule() {
        return schedule;
    }

    public Usuario getPassenger(int p) {
        if (p-1 > passengers.size()) return passengers.get(p-1);
        else return conductors.get(p-1-passengers.size());
    }


    public Vector<Short> getDistance() {
        return distance;
    }

    public void printBoard(boolean more) {
        System.out.println("Board:");
        for (int i = 0; i < distance.size(); i++) {
            System.out.println("Conductor " + i + ", distance: " + distance.get(i));
            System.out.print("Pasajeros: ");

            for (int j = 0; j < rowSize(); j++) {
                if (schedule.get(i * rowSize() + 1 + j)) {
                    System.out.print(j + " ");
                }
            }
            System.out.println("");
        }

        if (more) {
            for (int k = 0; k < passengers.size(); k++) {
                System.out.println("passenger " + k + " " + coordIni(passengers.get(k)));
            }

            for (int k = 0; k < conductors.size(); k++) {
                System.out.println("conductors " + k + " " + coordIni(conductors.get(k)));
            }
        }
    }

    private String coordIni(Usuario u) {
        String origin = "[" + u.getCoordOrigenX() + "," + u.getCoordOrigenY() + "]";
        String destination = "[" + u.getCoordDestinoX() + "," + u.getCoordDestinoY() + "]";
        return origin + " " + destination;
    }

}
