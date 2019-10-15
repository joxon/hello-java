package uci.mswe.swe241p.ex3;

import java.util.Random;

/**
 * UndirectedGraph
 */
abstract class UndirectedGraph {
  int vertexCount;

  int edgeCount;

  abstract boolean connect(int a, int b);

  abstract boolean isConnected(int a, int b);

  abstract boolean disconnect(int a, int b);

  abstract void print();

  boolean isValid(int a) {
    return 0 <= a && a < vertexCount;
  }

  boolean isValid(int a, int b) {
    return isValid(a) && isValid(b);
  }

  void randomize() {
    var rand = new Random();
    var edgeCount = vertexCount / 2;
    for (int i = 0; i < edgeCount; ++i) {
      int a = rand.nextInt(vertexCount);
      int b = rand.nextInt(vertexCount);
      while (a == b) {
        b = rand.nextInt(vertexCount);
      }
      this.connect(a, b);
    }
  }
}