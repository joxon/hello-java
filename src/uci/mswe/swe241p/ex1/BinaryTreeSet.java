package uci.mswe.swe241p.ex1;

/**
 * BinaryTreeSet
 */
public class BinaryTreeSet extends Set {
  private BinaryTreeNode root;
  private int size;

  public BinaryTreeSet() {
    this.root = null;
    this.size = 0;
  }

  public BinaryTreeSet(String word) {
    this.root = new BinaryTreeNode(word);
    this.size = 1;
  }

  @Override
  public boolean add(String word) {
    if (this.root == null) {
      this.root = new BinaryTreeNode(word);
      ++size;
      return true;
    } else {
      var node = this.root;
      while (node != null) {
        /**
         * String.compareTo(String s): the value 0 if the argument string is equal to
         * this string; a value less than 0 if this string is lexicographically less
         * than the string argument; and a value greater than 0 if this string is
         * lexicographically greater than the string argument.
         *
         * In a BST, key in left children is less than the parent node and key in the
         * right node is greater.
         */
        int comparison = word.compareTo(node.getWord());
        if (comparison < 0) {
          // The word to add is lexicographically less than the node's word
          var left = node.getLeft();
          if (left == null) {
            node.setLeft(new BinaryTreeNode(word));
            ++size;
            return true;
          } else {
            node = node.getLeft();
          }
        } else if (comparison == 0) {
          // The word to add already exists
          return false;
        } else if (comparison > 0) {
          // The word to add is lexicographically greater than the node's word
          var right = node.getRight();
          if (right == null) {
            node.setRight(new BinaryTreeNode(word));
            ++size;
            return true;
          } else {
            node = node.getRight();
          }
        }
      }
    }
    // Should be unreachable
    return false;
  }

  @Override
  public boolean contains(String word) {
    if (this.root == null) {
      return false;
    } else {
      var node = this.root;
      while (node != null) {
        int comparison = word.compareTo(node.getWord());
        if (comparison < 0) {
          // The word to add is lexicographically less than the node's word
          var left = node.getLeft();
          if (left == null) {
            return false;
          } else {
            node = node.getLeft();
          }
        } else if (comparison == 0) {
          // The word is contained
          return true;
        } else if (comparison > 0) {
          // The word to add is lexicographically greater than the node's word
          var right = node.getRight();
          if (right == null) {
            return false;
          } else {
            node = node.getRight();
          }
        }
      }
    }
    // Should be unreachable
    return false;
  }

  @Override
  public int size() {
    return size;
  }

  public static void main(String[] args) {
    var set = new BinaryTreeSet();

    System.out.println(set.add("hello"));
    System.out.println(set.size());

    System.out.println(set.add("world"));
    System.out.println(set.size());

    System.out.println(set.add("你好"));
    System.out.println(set.size());

    System.out.println(set.add("世界"));
    System.out.println(set.size());

    System.out.println(set.add("world"));
    System.out.println(set.size());

    System.out.println(set.contains("hello"));
    System.out.println(set.contains("你好"));
    System.out.println(set.contains("bye"));
  }
}