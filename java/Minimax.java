public class Minimax {

    int depth;
    Move bestMove;

    public Minimax(int depth) {
        this.depth = depth;
    }

    //receives Game game and returns the best move for AI player to make (Move) 
    public Move bestMoveCalc(Game game) {
        if (game.getCurrPlayer().hasFences()) {
            //if has fences left - check best move option using minimax alpha beta
            Game gameCopy = new Game(game);
            minimaxWithAlphaBetaWithVal(gameCopy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        }
        
        else {
            //no fences left - best option is to head for the path shortest to the finish line
            this.bestMove = new PlayerMove(game.getCurrentPlayerPos(),
                    game.shortestPathToRow(game.getCurrentPlayerPos(), 0).get(0));
        }

        return this.bestMove;
    }

    //receives the game (Game game)
    //receives the depth for AI to search (int depth)
    //receives int alpha, int beta foor alpha beta pruning
    //recieves (boolean maxPlayer): true if we are searching for max resalt, false if searching for min result
    
    //goes over all possible moves of AI (at current game state)
    //function will give a score to each move, saves the best
    public void minimaxWithAlphaBetaWithVal(Game game, int depth, int alpha, int beta, boolean maxPlayer) {
        
        int val;
        ValidMoves validMoves = new ValidMoves();

        //goes ove list of valid moves
        for (Move move : validMoves.getValidMovesList(game, game.getCurrentPlayerPos())) {
            Game child = new Game(game);
            child.doMove(move);//tries move

            //gets score (val)for move
            val = minimaxWithAlphaBeta(child, depth, alpha, beta, false);

            if (val > alpha) {
                //found bbest move yet
                alpha = val;

                //sets as best move
                if(move.getMoveType().equals("m")){
                    this.bestMove = new PlayerMove(((PlayerMove)move).getPlayerOldPos(), ((PlayerMove)move).getPlayerNewPos());
                }
                else{
                    this.bestMove = new Fence(((Fence)move).getFirstId(), ((Fence)move).getSecondId(),
                    ((Fence)move).getIsHorizontal(), ((Fence)move).getA(), ((Fence)move).getB(),
                    ((Fence)move).getC(), ((Fence)move).getD());
                }

            }

            //pruning
            if (beta <= alpha) {
                break;
            }
        }

    }


    //receives the game (Game game)
    //receives the depth for AI to search (int depth)
    //receives int alpha, int beta foor alpha beta pruning
    //recieves (boolean maxPlayer): true if we are searching for max resalt, false if searching for min result
    
    //calcuates score for each possible move of the computer
    //uses minimax algorithm with the extension of alpha beta pruning
    //returns int: the score
    public int minimaxWithAlphaBeta(Game game, int depth, int alpha, int beta, boolean maxPlayer) {

        if (depth == 0 || game.isOver()) {
            return heuristic(game);
        }

        if (maxPlayer) {
            ValidMoves validMoves = new ValidMoves();

            //goes ove list of valid moves
            for (Move move : validMoves.getValidMovesList(game, game.getCurrentPlayerPos())) {

                Game child = new Game(game);
                child.doMove(move); //tries move

                //alpha has to be maximum score
                alpha = Math.max(alpha, minimaxWithAlphaBeta(child, depth - 1, alpha, beta, false));

                //pruning
                if (beta <= alpha) {
                    break;
                }
            }

            return alpha;
        }

        else {
            ValidMoves validMoves = new ValidMoves();

            //goes ove list of valid moves
            for (Move move : validMoves.getValidMovesList(game, game.getCurrentPlayerPos())) {

                Game child = new Game(game);
                child.doMove(move);//tries move

                //beta has to be minimum score
                beta = Math.min(beta, minimaxWithAlphaBeta(child, depth - 1, alpha, beta, true));

                //pruning
                if (beta <= alpha) {
                    break;
                }
            }

            return beta;
        }
    }


    //receives Game game
    //returns int: the difference between the shortest path from AI player to it's finish
    //and between the shortest path from human player to it's finish line
    public int heuristic(Game game) {// shortest path difference

        return game.shortestPathToRow(game.getPlayerTwo().getPossition(), 0).size()
                - game.shortestPathToRow(game.getPlayerOne().getPossition(), 8).size();
    }
}
