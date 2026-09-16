/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 *
 * @author Student
 */
public class ChatApp {

    private static class RegistrationLogin {
        private static Map<String, String> users = new HashMap<>();

        public RegistrationLogin() {
            
        }

        private void registerUser(Scanner input) {
            System.out.println("Enter your username:");
            String username = input.nextLine();
            
            if(users.containsKey(username)){
                System.out.println("The username is already taken");
                return;
            }
            
            System.out.println("Enter your password:");
            String password = input.nextLine();
            
            users.put(username, password);
            System.out.println("Registration successful!");
        }

        private void LoginUser(Scanner input) {
            System.out.println("Enter your username:");
            String username = input.nextLine();
                
            System.out.println("Enter your password");
            String password = input.nextLine();
            
            if(users.containsKey(username) && users.get(username).equals(password)){
                System.out.println("Login successful!! Welcome, " + username + ".");
            }else{
                System.out.println("Invalid username or password");
            }
        }
    }
            
        
     
}
