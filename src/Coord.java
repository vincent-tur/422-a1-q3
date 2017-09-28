public class Coord {
    public int x;
    public int y;

    public Coord(){}

    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean eq(Coord checkCoord){
        if(checkCoord.x == this.x && checkCoord.y == this.y)
            return true;

        return false;
    }

    public boolean eq(CoordAction checkCoord){
        if(checkCoord.finalCoord.x == this.x && checkCoord.finalCoord.y == this.y)
            return true;

        return false;
    }

    public boolean isTerminalState(){
        if(x == 3 && y == 1){
            return true;
        }else if(x == 3 && y == 2){
            return true;
        }
        return false;
    }
    public boolean isTerminalState(Coord checkCoord){
        if(checkCoord.x == 3 && checkCoord.y == 1){
            return true;
        }else if(checkCoord.x == 3 && checkCoord.y == 2){
            return true;
        }
        return false;
    }

    public Coord terminalDetect(Coord newCoord){
        if(newCoord.isTerminalState()){
            return this;
        }else{
            return newCoord;
        }
    }


    public Coord wallDetect(Coord newCoord){

        if(newCoord.x < 0) newCoord.x = 0;
        if(newCoord.x > 3) newCoord.x = 3;
        if(newCoord.y < 0) newCoord.y = 0;
        if(newCoord.y > 2) newCoord.y = 2;

        if(inMiddleWall(newCoord)){
            return this;
        }else{
           return newCoord;
        }

    }

    public boolean inMiddleWall(Coord checkCoord){
        if(checkCoord.x == 1 && checkCoord.y == 1){
            return true;
        }
        return false;
    }






    public Coord getLeftCoord(){
        return wallDetect(new Coord(this.x - 1, this.y));
//        return getGeneralCoord(new Coord(this.x - 1, this.y));
    }

    public Coord getRightCoord(){
        return wallDetect(new Coord(this.x + 1, this.y));
//        return getGeneralCoord(new Coord(this.x + 1, this.y));
    }

    public Coord getDownCoord(){
        return wallDetect(new Coord(this.x, this.y - 1));
//        return getGeneralCoord(new Coord(this.x, this.y - 1));
    }

    public Coord getUpCoord(){
        return wallDetect(new Coord(this.x, this.y + 1));
//        return getGeneralCoord(new Coord(this.x, this.y+1));

    }
//
    private Coord getGeneralCoord(Coord newCoord){

        newCoord = wallDetect(newCoord);
        newCoord = terminalDetect(newCoord);
        return newCoord;

    }


}
