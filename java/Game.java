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
    private HashMap<Integer, Fence> fencesInGame;

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

    public Player getPlayerOne() {
        return this.player1;
    }

    public Player getPlayerTwo() {
        return this.player2;
    }

    public void switchPayer() {
        if (this.currentPlayer == player1)
            this.currentPlayer = player2;
        else
            this.currentPlayer = player1;
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrPlayer() {
        return this.currentPlayer;
    }

    public int getCurrentPlayerPos() {
        return this.currentPlayer.getPossition();
    }

    public int getOtherPlayerPos() {
        if (this.currentPlayer == this.player1)
            return this.player2.getPossition();
        return this.player1.getPossition();
    }

    public void setPlayerPos(Player player, int pos) {
        player.setPossition(pos);
    }

    public boolean checkIfLegalConsideringJump(int oldPos, int newPos) {
        Board boardCopy = new Board(this.getBoard());
        int playerPosDif = getOtherPlayerPos() - getCurrentPlayerPos();

        switch (playerPosDif) {
            case (1):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() + 1)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() + 1));
                break;

            case (-1):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() - 1)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() - 1));
                break;

            case (9):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() + 9)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() + 9));
                break;

            case (-9):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                if (boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() - 9)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() - 9));
                break;

            default:
                // System.out.println("regular move");
        }

        return boardCopy.isAdj(oldPos, newPos);
    }

    // fence related functions

    public HashMap<Integer, Fence> getfencesInGame() {
        return this.fencesInGame;
    }

    public void addFenceToMap(Fence fence) {
        this.fencesInGame.put(fence.getFirstId(), fence);
        this.fencesInGame.put(fence.getSecondId(), fence);
    }

    public Fence getFenceById(int id) {
        return this.fencesInGame.get(id);
    }

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

    public boolean isOver() {
        return (this.player1.getPossition() > 71 || this.player2.getPossition() < 9);
    }

    public void doMove(Move move) {
        if (move.getMoveType().equals("m")) {
            setPlayerPos(getCurrPlayer(), ((PlayerMove) move).getPlayerNewPos());
            switchPayer();
        } else if (move.getMoveType().equals("f")) {
            addFenceToMap(((Fence) move));
            getCurrPlayer().useFence();
            switchPayer();
        }
    }

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
                // int cur_pos = (t_row * 9 + i % 9);

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
