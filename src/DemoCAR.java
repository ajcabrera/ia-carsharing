import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class DemoCAR {
    public class ProbTSPDemo {

        public static void main(String[] args){
            BoardCAR city =new BoardCAR(30);
            HillClimbingSearch(city);
            SimulatedAnnealingSearch(city);
        }

        private static void HillClimbingSearch(BoardCAR city) {
            System.out.println("\nTSP HillClimbing  -->");
            try {
                Problem problem =  new Problem(BoardCAR,new SuccessorFunctionCAR(), new GoalTestCAR(),new HeuristicFunction());
                Search search =  new HillClimbingSearch();
                SearchAgent agent = new SearchAgent(problem,search);

                System.out.println();
                printActions(agent.getActions());
                printInstrumentation(agent.getInstrumentation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void SimulatedAnnealingSearch(BoardCAR TSPB) {
            System.out.println("\nTSP Simulated Annealing  -->");
            try {
                Problem problem =  new Problem(TSPB,new SuccessorFunctionCAR(), new GoalTestCAR(),new HeuristicFunctionCAR());
                SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(2000,100,5,0.001);
                //search.traceOn();
                SearchAgent agent = new SearchAgent(problem,search);

                System.out.println();
                printActions(agent.getActions());
                printInstrumentation(agent.getInstrumentation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
