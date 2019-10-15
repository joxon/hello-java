package uci.mswe.swe241p.ex3;

/**
 * Main
 */
public class Main {

  public static void main(String[] args) {
    final int VERTEX_COUNT = 10;

    var adjMat = new AdjacencyMatrix(VERTEX_COUNT);
    adjMat.randomize();
    adjMat.print();
    AdjacencyList.fromAdjacencyMatrix(adjMat).print();
    System.out.println();

    var incMat = new IncidenceMatrix(VERTEX_COUNT);
    incMat.randomize();
    incMat.print();
    AdjacencyList.fromIncidenceMatrix(incMat).print();
    System.out.println();

    var adjList = new AdjacencyList(VERTEX_COUNT);
    adjList.randomize();
    adjList.print();
    IncidenceMatrix.fromAdjacencyList(adjList).print();
  }
}