import java.util.Scanner;

public class board {
    public static int vertices = 81;
    public static boolean adjMatrix[][] = new boolean [vertices][vertices];


    //default constructor
    board(){
    }
    
    //copy constructor
    board(board board){
        this.vertices = board.vertices;
        
        for(int i = 0; i < this.vertices; i++){
            for(int j = 0; j < this.vertices; j++){
                this.adjMatrix[i][j] = board.adjMatrix[i][j];
            }
        }
    }


    static void addEdge(int source, int destination){
        adjMatrix[source][destination] = true;
        adjMatrix[destination][source] = true;
    }

    static void removeEdge(int source, int destination){ //add fence
        adjMatrix[source][destination] = false;
        adjMatrix[destination][source] = false;
        //next fence
    }

    static void initAdjBoard(){
        int i = 0, j = 0;
        for (i = 0; i < vertices; i++)
        {
            for(j = 0; j < vertices; j++)
            {
                adjMatrix[i][j] = false;
            }
        }
        for(i = 0; i < vertices-1; i++){
                adjMatrix[i][i+1] = true;
                adjMatrix[i+1][i] = true;
                if (i<72){
                    adjMatrix[i][i+9] = true;
                    adjMatrix[i+9][i] = true;
                }

        }
    }
    
    static boolean isReachable(int playerPos, Player currentPlayer){
        int[] parents = new int[board.vertices];
        // Initialize with -1 to know when to stop if we haven't reached playerPos
        for (int i = 0; i < board.vertices; i++) {
            parents[i] = -1;
        }
        
        boolean[] visited = new boolean[board.vertices];
        
        dfs(playerPos, visited, parents);
        
        // why 72 and 0?
        int endPlayerPos = (currentPlayer.playerNo == true) ? 72 : 0;
        
        // why 9?
        for (int i = 0; i < 9; i++){
            int curPlayerPos = endPlayerPos + i;
            
            while (parents[curPlayerPos] != -1) {
                if (parents[curPlayerPos] == playerPos) {
                    return true;
                }
                
                curPlayerPos = parents[curPlayerPos];
            }
        }
        
        return false;
    }
    
    static void dfs(int start, boolean[] visited, int[] parents)
    {
        // Set current node as visited
        visited[start] = true;
 
        // For every node of the graph
        for (int i = 0; i < adjMatrix[start].length; i++) {
 
            // If some node is adjacent to the current node
            // and it has not already been visited
            if (adjMatrix[start][i] && (!visited[i])) {
                parents[i] = start;
                dfs(i, visited, parents);
            }
        }
    }

    static void printAdjGraph(){
        for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                if(adjMatrix[i][j]){System.out.print("1 ");}
                else{System.out.print("0 ");}
            }
            System.out.println();
        }
    }

    static Boolean isAdj(int sourcePos, int destPos){
        return (adjMatrix[sourcePos][destPos]);
    }
}
