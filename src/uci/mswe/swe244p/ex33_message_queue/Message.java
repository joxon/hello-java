package uci.mswe.swe244p.ex33_message_queue;

public class Message {
    private String msg;

    public Message(String m) {
        msg = m;
    }

    public String get() {
        return msg;
    }
}
