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

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        RegAndLogFeature app = new RegAndLogFeature();
        
        boolean running = true;
        while(running){
            System.out.println("\n==== Menu ====");
            System.out.println("1. Register a new account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Choose an option from (1-3)");
            
            String choice = input.nextLine();
            
            if(choice.equals("1")){
                app.registerUser(input);
            }else if(choice.equals("2")){
                app.loginUser(input);
            }else if(choice.equals("3")){
                System.out.println("Goodbye!!");
                running = false;
            }else{
                System.out.println("Invalid option, Please choose 1,2 or 3");
            }
        }
        input.close();
    }

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
