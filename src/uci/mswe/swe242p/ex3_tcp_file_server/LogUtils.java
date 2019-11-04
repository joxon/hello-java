package uci.mswe.swe242p.ex3_tcp_file_server;

import java.time.LocalDateTime;

/**
 * LogUtils
 */
public class LogUtils {

  public static String dateTime() {
    // yyyy-mm-ddTHH:mm:ss
    return LocalDateTime.now().toString().split("[.]")[0];
  }

  public static void logi(String s) {
    System.out.println(dateTime() + " INFO: " + s);
  }

  public static void loge(String s) {
    System.err.println(dateTime() + " ERROR: " + s);
  }

}
