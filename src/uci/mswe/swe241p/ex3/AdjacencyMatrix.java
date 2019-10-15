package uci.mswe.swe241p.ex3;

/**
 * AdjacencyMatrix
 */
public class AdjacencyMatrix extends UndirectedGraph {
  int matrix[][];

  AdjacencyMatrix(int v) {
    this.vertexCount = v;
    this.matrix = new int[v][v];
  }

  @Override
  boolean connect(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    this.matrix[a][b] = 1;
    this.matrix[b][a] = 1;
    ++this.edgeCount;
    return true;
  }

  @Override
  boolean isConnected(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    return this.matrix[a][b] != 0;
  }

  @Override
  boolean disconnect(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    this.matrix[a][b] = 0;
    this.matrix[b][a] = 0;
    --this.edgeCount;
    return true;
  }

  @Override
  void print() {
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