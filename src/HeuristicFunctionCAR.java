import aima.search.framework.HeuristicFunction;

public class HeuristicFunctionCAR implements HeuristicFunction {
    public double getHeuristicValue(Object state) {
        BoardCAR board = (BoardCAR) state;
        return board.heuristicValue();
    }
}
