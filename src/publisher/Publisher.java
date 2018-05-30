package publisher;

public interface Publisher {
    public void publish(String topic, String event);
    
    //I need this when restoring the user profile:
    public String topicName();
}
