import IA.Comparticion.Usuario;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SuccessorFunctionCAR implements SuccessorFunction {
    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) {
        ArrayList retval = new ArrayList();
        BoardCAR board = (BoardCAR) aState;
        Vector<ArrayList<Usuario>> oldCaminos = board.getCaminos();
        Vector<Short> oldDistance = board.getDistance();
        HeuristicFunctionCAR hf = new HeuristicFunctionCAR();
        int ncond = board.getNumCond();

        // swap Operator
        for (int i = 0; i < ncond; i++) {
            for (int k = 0; k < board.getSize(i); k++) {
                for (int j = i; j < ncond; j++) {
                    for (int l = 0; l < board.getSize(j); l++) {
                        // swap conductor i pasajero k por conductor j pasajero l
                        BoardCAR newBoard = new BoardCAR(oldCaminos,oldDistance);
                        String s = BoardCAR.SWAP + " C" + i + " " + coordIni(board,i,k) + " with C" + j + " " + coordIni(board,j,l);
                        newBoard.swapPassajero(i,k,j,l);
                        retval.add(new Successor(s,newBoard));

                    }
                }
            }

        }
        // move Operator
        for (int i = 0; i < ncond; i++) {
            for (int k = 0; k < board.getSize(i); k++) {
                for (int j = 0; j < ncond; j++) {
                    if (i != j) {
                        BoardCAR newBoard = new BoardCAR(oldCaminos,oldDistance);
                        String s = BoardCAR.MOVE + " C" + i + " " + coordIni(board, i, k) + " to C" + j;
                        newBoard.movePasajero(i, k, j);
                        retval.add(new Successor(s, newBoard));
                    }
                }
            }
        }
        return retval;
    }

    private String coordIni(BoardCAR board,int conductor, int pasajero) {
        return "[" + board.getOriginX(conductor,pasajero) + "," + board.getOriginY(conductor,pasajero) + "]";
    }
}
