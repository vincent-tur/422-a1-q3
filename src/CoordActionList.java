import java.util.ArrayList;
import java.util.List;

public class CoordActionList {
    public boolean terminalStateAdded;
    public List<CoordAction> coordActions;

    public int terminalStateIndex;
    public Coord originalCoord;

    public CoordActionList(Coord originalCoord){
        doConstructor(originalCoord);
    }

    public void doConstructor(Coord originalCoord){
        this.terminalStateIndex = -1;
        this.originalCoord = originalCoord;
        terminalStateAdded = false;
        coordActions = new ArrayList<>();
    }

    public CoordActionList(List<CoordAction> coordActions, Coord originalCoord){
        doConstructor(originalCoord);
        convertListToThis(coordActions);
    }

    public void convertListToThis(List<CoordAction> coordActions){
        int counter = 0;
        for (CoordAction coordAction: coordActions){
            this.add(coordAction);
            coordAction.potentialCoordIndex = counter;
            counter++;
        }
    }


    public void add(CoordAction coordAction){
        if(!this.originalCoord.eq(coordAction.finalCoord)){
            return;
        }
        if(coordAction.isTerminal && this.terminalStateAdded){
            return;
        }

        if(coordAction.isTerminal){
           terminalStateAdded = true;
           terminalStateIndex = coordActions.size();
        }
        this.coordActions.add(coordAction);

    }

    public CoordAction get(int i){
        return coordActions.get(i);
    }

    public int size(){
        return coordActions.size();
    }

}
