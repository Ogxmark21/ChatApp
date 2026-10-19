/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Student
 */
public class Message {
    private static final List <Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static int totalSent = 0;
    private static final String STORE_FILE = "storedMessages.json";
    private static final Random RANDOM =new Random();
    
    //details of the message
    
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;
    
    
    
    public Message(String messageID, int messageNumber, String recipient, String text){
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = messageHash;
        
    }
    
    public boolean checkMessageID(){
        return messageID != null && messageID.length() <=10;
    }
    
    
    
    
    
}
