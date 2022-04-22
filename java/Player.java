public class Player {
    private int possition;
    private boolean isWhite;
    private int amountFences;

    public Player(int possition, boolean playerNo) {
        this.isWhite = playerNo;
        this.possition = possition;
        this.amountFences = 10;
    }
    public boolean getIsPlayerWhite(){
        return this.isWhite;
    }

    public int getPossition() {
        return this.possition;
    }

    public void setPossition(int possition) {
        this.possition = possition;
    }

    public void useFence(){
        this.amountFences--;
    }

    public int getAmountFences(){
        return this.amountFences;
    }
}
