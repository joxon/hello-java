package uci.mswe.swe241p.ex4;

/**
 * Main
 */
public class Main {

  public static void main(String[] args) {
    var mat = new AdjacencyMatrixWithSearch();
    mat.init();
    mat.print();
    mat.depthFirstSearch(0);
    mat.breadthFirstSearch(0);
  }
}