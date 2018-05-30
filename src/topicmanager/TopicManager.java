package topicmanager;

import entity.User;
import java.util.*;
import publisher.Publisher;
import subscriber.Subscriber;

public interface TopicManager {

  Publisher   addPublisherToTopic(String topic);
  int         removePublisherFromTopic(String topic);
  boolean     isTopic(String topic);
  Set<String> topics();
  boolean     subscribe(String topic, Subscriber subscriber);
  boolean     unsubscribe(String topic, Subscriber subscriber);
  
  //to restore the user profile:
  Publisher   publisherOf();
  List<entity.Subscriber> mySubscriptions();
  List<entity.Message> messagesFrom(entity.Topic topic);
}
