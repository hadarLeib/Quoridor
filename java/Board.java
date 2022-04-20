public class Board {
    public int vertices;
    public boolean adjMatrix[][];

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

    public void addEdge(int source, int destination) {
        this.adjMatrix[source][destination] = true;
        this.adjMatrix[destination][source] = true;
    }

    public void removeEdge(int source, int destination) {
        this.adjMatrix[source][destination] = false;
        this.adjMatrix[destination][source] = false;
    }

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

    public boolean isValidFence(int playerPos, Player currentPlayer) {
        boolean[] visited = new boolean[this.vertices];
        int[] parents = new int[this.vertices];

        // Initialize with -1 to know when to stop if we haven't reached playerPos
        for (int i = 0; i < this.vertices; i++) {
            parents[i] = -1;
        }

        dfs(playerPos, visited, parents);

        // 72 is starting point for range 72 -> 80. possible end points for
        // player1(playerNo = true)
        // 0 for range 0 -> 8. possible end points for player2(playerNo = false)
        int endPlayerPos = (currentPlayer.playerNo == true) ? 72 : 0;

        // 9 possible endpoints.
        for (int i = 0; i < 9; i++) {
            int curPlayerPos = endPlayerPos + i;

            while (parents[curPlayerPos] != -1) {
                if (parents[curPlayerPos] == playerPos) {
                    return true;
                }

                curPlayerPos = parents[curPlayerPos];
            }
        }

        return false; // send to client
    }

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

    public boolean isAdj(int sourcePos, int destPos) {
        return (this.adjMatrix[sourcePos][destPos] || this.adjMatrix[destPos][sourcePos]);
    }
}
