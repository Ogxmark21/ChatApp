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

public class Message {

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static int totalSent = 0;
    private static final String STORE_FILE = "storedMessages.json";
    private static final Random RANDOM = new Random();

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;

    public Message(String messageID, int messageNumber,
                   String recipient, String text) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = createMessageHash();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        return validateRecipient(recipient);
    }

    public String createMessageHash() {
        String[] words = text.trim().split("\\s+");

        String firstWord = cleanWord(words[0]);
        String lastWord = cleanWord(words[words.length - 1]);
        String firstTwo = messageID.substring(
                0, Math.min(2, messageID.length()));

        return (firstTwo + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";

            case 2:
                return "Message disregarded.";

            case 3:
                try {
                    storeMessage();
                    return "Message successfully stored.";
                } catch (IOException e) {
                    return "Unable to store message: " + e.getMessage();
                }

            default:
                return "Invalid option. Please choose 1, 2 or 3.";
        }
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        StringBuilder output = new StringBuilder();

        for (Message message : sentMessages) {
            output.append(message.details())
                  .append(System.lineSeparator());
        }

        return output.toString();
    }

    public static List<String> returnAllMessageHash() {
        List<String> hashes = new ArrayList<>();

        for (Message message : sentMessages) {
            hashes.add(message.getMessageHash());
        }

        return hashes;
    }

    public static int returnTotalMessages() {
        return totalSent;
    }

    public void storeMessage() throws IOException {
        if (!storedMessages.contains(this)) {
            storedMessages.add(this);
        }

        StringBuilder json = new StringBuilder("[\n");

        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);

            json.append("  {\n")
                .append("    \"messageID\": \"")
                .append(escape(m.messageID)).append("\",\n")
                .append("    \"messageHash\": \"")
                .append(escape(m.messageHash)).append("\",\n")
                .append("    \"recipient\": \"")
                .append(escape(m.recipient)).append("\",\n")
                .append("    \"message\": \"")
                .append(escape(m.text)).append("\"\n")
                .append("  }");

            if (i < storedMessages.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("]\n");

        try (FileWriter writer = new FileWriter(STORE_FILE)) {
            writer.write(json.toString());
        }
    }

    public static String validateMessageLength(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "Message cannot be empty.";
        }

        int length = text.length();

        if (length <= 250) {
            return "Message ready to send.";
        }

        return "Message exceeds 250 characters by "
                + (length - 250) + "; please reduce the size.";
    }

    public static String validateRecipient(String number) {
        if (number != null
                && number.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. "
                + "Please correct the number and try again.";
    }

    public static String generateMessageID() {
        long n = RANDOM.nextLong(10_000_000_000L);
        return String.format("%010d", n);
    }

    public String details() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + text + "\n";
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }

    static void clearAll() {
        sentMessages.clear();
        storedMessages.clear();
        totalSent = 0;
    }

    private static String cleanWord(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
   
  
