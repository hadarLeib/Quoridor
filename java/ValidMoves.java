import java.util.List;
import java.util.ArrayList;

public class ValidMoves {
    private List <Fence> validFenceMoves;
    private List <PlayerMove> validPlayerMoves;
    private List <Move> validMoves;
    

    private int AMOUNT_SQUARES_ON_BOARD;
    private int MAX_POSSIBLE_FENCE_ID;
    private int CURRENT_A;
    private int CURRENT_B;
    private int CURRENT_C;
    private int CURRENT_D;


    public ValidMoves(){
        validMoves = new ArrayList<Move>();

        //constants
        AMOUNT_SQUARES_ON_BOARD = 81;
        MAX_POSSIBLE_FENCE_ID = 134;
        
    }

    

    public List <Move> getValidMovesList(Game game, int playerPosition){
        this.validMoves.clear();

        // adding player moves
        for(int i = 0; i < AMOUNT_SQUARES_ON_BOARD; i++){
            if(game.checkIfLegalConsideringJump(playerPosition, i)){
                PlayerMove playerMove = new PlayerMove(playerPosition, i);
                addMoveToValidMovesList(playerMove);

            }

        }

        // adding fence moves
        for(int i = 0; i < MAX_POSSIBLE_FENCE_ID; i++){
            getCurrABCD(i);
            
            Fence fence = new Fence(i, getSecondId(i), isFenceHorizontal(i), CURRENT_A, CURRENT_B, CURRENT_C, CURRENT_D);
            if(game.checkFenceLegal(fence) == 1){
                
                addMoveToValidMovesList(fence);
            }

        }

        return this.validMoves;

    }

    public void addMoveToValidMovesList(Move move){
        this.validMoves.add(move);
        
    }


    public int getSecondId(int firstId){
        if (isFenceHorizontal(firstId))
            return firstId  + 1;
        return firstId + 17;
    }

    public boolean isFenceHorizontal(int firstId){
        return (firstId % 17 > 7);
    }

    public void getCurrABCD(int firstId){
        if(isFenceHorizontal(firstId)){
            CURRENT_A = firstId - (8 * (firstId / 17) - 8);
            CURRENT_B = CURRENT_A + 9;
            CURRENT_C = CURRENT_A + 1;
            CURRENT_D = CURRENT_B + 1;
        }

        else{
            CURRENT_A = firstId - (8 * (firstId / 17));
            CURRENT_B = CURRENT_A + 1;
            CURRENT_C = firstId - (8 * (getSecondId(firstId) / 17));
            CURRENT_D = CURRENT_C + 1;
        }
    }

}
