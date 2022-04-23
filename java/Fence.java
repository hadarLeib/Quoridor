public class Fence extends Move{
    private int firstId;
    private int secondId;
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean isHorizontal;

    
    public Fence(int firstId, int secondId, boolean isHorizontal, int a, int b, int c, int d){
        super("f");
        this.firstId = firstId;
        this.secondId = secondId;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
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

    public int getA(){
        return this.a;
    }

    public int getB(){
        return this.b;
    }

    public int getC(){
        return this.c;
    }

    public int getD(){
        return this.d;
    }

    
}
