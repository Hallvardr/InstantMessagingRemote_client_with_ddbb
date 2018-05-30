package topicmanager;

import apiREST.apiREST_Message;
import apiREST.apiREST_Publisher;
import apiREST.apiREST_Subscriber;
import apiREST.apiREST_Topic;
import entity.Message;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import publisher.Publisher;
import publisher.PublisherStub;
import subscriber.Subscriber;
import webSocketService.WebSocketClient;

public class TopicManagerStub implements TopicManager {

  public entity.User user;

  public TopicManagerStub(entity.User user) {
    WebSocketClient.newInstance();
    this.user = user;
  }

  public void close() {
    WebSocketClient.close();
  }

  public Publisher addPublisherToTopic(String topic) {
      entity.Topic new_topic = new entity.Topic();
      entity.Publisher newDB_publisher = new entity.Publisher();
      
      new_topic.setName(topic);
      newDB_publisher.setTopic(new_topic);
      newDB_publisher.setUser(this.user);
      
      apiREST_Publisher.create_and_return_Publisher(newDB_publisher);
      
      return new PublisherStub(new_topic);

  }

    public int removePublisherFromTopic(String topic) {
      //asiming 0 as false and 1 as true:
        entity.Publisher newDB_publisher = apiREST_Publisher.PublisherOf(this.user);
        if (newDB_publisher.getTopic().getName().equals(topic)){
            apiREST_Publisher.deletePublisher(newDB_publisher);
            return 1;
        }
        return 0;

  }

  public boolean isTopic(String topic_name) {

    return this.topics().contains(topic_name);

  }

  public Set<String> topics() {
    
    Set<String> topic_names = new HashSet<String>();
    for(entity.Topic it_topic : apiREST_Topic.allTopics()){
        topic_names.add(it_topic.getName());
    }
    return topic_names;
  }

  public boolean subscribe(String topic, Subscriber subscriber) {
    entity.Subscriber newDB_subscriber = new entity.Subscriber();
    newDB_subscriber.setTopic(apiREST_Topic.retrieveTopicByName(topic));
    newDB_subscriber.setUser(user);
    WebSocketClient.addSubscriber(topic, subscriber);
    apiREST_Subscriber.create_and_return_Subscriber(newDB_subscriber);
    return true;

  }

    public boolean unsubscribe(String topic, Subscriber subscriber) {
        List<entity.Subscriber> subscriber_list = apiREST_Subscriber.mySubscriptions(user);
        for (entity.Subscriber it_subscriber : subscriber_list){
            if(it_subscriber.getTopic().getName().equals(topic)){
                apiREST_Subscriber.deleteSubscriber(it_subscriber);
                return true;
            }
        }
        return false;
    }
  
  public Publisher publisherOf() {
      Publisher new_publisher = null;
    if(apiREST_Publisher.PublisherOf(this.user) != null){
         new_publisher = new PublisherStub(apiREST_Publisher.PublisherOf(this.user).getTopic());
         apiREST_Publisher.PublisherOf(this.user).setUser(this.user);
    }
    return new_publisher;  
  }

  public List<entity.Subscriber> mySubscriptions() {
      
    return apiREST_Subscriber.mySubscriptions(this.user);
    
  }

  public List<Message> messagesFrom(entity.Topic topic) {
      
    return apiREST_Message.messagesFrom(topic);
  }

}
