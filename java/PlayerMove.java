public class PlayerMove extends Move{
    private int oldPos;
    private int newPos;

    public PlayerMove(int oldPos, int newPos){
        super("m");
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    public int getPlayerOldPos(){
        return this.oldPos;
    }

    public int getPlayerNewPos(){
        return this.newPos;
    }
}
