/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author משתמש
 */
public class Player {
    int possition;
    boolean playerNo;
    
    Player(int possition, boolean playerNo){
        this.playerNo = playerNo;
        this.possition = possition;
    }
    
    int getPossition(){
        return this.possition;
    }
    
    void setPossition(int possition){
        this.possition = possition;
    }
    
}
