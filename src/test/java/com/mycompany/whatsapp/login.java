/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.whatsapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author uhone
 */
public class login {
    private static final String TEXT1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String TEXT2 = "Hi Keegan, did you receive the payment?";

    private Message msg1() { return new Message("0012345678", 0, "+27718693002", TEXT1); }
    private Message msg2() { return new Message("0098765432", 1, "08575975889", TEXT2); }

     @BeforeEach
    public void reset() { Message.resetState(); }

    @Test
    public void messageLengthSuccess() {
        assertEquals("Message ready to send.", Message.checkMessageLength(TEXT1));
    }

    @Test
    public void messageLengthFailure() {
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                Message.checkMessageLength("a".repeat(260)));
    }

    @Test
    public void recipientSuccess() {
        assertEquals("Cell phone number successfully captured.", msg1().checkRecipientCell());
    }

    @Test
    public void recipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg2().checkRecipientCell());
    }
    
    @Test
    public void hashCorrectForTestData() {
        assertEquals("00:0:HITONIGHT", msg1().getMessageHash());
        assertEquals("00:1:HIPAYMENT", msg2().getMessageHash());
    }

    @Test
    public void hashesInLoop() {
        for (int i = 0; i < 2; i++) {
            Message m = (i == 0) ? msg1() : msg2();
            String expected = (i == 0) ? "00:0:HITONIGHT" : "00:1:HIPAYMENT";
            assertEquals(expected, m.createMessageHash());
        }
    }
    
     @Test
    public void messageIdCreated() {
        String id = Message.generateMessageID();
        assertEquals(10, id.length());
        assertTrue(new Message(id, 0, "+27718693002", TEXT1).checkMessageID());
    }

    @Test
    public void sendDisregardStore() {
        assertEquals("Message successfully sent.", msg1().sentMessage(1));
        assertEquals("Press 0 to delete the message.", msg2().sentMessage(2));
        assertEquals("Message successfully stored.", msg1().sentMessage(3));
    }

    @Test
    public void totalMessagesReturned() {
        msg1().sentMessage(1);   // sent
        msg2().sentMessage(2);   // discarded
        assertEquals(1, Message.returnTotalMessages());
        assertTrue(Message.printMessages().contains("00:0:HITONIGHT"));
    }
    
    
}
