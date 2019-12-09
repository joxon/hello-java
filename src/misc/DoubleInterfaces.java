package misc;

/**
 * DoubleInterfaces
 */
public class DoubleInterfaces {

  public static void main(String[] args) {
    new Three().print();
  }

  public interface IOne {
    default void print() {
      System.out.println("ONE");
    }
  }

  public interface ITwo {
    default void print() {
      System.out.println("TWO");
    }
  }

  public static class Three implements IOne, ITwo {
    @Override
    public void print() {
      System.out.println(super.toString());
      IOne.super.print();
      ITwo.super.print();
    }
  }

}
