package uci.mswe.swe241p.ex3;

/**
 * IncidenceMatrix
 */
public class IncidenceMatrix extends UndirectedGraph {
  int matrix[][];

  IncidenceMatrix(int v) {
    this.vertexCount = v;
    this.edgeCount = 0;
    this.matrix = new int[v][0];
  }

  IncidenceMatrix(AdjacencyList list) {
    var v = list.vertexCount;
    var e = list.edgeCount;

    this.vertexCount = v;
    this.edgeCount = 0;
    this.matrix = new int[v][e];

    var lists = list.lists;
    for (var i = 0; i < v && this.edgeCount < e; ++i) {
      var neighbours = lists.get(i);
      var neighbourCount = neighbours.size();
      for (var j = 0; j < neighbourCount && this.edgeCount < e; ++j) {
        this.connect(i, neighbours.get(j));
      }
    }
  }

  @Override
  public boolean connect(int a, int b) {
    if (!isValid(a, b) || isConnected(a, b)) {
      return false;
    }

    var oldMatrix = this.matrix;
    var v = this.vertexCount;
    var e = this.edgeCount;
    var newMatrix = new int[v][e + 1];

    for (var row = 0; row < v; ++row) {
      for (var col = 0; col < e; ++col) {
        newMatrix[row][col] = oldMatrix[row][col];
      }
    }

    for (var row = 0; row < v; ++row) {
      newMatrix[row][e] = row == a || row == b ? 1 : 0;
    }

    this.matrix = newMatrix;
    ++this.edgeCount;
    return true;
  }

  @Override
  public boolean isConnected(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    var vertexA = this.matrix[a];
    var vertexB = this.matrix[b];
    for (var col = 0; col < edgeCount; ++col) {
      if (vertexA[col] != 0 && vertexB[col] != 0) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean disconnect(int a, int b) {
    if (!isValid(a, b) || !isConnected(a, b)) {
      return false;
    }

    var oldMatrix = this.matrix;
    var v = this.vertexCount;
    var oldE = this.edgeCount;
    var newE = oldE - 1;
    var newMatrix = new int[v][newE];

    for (var row = 0; row < v; ++row) {
      var edgeFound = false;
      for (var col = 0; col < newE; ++col) {
        if (!edgeFound && oldMatrix[a][col] != 0 && oldMatrix[b][col] != 0) {
          edgeFound = true;
        }
        if (!edgeFound) {
          newMatrix[row][col] = oldMatrix[row][col];
        } else {
          newMatrix[row][col] = oldMatrix[row][col + 1];
        }
      }
    }

    this.matrix = newMatrix;
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

  static IncidenceMatrix fromAdjacencyList(AdjacencyList list) {
    return new IncidenceMatrix(list);
  }
}