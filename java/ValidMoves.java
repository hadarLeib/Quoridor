import java.util.List;
import java.util.ArrayList;

public class ValidMoves {
    private List<Move> validMoves;

    private int AMOUNT_SQUARES_ON_BOARD;
    private int MAX_POSSIBLE_FENCE_ID;
    private int CURRENT_A;
    private int CURRENT_B;
    private int CURRENT_C;
    private int CURRENT_D;


    public ValidMoves(){

        validMoves = new ArrayList<Move>();

        // constants
        AMOUNT_SQUARES_ON_BOARD = 81;
        MAX_POSSIBLE_FENCE_ID = 134;
    }

    public List<Move> getValidMovesList(Game game, int playerPosition) {
        this.validMoves.clear();

        // adding player moves
        for (int i = 0; i < AMOUNT_SQUARES_ON_BOARD; i++) {
            if (game.checkIfLegalConsideringJump(playerPosition, i)) {
                PlayerMove playerMove = new PlayerMove(playerPosition, i);
                this.addMoveToValidMovesList(playerMove);
            }
        }
        

        // adding fence moves
        // int count = 0;
        for (int i = 0; i <= MAX_POSSIBLE_FENCE_ID; i++) {
            this.getCurrABCD(i);

            Fence fence = new Fence(i, getSecondId(i), isFenceHorizontal(i),
                    this.CURRENT_A, this.CURRENT_B,
                    this.CURRENT_C, this.CURRENT_D);

                    
            if (game.checkFenceLegal(fence) == 1) {
                addMoveToValidMovesList(fence);
            }
        }

        return this.validMoves;
    }

    public void addMoveToValidMovesList(Move move) {
        this.validMoves.add(move);
    }

    public int getSecondId(int firstId) {
        if (isFenceHorizontal(firstId))
            return firstId + 1;
        return firstId + 17;
    }

    public boolean isFenceHorizontal(int firstId) {
        return (firstId % 17 > 7);
    }

    public void getCurrABCD(int firstId) {

        int row = firstId / 17;
        int col = (firstId - 9 * row) % 8;
        
        if (isFenceHorizontal(firstId)) {
            this.CURRENT_A = col + row * 9;
            this.CURRENT_B = this.CURRENT_A + 9;
            this.CURRENT_C = this.CURRENT_A + 1;
            this.CURRENT_D = this.CURRENT_B + 1;
        }

        else {
            this.CURRENT_A = col + row * 9;
            this.CURRENT_B = this.CURRENT_A + 1;
            this.CURRENT_C = this.CURRENT_A + 9;
            this.CURRENT_D = this.CURRENT_C + 1;
        }
    }

}
