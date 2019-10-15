package uci.mswe.swe241p.ex3;

import java.util.ArrayList;

/**
 * AdjacencyList
 */
public class AdjacencyList extends UndirectedGraph {
  ArrayList<ArrayList<Integer>> lists;

  AdjacencyList(int v) {
    if (v < 1) {
      throw new IllegalArgumentException();
    }

    this.vertexCount = v;
    this.edgeCount = 0;
    this.lists = new ArrayList<ArrayList<Integer>>(v);
    for (int i = 0; i < v; ++i) {
      lists.add(new ArrayList<Integer>());
    }
  }

  AdjacencyList(AdjacencyMatrix matrix) {
    var v = matrix.vertexCount;

    this.vertexCount = v;
    this.edgeCount = 0;
    this.lists = new ArrayList<ArrayList<Integer>>(v);
    for (int i = 0; i < v; ++i) {
      lists.add(new ArrayList<Integer>());
    }

    var mat = matrix.matrix;
    // matrix should be symmetrical
    for (int row = 0; row < v; ++row) {
      for (int col = 0; col <= row; ++col) {
        if (mat[row][col] > 0) {
          this.connect(row, col);
        }
      }
    }
  }

  AdjacencyList(IncidenceMatrix matrix) {
    var v = matrix.vertexCount;
    var e = matrix.edgeCount;

    this.vertexCount = v;
    this.edgeCount = e;
    this.lists = new ArrayList<ArrayList<Integer>>(v);
    for (int i = 0; i < v; ++i) {
      lists.add(new ArrayList<Integer>());
    }

    var mat = matrix.matrix;
    for (int col = 0; col < e; ++col) {
      var first = -1;
      for (int row = 0; row < v; ++row) {
        if (mat[row][col] > 0) {
          if (first == -1) {
            first = row;
          } else {
            var second = row;
            this.connect(first, second);
            break;
          }
        }
      }
    }

  }

  @Override
  boolean connect(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    var listA = lists.get(a);
    if (!listA.contains(b)) {
      listA.add(b);
    }

    var listB = lists.get(b);
    if (!listB.contains(a)) {
      listB.add(a);
    }

    ++this.edgeCount;
    return true;
  }

  @Override
  boolean isConnected(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    return lists.get(a).contains(b);
  }

  @Override
  boolean disconnect(int a, int b) {
    if (!isValid(a, b)) {
      return false;
    }

    var listA = lists.get(a);
    listA.remove(Integer.valueOf(b));

    var listB = lists.get(b);
    listB.remove(Integer.valueOf(a));

    --this.edgeCount;
    return true;
  }

  @Override
  void print() {
    System.out.println(this.getClass().getSimpleName());
    var i = 0;
    for (var list : lists) {
      System.out.print("[" + (i++) + "]: ");
      for (var val : list) {
        System.out.print(val + "->");
      }
      System.out.println("null");
    }
  }

  static AdjacencyList fromAdjacencyMatrix(AdjacencyMatrix matrix) {
    return new AdjacencyList(matrix);
  }

  static AdjacencyList fromIncidenceMatrix(IncidenceMatrix matrix) {
    return new AdjacencyList(matrix);
  }
}