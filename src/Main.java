import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    /*
        CPSC422: Assignment 1 Question 3
     */


    //Coordinate System.
    //beliefState<0, 3> = beliefStateValue for (0,3)
//    static Map<Integer, Map<Integer, Integer>> beliefState;

    static double[][] lastBeliefState;
    static double[][] beliefState;
    static StateReachable[][] stateReachables;

    public static void main(String [] args){
        beliefState = new double[4][3];
        stateReachables = new StateReachable[4][3];
        testSequence();
//        firstSequence();
    }

    public static void testSequence(){
        String[] actions = {"left", "left", "left", "left", "left"};
        String[] observations = {"2","2","2","2","2"};
        setUniformBeliefState();
        for (String action:
             actions) {
            oneBeliefUpdate(action, "2");
        }


    }



    public static void setUniformBeliefState(){
        beliefState[0][0] = 0.111;
        beliefState[0][1] = 0.111;
        beliefState[0][2] = 0.111;

        beliefState[1][0] = 0.111;

        //Middle wall set to 0
        beliefState[1][1] = 0;

        beliefState[1][2] = 0.111;

        beliefState[2][0] = 0.111;
        beliefState[2][1] = 0.111;
        beliefState[2][2] = 0.111;

        beliefState[3][0] = 0.111;

        //TODO: Must set terminal states to 0;
        beliefState[3][2] = 0;
        beliefState[3][1] = 0;
    }

    //0.111 for each square if initial state not given.
    public static double[][] beliefUpdate(double[][] initialBeliefState, String[] actions, String[] observations){
        lastBeliefState = initialBeliefState;
        double[][] intermBeliefState = initialBeliefState;
        if(actions.length == 0){
            return initialBeliefState;
        }

        int observationProb = 0;
        if(observations[observations.length -1].equals("1")){

        }else if(observations[observations.length -1].equals("2")){

        }else if(observations[observations.length -1].equals("end")){

        }

        return beliefState;
    }

    public static double[][] oneBeliefUpdate(String givenAction, String observation){

//        double alpha = 1; //TODO https://piazza.com/class/j6zftw7rj7z5bx?cid=71  IT'S NOT 1/K. Normalize values!
        int sensorProb = 1; //TODO

        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 3; y++){
                if(!(x == 1 && y==1)){
                    if(x == 3 && y == 1){
                        System.out.println("yo");
                    }
                    stateReachables[x][y] = new StateReachable(new Coord(x, y),givenAction);


                    double summationValue = doSummation(stateReachables[x][y]);

                    beliefState[x][y] =  sensorProb * summationValue;

                    normalizeTable();

                    double totalCheck = getTotal();
                    System.out.println(totalCheck);
                }
            }
        }

        return beliefState;
    }

    /*
        Normalizes belief state
     */
    public static double getTotal(){
        double counter = 0;
        double runningSum = 0;

        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 3; y++){
                if(!(x == 1 && y == 1)){
                    counter++;
                    runningSum += beliefState[x][y];
                }
            }
        }
//        double toRtn = (runningSum/counter);
//        return (runningSum/counter);
        return runningSum;
    }

    public static void normalizeTable(){
        double total = getTotal();

        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 3; y++){
                if(!(x == 1 && y == 1)){
                    beliefState[x][y] = (beliefState[x][y] / total);
                }
            }
        }
    }



    public static double doSummation(StateReachable stateReachable){
        double summationValue = 0;
        for(int i = 0; i < stateReachable.rtnCoords.size(); i++){

            CoordAction curCoord = stateReachable.rtnCoords.get(i);
            Coord reachedFromCoord = curCoord.originalCoord;
            double beliefStateCell = beliefState[reachedFromCoord.x][reachedFromCoord.y];

            double rtnProb = curCoord.prob;
            double summationPart = beliefStateCell * rtnProb;
            double oldSummation = summationValue;


            summationValue = oldSummation + summationPart;

            if(stateReachable.originalCoord.x == 3 && stateReachable.originalCoord.y == 1){
                boolean sup = false;
            }
            System.out.println("sup");
        }

        return summationValue;
    }


    public static void firstSequence(){
        String[] actions = {"up", "up", "up"};
        String[] observations = {"2","2","2"};
        setUniformBeliefState();

//        Main.beliefUpdate(beliefState, actions, observations);
        Main.oneBeliefUpdate("up", "2");

    }
    public static void secondSequence(){
        String[] actions = {"up", "up", "up"};
        String[] observations = {"1","1","1"};
        setUniformBeliefState();

        Main.beliefUpdate(beliefState, actions, observations);
    }
    public static double subFormulaDoSummation(List<Integer> x, List<Integer> y, List<Double> probs){
        //Does the summation part of the formula.
        int rtnVal = 0;
        for(int i = 0; i < x.size(); i++){
            rtnVal += (probs.get(i) * beliefState[x.get(i)][y.get(i)]);
        }

        return rtnVal;
    }

}
