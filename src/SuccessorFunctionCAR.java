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
        ArrayList<ArrayList<Usuario>> oldItineraries = board.getItineraries();


        // swap Operator
        for (int ci = 0; ci < oldItineraries.size(); ci++) {
            ArrayList<Usuario> done1 = new ArrayList<>();
            for (int pi = 1; pi < oldItineraries.get(ci).size()-1; pi++) {
                if (done1.indexOf(oldItineraries.get(ci).get(pi)) == -1) {
                    done1.add(oldItineraries.get(ci).get(pi));
                    for (int cj = ci+1 ; cj < oldItineraries.size(); cj++) {
                        ArrayList<Usuario> done2 = new ArrayList<>();
                        if (ci != cj && oldItineraries.get(cj).size() > 2) {
                            for (int pj = 1; pj < oldItineraries.get(cj).size() - 1; pj++) {
                                if (done2.indexOf(oldItineraries.get(cj).get(pj)) == -1) {
                                    done2.add(oldItineraries.get(cj).get(pj));
                                    BoardCAR newBoard = new BoardCAR(oldItineraries);
                                    newBoard.swapPassenger(ci, pi, cj, pj);
                                    String s = BoardCAR.SWAP + " C" + ci + " P" + pi + " with C" + cj + " " + cj;
                                    retval.add(new Successor(s, newBoard));
                                }

                            }
                        }
                    }
                }
            }
        }
        //System.out.println("size swap : " + retval.size());
        // move Operator
        for (int ci = 0; ci < oldItineraries.size(); ci++) {
            ArrayList<Usuario> done = new ArrayList<>();
            for (int pi = 1; pi < oldItineraries.get(ci).size()-1; pi++) {
                if (done.indexOf(oldItineraries.get(ci).get(pi)) == -1) {
                    done.add(oldItineraries.get(ci).get(pi));
                    for (int cj = 0; cj < oldItineraries.size(); cj++) {
                        if (ci != cj && oldItineraries.get(cj).size() > 2) {
                            BoardCAR newBoard = new BoardCAR(oldItineraries);
                            newBoard.movePassenger(ci, pi, cj);

                            String s = BoardCAR.MOVE + " P" + pi + " from C" + ci + " to C" + cj;
                            retval.add(new Successor(s, newBoard));
                        }
                    }
                }
            }
        }
        //System.out.println("size move : " + retval.size());
        // moveConductor
        for (int ci = 0; ci < oldItineraries.size(); ci++) {
            if (oldItineraries.get(ci).size() == 2) {
                for (int cj = 0; cj < oldItineraries.size(); cj++) {
                    if (ci != cj && oldItineraries.get(cj).size() > 2) {
                        BoardCAR newBoard = new BoardCAR(oldItineraries);
                        newBoard.movePassenger(ci, 0, cj);

                        String s = BoardCAR.MOVECONDUCTOR + " C" + ci + " to C" + cj;
                        retval.add(new Successor(s, newBoard));
                    }
                }
            }
        }
        //System.out.println("size moveC : " + retval.size());

        /*
        // order Operator
        for (int c = 0; c < oldItineraries.size();c++) {
            for (int p1 = 0; p1 < oldItineraries.get(c).size(); p1++) {
                for (int p2 = p1+1; p2 < oldItineraries.get(c).size(); p2++) {
                    if (oldItineraries.get(c).get(p1).equals(oldItineraries.get(c).get(p2))) {
                        BoardCAR newBoard = new BoardCAR(oldItineraries);
                        newBoard.reOrdenate(c,p1,p2);
                        String s = BoardCAR.ORDER + " C" + c + " pos " + p1 + " and " + p2;
                        retval.add(new Successor(s, newBoard));
                    }
                }
            }
        }
        */
        return retval;
    }
}
