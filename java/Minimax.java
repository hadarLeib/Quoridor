public class Minimax {

    int depth;
    //best move
    
    public Minimax(int depth){
        this.depth = depth;
    }

    //should get game copy!!!!
    public int minimaxWithAlphaBeta(Game game, int depth, int alpha, int beta, boolean maxPlayer){

        if(depth == 0){ // or game is over - TO DO
            return heuristic(game);
        }

        if(maxPlayer){
            ValidMoves validMoves = new ValidMoves();

            validMoves.createValidFenceMovesList(game);
            validMoves.createValidPlayerMovesList(game, game.getCurrentPlayerPos());
            
            for(PlayerMove move:validMoves.getValidPlayerMovesList()){


                Game child = new Game(game);
                child.setPlayerPos(game.getCurrPlayer(), move.getPlayerNewPos());
                child.switchPayer();

                alpha = Math.max(alpha, minimaxWithAlphaBeta(child, (depth - 1), alpha, beta, false));

                if(beta <= alpha)
                    break;
            }
        }



        return 0;
    }

    public int heuristic(Game game){

        return 0;
    }
}
