import java.util.List;
import java.util.ArrayList;

public class ValidMoves {

    private int AMOUNT_SQUARES_ON_BOARD;
    private int MAX_POSSIBLE_FENCE_ID;
    private int CURRENT_A;
    private int CURRENT_B;
    private int CURRENT_C;
    private int CURRENT_D;


    public ValidMoves(){
        // constants
        AMOUNT_SQUARES_ON_BOARD = 81;
        MAX_POSSIBLE_FENCE_ID = 134;
    }

    //receives the game(Game) and (int)playerPositions
    //function will run on all the options of moves in the game and add the legal options to th list
    //returns List<Move>: the list of valid moves created
    public List<Move> getValidMovesList(Game game, int playerPosition) {
        List<Move> validMoves = new ArrayList<Move>();

        // adding player moves
        
        for (int i = 0; i < AMOUNT_SQUARES_ON_BOARD; i++) {
            if (game.checkIfLegalConsideringJump(playerPosition, i)) {
                //move is legal
                PlayerMove playerMove = new PlayerMove(playerPosition, i);//create PlayerMove
                validMoves.add(playerMove);//add to list
            }
        }
        
/*
        int row;
        if(game.getCurrPlayer().getIsPlayerWhite()){
            row = 8;
        }
        else{
            row = 0;
        }
        PlayerMove playerMove = new PlayerMove(playerPosition, game.shortestPathToRow(playerPosition, row).get(0));

        validMoves.add(playerMove);
*/
        
        // adding fence moves
        for (int i = 0; i <= MAX_POSSIBLE_FENCE_ID; i++) {
            
            this.getCurrABCD(i);

            Fence fence = new Fence(i, getSecondId(i), isFenceHorizontal(i),
                    this.CURRENT_A, this.CURRENT_B,
                    this.CURRENT_C, this.CURRENT_D);//create Fence to try

                    
            if (game.checkFenceLegal(fence) == 1) {
                //fence placement is legal
                validMoves.add(fence);//add to list
            }
        }

        return validMoves;
    }

    //receives int firstId (of a fence)
    //returns int: the second id of the fence
    public int getSecondId(int firstId) {
        if (isFenceHorizontal(firstId)){
            //if fence is horizontal, the next id will be one on the right
            return firstId + 1;
        }

        //if fence is vertical, the next fence id will be one bellow (+17)
        return firstId + 17;
    }

    //receives the first id of the fence
    //calculates by the id if the fence is horizontal or not
    //returns boolean: true if it is horizontal, false if not
    public boolean isFenceHorizontal(int firstId) {
        return (firstId % 17 > 7);
    }

    //receives int firstId (the first id of a fence)
    //calculates the id s of the four nodes (a b c d) affected by the fence
    //a and b will have a fence between them
    //c and d will have a fence between them
    public void getCurrABCD(int firstId) {

        int row = firstId / 17; //row fence is in
        int col = (firstId - 9 * row) % 8; //columb fence is in
        
        if (isFenceHorizontal(firstId)) {
            //if fence is horizontal, b is below a, c is right of a, d is below c
            this.CURRENT_A = col + row * 9;
            this.CURRENT_B = this.CURRENT_A + 9;
            this.CURRENT_C = this.CURRENT_A + 1;
            this.CURRENT_D = this.CURRENT_B + 1;
        }

        else {
            //if fence is vertical, b right of a, c below a, d is right of c
            this.CURRENT_A = col + row * 9;
            this.CURRENT_B = this.CURRENT_A + 1;
            this.CURRENT_C = this.CURRENT_A + 9;
            this.CURRENT_D = this.CURRENT_C + 1;
        }
    }

}
