public class Hello {
  public static void main(String[] args) throws NullPointerException {
    try {
      System.out.println("hello");
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
  }
}
