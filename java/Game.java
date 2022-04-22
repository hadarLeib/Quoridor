import java.util.HashMap;

public class Game {
    private Board board;
    private Player player1; // white - starts from low to high
    private Player player2; // black - starts from high to low
    private Player currentPlayer;
    private HashMap<Integer, Fence> fencesInGame;


    Game() {
        this.board = new Board();
        this.board.initAdjBoard();
        this.player1 = new Player(4, true); // white - starts from low to high
        this.player2 = new Player(76, false); // black - starts from high to low
        this.currentPlayer = player1;
        this.fencesInGame = new HashMap<Integer, Fence>();
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

    Player getCurrPlayer() {
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
                System.out.println("regular move");
        }

        return boardCopy.isAdj(oldPos, newPos);
    }


    //fence related functions

    public HashMap<Integer, Fence> getfencesInGame(){
        return this.fencesInGame;
    }

    public void addFenceToMap(int firstId, int secondId, boolean isHorizontal){
        Fence fence = new Fence(firstId, secondId, isHorizontal);
        this.fencesInGame.put(firstId, fence);
        this.fencesInGame.put(secondId, fence);
    }

    public Fence getFenceById(int id){
        return this.fencesInGame.get(id);
    }

    public boolean checkFenceLegal(int firstId, int secondId){
        boolean isLegal;
        //no fence on fence
        isLegal = ((!this.fencesInGame.containsKey(firstId))&&(!this.fencesInGame.containsKey(secondId)));
        
        //no out of bounds
        isLegal = (!(firstId % 17 == 16)) && (!(firstId > 135));

        //no crossing fence
        if(secondId-firstId == 1){ // horizontal fence
            if(this.fencesInGame.containsKey(firstId - 8)){
                if(this.fencesInGame.get(firstId - 8).getFirstId() == firstId - 8){
                    isLegal = false;
                }

            }

        }

        // TO DO :
        else{
            if(this.fencesInGame.containsKey(firstId)){
                if(this.fencesInGame.get(firstId).getFirstId() == firstId){
                    isLegal = false;
                }
            }
        }


        return isLegal;
    }
}
