package edu.uci.swe241p.ex1_set_implementations;

/**
 * vscode-java's "Classpath is incomplete" warning: You need to open a folder
 * containing a pom.xml, build.gradle or at least default eclipse setting files,
 * so that a complete classpath and project hierarchy can be set.
 */

class RunSets {
  public static void main(String[] args) {
    final var runCount = 10;
    for (var i = 0; i < runCount; ++i) {
      new LinkedListSet().run();
      new BinaryTreeSet().run();
      new HashTableSet().run();
    }
  }
}