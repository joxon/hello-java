package edu.uci.swe241p.ex4_graph_traversal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import edu.uci.swe241p.ex3_graph_conversion.AdjacencyMatrix;

/**
 * AdjacencyMatrixWithSearch
 */
public class AdjacencyMatrixWithSearch extends AdjacencyMatrix {

  AdjacencyMatrixWithSearch() {
    super(0);
  }

  AdjacencyMatrixWithSearch(int v) {
    super(v);
  }

  void init() {
    /**
     * A B C D: 0 1 2 3
     *
     * E F G H: 4 5 6 7
     *
     * I J K L: 8 9 10 11
     *
     * M N O P: 12 13 14 15
     */
    this.matrix = new int[16][16];
    this.vertexCount = 16;
    for (int i = 0; i < 15; i++) {
      if (i % 4 != 3 && i < 11) {
        connect(i, i + 1);
        connect(i, i + 4);
      } else if (i % 4 == 3 && i < 12) {
        connect(i, i + 4);
      } else if (11 < i && i < 15) {
        connect(i, i + 1);
      }
    }
  }

  void breadthFirstSearch(int startIndex) {
    if (!isValid(startIndex)) {
      return;
    }

    System.out.println("breadthFirstSearch");
    final var A = (int) 'A';

    var visited = new boolean[vertexCount];
    Queue<Integer> queue = new LinkedList<Integer>();
    queue.add(startIndex);

    while (!queue.isEmpty()) {
      var current = queue.poll();

      if (!visited[current]) {
        System.out.print((char) (A + current) + " ");
        visited[current] = true;
      }

      var neighbours = matrix[current];
      // maintaining alphabetical order
      for (var i = 0; i < vertexCount; ++i) {
        if (neighbours[i] == 1 && !visited[i]) {
          queue.add(i);
        }
      }
    }

    System.out.println();
  }

  void depthFirstSearch(int startIndex) {
    if (!isValid(startIndex)) {
      return;
    }

    System.out.println("depthFirstSearch");
    final var A = (int) 'A';

    var visited = new boolean[vertexCount];
    var stack = new Stack<Integer>();
    stack.push(startIndex);

    while (!stack.isEmpty()) {
      var current = stack.pop();

      if (!visited[current]) {
        System.out.print((char) (A + current) + " ");
        visited[current] = true;
      }

      var neighbours = matrix[current];
      // maintaining alphabetical order
      for (var i = vertexCount - 1; i >= 0; --i) {
        if (neighbours[i] == 1 && !visited[i]) {
          stack.push(i);
        }
      }
    }

    System.out.println();
  }
}