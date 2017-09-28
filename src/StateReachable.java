import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StateReachable {
    //Given (x,y), what coordinates are reachable to it given the action?
//    public int x;
//    public int y;
    public Coord originalCoord;
    public ArrayList<CoordAction> rtnCoords;
    public String action;

    public List<CoordAction> potentialCoords;
    public CoordActionList newPotentialCoords;
    //
//    public List<Integer> rtnX = new ArrayList<Integer>();
//    public List<Integer> rtnY = new ArrayList<Integer>();
//    public List<String> rtnAction = new ArrayList<String>();
//    public List<Double> rtnProb = new ArrayList<Double>();


    public StateReachable(Coord originalCoord, String action){
        rtnCoords = new ArrayList<CoordAction>();
        this.action = action;
        this.originalCoord = originalCoord;
        this.potentialCoords = new ArrayList<CoordAction>();
        this.cellsReachableFrom();
    }

    public void cellsReachableFrom(){
        List<Coord> checkCoords = new ArrayList<>();

        checkCoords.add(this.originalCoord.getLeftCoord());
        checkCoords.add(this.originalCoord.getRightCoord());
        checkCoords.add(this.originalCoord.getDownCoord());
        checkCoords.add(this.originalCoord.getUpCoord());

        if(this.originalCoord.isTerminalState()){
            checkCoords.add(this.originalCoord);
        }
        checkCoords = removeDupes(checkCoords);
        checkCells(checkCoords);
        this.newPotentialCoords = new CoordActionList(this.potentialCoords, this.originalCoord);
        populateRtnCoords();
    }

    public void checkCells(List<Coord> checkCoords){
        for(int i = 0; i < checkCoords.size(); i++){
            checkCell(checkCoords.get(i), action);
//            checkCell(checkCoords.get(i), "right");
//            checkCell(checkCoords.get(i), "down");
//            checkCell(checkCoords.get(i), "up");
        }
    }

    public List<Coord> removeDupes(List<Coord> checkCoords){
        List<Coord> newList = new ArrayList<Coord>();

        for(int i = 0; i < checkCoords.size(); i++){
            boolean addToList = true;
            for(int j = 0; j < newList.size(); j++){
                if(newList.get(j).eq(checkCoords.get(i))){
                    addToList = false;
                    break;
                }
            }
            if(addToList){
                newList.add(checkCoords.get(i));
            }
        }

        return newList;
    }

    public void checkCell(Coord checkCoord, String givenAction){


        if(givenAction.equals("left")){
            potentialCoords.add(new CoordAction(checkCoord.getLeftCoord(), checkCoord, givenAction, 0.8));
            potentialCoords.add(new CoordAction(checkCoord.getUpCoord(), checkCoord, givenAction, 0.1));
            potentialCoords.add(new CoordAction(checkCoord.getDownCoord(), checkCoord, givenAction, 0.1));
        }else if(givenAction.equals("right")){
            potentialCoords.add(new CoordAction(checkCoord.getRightCoord(), checkCoord, givenAction, 0.8));
            potentialCoords.add(new CoordAction(checkCoord.getUpCoord(), checkCoord, givenAction, 0.1));
            potentialCoords.add(new CoordAction(checkCoord.getDownCoord(), checkCoord, givenAction, 0.1));
        }else if(givenAction.equals("down")) {
            potentialCoords.add(new CoordAction(checkCoord.getDownCoord(), checkCoord, givenAction, 0.8));
            potentialCoords.add(new CoordAction(checkCoord.getLeftCoord(), checkCoord, givenAction, 0.1));
            potentialCoords.add(new CoordAction(checkCoord.getRightCoord(), checkCoord, givenAction, 0.1));
        }else if(givenAction.equals("up")){
            potentialCoords.add(new CoordAction(checkCoord.getUpCoord(), checkCoord, givenAction, 0.8));
            potentialCoords.add(new CoordAction(checkCoord.getLeftCoord(), checkCoord, givenAction, 0.1));
            potentialCoords.add(new CoordAction(checkCoord.getRightCoord(), checkCoord, givenAction, 0.1));
        }


    }

    public void populateRtnCoords(){
        for(int i = 0; i < newPotentialCoords.size(); i++){
            if(i == newPotentialCoords.terminalStateIndex){
                int xx = 0;
            }
            CoordAction curPotentialCoord = newPotentialCoords.get(i);
            if(!curPotentialCoord.isTerminalDifferentOriginalFinal()){
                if(this.originalCoord.eq(curPotentialCoord.finalCoord)){
                    if(action.equals(curPotentialCoord.action)){
//                        curPotentialCoord.potentialCoordIndex = i;
                        rtnCoords.add(curPotentialCoord);
                    }
                }
            }
        }

        int xx = 0;
    }






















//
//    public StateReachable(int x, int y, String action){
//        rtnCoords = new ArrayList<CoordAction>();
//        this.x = x;
//        this.y = y;
//        this.action = action;
//
//        if(action.equals("left") || action.equals("right")) {
//            getCoordsReachableTo(action);
//            getCoordsReachableTo("up");
//            getCoordsReachableTo("down");
//
//        }else if(action.equals("down") || action.equals("up")) {
//            getCoordsReachableTo(action);
//            getCoordsReachableTo("left");
//            getCoordsReachableTo("right");
//        }
//    }
//
//    public void getCoordsReachableTo(String givenAction){
//        int newX = x;
//        int newY = y;
//        double newProb = 0.1;
//
//        if(givenAction == this.action){
//            newProb = 0.8;
//        }
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
//        rtnX.add(newX);
//        rtnY.add(newY);
//        rtnProb.add(newProb);
//    }
//
//    public boolean notInMiddleWall(int x, int y){
//        if(x == 1 && y == 1){
//            return true;
//        }
//        return false;
//    }


}
