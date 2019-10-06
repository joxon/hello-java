package uci.mswe.swe241p.ex1;

/**
 * BinaryTreeNode
 */
public class BinaryTreeNode {
  private String word;
  private BinaryTreeNode left;
  private BinaryTreeNode right;

  public BinaryTreeNode(String word) {
    this.word = word;
    this.left = null;
    this.right = null;
  }
}