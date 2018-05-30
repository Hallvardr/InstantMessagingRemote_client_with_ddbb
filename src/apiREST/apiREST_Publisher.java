package apiREST;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Publisher;
import entity.User;
import java.io.*;
import java.net.*;

public class apiREST_Publisher {
  public static Publisher create_and_return_Publisher(Publisher publisher) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.publisher/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(publisher);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      gson = new Gson();
      publisher = gson.fromJson(in, Publisher.class);
      return publisher;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static Publisher retrievePublisher(int id) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.publisher/" + id);
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Gson gson = new Gson();
      Publisher publisher = gson.fromJson(in, Publisher.class);
      return publisher;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static void deletePublisher(Publisher publisher) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.publisher/delete");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      
      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(publisher);
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
  public static Publisher PublisherOf(User user) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.publisher/publisher");
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
      Publisher publisher = gson.fromJson(in, Publisher.class);
      return publisher;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
