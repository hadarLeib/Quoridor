public class PlayerMove {
    private int oldPos;
    private int newPos;

    public PlayerMove(int oldPos, int newPos){
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
