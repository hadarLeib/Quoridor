public class Player {
    private int position;
    private boolean isWhite;
    private int amountFences;

    // constructor
    public Player(int possition, boolean isWhite) {
        this.isWhite = isWhite;
        this.position = possition;
        this.amountFences = 10;
    }

    // copy constructor
    public Player(Player player){
        this.isWhite = player.isWhite;
        this.position = player.position;
        this.amountFences = player.amountFences;
    }

    public boolean getIsPlayerWhite(){
        return this.isWhite;
    }

    public int getPossition() {
        return this.position;
    }

    public void setPossition(int position) {
        this.position = position;
    }

    public void useFence(){
        this.amountFences--;
    }

    public int getAmountFences(){
        return this.amountFences;
    }

    public boolean hasFences(){
        return (this.amountFences > 0);
    }
}
