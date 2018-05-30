package webSocketService;

import apiREST.Cons;
import com.google.gson.Gson;
import entity.Message;
import entity.Topic;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import subscriber.Subscriber;
import util.MySubscription;

@ClientEndpoint
public class WebSocketClient {

  static Map<String, Subscriber> subscriberMap;
  static Session session;

  public static void newInstance() {
    subscriberMap = new HashMap<String, Subscriber>();
    try {
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      session = container.connectToServer(WebSocketClient.class,
        URI.create(Cons.SERVER_WEBSOCKET));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //only one subscriber per topic allowed:
  public static synchronized void addSubscriber(String topic_name, Subscriber subscriber) {
    //...
    if (!subscriberMap.containsKey(topic_name)) {
            subscriberMap.put(topic_name, subscriber);
            MySubscription subscritions = new MySubscription();
            subscritions.setTopic(topic_name);
            subscritions.setType(true);
            Gson gson = new Gson();            
            session.getAsyncRemote().sendText(gson.toJson(subscritions));
        }
        else{
            System.out.print("There is no topic named: " + topic_name);
        }
  }

  public static synchronized void removeSubscriber(String topic_name) {

      if (subscriberMap.containsKey(topic_name)) {
          subscriberMap.remove(topic_name);
          Gson gson = new Gson();
          MySubscription mySubs = new MySubscription();
          mySubs.type = false;
          mySubs.topic = topic_name;

          String json = gson.toJson(mySubs);
          session.getAsyncRemote().sendText(json);
      }
  }

  public static void close() {
    try {
      session.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @OnMessage
  public void onMessage(String message) {

    Gson gson = new Gson();
    Message the_message = gson.fromJson(message, Message.class);
    String topic = the_message.getTopic().getName();

    //message to warn closing a topic:
    if (topic.equals("CLOSED")) {
        Subscriber subscriber = subscriberMap.get(the_message.getContent());
        subscriber.onClose(the_message.getContent(), "PUBLISHER");
    } 
    //ordinary message from topic:
    else {
        Subscriber subscriber = (Subscriber) subscriberMap.get(topic);
        subscriber.onEvent(topic, the_message.getContent());
    }
  }

}
