import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;

import java.util.ArrayList;
import java.util.Vector;

public class BoardCAR {

    private Vector<ArrayList<Usuario>> caminos = new Vector<ArrayList<Usuario>>;


    public BoardCAR(Vector<Usuario> pasajeros, int ncond) {
        // Crear solución inicial
        RandomInit(pasajeros,ncond);
        printBoard();
    }

    private void RandomInit(Vector<Usuario> pasajeros, int ncond) {
        caminos = new Vector<ArrayList<Usuario>> (ncond);
        Random rand;
        for (Usuario i: U) {
            int cond = rand.nextInt(ncond);
            caminos.get(cond).add(i);
        }
    }

    public void printBoard() {
        System.out.println("Board:");
        for (ArrayList l: caminos) {
            System.out.println("Conductor:");
            for (int i = 0; i < l.size(); i++) {
                Usuario aux = l.get(i);
                System.out.println("[" + aux.getCoordOrigenX() + "," aux.getCoordOrigenY() + "]");
            }
        }
    }

}
