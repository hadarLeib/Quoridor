public class Fence {
    private int firstId;
    private int secondId;
    private boolean isHorizontal;

    public Fence(int firstId, int secondId, boolean isHorizontal){
        this.firstId = firstId;
        this.secondId = secondId;
        this.isHorizontal = isHorizontal;
    }

    public boolean getIsHorizontal(){
        return this.isHorizontal;
    }

    public int getFirstId(){
        return this.firstId;
    }

    public int getSecondId(){
        return this.secondId;
    }

    
}
