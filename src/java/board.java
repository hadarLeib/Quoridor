import java.util.Scanner;

public class board {
    int vertices;
    Boolean adjMatrix[][];



    board(int vertices){
        this.vertices = vertices;
        adjMatrix = new Boolean [vertices][vertices];
    }

    void addEdge(int source, int destination){
        adjMatrix[source][destination] = true;
        adjMatrix[destination][source] = true;
    }

    void removeEdge(int source, int destination){ //add fence
        adjMatrix[source][destination] = false;
        adjMatrix[destination][source] = false;
        //next fence
    }

    void initAdjBoard(){
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

    void printAdjGraph(){
        for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                if(adjMatrix[i][j]){System.out.print("1 ");}
                else{System.out.print("0 ");}
            }
            System.out.println();
        }
    }

    Boolean isAdj(int sourcePos, int destPos){
        return (adjMatrix[sourcePos][destPos]);
    }

    public static void main(String[] args){
        //Scanner s = new Scanner(System.in);
        int v = 81;
        board b = new board(v);
        b.initAdjBoard();
        b.printAdjGraph();
    }

}
