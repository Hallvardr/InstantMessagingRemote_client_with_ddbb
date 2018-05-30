package apiREST;

import com.google.gson.Gson;
import entity.Subscriber;
import entity.User;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class apiREST_Subscriber {
  public static Subscriber create_and_return_Subscriber(Subscriber subscriber) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.subscriber/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(subscriber);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      gson = new Gson();
      subscriber = gson.fromJson(in, Subscriber.class);
      return subscriber;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static Subscriber retrieveSubscriber(int id) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.subscriber/" + id);
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));  
      Gson gson = new Gson();
      Subscriber subscriber = gson.fromJson(in, Subscriber.class);
      return subscriber;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
  public static void deleteSubscriber(Subscriber subscriber) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.subscriber/delete");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      
      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(subscriber);
      System.out.println(json);
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
  public static List<Subscriber> mySubscriptions(User user) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.subscriber/subscriptions");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(user);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      gson = new Gson();
      Subscriber[] subscriberArray = gson.fromJson(in, Subscriber[].class);
      List<Subscriber> subscriptions = Arrays.asList(subscriberArray);
      return subscriptions;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
