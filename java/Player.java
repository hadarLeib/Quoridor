public class Player {
    int possition;
    boolean playerNo;

    Player(int possition, boolean playerNo) {
        this.playerNo = playerNo;
        this.possition = possition;
    }

    int getPossition() {
        return this.possition;
    }

    void setPossition(int possition) {
        this.possition = possition;
    }
}
