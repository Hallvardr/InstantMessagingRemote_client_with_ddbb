package publisher;

import apiREST.apiREST_Message;
import entity.Message;
import entity.Topic;

public class PublisherStub implements Publisher {

  Topic topic;

  public PublisherStub(Topic topic) {
    this.topic = topic;
  }

  public void publish(String topic, String event) {
      if(this.topic.getName().equals(topic)){
        Message db_message = new Message();
        db_message.setTopic(this.topic);
        db_message.setContent(event);
        apiREST_Message.createMessage(db_message);
      }
      
  }

  public String topicName() {
    return topic.getName();
  }

}
