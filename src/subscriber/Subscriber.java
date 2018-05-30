package subscriber;

public interface Subscriber {
    void onEvent(String topic, String event);
    void onClose(String topic, String cause);
}
