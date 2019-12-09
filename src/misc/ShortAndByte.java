package misc;

/**
 * ShortAndByte
 */
public class ShortAndByte {

  public static void main(String[] args) {
    // int ia = 123451234512345; // The literal 123451234512345 of type int is out of range

    short sa = 32767; // short: -32,768 - 32,767
    short sb = 1;
    // short sc = sa + sb; // Type mismatch: cannot convert from int to short
    short sc = (short) (sa + sb);
    System.out.println(sc);

    byte ba = 127; // byte: -128 - 127
    byte bb = 1;
    // byte bc = ba + bb; // Type mismatch: cannot convert from int to byte
    byte bc = (byte) (ba + bb);
    System.out.println(bc);
  }
}
