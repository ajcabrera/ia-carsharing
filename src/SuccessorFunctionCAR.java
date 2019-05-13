import IA.Comparticion.Usuario;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class SuccessorFunctionCAR implements SuccessorFunction {
    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) {
        ArrayList retval = new ArrayList();
        BoardCAR board = (BoardCAR) aState;
        ArrayList<ArrayList<Usuario>> oldItineraries = board.getItineraries();

        double heuristic = board.heuristicValue();

        String cfg = board.getCFG();

        // swap Operator
        if (cfg.contains("S")) {
            for (int ci = 0; ci < oldItineraries.size(); ci++) {
                ArrayList<Usuario> done1 = new ArrayList<>();
                for (int pi = 1; pi < oldItineraries.get(ci).size() - 1; pi++) {
                    if (done1.indexOf(oldItineraries.get(ci).get(pi)) == -1) {
                        done1.add(oldItineraries.get(ci).get(pi));
                        for (int cj = ci + 1; cj < oldItineraries.size(); cj++) {
                            ArrayList<Usuario> done2 = new ArrayList<>();
                            if (ci != cj && oldItineraries.get(cj).size() > 2) {
                                for (int pj = 1; pj < oldItineraries.get(cj).size() - 1; pj++) {
                                    if (done2.indexOf(oldItineraries.get(cj).get(pj)) == -1) {
                                        done2.add(oldItineraries.get(cj).get(pj));
                                        BoardCAR newBoard = new BoardCAR(oldItineraries);
                                        newBoard.swapPassenger(ci, pi, cj, pj);

                                        if (newBoard.heuristicValue() < heuristic) {
                                            String s = BoardCAR.SWAP + " C" + ci + " P" + pi + " with C" + cj + " " + cj;
                                            retval.add(new Successor(s, newBoard));
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        // move Operator
        if (cfg.contains("M")) {
            for (int ci = 0; ci < oldItineraries.size(); ci++) {
                ArrayList<Usuario> done = new ArrayList<>();
                for (int pi = 1; pi < oldItineraries.get(ci).size() - 1; pi++) {
                    if (done.indexOf(oldItineraries.get(ci).get(pi)) == -1) {
                        done.add(oldItineraries.get(ci).get(pi));
                        for (int cj = 0; cj < oldItineraries.size(); cj++) {
                            if (ci != cj && oldItineraries.get(cj).size() > 2) {
                                BoardCAR newBoard = new BoardCAR(oldItineraries);
                                newBoard.movePassenger(ci, pi, cj);

                                if (newBoard.heuristicValue() < heuristic) {
                                    String s = BoardCAR.MOVE + " P" + pi + " from C" + ci + " to C" + cj;
                                    retval.add(new Successor(s, newBoard));
                                }
                            }
                        }
                    }
                }
            }
        }
        // moveConductor
        if (cfg.contains("R")) {
            for (int ci = 0; ci < oldItineraries.size(); ci++) {
                if (oldItineraries.get(ci).size() == 2) {
                    for (int cj = 0; cj < oldItineraries.size(); cj++) {
                        if (ci != cj && oldItineraries.get(cj).size() > 2) {
                            BoardCAR newBoard = new BoardCAR(oldItineraries);
                            newBoard.movePassenger(ci, 0, cj);

                            if (newBoard.heuristicValue() < heuristic) {
                                String s = BoardCAR.MOVECONDUCTOR + " C" + ci + " to C" + cj;
                                retval.add(new Successor(s, newBoard));
                            }
                        }
                    }
                }
            }
        }
        return retval;
    }
}
