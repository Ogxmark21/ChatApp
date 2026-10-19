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
public class QuickChat {
 
    private static final Scanner in = new Scanner(System.in);
 
    public static void main(String[] args) {
 
        // Only logged-in users may send messages.
        if (!loginSuccessful()) {
            System.out.println("Login failed. Exiting QuickChat.");
            return;
        }
 
        // Welcome message
        System.out.println("Welcome to QuickChat.");
 
        // The user sets how many messages they want to enter
        int maxMessages = readInt("How many messages do you want to enter? ", 1, 100);
        boolean messagesEntered = false;
        int option;
 
        //  Runs until the user chooses 3) Quit
        do {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            option = readInt("Choose an option (1-3): ", 1, 3);
 
            switch (option) {
                case 1:
                    if (messagesEntered) {
                        System.out.println("You have already entered all " + maxMessages + " messages.");
                        break;
                    }
                    // Definite loop: we know exactly how many messages to process
                    for (int i = 0; i < maxMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + maxMessages + " ---");
                        processOneMessage(i);
                    }
                    messagesEntered = true;
                    // Total accumulated and displayed after all messages
                    System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
                    break;
                case 2:
                    System.out.println("\n==== Recently Sent Messages ====");
                    System.out.println(Message.printMessages());
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
            }
        } while (option != 3);
    }
 
    private static void processOneMessage(int index) {
 
        // Recipient: reprompt until valid
        String recipient;
        String result;
        do {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            recipient = in.nextLine().trim();
            result = Message.validateRecipient(recipient);
            System.out.println(result);
        } while (!result.equals("Cell phone number successfully captured."));
 
        // Message text: reprompt until 1-250 characters
        String text;
        do {
            System.out.print("Enter your message (max 250 characters): ");
            text = in.nextLine().trim();
            if (text.isEmpty()) {
                System.out.println("The message cannot be empty.");
                result = "empty";
                continue;
            }
            result = Message.validateMessageLength(text);
            System.out.println(result);
        } while (!result.equals("Message ready to send."));
 
       String messageID = Message.generateMessageID();

        Message msg = new Message(
        messageID,
        index + 1,
        recipient,
        text
        );
 
        // Send / Disregard / Store
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        int choice = readInt("Choose an option (1-3): ", 1, 3);
        System.out.println(msg.sentMessage(choice));
 
        if (choice == 2) {
            readInt("Press 0 to delete the message: ", 0, 0);
            System.out.println("Message deleted.");
        }
 
        
        if (choice == 1) {
            System.out.println("\n" + msg.details());
        }
    }
 
   
    private static int readInt(String prompt, int min, int max) {
        int number = min - 1;
        boolean valid;
        do {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                number = Integer.parseInt(line);
                valid = number >= min && number <= max;
            } catch (NumberFormatException e) {
                valid = false;
            }
            if (!valid) {
                System.out.println("Invalid input. Enter a number between " + min + " and " + max + ".");
            }
        } while (!valid);
        return number;
    }
 
    
    private static boolean loginSuccessful() {
        System.out.print("Username: ");
        String user = in.nextLine().trim();
        System.out.print("Password: ");
        String pass = in.nextLine().trim();
        return !user.isEmpty() && !pass.isEmpty();
    }
    
}
