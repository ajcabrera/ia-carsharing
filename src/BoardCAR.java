import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;
import IA.Comparticion.Usuario;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class BoardCAR {

    private Vector<ArrayList<Usuario>> caminos = new Vector<ArrayList<Usuario>> ();


    public BoardCAR(Vector<Usuario> pasajeros, int ncond) {
        // Crear solución inicial
        RandomInit(pasajeros,ncond);
        printBoard();
    }

    // Null pointer -> need to initialize all values.

    private void RandomInit(Vector<Usuario> pasajeros, int ncond) {
        System.out.println(ncond);
        caminos.setSize(ncond);
        System.out.println(caminos.size());
        Random rand = new Random();
        System.out.println("ini");
        for (Usuario i: pasajeros) {
            int cond = rand.nextInt(ncond);
            caminos.get(cond).add(i);
        }
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
