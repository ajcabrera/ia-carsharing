import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class DemoCAR {

    private static Vector<Usuario> conductores = new Vector<Usuario>();
    private static Vector<Usuario> pasajeros = new Vector<Usuario>();

    public static void main(String[] args){
        int nusuarios = Integer.parseInt(args[0]);
        int ncond = Integer.parseInt(args[1]);
        int seed = Integer.parseInt(args[2]);
        Usuarios U = new Usuarios(nusuarios,ncond,seed);

        Init(U);

        BoardCAR city =new BoardCAR(pasajeros,ncond);
        //HillClimbingSearch(city);
        //SimulatedAnnealingSearch(city);
    }

    public static void Cout(String s) {
        System.out.println(s);
    }

    private static void Init(Usuarios U) {
        for (Usuario i:U) {
            if (i.isConductor()) conductores.add(i);
            else pasajeros.add(i);
        }
    }

    public static Usuario getConductor(int i) {
        return conductores.get(i);
    }

    public static Usuario getPasajero(int i) {
        return pasajeros.get(i);
    }

    private static void HillClimbingSearch(BoardCAR city) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem =  new Problem(city,new SuccessorFunctionCAR(), new GoalTestCAR(),new HeuristicFunctionCAR());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
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
    */

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
