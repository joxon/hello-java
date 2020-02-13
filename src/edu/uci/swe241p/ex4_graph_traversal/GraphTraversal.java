package edu.uci.swe241p.ex4_graph_traversal;

public class GraphTraversal {

  public static void main(String[] args) {
    var mat = new AdjacencyMatrixWithSearch();
    mat.init();
    mat.print();
    mat.depthFirstSearch(0);
    mat.breadthFirstSearch(0);
  }
}