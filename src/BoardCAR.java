import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;
import IA.Comparticion.Usuario;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class BoardCAR {

    private Vector<ArrayList<Usuario>> caminos = new Vector<ArrayList<Usuario>> ();
    private Vector<Integer> distance = new Vector<Integer> ();

    public static String SWAP = "swap";
    public static String MOVE = "move";


    public BoardCAR(Vector<Usuario> pasajeros, int ncond) {
        for (int i = 0; i < ncond; i++) {
            caminos.add(new ArrayList<Usuario>());
        }
        distance.setSize(ncond);
        EquitableInit(pasajeros,ncond);
        printBoard();
    }

    public BoardCAR(Vector<ArrayList<Usuario>> oldCaminos, Vector<Integer> oldDistance) {
        distance.setSize(oldDistance.size());
        for (int i = 0; i < oldCaminos.size(); i++) {
            caminos.add(new ArrayList<Usuario>());
            for (int k = 0; k < oldCaminos.get(i).size(); k++) {
                caminos.get(i).add(oldCaminos.get(i).get(k));
            }
            distance.set(i,oldDistance.get(i));
        }
    }

    private void RandomInit(Vector<Usuario> pasajeros, int ncond) {
        Random rand = new Random();
        for (Usuario i: pasajeros) {
            int cond = rand.nextInt(ncond);
            caminos.get(cond).add(i);
        }
    }

    private void EquitableInit(Vector<Usuario> pasajeros, int ncond) {
        int each = pasajeros.size()/ncond;
        int pas = 0;
        for (int i = 0; i < ncond; i++) {
            for (int j = 0; j < each; j++) {
                caminos.get(i).add(pasajeros.get(pas));
                pas++;
            }
        }
        int cond = 0;
        while (pas < pasajeros.size()) {
            caminos.get(cond).add(pasajeros.get(pas));
            cond++;
            pas++;
        }
    }

    public void swapPassajero(int condI, int pasI, int condJ, int pasJ) {
        Usuario pasajeroI = caminos.get(condI).get(pasI);
        Usuario pasajeroJ = caminos.get(condJ).get(pasJ);
        caminos.get(condI).set(pasI, pasajeroJ);
        caminos.get(condJ).set(pasJ, pasajeroI);
    }

    public void movePasajero(int condI, int pasI, int condJ) {
        Usuario pasajero = caminos.get(condI).get(pasI);
        caminos.get(condJ).add(pasajero);
    }

    public double heuristicValue() {
        return hfSumPow3();
    }

    private double hfSumPow3() {
        int sum = 0;
        for (int i = 0; i < distance.size(); i++) {
            sum += Math.pow(distance.get(i),3);
        }
        return sum;
    }


    // Auxiliar functions

    public int getNumCond() {
        return caminos.size();
    }

    public ArrayList<Usuario> getPasajeros(int i) {
        return caminos.get(i);
    }

    public int getSize(int i) {
        return caminos.get(i).size();
    }

    public int getOriginX(int conductor, int pasajero) {
        return caminos.get(conductor).get(pasajero).getCoordOrigenX();
    }

    public int getOriginY(int conductor, int pasajero) {
        return caminos.get(conductor).get(pasajero).getCoordOrigenY();
    }

    public Vector<ArrayList<Usuario>> getCaminos() {
        return caminos;
    }

    public Vector<Integer> getDistance() {
        return distance;
    }

    public void printBoard() {
        System.out.println("Board:");
        for (ArrayList<Usuario> l: caminos) {
            System.out.println("Conductor:");
            for (int i = 0; i < l.size(); i++) {
                Usuario aux = l.get(i);
                System.out.println("[" + aux.getCoordOrigenX() + "," + aux.getCoordOrigenY() + "]");
            }
        }
    }

}
