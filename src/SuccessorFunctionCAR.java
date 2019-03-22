import IA.Comparticion.Usuario;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;

public class SuccessorFunctionCAR implements SuccessorFunction {
    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) {
        ArrayList retval = new ArrayList();
        BoardCAR board = (BoardCAR) aState;
        BitSet oldSchedule = board.getSchedule();
        Vector<Short> oldDistance = board.getDistance();
        HeuristicFunctionCAR hf = new HeuristicFunctionCAR();
        int ncond = board.getNumCond();
        int row = board.rowSize();

        double hvalue = board.heuristicValue();
        boolean last = true;

        // swap Operator
        // will need to check if conductor is passenger or not
        for (int i = 0; i < ncond; i++) {
            for (int k = 0; k < row; k++) {
                if (oldSchedule.get(i*row+k+1)) {
                    for (int j = i+1; j < ncond; j++) {
                        for (int l = 0; l < row; l++) {
                            if (oldSchedule.get(j*row+l+1)) {
                                // swap conductor i pasajero k por conductor j pasajero l
                                BoardCAR newBoard = new BoardCAR(oldSchedule, oldDistance);
                                String s = BoardCAR.SWAP + " C" + i + " P" + k + " with C" + j + " " + l;
                                newBoard.swapPassajero(i, k, j, l);
                                if (hvalue > newBoard.heuristicValue()) last = false;
                                retval.add(new Successor(s, newBoard));
                            }

                        }
                    }
                }
            }

        }

        // move Operator
        for (int i = 0; i < ncond; i++) {
            for (int k = 0; k < row; k++) {
                if (oldSchedule.get(i*row+k+1)) {
                    for (int j = 0; j < ncond; j++) {
                        if (i != j) {
                            BoardCAR newBoard = new BoardCAR(oldSchedule, oldDistance);
                            String s = BoardCAR.MOVE + " P" + k + " from C" + i + " to C" + j;
                            newBoard.movePasajero(i, k, j);
                            if (hvalue > newBoard.heuristicValue()) last = false;
                            retval.add(new Successor(s, newBoard));
                        }
                    }
                }
            }
        }
        System.out.println("\n");
        if (last) {
            board.printBoard(false);
            System.out.println("Heuristic Value: " + hvalue);
        }
        return retval;
    }
}
