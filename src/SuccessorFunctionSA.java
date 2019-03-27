import IA.Comparticion.Usuario;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuccessorFunctionSA implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retval = new ArrayList();
        BoardCAR board = (BoardCAR) aState;
        ArrayList<ArrayList<Usuario>> oldItineraries = board.getItineraries();

        BoardCAR newBoard = new BoardCAR(oldItineraries);

        Random rand = new Random();
        int c1,c2,p1,p2;

        c1 = rand.nextInt(board.getNumCond());

        do {
            c2 = rand.nextInt(board.getNumCond());
        } while (c1==c2);

        p1 = rand.nextInt(oldItineraries.get(c1).size());
        p2 = rand.nextInt(oldItineraries.get(c2).size());

        newBoard.swapPassenger(c1,p1,c2,p2);
        retval.add(newBoard);

        return retval;
    }
}
