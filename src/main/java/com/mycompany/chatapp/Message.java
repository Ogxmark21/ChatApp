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
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONArray;
import org.json.JSONObject;

public class Message {

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final Random RANDOM = new Random();
    private static int totalSent = 0;

    private static final String STORE_FILE = "storedMessages.json";
    
    // PART 3: ARRAYS 
    private static final ArrayList<String> sentMessageTexts = new ArrayList<>();        // all messages sent
    private static final ArrayList<String> disregardedMessageTexts = new ArrayList<>(); // all messages disregarded
    private static final ArrayList<String> storedMessageTexts = new ArrayList<>();      // read from the JSON file
    private static final ArrayList<String> messageHashArray = new ArrayList<>();        // all message hashes
    private static final ArrayList<String> messageIDArray = new ArrayList<>(); 
    
    private static final ArrayList<String> recipientArray = new ArrayList<>();
    private static final ArrayList<String> messageTextArray = new ArrayList<>();
    private static final ArrayList<String> flagArray = new ArrayList<>();

    private static final String FLAG_SENT = "Sent";
    private static final String FLAG_STORED = "Stored";
    private static final String FLAG_DISREGARD = "Disregard";
    
    private static String sender = "Developer";
    

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;

    // Constructor used by existing tests and QuickChat
    public Message(String messageID, int messageNumber,
                   String recipient, String text) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = createMessageHash();
    }

    // Constructor for MessageTest
    public Message(int messageNumber, String recipient, String text) {
        this(generateMessageID(), messageNumber, recipient, text);
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("\\d{1,10}");
    }

    public String checkRecipientCell() {
        return validateRecipient(recipient);
    }

    public String createMessageHash() {
        String firstTwo = messageID == null
                ? ""
                : messageID.substring(0, Math.min(2, messageID.length()));

        String firstWord = "";
        String lastWord = "";

        if (text != null && !text.trim().isEmpty()) {
            String[] words = text.trim().split("\\s+");

            firstWord = cleanWord(words[0]);
            lastWord = cleanWord(words[words.length - 1]);
        }

        return (firstTwo + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int choice) {
    switch (choice) {
        case 1:
            sentMessages.add(this);
            totalSent++;
            recordMessage(FLAG_SENT);
            return "Message successfully sent.";

        case 2:
            recordMessage(FLAG_DISREGARD);
            return "Message disregarded.";

        case 3:
            try {
                storeMessage();
                recordMessage(FLAG_STORED);
                readStoredMessagesFromJson();
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

    public static List<String> returnAllMessageHashes() {
        List<String> hashes = new ArrayList<>();

        for (Message message : sentMessages) {
            hashes.add(message.getMessageHash());
        }

        return hashes;
    }

    // Keep the singular method name for compatibility
    public static List<String> returnAllMessageHash() {
        return returnAllMessageHashes();
    }

    public static int returnTotalMessages() {
        return totalSent;
    }

    public void storeMessage() throws IOException {
        if (!storedMessages.contains(this)) {
            storedMessages.add(this);
        }
        writeStoredFile();
    }
    
    private static void writeStoredFile() throws IOException {

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

        if (text.length() <= 250) {
            return "Message ready to send.";
        }

        return "Message exceeds 250 characters by "
                + (text.length() - 250)
                + "; please reduce the size.";
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
        return String.format("%010d",
                RANDOM.nextLong(10_000_000_000L));
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

    private static String escape(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
    
    // part 3
    
    public static void setSender(String name) {
        sender = name;
    }

    /** Adds this message to the arrays that match its flag. */
    private void recordMessage(String flag) {
        messageIDArray.add(messageID);
        messageHashArray.add(messageHash);
        recipientArray.add(recipient);
        messageTextArray.add(text);
        flagArray.add(flag);

        if (flag.equals(FLAG_SENT)) {
            sentMessageTexts.add(text);
        } else if (flag.equals(FLAG_DISREGARD)) {
            disregardedMessageTexts.add(text);
        }
        // Stored messages are added to storedMessageTexts when the JSON file is read back
    }

    /** Populates all the arrays from the test data in the task (no hard-coding of the arrays). */
    public static void loadTestData() {
        // { recipient, message, flag, messageID (blank = generate one) }
        String[][] testData = {
            {"+27834557896", "Did you get the cake?", "Sent", ""},
            {"+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", ""},
            {"+27834484567", "Yoohoooo, I am at your gate.", "Disregard", ""},
            {"0838884567", "It is dinner time!", "Sent", "0838884567"},
            {"+27838884567", "Ok, I am leaving without you.", "Stored", ""}
        };

        for (int i = 0; i < testData.length; i++) {
            String id = testData[i][3].isEmpty() ? generateMessageID() : testData[i][3];
            Message m = new Message(id, i + 1, testData[i][0], testData[i][1]);

            String flag = testData[i][2];
            if (flag.equalsIgnoreCase(FLAG_SENT)) {
                m.sentMessage(1);
            } else if (flag.equalsIgnoreCase(FLAG_DISREGARD)) {
                m.sentMessage(2);
            } else {
                m.sentMessage(3);
            }
        }
    }

    /** Reads storedMessages.json into the storedMessageTexts array (uses org.json). */
    public static void readStoredMessagesFromJson() throws IOException {
        storedMessageTexts.clear();

        Path path = Path.of(STORE_FILE);
        if (!Files.exists(path)) {
            return;
        }

        String content = Files.readString(path);
        JSONArray array = new JSONArray(content);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            storedMessageTexts.add(obj.getString("message"));
        }
    }

    /** 2a. Sender and recipient of all stored messages. */
    
    public static String displaySenderAndRecipientOfStored() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flagArray.size(); i++) {
            if (flagArray.get(i).equals(FLAG_STORED)) {
                sb.append("Sender: ").append(sender)
                  .append(" | Recipient: ").append(recipientArray.get(i))
                  .append("\n");
            }
        }
        return sb.length() == 0 ? "There are no stored messages.\n" : sb.toString();
    }

    /** 2b. The longest message. */
    public static String getLongestMessage() {
        String longest = "";
        for (String t : messageTextArray) {
            if (t.length() > longest.length()) {
                longest = t;
            }
        }
        return longest.isEmpty() ? "There are no messages." : longest;
    }

    /** 2c. Search for a message ID. Returns {recipient, message}, or null if not found. */
    public static String[] searchByMessageID(String id) {
        int index = messageIDArray.indexOf(id == null ? "" : id.trim());
        if (index == -1) {
            return null;
        }
        return new String[]{recipientArray.get(index), messageTextArray.get(index)};
    }

    /** 2d. All messages sent or stored for a recipient (disregarded messages are ignored). */
    public static List<String> searchByRecipient(String number) {
        List<String> results = new ArrayList<>();
        String wanted = number == null ? "" : number.trim();

        for (int i = 0; i < recipientArray.size(); i++) {
            boolean sameRecipient = recipientArray.get(i).equals(wanted);
            boolean notDisregarded = !flagArray.get(i).equals(FLAG_DISREGARD);
            if (sameRecipient && notDisregarded) {
                results.add(messageTextArray.get(i));
            }
        }
        return results;
    }

    /** 2e. Delete a message using its message hash. */
    public static String deleteByMessageHash(String hash) {
        int index = messageHashArray.indexOf(hash == null ? "" : hash.trim());
        if (index == -1) {
            return "Message hash not found.";
        }

        String foundHash = messageHashArray.get(index);
        String deletedText = messageTextArray.get(index);
        String flag = flagArray.get(index);

        // Position of this message inside its own flag array
        int position = 0;
        for (int i = 0; i < index; i++) {
            if (flagArray.get(i).equals(flag)) {
                position++;
            }
        }

        messageIDArray.remove(index);
        messageHashArray.remove(index);
        recipientArray.remove(index);
        messageTextArray.remove(index);
        flagArray.remove(index);

        if (flag.equals(FLAG_SENT)) {
            sentMessageTexts.remove(position);
            sentMessages.removeIf(m -> m.messageHash.equals(foundHash));
            totalSent--;
        } else if (flag.equals(FLAG_DISREGARD)) {
            disregardedMessageTexts.remove(position);
        } else {
            storedMessages.removeIf(m -> m.messageHash.equals(foundHash));
            try {
                writeStoredFile();            // rewrite the JSON file without the deleted message
                readStoredMessagesFromJson(); // refresh the stored array
            } catch (IOException e) {
                return "Message deleted, but the JSON file could not be updated: " + e.getMessage();
            }
        }
        return "Message \"" + deletedText + "\" successfully deleted.";
    }
    
    public static String messageReport() {
        StringBuilder sb = new StringBuilder("===== STORED MESSAGES REPORT =====\n");
        int count = 0;

        for (int i = 0; i < flagArray.size(); i++) {
            if (flagArray.get(i).equals(FLAG_STORED)) {
                count++;
                sb.append("\nMessage ").append(count).append("\n")
                  .append("Message ID   : ").append(messageIDArray.get(i)).append("\n")
                  .append("Message Hash : ").append(messageHashArray.get(i)).append("\n")
                  .append("Recipient    : ").append(recipientArray.get(i)).append("\n")
                  .append("Message      : ").append(messageTextArray.get(i)).append("\n");
            }
        }

        if (count == 0) {
            sb.append("There are no stored messages.\n");
        }
        return sb.toString();
    }

    // Getters (copies) so the unit tests can check the arrays
    public static List<String> getSentMessageTexts() { return new ArrayList<>(sentMessageTexts); }
    public static List<String> getDisregardedMessageTexts() { return new ArrayList<>(disregardedMessageTexts); }
    public static List<String> getStoredMessageTexts() { return new ArrayList<>(storedMessageTexts); }
    public static List<String> getAllMessageHashes() { return new ArrayList<>(messageHashArray); }
    public static List<String> getAllMessageIDs() { return new ArrayList<>(messageIDArray); }
}





   
  
