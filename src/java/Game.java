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
    
    Game(){
        this.board = new board();
        this.board.initAdjBoard();
        this.player1 = new Player(4, true); //white - starts from low to high
        this.player2 = new Player(76, false); //black - starts from high to low
    }
    
    board getBoard(){
        return this.board;
    }
}
