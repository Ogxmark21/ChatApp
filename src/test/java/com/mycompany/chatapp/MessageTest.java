/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import com.mycompany.chatapp.Message;
import static org.junit.jupiter.api.Assertions.*;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Student
 */
public class MessageTest {
 
    private Message msg1;
    private Message msg2;
 
    @BeforeEach
    void setUp() {
        Message.clearAll();
        // Test Data for Task 1
        msg1 = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        // Test Data for Message 2
        msg2 = new Message("0098765432", 1, "08575975889", "Hi Keegan, did you receive the payment?");
    }
 
    @Test
    void messageLength_success() {
        assertEquals("Message ready to send.", Message.validateMessageLength("a".repeat(250)));
    }
 
    @Test
    void messageLength_failure() {
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                Message.validateMessageLength("a".repeat(260)));
    }
 
    @Test
    void recipient_success() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }
 
    @Test
    void recipient_failure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg2.checkRecipientCell());
    }
 
    @Test
    void messageHash_correct() {
        assertEquals("00:0:HITONIGHT", msg1.getMessageHash());
        assertEquals("00:1:HIPAYMENT", msg2.getMessageHash());
    }
 
    @Test
    void messageID_created() {
        assertTrue(msg1.checkMessageID());
        assertEquals("Message ID generated: 0012345678", "Message ID generated: " + msg1.getMessageID());
    }
 
    @Test
    void generatedID_isTenDigits() {
        assertTrue(new Message(0, "+27718693002", "Hello there").getMessageID().matches("\\d{10}"));
    }
 
    @Test
    void sentMessage_send() {
        assertEquals("Message successfully sent.", msg1.sentMessage(1));
    }
 
    @Test
    void sentMessage_disregard() {
        assertEquals("Press 0 to delete the message.", msg2.sentMessage(2));
    }
 
    @Test
    void sentMessage_store() {
        assertEquals("Message successfully stored.", msg1.sentMessage(3));
    }
 
    @Test
    void totalMessages_countsOnlySent() {
        msg1.sentMessage(1);   // sent
        msg2.sentMessage(2);   // disregarded
        assertEquals(1, Message.returnTotalMessages());
    }
 
    @Test
    void printMessages_listsSent() {
        msg1.sentMessage(1);
        String out = Message.printMessages();
        assertTrue(out.contains("00:0:HITONIGHT"));
        assertTrue(out.contains("+27718693002"));
    }
 
    @Test
    void allMessageHashes_returnedInLoop() {
        msg1.sentMessage(1);
        Message second = new Message("0098765432", 1, "+27835550000", "Hi Keegan, did you receive the payment?");
        second.sentMessage(1);
        assertEquals(java.util.List.of("00:0:HITONIGHT", "00:1:HIPAYMENT"), Message.returnAllMessageHashes());
    }
    
    // PART 3 unit tests
    
    private static final String MSG2 = "Where are you? You are late! I have asked you to be on time.";
    private static final String MSG5 = "Ok, I am leaving without you.";

    @Test
    void part3_sentMessagesArray_correctlyPopulated() {
        Message.loadTestData();
        assertEquals(java.util.List.of("Did you get the cake?", "It is dinner time!"),
                Message.getSentMessageTexts());
    }

    @Test
    void part3_disregardedAndStoredArrays_correctlyPopulated() {
        Message.loadTestData();
        assertEquals(java.util.List.of("Yoohoooo, I am at your gate."), Message.getDisregardedMessageTexts());
        assertEquals(java.util.List.of(MSG2, MSG5), Message.getStoredMessageTexts()); // read from the JSON file
    }

    @Test
    void part3_hashAndIdArrays_containAllMessages() {
        Message.loadTestData();
        assertEquals(5, Message.getAllMessageHashes().size());
        assertEquals(5, Message.getAllMessageIDs().size());
    }

    @Test
    void part3_displayLongestMessage() {
        Message.loadTestData();
        assertEquals(MSG2, Message.getLongestMessage());
    }

    @Test
    void part3_searchForMessageID() {
        Message.loadTestData();
        String[] result = Message.searchByMessageID("0838884567");
        assertNotNull(result);
        assertEquals("It is dinner time!", result[1]);
    }

    @Test
    void part3_searchForMessageID_notFound() {
        Message.loadTestData();
        assertNull(Message.searchByMessageID("0000000000"));
    }

    @Test
    void part3_searchAllMessagesForRecipient() {
        Message.loadTestData();
        assertEquals(java.util.List.of(MSG2, MSG5), Message.searchByRecipient("+27838884567"));
    }

    @Test
    void part3_deleteMessageUsingHash() {
        Message.loadTestData();
        String hashOfMessage2 = Message.getAllMessageHashes().get(1);
        assertEquals("Message \"" + MSG2 + "\" successfully deleted.",
                Message.deleteByMessageHash(hashOfMessage2));
        assertEquals(java.util.List.of(MSG5), Message.getStoredMessageTexts());
    }

    @Test
    void part3_deleteMessage_unknownHash() {
        Message.loadTestData();
        assertEquals("Message hash not found.", Message.deleteByMessageHash("XX:9:NOTHING"));
    }

    @Test
    void part3_displayReport_showsHashRecipientAndMessage() {
        Message.loadTestData();
        String report = Message.messageReport();
        assertTrue(report.contains(Message.getAllMessageHashes().get(1)));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains(MSG2));
        assertTrue(report.contains(MSG5));
    }
}
