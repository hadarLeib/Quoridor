public class Move {
    //moveType- "f" for fence placement, "m" for piece movement
    private String moveType;

    //abstract parent of Fence and PlayerMove

    //default constructor
    public Move(){}

    //constructor
    public Move(String moveType){
        this.moveType = moveType;
    }

    //returns string: the movement type ("f" or "m")
    public String getMoveType(){
        return this.moveType;
    }
    
}
