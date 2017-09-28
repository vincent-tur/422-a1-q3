public class CoordAction extends Coord {
    public String action;

    public Coord checkCoord;
    public Coord originalCoord;

    public Coord finalCoord;

    public double prob;

    public int potentialCoordIndex;


    public boolean isTerminal;
    public CoordAction(Coord checkCoord, Coord originalCoord, String action, double prob){
        isTerminal = false;

        this.checkCoord = checkCoord;
        this.originalCoord = originalCoord;
        if(this.originalCoord.isTerminalState()){
            this.checkCoord = this.originalCoord;
            isTerminal = true;
        }
        this.action = action;
        this.prob = prob;




        wallDetect();
    }

    public void wallDetect(){
        Coord tempCoord = new Coord(checkCoord.x, checkCoord.y);

        if(tempCoord.x < 0) tempCoord.x = 0;
        if(tempCoord.x > 3) tempCoord.x = 3;
        if(tempCoord.y < 0) tempCoord.y = 0;
        if(tempCoord.y > 2) tempCoord.y = 2;

        if(inMiddleWall(tempCoord)){
            setFinalCoord(originalCoord);
        }else{
            setFinalCoord(tempCoord);
            this.finalCoord = tempCoord;
        }
    }

    public boolean isTerminalOriginally(){
        if(isTerminalState(originalCoord)){
            return true;
        }else{
            return false;
        }
    }

    /*
        If it's originally a terminal state and the finalCoord is a DIFFERENT terminal state,
        then return true

        If it's originally a terminal state and the finalCoord is the same terminal state,
        then return false
     */
    public boolean isTerminalDifferentOriginalFinal(){
        if((originalCoord.x == 3 && originalCoord.y == 1) &&
                (finalCoord.x == 3 && finalCoord.y == 2)){
            return true;
        }else if((originalCoord.x == 3 && originalCoord.y == 2) &&
        (finalCoord.x == 3 && finalCoord.y == 1)){
            return true;
        }
        return false;
    }


    public void setFinalCoord(Coord setCoord){
        this.finalCoord = setCoord;
        this.x = setCoord.x;
        this.y = setCoord.y;

        if(isTerminalOriginally() && !(isTerminalDifferentOriginalFinal())){
            if(isTerminalState(this.finalCoord)){
                this.isTerminal = true;
                this.prob = 1;
            }
        }
    }

    public boolean inMiddleWall(Coord checkCoord){
        if(checkCoord.x == 1 && checkCoord.y == 1){
            return true;
        }
        return false;
    }

    public boolean eq(CoordAction checkCoord){
        if(checkCoord.finalCoord.x == this.finalCoord.x && checkCoord.finalCoord.y == this.finalCoord.y)
            return true;

        return false;
    }
    public boolean eq(Coord checkCoord){
        if(checkCoord.x == this.finalCoord.x && checkCoord.y == this.finalCoord.y)
            return true;

        return false;
    }
}
