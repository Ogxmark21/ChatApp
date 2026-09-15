/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import java.util.Scanner;


/**
 *
 * @author Student
 */
public class RegAndLogFeature {
   //Fields to store the registered account when created
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellNumber;
    private boolean accountCreated = false;
    
    //checks username 
    public boolean checkUserName(String username){
        if(username.length() >=5){
            return true;
        }else{
            return false;
        }
    }
    
    //checks password 
    public boolean checkPasswordComplexity(String password){
        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;
        
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            
            if(Character.isUpperCase(c)){
                hasUpperCase = true;
            }else if(Character.isDigit(c)){
                hasNumber = true;
            }else if(Character.isLetterOrDigit(c)){
                hasSpecialChar = true;
            }
        }
       
        if(password.length()>8 && hasUpperCase && hasNumber && hasSpecialChar){
            return true;
        }else{
            return false;
        }
    }
    
    //checks the South african cell number
    public boolean checkCellPhoneNumber(String cellNumber){
        if(cellNumber.length() != 10){
        return false;
        }
        if(!cellNumber.startsWith("0")){
            return false;
        }
        
        for(int i = 0; i < cellNumber.length(); i++){
            if(!Character.isDigit(cellNumber.charAt(i))){
                return false;
            }
        }
        
        return true;
    }

   //registration method
    public boolean registerUser(Scanner input){
        System.out.println("\n====Account Registartion====");
        System.out.println("Enter a username");
        String username = input.nextLine();
        
        System.out.println("Enter a password");
        String password = input.nextLine();
        
        System.out.println("Enter your South African cell phone number");
        String cellNumber = input.nextLine();
        
        boolean usernameValid = checkUserName(username);
        boolean passwordValid = checkPasswordComplexity(password);
        boolean cellNumberValid = checkCellPhoneNumber(cellNumber);
        
        //username feedback
        if(usernameValid){
            System.out.println("Username successfully captured");
        }else{
            System.out.println("Username not formatted correctly; please ensure "
                    + "that the username is atleast five character long");
        }
        //password feedback
        if(passwordValid){
            System.out.println("Password successfully captured");
        }else{
            System.out.println("Password not formatted correctly; please ensure that "
                    + "the password contains atleast eight characters, a capital letter, "
                    + "a number and a special character");
        }
        
        //cell phone number feedback
        if(cellNumberValid){
            System.out.println("Cellphone number successfully captured");
        }else{
            System.out.println("Cellphone number not formatted correctly; please ensure that "
                    + "your cellphone number contains an international code or 10 digits ");
        }
         if(usernameValid && passwordValid && cellNumberValid){
            this.registeredUsername = username;
            this.registeredPassword = password;
            this.registeredCellNumber = cellNumber;
            this.accountCreated = true;
            
             System.out.println("\nUser " + username + " Successfully registered");
             return true;
        }else {
             System.out.println("\nRegistration failed. Please revisit the messages above and try again.");
             return false;
         }
            
    }
    public void loginUser(Scanner input){
        System.out.println("\n==== User Login ====");
        
        if(!accountCreated){
            System.out.println("No account has been registered as of yet. Please register first");
            return;
        }
        System.out.println("Enter your username:");
        String username = input.nextLine();
        
        System.out.println("Enter your password");
        String password = input.nextLine();
        
        if(username.equals(registeredUsername) && password.equals(registeredPassword)){
            System.out.println("Welcome " + username + " it is nice to see you again.");
        }else{
            System.out.println("Username or password is incorrect, please try again");
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        RegistrationLogin app = new RegistrationLogin();
        
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
                app.LoginUser(input);
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

        public RegistrationLogin() {
        }

        private void registerUser(Scanner input) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void LoginUser(Scanner input) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    
}

