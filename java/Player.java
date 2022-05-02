public class Player {
    private int position;
    private boolean isWhite;
    private int amountFences;

    // constructor
    public Player(int possition, boolean isWhite) {
        this.isWhite = isWhite;
        this.position = possition;
        this.amountFences = 10; //starts game with ten fences, goes down with use
    }

    // copy constructor
    public Player(Player player){
        this.isWhite = player.isWhite;
        this.position = player.position;
        this.amountFences = player.amountFences;
    }

    //returns boolean: true if player is white, false if not
    public boolean getIsPlayerWhite(){
        return this.isWhite;
    }

    //returns int: position of the player on the board (id)
    public int getPossition() {
        return this.position;
    }

    //sets player position on board (id)
    public void setPossition(int position) {
        this.position = position;
    }

    public void useFence(){
        this.amountFences--;
    }

    //returns int: the amount of fences the player has remaining
    public int getAmountFences(){
        return this.amountFences;
    }

    //returns boolean: true if player has fences left, false if not
    public boolean hasFences(){
        return (this.amountFences > 0);
    }
}
