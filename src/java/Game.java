/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author משתמש
 */
public class Game {
    private board board;
    private Player player1; //white - starts from low to high
    private Player player2; //black - starts from high to low
    private Player currentPlayer;
    
    Game(){
        this.board = new board();
        this.board.initAdjBoard();
        this.player1 = new Player(4, true); //white - starts from low to high
        this.player2 = new Player(76, false); //black - starts from high to low
        this.currentPlayer = player1;
    }
    
    public void switchPayer(){
        if(this.currentPlayer == player1)
            this.currentPlayer = player2;
        else
            this.currentPlayer = player1;
    }
    
    public board getBoard(){
        return this.board;
    }
    
    Player getCurrPlayer(){
        return this.currentPlayer;
    }
    
    public int getCurrentPlayerPos(){
        return this.currentPlayer.getPossition();
    }
    
    public int getOtherPlayerPos(){
        if(this.currentPlayer == this.player1)
            return this.player2.getPossition();
        return this.player1.getPossition();
    }
    
    public void setPlayerPos(Player player, int pos){
        player.setPossition(pos);
    }
    
    public boolean checkIfLegalConsideringJump(int oldPos, int newPos){
        board boardCopy = new board(this.getBoard());
        int playerPosDif = getOtherPlayerPos() - getCurrentPlayerPos();
        int otherPos = getOtherPlayerPos();
        
        switch(playerPosDif){
            case (1):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                if(boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() + 1)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() + 1)); 
                break;
                
            case (-1):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                if(boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() - 1)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() - 1)); 
                break;
                
            case (9):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());

                if(boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() + 9)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() + 9)); 
                break;

            case (-9):
                boardCopy.removeEdge(oldPos, getOtherPlayerPos());
                
                if(boardCopy.isAdj((getOtherPlayerPos()), (getOtherPlayerPos() - 9)))
                    boardCopy.addEdge(oldPos, (getOtherPlayerPos() - 9));               
                break;
                
            default:
                System.out.println("regular move");    
        }
        
        return boardCopy.isAdj(oldPos, newPos);
    }
}
