package main;

import apiREST.apiREST_Topic;
import apiREST.apiREST_Message;
import entity.Message;
import subscriber.SubscriberImpl;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import publisher.Publisher;
import subscriber.Subscriber;
import topicmanager.TopicManager;
import topicmanager.TopicManagerStub;
import webSocketService.WebSocketClient;

public class ClientSwing {

  public Map<String, Subscriber> my_subscriptions;
  Publisher publisher;
  String publisherTopic;
  TopicManager topicManager;

  JFrame frame;
  JTextArea topic_list_TextArea;
  public JTextArea messages_TextArea;
  public JTextArea my_subscriptions_TextArea;
  JTextArea publisher_TextArea;
  JTextField argument_TextField;

  public ClientSwing(TopicManager topicManager) {
    this.topicManager = topicManager;
    publisher = null;
    my_subscriptions = new HashMap<String, Subscriber>();
  }

  public void createAndShowGUI() {

    String login = ((TopicManagerStub) topicManager).user.getLogin();
    frame = new JFrame("Publisher/Subscriber demo, user : " + login);
    frame.setSize(300, 300);
    frame.addWindowListener(new CloseWindowHandler());

    topic_list_TextArea = new JTextArea(5, 10);
    messages_TextArea = new JTextArea(10, 20);
    my_subscriptions_TextArea = new JTextArea(5, 10);
    publisher_TextArea = new JTextArea(1, 10);
    argument_TextField = new JTextField(20);

    JButton show_topics_button = new JButton("show Topics");
    JButton new_publisher_button = new JButton("new Publisher");
    JButton new_subscriber_button = new JButton("new Subscriber");
    JButton to_unsubscribe_button = new JButton("to unsubscribe");
    JButton to_post_an_event_button = new JButton("post an event");
    JButton to_close_the_app = new JButton("close app.");

    show_topics_button.addActionListener(new showTopicsHandler());
    new_publisher_button.addActionListener(new newPublisherHandler());
    new_subscriber_button.addActionListener(new newSubscriberHandler());
    to_unsubscribe_button.addActionListener(new UnsubscribeHandler());
    to_post_an_event_button.addActionListener(new postEventHandler());
    to_close_the_app.addActionListener(new CloseAppHandler());

    JPanel buttonsPannel = new JPanel(new FlowLayout());
    buttonsPannel.add(show_topics_button);
    buttonsPannel.add(new_publisher_button);
    buttonsPannel.add(new_subscriber_button);
    buttonsPannel.add(to_unsubscribe_button);
    buttonsPannel.add(to_post_an_event_button);
    buttonsPannel.add(to_close_the_app);

    JPanel argumentP = new JPanel(new FlowLayout());
    argumentP.add(new JLabel("Write content to set a new_publisher / new_subscriber / unsubscribe / post_event:"));
    argumentP.add(argument_TextField);

    JPanel topicsP = new JPanel();
    topicsP.setLayout(new BoxLayout(topicsP, BoxLayout.PAGE_AXIS));
    topicsP.add(new JLabel("Topics:"));
    topicsP.add(topic_list_TextArea);
    topicsP.add(new JScrollPane(topic_list_TextArea));
    topicsP.add(new JLabel("My Subscriptions:"));
    topicsP.add(my_subscriptions_TextArea);
    topicsP.add(new JScrollPane(my_subscriptions_TextArea));
    topicsP.add(new JLabel("I'm Publisher of topic:"));
    topicsP.add(publisher_TextArea);
    topicsP.add(new JScrollPane(publisher_TextArea));

    JPanel messagesPanel = new JPanel();
    messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.PAGE_AXIS));
    messagesPanel.add(new JLabel("Messages:"));
    messagesPanel.add(messages_TextArea);
    messagesPanel.add(new JScrollPane(messages_TextArea));

    Container mainPanel = frame.getContentPane();
    mainPanel.add(buttonsPannel, BorderLayout.PAGE_START);
    mainPanel.add(messagesPanel, BorderLayout.CENTER);
    mainPanel.add(argumentP, BorderLayout.PAGE_END);
    mainPanel.add(topicsP, BorderLayout.LINE_START);

    //this is where you restore the user profile
    clientSetup();

    frame.pack();
    frame.setVisible(true);
  }

  private void clientSetup() {
    
    //Restore publisher of topics
    this.publisher = topicManager.publisherOf();
    if(this.publisher != null){
        this.publisherTopic = this.publisher.topicName();
        publisher_TextArea.append(this.publisherTopic + "\n");
    }

//Retreive list of topics and update self topicManager
    topic_list_TextArea.setText("");
    for (String it : topicManager.topics()){
        topic_list_TextArea.append(it);
        topic_list_TextArea.append("\n");
    }
    System.out.print("welrugasl" + topicManager.mySubscriptions().isEmpty());
    //Restore subcriptions
    for (entity.Subscriber it_subscriber: topicManager.mySubscriptions()){
        //We retreive the information from the DB and update the client
        Subscriber new_subcriber = new SubscriberImpl(ClientSwing.this);
        topicManager.subscribe(it_subscriber.getTopic().getName(), new_subcriber);
        String current_topic = it_subscriber.getTopic().getName();
        my_subscriptions_TextArea.append(current_topic + "\n");
        my_subscriptions.put(current_topic, new_subcriber);
        //¿Also update messages?
        
        Collection<entity.Message> old_messages = apiREST_Message.messagesFrom(it_subscriber.getTopic());        
        if(old_messages != null){
            for(entity.Message it_mess: old_messages){
                messages_TextArea.append(it_mess.getTopic().getName() + ": " + it_mess.getContent() + "\n");                    
            }
        }
    }
  }

  class showTopicsHandler implements ActionListener {
      public void actionPerformed(ActionEvent e) {

        topic_list_TextArea.setText("");
        for (String s : topicManager.topics()) {
            topic_list_TextArea.append(s);
            topic_list_TextArea.append("\n");
        }
      }
  }


  class newPublisherHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if(argument_TextField.getText().length() == 0){
                messages_TextArea.append("CONSOLE: Specify a content" +  "\n");
                return;
            }
            else
            {
                publisherTopic = argument_TextField.getText();
                topicManager.removePublisherFromTopic(publisherTopic);
                publisher = topicManager.addPublisherToTopic(publisherTopic);
                publisher_TextArea.setText("");
                publisher_TextArea.append(publisherTopic);
                publisher_TextArea.append("\n");
                messages_TextArea.append("- You are publisher of " + publisherTopic + " topic." + "\n");
            }
        }
  }


  class newSubscriberHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            String subscriberTopic = argument_TextField.getText();
            Subscriber sub = new SubscriberImpl(ClientSwing.this);
            if (!my_subscriptions.containsKey(subscriberTopic) && topicManager.isTopic(subscriberTopic)) {
                topicManager.subscribe(subscriberTopic, sub);
                my_subscriptions.put(subscriberTopic, sub);
                my_subscriptions_TextArea.append(subscriberTopic);
                my_subscriptions_TextArea.append("\n");
                messages_TextArea.append("- You have been subscribed to " + subscriberTopic + "." + "\n");
            } else {
                messages_TextArea.append("- You can't be subscribed to " + subscriberTopic + "." + "\n");
            }
            argument_TextField.setText("");
        }
  }

  class UnsubscribeHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            String subscriberTopic = argument_TextField.getText();
            if (topicManager.unsubscribe(subscriberTopic, my_subscriptions.get(subscriberTopic)) && my_subscriptions.containsKey(subscriberTopic)) {
                my_subscriptions.remove(subscriberTopic);
                my_subscriptions_TextArea.setText("");
                for (String s : my_subscriptions.keySet()) {
                    my_subscriptions_TextArea.setText(s);
                    my_subscriptions_TextArea.append("\n");
                }
                messages_TextArea.append("- You have been unsubscribed from " + subscriberTopic + "." + "\n");
            } else {
                messages_TextArea.append("- You can't be unsubscribed from " + subscriberTopic + "." + "\n");
            }
            argument_TextField.setText("");
        }
  }

  class postEventHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if(argument_TextField.getText().length() == 0){
                messages_TextArea.append("CONSOLE: Specify a content" +  "\n");
                return;
            }
            else
            {
                String new_event = argument_TextField.getText();
                publisher.publish(publisherTopic, new_event);
            }
        }
  }

  class CloseAppHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (!my_subscriptions.isEmpty()) {
                for (String s : my_subscriptions.keySet()) {
                    topicManager.unsubscribe(s, my_subscriptions.get(s));
                }
            }
            if(!publisherTopic.isEmpty() && publisherTopic != null){
                topicManager.removePublisherFromTopic(publisherTopic);
            }
            System.exit(0);
        }
  }

  class CloseWindowHandler implements WindowListener{

        public void windowDeactivated(WindowEvent e) {

        }
        public void windowActivated(WindowEvent e) {

        }
        public void windowIconified(WindowEvent e) {

        }
        public void windowDeiconified(WindowEvent e) {

        }
        public void windowClosed(WindowEvent e) {

        }
        public void windowOpened(WindowEvent e) {

        }
        public void windowClosing(WindowEvent e) {
            //...
            System.out.println("app closed");
            System.exit(0);
        }
  }
}