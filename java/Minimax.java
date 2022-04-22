public class Minimax {

    int depth;
    int bestMove; // if fence - first id of fence, if movement - new position
    String bestMoveType; // "f" for fence, "p" for movement
    
    public Minimax(int depth){
        this.depth = depth;
    }

    public void bestMoveCalc(Game game){
        if(game.getCurrPlayer().hasFences()){
            minimaxWithAlphaBetaWithVal(game, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        }

        //else - next move is closest move to end
    }

    //should get game copy!!!!
    public int minimaxWithAlphaBetaWithVal(Game game, int depth, int alpha, int beta, boolean maxPlayer){

        if(depth == 0){ // or game is over - TO DO
            return heuristic(game);
        }

        if(maxPlayer){
            int val;
            ValidMoves validMoves = new ValidMoves();

            validMoves.createValidFenceMovesList(game);
            validMoves.createValidPlayerMovesList(game, game.getCurrentPlayerPos());
            
            for(PlayerMove move:validMoves.getValidPlayerMovesList()){

                Game child = new Game(game);
                child.setPlayerPos(game.getCurrPlayer(), move.getPlayerNewPos());
                child.switchPayer();

                val = minimaxWithAlphaBeta(child, (depth - 1), alpha, beta, false);

                if (val > alpha) {
                    alpha = val;
                    this.bestMove = move.getPlayerNewPos();
                    this.bestMoveType = "m";
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return alpha;
        }

        else{
            ValidMoves validMoves = new ValidMoves();

            validMoves.createValidFenceMovesList(game);
            validMoves.createValidPlayerMovesList(game, game.getCurrentPlayerPos());
            
            for(PlayerMove move:validMoves.getValidPlayerMovesList()){

                Game child = new Game(game);
                child.setPlayerPos(game.getCurrPlayer(), move.getPlayerNewPos());
                child.switchPayer();

                beta = Math.max(beta, minimaxWithAlphaBeta(child, (depth - 1), alpha, beta, true));

                if(beta <= alpha)
                    break;
            }
            return beta;
        }

    }

    
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
            return alpha;
        }

        else{
            ValidMoves validMoves = new ValidMoves();

            validMoves.createValidFenceMovesList(game);
            validMoves.createValidPlayerMovesList(game, game.getCurrentPlayerPos());
            
            for(PlayerMove move:validMoves.getValidPlayerMovesList()){

                Game child = new Game(game);
                child.setPlayerPos(game.getCurrPlayer(), move.getPlayerNewPos());
                child.switchPayer();

                beta = Math.max(beta, minimaxWithAlphaBeta(child, (depth - 1), alpha, beta, true));

                if(beta <= alpha)
                    break;
            }
            return beta;
        }

    }

    public int heuristic(Game game){// shortest path difference

        return 0;
    }
}
