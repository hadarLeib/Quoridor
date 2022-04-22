import java.util.List;
import java.util.ArrayList;

public class ValidMoves {
    private List <Fence> validFenceMoves;
    private List <PlayerMove> validPlayerMoves;

    private int AMOUNT_SQUARES_ON_BOARD;
    private int MAX_POSSIBLE_FENCE_ID;


    public ValidMoves(){
        validFenceMoves = new ArrayList<Fence>();
        validPlayerMoves = new ArrayList<PlayerMove>();

        //constants
        AMOUNT_SQUARES_ON_BOARD = 81;
        MAX_POSSIBLE_FENCE_ID = 134;
    }

    public List <Fence> getValidFenceMovesLis(){
        return this.validFenceMoves;
    }

    public List <PlayerMove> getValidPlayerMovesList(){
        return this.validPlayerMoves;
    }

    public void addPlayerMoveToValidPlayerMoveList(int oldPos, int newPos){
        PlayerMove newMove = new PlayerMove(oldPos, newPos);
        this.validPlayerMoves.add(newMove);
    }

    public void addFenceMoveToValidFenceMoveList(int firstId, int secondId, boolean isHorizontal, int a, int b, int c, int d){
        Fence newFence = new Fence(firstId, secondId, isHorizontal, a, b, c, d);
        this.validFenceMoves.add(newFence);
    }

    public void createValidPlayerMovesList(Game game, Board board, int playerPosition){

        for(int i = 0; i < AMOUNT_SQUARES_ON_BOARD; i++){
            if(game.checkIfLegalConsideringJump(playerPosition, i)){
                addPlayerMoveToValidPlayerMoveList(playerPosition, i);

            }

        }

    }

    public void createValidFenceMovesList(Game game, Board board, int firstId, int secondId, boolean isHorizontal, int aSquare, int bSquare, int cSquare, int dSquare){

        for(int i = 0; i < MAX_POSSIBLE_FENCE_ID; i++){
            if(game.checkFenceLegal(firstId, secondId, aSquare, bSquare, cSquare, dSquare) == 1){
                addFenceMoveToValidFenceMoveList(firstId, secondId, isHorizontal, aSquare, bSquare, cSquare, dSquare);
                
            }

        }

    }

}
