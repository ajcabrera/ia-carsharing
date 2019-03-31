import IA.Comparticion.Usuario;

import java.util.*;

public class BoardCAR {

    ArrayList<ArrayList<Usuario>> itineraries;

    public static String SWAP = "swap";
    public static String MOVE = "move";
    public static String MOVECONDUCTOR = "move conductor";

    public static String cfg;

    public static Vector<Usuario> passengers;
    public static Vector<Usuario> drivers;

    public BoardCAR(Vector<Usuario> passengersAt, Vector<Usuario> driversAt, String cfgAt) {
        itineraries = new ArrayList<>();
        cfg = cfgAt;
        passengers = passengersAt;
        drivers = driversAt;

        if (cfg.contains("E")) equitableRandomInit(passengers,drivers);
        else if (cfg.contains("D")) thomasMethod(passengers, drivers);

    }

    public BoardCAR(ArrayList<ArrayList<Usuario>> oldItineraries) {
        itineraries = new ArrayList<>();

        for (int i = 0; i < oldItineraries.size(); i++) {
            ArrayList<Usuario> aux = (ArrayList<Usuario>) oldItineraries.get(i).clone();
            itineraries.add(aux);
        }
    }

    private void thomasMethod(Vector<Usuario> passengers, Vector<Usuario> drivers) {
        int n = drivers.size()/2;
        int each = passengers.size()/n;

        for (int i = 0; i < drivers.size(); i++) {
            itineraries.add(new ArrayList<>());
            itineraries.get(i).add(drivers.get(i));
        }
        for (int i = passengers.size()-1; i >= 0; i--) {
                itineraries.get(i%n+n).add(passengers.get(i));
                itineraries.get(i%n+n).add(passengers.get(i));
        }
        for (int i = 0; i < drivers.size(); i++) itineraries.get(i).add(drivers.get(i));
    }

    private void equitableInit(Vector<Usuario> passengers, Vector<Usuario> drivers) {
        int each = passengers.size()/drivers.size();
        int n = 0;
        for (int i = 0; i < drivers.size(); i++) {
            itineraries.add(new ArrayList<>());
            itineraries.get(i).add(drivers.get(i));
            for (int j = 0; j < each; j++) {
                itineraries.get(i).add(passengers.get(n));
                itineraries.get(i).add(passengers.get(n));
                n++;
            }
        }

        int c = 0;
        while (n < passengers.size()) {
            itineraries.get(c).add(1,passengers.get(n));
            itineraries.get(c).add(2,passengers.get(n));
            c++;
        }

        for (int i = 0; i < drivers.size(); i++) itineraries.get(i).add(drivers.get(i));
    }

    private void equitableRandomInit(Vector<Usuario> passengers, Vector<Usuario> drivers) {
        Random rand = new Random(12);
        int each = passengers.size()/drivers.size();
        Vector<Integer> aux = new Vector<> ();

        for (int i = 0; i < passengers.size(); i++) aux.add(i);

        for (int i = 0; i < drivers.size(); i++) {
            itineraries.add(new ArrayList<>());
            itineraries.get(i).add(drivers.get(i));
            for (int j = 0; j < each; j++) {
                int r = rand.nextInt(aux.size());
                int idx = aux.get(r);
                aux.remove(r);
                itineraries.get(i).add(passengers.get(idx));
                itineraries.get(i).add(passengers.get(idx));
            }
        }

        int c = 0;
        while (aux.size() > 0) {
            int r = rand.nextInt(aux.size());
            int idx = aux.get(r);
            aux.remove(r);
            itineraries.get(c).add(1,passengers.get(idx));
            itineraries.get(c).add(2,passengers.get(idx));
            c++;
        }
        for (int i = 0; i < drivers.size(); i++) itineraries.get(i).add(drivers.get(i));
    }

    public void swapPassenger(int condI, int pasI, int condJ, int pasJ) {

        //System.out.println("Swap C" + condI + " P" + pasI + " with C" + condJ + " P" + pasJ);

        Usuario usI = itineraries.get(condI).get(pasI);
        itineraries.get(condI).remove(usI);
        itineraries.get(condI).remove(usI);

        Usuario usJ = itineraries.get(condJ).get(pasJ);
        itineraries.get(condJ).remove(usJ);
        itineraries.get(condJ).remove(usJ);

        // condI rajoute pasJ
        insertPassenger(condI,usJ);

        // condJ rajoute pasI
        insertPassenger(condJ,usI);


        //System.out.println("Heuristic value: " + this.heuristicValue());
    }

    public void insertPassenger(int cond, Usuario u) {
        int solIdxOr = -1;
        int solIdxFi = -1;
        int solDistance = 300;

        Vector<Usuario> car = new Vector<>(2);
        int minOr = 300;
        int minFi = 300;
        int idxOr = 0;
        int idxFi = 0;
        Vector<Integer> CoordIni = new Vector<>(2);
        CoordIni.add(itineraries.get(cond).get(0).getCoordOrigenX());
        CoordIni.add(itineraries.get(cond).get(0).getCoordOrigenY());
        car.add(itineraries.get(cond).get(0));

        Vector<Integer> CoordEnd = new Vector<>(2);
        CoordEnd.add(0);
        CoordEnd.add(0);

        for (int i = 1; i < itineraries.get(cond).size(); i++) {
            if (car.indexOf(itineraries.get(cond).get(i)) == -1) {
                CoordEnd.set(0,itineraries.get(cond).get(i).getCoordOrigenX());
                CoordEnd.set(1,itineraries.get(cond).get(i).getCoordOrigenY());
                car.add(itineraries.get(cond).get(i));
            }
            else {
                CoordEnd.set(0,itineraries.get(cond).get(i).getCoordDestinoX());
                CoordEnd.set(1,itineraries.get(cond).get(i).getCoordDestinoY());
                car.remove(itineraries.get(cond).get(i));
            }

            if (car.size() < 2) {
                int distOr = getDistance(CoordEnd.get(0),CoordIni.get(0),u.getCoordOrigenX()) + getDistance(CoordEnd.get(1),CoordIni.get(1),u.getCoordOrigenY());
                if (distOr < minOr) {
                    minOr = distOr;
                    idxOr = i;

                    CoordIni.set(0,u.getCoordOrigenX());
                    CoordIni.set(1,u.getCoordOrigenY());

                    minFi = getDistance(CoordEnd.get(0),CoordIni.get(0),u.getCoordDestinoX()) + getDistance(CoordEnd.get(1),CoordIni.get(1),u.getCoordDestinoY());
                    idxFi = i;
                }
                else {
                    int distFi = getDistance(CoordEnd.get(0),CoordIni.get(0),u.getCoordDestinoX()) + getDistance(CoordEnd.get(1),CoordIni.get(1),u.getCoordDestinoY());
                    if (distFi < minFi) {
                        minFi = distFi;
                        idxFi = i;
                    }
                    minFi = distFi < minFi ? distFi : minFi;
                }
            }
            else {
                if (solDistance > minFi + minOr) {
                    solDistance = minFi + minOr;
                    solIdxFi = idxFi;
                    solIdxOr = idxOr;
                }
                minFi = 300;
                minOr = 300;
            }
            CoordIni.set(0,CoordEnd.get(0));
            CoordIni.set(1,CoordEnd.get(1));
        }
        if ( solDistance > minFi + minOr) {
            solIdxFi = idxFi;
            solIdxOr = idxOr;
        }
        itineraries.get(cond).add(solIdxOr,u);
        itineraries.get(cond).add(solIdxFi,u);
    }

    public void movePassenger(int condI, int pasI, int condJ) {

        //System.out.println("Move P" + pasI + " from C" + condI + "  to C" + condJ);

        Usuario usI = itineraries.get(condI).get(pasI);
        itineraries.get(condI).remove(usI);
        itineraries.get(condI).remove(usI);

        insertPassenger(condJ,usI);

        //System.out.println("Heuristic value: " + this.heuristicValue());
    }

    public double heuristicValue() {
        return hf43();
    }

    private double hfAntonio() { // (SMRE: 992.8 - 37) (lo demás es inutil)
        double sum = 0;
        int ncond = getNumCond();
        for (int i = 0; i < itineraries.size(); i++) {
            double dist = computeDistance(i);
            sum += (dist > 300 ? 4096 : dist/10);
        }
        return sum*ncond;
    }

    private double hfAntonio2() { // (SMRE: 985.4 - 35) (MRE: 1207.2km - 45) (SMRD 1254.8 - 47) (MRD: 1313.1 - 48)
        double sum = 0;
        int ncond = getNumCond();
        for (int i = 0; i < itineraries.size(); i++) {
            double dist = computeDistance(i);
            sum += (dist > 300 ? dist*10 : dist/10);
        }
        return sum*ncond;
    }

    private double hfAntonio3() { // (SMRE: 985.4 - 35) (MRE: 1207.2km - 45) (SMRD 1254.8 - 47) (MRD: 1313.1 - 48)
        double sum = 0;
        int ncond = getNumCond();
        for (int i = 0; i < itineraries.size(); i++) {
            double dist = computeDistance(i);
            if( dist > 300 ) sum += dist * dist;
            else if (dist < 70 ) sum += dist*(300-dist);
            else sum += dist;
        }
        return sum*ncond;
    }

    private double hfEntrega() { // (SMRE: 993.7 - 27) (resto muy malos)
        double sum = 0;
        for (int i = 0; i < itineraries.size(); i++) { // 9618 28
            double dist = computeDistance(i);
            sum += dist > 295 ? dist*500 : dist;
        }
        return sum;
    }

    private double hf43() { // (SMRE: 996.4 - 37) (MRE: 1207.2 - 45) (SMRD: 1253 - 47) (MRD: 1328.1 - 49)
        double sum = 0;
        int ncond = getNumCond();
        for (int i = 0; i < itineraries.size(); i++) {
            double dist = computeDistance(i);
            sum += (dist > 300 ? dist*500 : dist)*ncond;
        }
        return sum;
    }

    private double hfThomasMethod() { // (SMRE y MRE muy malos) (SMRD: ) (MRD: )
        double sum = 0;
        int ncond = getNumCond();
        for (int i = 0; i < itineraries.size(); i++) {
            double dist = computeDistance(i);
            if (dist < 200 || dist > 300) dist *= dist;
            sum += dist;
        }
        return sum;
    }

    // Auxiliary functions

    public String getCFG() {
        return cfg;
    }

    public ArrayList<Integer> getNumVacios() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < itineraries.size(); i++) {
            if (itineraries.get(i).size() == 2) result.add(i);
        }
        return result;
    }

    public int getNumCond() {
        int cont = 0;
        for (int i = 0; i < itineraries.size(); i++) {
            if (itineraries.get(i).size() > 0) cont++;
        }
        return cont;
    }

    private int getDistance(int x1, int x2, int n) {
        int min = x1 > x2 ? x2 : x1;
        int max = x1 > x2 ? x1 : x2;
        if (n < min) return min-n;
        if (n > max) return n-max;
        return 0;
    }

    public ArrayList<ArrayList<Usuario>> getItineraries() {
        return itineraries;
    }

    //(abs(p1.get(0) - p2.get(0)) + abs(p1.get(1) - p2.get(1)));

    public int computeAllDistance() {
        int sum = 0;
        for (int i = 0; i < itineraries.size(); i++) {
            sum += computeDistance(i);
        }
        return sum;
    }

    private int computeDistance(int  c) {
        if (itineraries.get(c).size() == 0) return 0;
        Vector<Usuario> car = new Vector<>(2);
        Vector<Integer> fromPos = new Vector<>(2);
        fromPos.add(itineraries.get(c).get(0).getCoordOrigenX());
        fromPos.add(itineraries.get(c).get(0).getCoordOrigenY());
        car.add(itineraries.get(c).get(0));

        int dist = 0;

        for (int j = 0; j < itineraries.get(c).size(); j++) {
            Usuario current = itineraries.get(c).get(j);
            if (car.indexOf(current) != -1) {
                dist += Math.abs(fromPos.get(0) - current.getCoordOrigenX()) + Math.abs(fromPos.get(1) - current.getCoordOrigenY());
                fromPos.set(0,current.getCoordOrigenX());
                fromPos.set(1,current.getCoordOrigenY());
                car.remove(current);
            }
            else {
                dist += Math.abs(fromPos.get(0) - current.getCoordDestinoX()) + Math.abs(fromPos.get(1) - current.getCoordDestinoY());
                fromPos.set(0,current.getCoordDestinoX());
                fromPos.set(1,current.getCoordDestinoY());
                car.add(current);
            }
        }
        return dist;
    }


    public void printBoard() {
        System.out.println("\nBoard:\n");
        int sum = 0;
        boolean bigInput = cfg.contains("C");

        int conductores = 0;
        int contador = 0;
        int aboveCarSize = 0;

        for (int i = 0; i < itineraries.size(); i++) {
            if (itineraries.get(i).size() == 0) {
                if (bigInput) System.out.println("Condutor " + i + " not used");
                conductores++;
            }
            else {
                Vector<Usuario> car = new Vector<>(2);
                for (int j = 0; j < itineraries.get(i).size(); j++) {
                    Usuario current = itineraries.get(i).get(j);
                    if (car.indexOf(current) != -1) {
                        // mode destino
                        if (bigInput) System.out.print(getUserIdentity(current) + "-[" + current.getCoordOrigenX() + "," + current.getCoordOrigenY() + "] ");
                        car.remove(current);
                    } else {
                        // mode origen
                        if (bigInput) System.out.print(getUserIdentity(current) + "-[" + current.getCoordDestinoX() + "," + current.getCoordDestinoY() + "] ");
                        car.add(current);
                    }
                    if (car.size() > 3) aboveCarSize++;
                }
                int dist = computeDistance(i);
                sum += dist;
                if (dist > 300) contador++;
                if (bigInput) System.out.println("  ---  Distance: " + (double) dist / 10 + " km, # Passengers: " + (itineraries.get(i).size() / 2));
            }
        }
        System.out.println("Distancia total: " + (double)sum/10 + " km, conductores eliminados: " + conductores);
        System.out.println("above 300: " + contador + ", car Size Exceeded: " + aboveCarSize);
    }

    public ArrayList<Integer> getInfo() {
        int sum = 0;

        int conductores = 0;
        int contador = 0;
        int aboveCarSize = 0;

        for (int i = 0; i < itineraries.size(); i++) {
            if (itineraries.get(i).size() == 0) {
                conductores++;
            } else {
                Vector<Usuario> car = new Vector<>(2);
                for (int j = 0; j < itineraries.get(i).size(); j++) {
                    Usuario current = itineraries.get(i).get(j);
                    if (car.indexOf(current) != -1) {
                        // mode destino
                        car.remove(current);
                    } else {
                        // mode origen
                        car.add(current);
                    }
                    if (car.size() > 3) aboveCarSize++;
                }
                int dist = computeDistance(i);
                sum += dist;
                if (dist > 300) contador++;
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(sum);
        result.add(contador+aboveCarSize);
        result.add(conductores);
        return result;
    }

    private String getUserIdentity(Usuario u) {
        int idx = passengers.indexOf(u);
        if (idx != -1) return "P" + idx;
        idx = drivers.indexOf(u);
        return "C" + idx;
    }
}
