public class Board {
    private int vertices;
    private boolean adjMatrix[][];

    // Default constructor
    public Board() {
        this.vertices = 81;
        this.adjMatrix = new boolean[vertices][vertices];
    }

    // Copy constructor
    public Board(Board board) {
        this.vertices = board.vertices;
        this.adjMatrix = new boolean[vertices][vertices];

        for (int i = 0; i < this.vertices; i++) {
            for (int j = 0; j < this.vertices; j++) {
                this.adjMatrix[i][j] = board.adjMatrix[i][j];
            }
        }
    }

    //returns adjMatrix
    public boolean[][] getAdjacentMatrix(){
        return this.adjMatrix;
    }

    //receives two points and adds an edge between them
    //both directions
    public void addEdge(int source, int destination) {
        this.adjMatrix[source][destination] = true;
        this.adjMatrix[destination][source] = true;
    }

    //receives two points and removes the edge between them
    //both directions
    public void removeEdge(int source, int destination) {
        this.adjMatrix[source][destination] = false;
        this.adjMatrix[destination][source] = false;
    }

    //is called in the begining of the game
    //adds edges where there needs to be in initialization
    public void initAdjBoard() {
        int i = 0, j = 0;
        for (i = 0; i < this.vertices; i++) {
            for (j = 0; j < this.vertices; j++) {
                this.adjMatrix[i][j] = false;
            }
        }
        for (i = 0; i < this.vertices - 1; i++) {
            this.adjMatrix[i][i + 1] = true;
            this.adjMatrix[i + 1][i] = true;
            if (i < 72) {
                this.adjMatrix[i][i + 9] = true;
                this.adjMatrix[i + 9][i] = true;
            }

        }
    }

    //receives a position on the board ((int) playerPos)
    //receives the Player ((Player) currentPlayer)
    //checks if it is possible for the player to get to the end (it's winning row)
    //returns boolean: true if this is possible, false if not
    public boolean playerCanGetToEnd(int playerPos, Player currentPlayer) {
        boolean[] visited = new boolean[this.vertices];
        int[] parents = new int[this.vertices];
        int curPlayerPos;

        // Initialize with -1 to know when to stop if we haven't reached playerPos
        for (int i = 0; i < this.vertices; i++) {
            parents[i] = -1;
        }

        dfs(playerPos, visited, parents);


        // 72 is starting point for range 72 -> 80. possible end points for
        // player1(playerNo = true)
        // 0 for range 0 -> 8. possible end points for player2(playerNo = false)
        int endPlayerPos = (currentPlayer.getIsPlayerWhite() == true) ? 72 : 0;

        // 9 possible endpoints.
        for (int i = 0; i < 9; i++) {
            curPlayerPos = endPlayerPos + i;

            while (parents[curPlayerPos] != -1) {
                if (parents[curPlayerPos] == playerPos) {
                    return true;
                }

                curPlayerPos = parents[curPlayerPos];
            }
        }

        return false;
    }

    //receives starting position ((int) start)
    //receives (boolean) array of visited 
    //receives (int) array of parents
    //function will fill parent array with the help of the visited array
    public void dfs(int start, boolean[] visited, int[] parents) {
        // Set current node as visited
        visited[start] = true;

        // For every node of the graph
        for (int i = 0; i < this.adjMatrix[start].length; i++) {

            // If some node is adjacent to the current node
            // and it has not already been visited
            if (this.adjMatrix[start][i] && (!visited[i])) {
                parents[i] = start;
                dfs(i, visited, parents);
            }
        }
    }

    //prints graph - helper to check graph state, not relevant for game
    public void printAdjGraph() {
        for (int i = 0; i < this.vertices; i++) {
            for (int j = 0; j < this.vertices; j++) {
                if (this.adjMatrix[i][j]) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }

            System.out.println();
        }
    }

    //receives to (int) points in graph
    //returns boolean: true if the two points are adjacent and false if not
    public boolean isAdj(int sourcePos, int destPos) {
        return (this.adjMatrix[sourcePos][destPos] || this.adjMatrix[destPos][sourcePos]);
    }
}
