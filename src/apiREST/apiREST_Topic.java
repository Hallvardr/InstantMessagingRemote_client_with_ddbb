package apiREST;

import com.google.gson.Gson;
import entity.Topic;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class apiREST_Topic {
  public static Topic create_and_return_Topic(Topic topic) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(topic);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      gson = new Gson();
      topic = gson.fromJson(in, Topic.class);
      return topic;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static Topic retrieveTopic(int id) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/" + id);
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Gson gson = new Gson();      
      Topic topic = gson.fromJson(in, Topic.class);
      return topic;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
  public static Topic retrieveTopicByName(String name) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/name");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      
      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      Topic topic = new Topic();
      topic.setName(name);
      String json = gson.toJson(topic);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      topic = gson.fromJson(in, Topic.class);
      return topic;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
  public static void updateTopic(int id, Topic topic) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/" + id);
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("PUT");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(topic);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void deleteTopic(int id) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/" + id);
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("DELETE");
      ucon.setDoInput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static List<Topic> allTopics(){
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Gson gson = new Gson();      
      Topic[] topicArray = gson.fromJson(in, Topic[].class);
      List<Topic> topics = Arrays.asList(topicArray);
      return topics;
      
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
