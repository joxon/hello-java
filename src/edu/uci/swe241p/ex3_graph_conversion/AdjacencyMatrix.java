package edu.uci.swe241p.ex3_graph_conversion;

/**
 * AdjacencyMatrix
 */
public class AdjacencyMatrix extends UndirectedGraph {
  protected int matrix[][];

  public AdjacencyMatrix(int v) {
    this.vertexCount = v;
    this.matrix = new int[v][v];
  }

  @Override
  public boolean connect(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    this.matrix[a][b] = 1;
    this.matrix[b][a] = 1;
    ++this.edgeCount;
    return true;
  }

  @Override
  public boolean isConnected(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    return this.matrix[a][b] != 0;
  }

  @Override
  public boolean disconnect(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    this.matrix[a][b] = 0;
    this.matrix[b][a] = 0;
    --this.edgeCount;
    return true;
  }

  @Override
  public void print() {
    System.out.println(this.getClass().getSimpleName());
    var i = 0;
    for (var row : matrix) {
      System.out.print("[" + (i++) + "]: ");
      for (var col : row) {
        System.out.print(col + " ");
      }
      System.out.println();
    }
  }

}