public class Fence extends Move{
    private int firstId;
    private int secondId;
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean isHorizontal;

    //constructor
    //a, b, c, d are the nodes in the graph that will be affected by the fence
    //in placing a fence, a will be disconnected froam b and c from d
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

    public Fence(Fence fence){
        super("f");
        this.firstId = fence.firstId;
        this.secondId = fence.secondId;
        this.a = fence.a;
        this.b = fence.b;
        this.c = fence.c;
        this.d = fence.d;
        this.isHorizontal = fence.isHorizontal;
    }

    //returns boolean: true if fence is horizontal, false if not
    public boolean getIsHorizontal(){
        return this.isHorizontal;
    }

    //returns int: the first id of the fence
    public int getFirstId(){
        return this.firstId;
    }

    //returns int: the second id of the fence
    public int getSecondId(){
        return this.secondId;
    }

    //returns int: A node
    public int getA(){
        return this.a;
    }

    //returns int: B node
    public int getB(){
        return this.b;
    }

    //returns int: C node
    public int getC(){
        return this.c;
    }

    //returns int: D node
    public int getD(){
        return this.d;
    }

    
}
