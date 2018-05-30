/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import apiREST.apiREST_User;
import entity.User;
import topicmanager.TopicManagerStub;
import util.MyLogin;

/**
 *
 * @author juanluis
 */
public class Client_Peter {

  public static void main(String[] args) {
    
    //execute one client at a time to open separate windows on Netbeans,
    //and also because otherwise all clients share the same WebSocket connection.

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        
        //the first time you have to create the user:        
//        User user_peter = new entity.User();
//        user_peter.setLogin("peter");
//        user_peter.setPassword("1234");
//        user_peter = apiREST_User.create_and_return_User(user_peter);
        
        //now you only have to log into the system:
        MyLogin login = new MyLogin();
        login.login = "peter";
        login.password = "1234";
        User user_peter = apiREST_User.loginUser(login);
        ClientSwing client = new ClientSwing(new TopicManagerStub(user_peter));
        client.createAndShowGUI();
      }
    });
    
  }
}
