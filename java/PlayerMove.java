public class PlayerMove extends Move{
    private int oldPos;
    private int newPos;

    //constructor
    public PlayerMove(int oldPos, int newPos){
        super("m");
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    //returns int: the old(current) position of a player on the board(id)
    public int getPlayerOldPos(){
        return this.oldPos;
    }

    //returns int: the position on the board a player will jump to in this move(id)
    public int getPlayerNewPos(){
        return this.newPos;
    }
}
