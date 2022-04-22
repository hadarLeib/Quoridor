import java.util.List;
import java.util.ArrayList;

public class ValidMoves {
    List <Fence> validFenceMoves;
    List <PlayerMove> validPlayerMoves;

    public ValidMoves(){
        validFenceMoves = new ArrayList<Fence>();
        validPlayerMoves = new ArrayList<PlayerMove>();
    }

    public List <Fence> getValidFenceMovesLis(){
        return this.validFenceMoves;
    }

    public List <PlayerMove> getValidPlayerMovesList(){
        return this.validPlayerMoves;
    }

    public void addPlayerMoveToValidPlayerMoveList(int oldPos, int newPos){
        PlayerMove newMove = new PlayerMove(oldPos, newPos);
        validPlayerMoves.add(newMove);
    }

    public void createValidPlayerMovesList(Board board){

    }

}
