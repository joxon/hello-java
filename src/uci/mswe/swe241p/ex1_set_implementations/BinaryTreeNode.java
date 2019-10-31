package uci.mswe.swe241p.ex1_set_implementations;

/**
 * BinaryTreeNode
 */
public class BinaryTreeNode {

  private String word;

  private BinaryTreeNode left;

  private BinaryTreeNode right;

  public BinaryTreeNode() {
    this.word = null;
    this.left = null;
    this.right = null;
  }

  public BinaryTreeNode(String word) {
    this.word = word;
    this.left = null;
    this.right = null;
  }

  public BinaryTreeNode(String word, BinaryTreeNode left) {
    this.word = word;
    this.left = left;
    this.right = null;
  }

  public BinaryTreeNode(String word, BinaryTreeNode left, BinaryTreeNode right) {
    this.word = word;
    this.left = left;
    this.right = right;
  }

  /**
   * @param word the word to set
   */
  public void setWord(String word) {
    this.word = word;
  }

  /**
   * @return the word
   */
  public String getWord() {
    return word;
  }

  /**
   * @param left the left child to set
   */
  public void setLeft(BinaryTreeNode left) {
    this.left = left;
  }

  /**
   * @return the left
   */
  public BinaryTreeNode getLeft() {
    return left;
  }

  /**
   * @param right the right child to set
   */
  public void setRight(BinaryTreeNode right) {
    this.right = right;
  }

  /**
   * @return the right
   */
  public BinaryTreeNode getRight() {
    return right;
  }
}