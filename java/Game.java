import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Queue;

public class Game {
    private Board board;
    private Player player1; // white - starts from low to high
    private Player player2; // black - starts from high to low
    private Player currentPlayer;

    /*
    the integer, fence hashmap:
    fences in game are entered twice into the hashmap-
    once with the key being the first id
    second time key is second id
    this makes the checking of thee fences later far less complex and */
    private HashMap<Integer, Fence> fencesInGame;

    //default constructor
    public Game() {
        this.board = new Board();
        this.board.initAdjBoard();
        this.player1 = new Player(4, true); // white - starts from low to high
        this.player2 = new Player(76, false); // black - starts from high to low
        this.currentPlayer = player1;
        this.fencesInGame = new HashMap<Integer, Fence>();
    }

    // copy constructor
    public Game(Game game) {
        this.board = new Board(game.getBoard());
        this.player1 = new Player(game.getPlayerOne());
        this.player2 = new Player(game.getPlayerTwo());
        this.currentPlayer = game.getCurrPlayer();
        this.fencesInGame = new HashMap<Integer, Fence>();
        this.fencesInGame.putAll(game.getfencesInGame());
    }

    //returns Player: player1
    public Player getPlayerOne() {
        return this.player1;
    }

    //returns Player: player2
    public Player getPlayerTwo() {
        return this.player2;
    }

    //switches the current player in this game
    public void switchPayer() {
        if (this.currentPlayer == player1)
            this.currentPlayer = player2;
        else
            this.currentPlayer = player1;
    }

    //returns Board: board of this game
    public Board getBoard() {
        return this.board;
    }

    //returns Player: this game's current player
    public Player getCurrPlayer() {
        return this.currentPlayer;
    }

    //returns int: this game's current player's position
    public int getCurrentPlayerPos() {
        return this.currentPlayer.getPossition();
    }

    //returns int: this game's other(not current) player's position
    public int getOtherPlayerPos() {
        if (this.currentPlayer == this.player1)
            return this.player2.getPossition();
        return this.player1.getPossition();
    }

    //recieves ((Player) player) and ((int) pos)
    //sets player's position to pos
    public void setPlayerPos(Player player, int pos) {
        player.setPossition(pos);
    }

    //receives ((int) oldPos) and ((int) newPos) - two positions on the board
    //checks if the jump from oldPos to newPos is legal
    //considers special cases of player jumping over other player
    //returns boolean: true if the jump is legal and false if not
    public boolean checkIfLegalConsideringJump(int oldPos, int newPos) {
        Board boardCopy = new Board(this.getBoard());
        int playerPosDif = getOtherPlayerPos() - getCurrentPlayerPos();

        switch (playerPosDif) {
            case (1): // curr player is on the left side of other player
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                //checks that there is no fence on right side of other player
                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() + 1))) 
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() + 1));
                break;

            case (-1): // curr player is on the right side of other player
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                //checks that there is no fence on left side of other player
                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() - 1)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() - 1));
                break;

            case (9): // curr player is below the other player
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                //checks that there is no fence above other player
                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() + 9)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() + 9));
                break;

            case (-9): // curr player is above the other player
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                //checks that there is no fence below other player
                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() - 9)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() - 9));
                break;

            default:
                // regular move
        }

        return boardCopy.isAdj(oldPos, newPos);
    }

    //retruns map of all fences in the game
    public HashMap<Integer, Fence> getfencesInGame() {
        return this.fencesInGame;
    }

    //adds a fence to the map
    public void addFenceToMap(Fence fence) {
        this.fencesInGame.put(fence.getFirstId(), fence);
        this.fencesInGame.put(fence.getSecondId(), fence);
    }

    //receves (int(id))
    //returns Fence: the fence from the map of fences in the game with that id(key)
    public Fence getFenceById(int id) {
        return this.fencesInGame.get(id);
    }

    //receives ((Fence)fence)
    //checks if the fence is legal on the board
    //return int: error type
    public int checkFenceLegal(Fence fence) {
        /*
         * returns 1 if move is legal
         * returns 2 if a fence is placed on another fence (either parallel or cross)
         * returns 3 if fence is placed out of bounds
         * returns 4 if placing the fence will create a situation in which a player is
         * completely blocked
         */

        int errorType = 1;

        // no fence on fence - parallel
        if ((this.fencesInGame.containsKey(fence.getFirstId()))
                || (this.fencesInGame.containsKey(fence.getSecondId()))) {
            errorType = 2;
        }

        // no fence on fence - cross
        if (fence.getIsHorizontal()) { // horizontal fence
            if (this.fencesInGame.containsKey(fence.getFirstId() - 8)) {
                if (this.fencesInGame.get(fence.getFirstId() - 8).getFirstId() == (fence.getFirstId() - 8)) {
                    errorType = 2;
                }

            }

        }

        else { // vertical fence
            if (this.fencesInGame.containsKey(fence.getFirstId() + 8)) {
                if (this.fencesInGame.get(fence.getFirstId() + 8).getFirstId() == (fence.getFirstId() + 8)) {
                    errorType = 2;
                }
            }
        }

        // no out of bounds
        //%17 == 16 for horizontal out of bounds
        //>135 for vertical out of bounds
        if ((fence.getFirstId() % 17 == 16) || (fence.getFirstId() > 135)) {
            errorType = 3;
        }
/*
        // setup for dfs check
        this.getBoard().addEdge(fence.getA(), fence.getB());
        this.getBoard().addEdge(fence.getC(), fence.getD());

        // player cant get across
        if (!this.getBoard().playerCanGetToEnd(this.getCurrentPlayerPos(), this.getCurrPlayer())) {
            errorType = 4;
        }

        // edges are no longer relavent
        this.getBoard().removeEdge(fence.getA(), fence.getB());
        this.getBoard().removeEdge(fence.getC(), fence.getD());
*/
        return errorType;
    }

    //returns boolean: true if game is over (player has reached anending point)
    //false if not
    public boolean isOver() {
        return (this.player1.getPossition() > 71 || this.player2.getPossition() < 9);
    }


    //receives ((Move) move)
    //function will do the move
    public void doMove(Move move) {
        if (move.getMoveType().equals("m")) {
            //the move is a piece movement
            setPlayerPos(getCurrPlayer(), ((PlayerMove) move).getPlayerNewPos());
            switchPayer();
        }

        else if (move.getMoveType().equals("f")) {
            //the move is a fence placement
            addFenceToMap(((Fence) move));
            getCurrPlayer().useFence();
            getBoard().removeEdge(((Fence) move).getA(), ((Fence) move).getB());
            getBoard().removeEdge(((Fence) move).getC(), ((Fence) move).getD());
            switchPayer();
        }
    }

    //receives ((int) src) position on the board
    //receives ((int)row) a row on the board (0 - 8)
    //function will find the shortest path from src position to anywhere on the row of the board
    //return List<Integer>: list of integers that represent the IDs of the positioons in the shortest path
    public List<Integer> shortestPathToRow(int src, int row) {
        List<Integer> path = new LinkedList<>();
        Queue<Integer> queue = new LinkedList<>();
        HashMap<Integer, Integer> parentNode = new HashMap<>();

        // enqueue start configuration onto queue
        queue.add(src);
        // mark start configuration
        parentNode.put(src, null);
        // Get game board of the current game
        boolean[][] gameBoard = this.getBoard().getAdjacentMatrix();

        while (!queue.isEmpty()) {
            int t = queue.poll();
            int t_row = t / 9; // Get row by position
            if (t_row == row) {
                while (t != src) {
                    path.add(t);
                    t = parentNode.get(t);
                }

                Collections.reverse(path);
                return path;
            }

            for (int i = 0; i < gameBoard[0].length; i++) {
                // Get current pos on board by row and col

                if(gameBoard[t][i]){
                    if (!parentNode.containsKey(i)) {
                        parentNode.put(i, t);
                        queue.add(i);
                    }
                }
                
            }
        }

        return path;
    }
}
