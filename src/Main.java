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
                        //TODO: After hitting this breakpoint, set a breakpoint in StateReachable populateRtnCoords
                        //TODO: Look at the potential Coords listed there. It's not right.
                    }
                    stateReachables[x][y] = new StateReachable(new Coord(x, y),givenAction);


                    double summationValue = doSummation(x, y, stateReachables[x][y]);

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



    public static double doSummation(int x, int y, StateReachable stateReachable){
        double summationValue = 0;
        for(int i = 0; i < stateReachable.rtnCoords.size(); i++){
            CoordAction curCoord = stateReachable.rtnCoords.get(i);
            Coord reachedFromCoord = curCoord.originalCoord;
            int reachedFromX = reachedFromCoord.x; //For debugging
            int reachedFromY = reachedFromCoord.y; //For debugging
            double beliefStateCell = beliefState[reachedFromX][reachedFromY];

            double rtnProb = curCoord.prob;
            summationValue += (beliefStateCell * rtnProb);
            System.out.println("sup");
        }

        return summationValue;
    }

//    public static double getReachable(int x, int y, String givenAction){
//        stateReachables[x][y] = new StateReachable(x,y,givenAction);
//        if(givenAction.equals("left"))
//            return calculateReachableTo(x,y, givenAction);
//
//        return 0;
//    }


    public static double calculateReachableTo(int x, int y, String givenAction){
        List<Integer> rtnX = new ArrayList<Integer>();
        List<Integer> rtnY = new ArrayList<Integer>();
        List<Double> probs = new ArrayList<Double>();


        int leftX = x + 1; //IT'S A (+ 1) because the given x,y is reachable from the right if a left action was taken
        int leftY = y;
        if(leftX >= 4) leftX = 0;
        rtnX.add(leftX);
        rtnY.add(leftY);
        probs.add(0.8);


        int downX = x;
        int downY = y + 1;
        if(downY >= 3) downY = 0;
        rtnX.add(downX);
        rtnY.add(downY);
        probs.add(0.1);

        int upX = x;
        int upY = y - 1;
        if(upY < 0) upY = 3;
        rtnX.add(upX);
        rtnY.add(upY);
        probs.add(0.1);

        return subFormulaDoSummation(rtnX, rtnY, probs);
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
//
//    public static double calculateReachableTo(int x, int y, String givenAction){
//        List<Integer> rtnX = new ArrayList<Integer>();
//        List<Integer> rtnY = new ArrayList<Integer>();
//        List<Double> probs = new ArrayList<Double>();
//
//        int leftX = x + 1; //IT'S A (+ 1) because the given x,y is reachable from the right if a left action was taken
//        int leftY = y;
//        if(leftX >= 4) leftX = 0;
//        rtnX.add(leftX);
//        rtnY.add(leftY);
//        probs.add(0.8);
//
//
//        int downX = x;
//        int downY = y + 1;
//        if(downY >= 3) downY = 0;
//        rtnX.add(downX);
//        rtnY.add(downY);
//        probs.add(0.1);
//
//        int upX = x;
//        int upY = y - 1;
//        if(upY < 0) upY = 3;
//        rtnX.add(upX);
//        rtnY.add(upY);
//        probs.add(0.1);
//
//        return formulaDoSummation(rtnX, rtnY, probs);
//    }

    /*
        Returns x,y coordinates of previous state that reached given coordinates after taking given action
     */
//    public static int[] getCoordsReachableTo(int x, int y, String givenAction){
//        int newX = 0;
//        int newY = 0;
//
//        if(givenAction.equals("left") && notInMiddleWall(x + 1, y)){
//            newX = x + 1;
//        }else if(givenAction.equals("right") && notInMiddleWall(x - 1, y)){
//            newX = x - 1;
//        }else if(givenAction.equals("down") && notInMiddleWall(x, y + 1)) {
//            newY = y + 1;
//        }else if(givenAction.equals("up") && notInMiddleWall(x, y - 1)){
//            newY = y - 1;
//        }
//
//        if(newX < 0) newX = 0;
//        if(newX > 3) newX = 3;
//        if(newY < 0) newY = 0;
//        if(newY > 2) newY = 2;
//
//        int[] rtnAry = {newX, newY};
//        return rtnAry;
//    }

//    public static boolean notInMiddleWall(int x, int y){
//        if(x == 1 && y == 1){
//            return true;
//        }
//        return false;
//    }

    public static double subFormulaDoSummation(List<Integer> x, List<Integer> y, List<Double> probs){
        //Does the summation part of the formula.
        int rtnVal = 0;
        for(int i = 0; i < x.size(); i++){
            rtnVal += (probs.get(i) * beliefState[x.get(i)][y.get(i)]);
        }

        return rtnVal;
    }

}
