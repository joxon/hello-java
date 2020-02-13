package edu.uci.swe242p.ex3_tcp_file_server;

/**
 * Although the enum type has special behavior in Java, we can add constructors, fields, and methods
 * as we do with other classes. Because of this, we can enhance our enum to include the values we
 * need.
 */
public enum Messages {
  // So the response cannot contain "EOR"?
  // ? improve the terminator
  END_OF_RESPONSE("EOR"), OK("OK"), ERROR("ERROR");

  private final String message;

  Messages(String msg) {
    this.message = msg;
  }

  @Override
  public String toString() {
    return message;
  }
}
