import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.*;

public class DemoCAR {

    private static Vector<Usuario> conductores = new Vector<Usuario>();
    private static Vector<Usuario> pasajeros = new Vector<Usuario>();

    public static void main(String[] args){
        int nusuarios = Integer.parseInt(args[0]);
        int ncond = Integer.parseInt(args[1]);
        int seed = Integer.parseInt(args[2]);
        Usuarios U = new Usuarios(nusuarios,ncond,seed);

        Init(U);

        BoardCAR city = new BoardCAR(pasajeros,conductores, args[4]);
        int distIni = city.computeAllDistance();

        if(args[3].equals("HC")) HillClimbingSearch(city,distIni);
        else if(args[3].equals("SA")) {
            int steps = Integer.valueOf(args[5]);
            int stiter = Integer.valueOf(args[6]);
            int k = Integer.valueOf(args[7]);
            double lamb = Double.valueOf(args[8]);

            SimulatedAnnealingSearch(city,steps,stiter,k,lamb, distIni);
        }
        else System.out.println("Select SA or HC");
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

    private static void HillClimbingSearch(BoardCAR city, int ini) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            long start_time = System.nanoTime();
            Problem problem =  new Problem(city,new SuccessorFunctionCAR(), new GoalTestCAR(),new HeuristicFunctionCAR());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            long end_time = System.nanoTime();
            double difference = (end_time-start_time) / 1e6;

            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());

            BoardCAR last = (BoardCAR) search.getGoalState();

            ArrayList<Integer> result = last.getInfo(); // result = distFinal, sol, conductores

            double iniKm = (double)ini/10;
            double endKm = (double)result.get(0)/10;
            boolean sol = result.get(1).equals(0);
            System.out.println(iniKm + ";" + endKm + ";" + sol + ";" + result.get(2) + ";" + (difference/1e3));


            //System.out.println("\n\nTime elapsed: " + difference + "milliseconds, which are " + (difference/1e3) + " seconds.");
            //last.printBoard();

        } catch (Exception e) {
            Cout("Exception");
            e.printStackTrace();
        }
    }

    private static void SimulatedAnnealingSearch(BoardCAR TSPB,int steps, int stiter, int k, double lamb, int ini) {
        System.out.println("\nTSP Simulated Annealing  -->");
        try {
            long start_time = System.nanoTime();
            Problem problem =  new Problem(TSPB,new SuccessorFunctionSA(), new GoalTestCAR(),new HeuristicFunctionCAR());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(steps,stiter,k,lamb);
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            long end_time = System.nanoTime();
            double difference = (end_time-start_time) / 1e6;

            System.out.println();
            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());

            BoardCAR last = (BoardCAR) search.getGoalState();

            ArrayList<Integer> result = last.getInfo(); // result = distFinal, sol, conductores

            double iniKm = (double)ini/10;
            double endKm = (double)result.get(0)/10;
            boolean sol = result.get(1).equals(0);
            System.out.println(iniKm + ";" + endKm + ";" + sol + ";" + result.get(2) + ";" + (difference/1e3));

            //System.out.println("\n\nTime elapsed: " + difference + "milliseconds, which are " + (difference/1e3) + " seconds.");
            //last.printBoard();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
