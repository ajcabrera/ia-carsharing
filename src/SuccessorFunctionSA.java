import IA.Comparticion.Usuario;
import aima.search.framework.Successor;
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
        String s = "";
        String cfg = board.getCFG();

        int probMoveConductor = 5;
        int probSwap = 3;
        if (!cfg.contains("M")) probSwap = 1;
        else if (!cfg.contains("S")) probSwap = 101;

        ArrayList<Integer> moveCond = board.getNumVacios();
        int p = rand.nextInt(100);


        if (moveCond.size() > 0 && p%probMoveConductor==0 && cfg.contains("R")) {
            c1 = rand.nextInt(moveCond.size());
            c1 = moveCond.get(c1);
            do {
                c2 = rand.nextInt(board.getNumCond());
            } while (oldItineraries.get(c2).size() <= 2);
            newBoard.movePassenger(c1,0,c2);
            s = BoardCAR.MOVECONDUCTOR + " C" + c1 + " to C" + c2;
        }
        else {
            do {
                c1 = rand.nextInt(board.getNumCond());
            } while (oldItineraries.get(c1).size() <= 2);

            p1 = rand.nextInt(oldItineraries.get(c1).size()-2) + 1;


            if (p%probSwap == 0) {

                do {
                    c2 = rand.nextInt(board.getNumCond());
                } while (c1==c2 || oldItineraries.get(c2).size() <= 2);


                p2 = rand.nextInt(oldItineraries.get(c2).size()-2) + 1;

                s = BoardCAR.SWAP + " C" + c1 + " P" + p1 + " with C" + c2 + " " + p2;

                newBoard.swapPassenger(c1, p1, c2, p2);
            }
            else {
                do {
                    c2 = rand.nextInt(board.getNumCond());
                } while (c1==c2 || oldItineraries.get(c2).size() == 0);

                newBoard.movePassenger(c1,p1,c2);
                s = BoardCAR.MOVE + " P" + p1 + " from C" + c1 + " to C" + c2;
            }
        }
        retval.add(new Successor(s,newBoard));

        return retval;
    }

}
