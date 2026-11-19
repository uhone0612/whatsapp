/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.whatsapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class MessageStoreTest {
    private static final Path JSON = Path.of(Message.JSON_FILE);

    private static final String TEXT1 = "Did you get the cake?";
    private static final String TEXT2 = "Where are you? You are late! I have asked you to be on time.";
    private static final String TEXT3 = "Yohoooo, I am at your gate.";
    private static final String TEXT4 = "It is dinner time!";
    private static final String TEXT5 = "Ok, I am leaving without you.";
    
    
    /** Developer entry of the test data (message 4's ID is 0838884567 as in the brief). */
    private MessageStore populated() {
        MessageStore s = new MessageStore();
        s.addMessage("0012345671", 0, "+27834557896", TEXT1, MessageStore.SENT);
        s.addMessage("0012345672", 1, "+27838884567", TEXT2, MessageStore.STORED);
        s.addMessage("0012345673", 2, "+27834484567", TEXT3, MessageStore.DISREGARD);
        s.addMessage("0838884567", 3, "0838884567", TEXT4, MessageStore.SENT);
        s.addMessage("0012345675", 4, "+27838884567", TEXT5, MessageStore.STORED);
        return s;
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(JSON);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(JSON);
    }
    
    @Test
    public void sentMessagesArrayCorrectlyPopulated() {
        assertArrayEquals(new String[]{TEXT1, TEXT4}, populated().getSentMessages());
    }

    @Test
    public void disregardedAndStoredArraysCorrectlyPopulated() {
        MessageStore s = populated();
        assertArrayEquals(new String[]{TEXT3}, s.getDisregardedMessages());
        assertArrayEquals(new String[]{TEXT2, TEXT5}, s.getStoredMessages());
    }

    @Test
    public void hashAndIdArraysCorrectlyPopulated() {
        MessageStore s = populated();
        assertEquals(5, s.getMessageHashes().length);
        assertEquals(5, s.getMessageIDs().length);
        assertEquals("00:0:DIDCAKE", s.getMessageHashes()[0]);
        assertEquals("0838884567", s.getMessageIDs()[3]);
    }
    
    @Test
    public void displayLongestMessage() {
        assertEquals(TEXT2, populated().longestStoredMessage());
    }

    @Test
    public void searchForMessageID() {
        MessageStore s = populated();
        assertEquals(TEXT4, s.getMessageTextByID("0838884567"));
        assertEquals("Recipient: 0838884567\nMessage: " + TEXT4, s.searchMessageID("0838884567"));
        assertEquals("Message ID not found.", s.searchMessageID("1111111111"));
    }

    @Test
    public void searchMessagesForRecipient() {
        assertArrayEquals(new String[]{TEXT2, TEXT5},
                populated().getMessagesForRecipient("+27838884567"));
    }

    @Test
    public void deleteMessageUsingHash() {
        MessageStore s = populated();
        String hash = new Message("0012345672", 1, "+27838884567", TEXT2).getMessageHash();
        assertEquals("00:1:WHERETIME", hash);
        assertEquals("Message: \"" + TEXT2 + "\" successfully deleted.", s.deleteByHash(hash));
        assertArrayEquals(new String[]{TEXT5}, s.getStoredMessages());
        assertEquals(4, s.getMessageHashes().length);
        assertEquals("Message hash not found.", s.deleteByHash(hash));
    }
    
     @Test
    public void displayReportShowsSentMessages() {
        String report = populated().displayReport();
        assertTrue(report.contains("Message Hash: 00:0:DIDCAKE"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: " + TEXT1));
        assertTrue(report.contains("Message: " + TEXT4));
        assertFalse(report.contains(TEXT2));
    }

    @Test
    public void storedReportShowsFullDetails() {
        String report = populated().displayStoredReport();
        assertTrue(report.contains("Message ID: 0012345672"));
        assertTrue(report.contains("Message Hash: 00:1:WHERETIME"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Message: " + TEXT5));
        assertFalse(report.contains(TEXT1));
    }
    
     @Test
    public void senderAndRecipientOfStoredMessages() {
        String result = populated().displayStoredSendersAndRecipients("Uhone Mathoma");
        assertEquals("""
                     Sender: Uhone Mathoma | Recipient: +27838884567
                     Sender: Uhone Mathoma | Recipient: +27838884567
                     """, result);
    } 

    @Test
    public void jsonFileIsReadIntoStoredArray() {
        new Message("0012345672", 1, "+27838884567", TEXT2).storeMessage();
        new Message("0012345675", 4, "+27838884567", TEXT5).storeMessage();

        MessageStore s = new MessageStore();
        assertEquals(2, s.loadStoredFromJson());
        assertArrayEquals(new String[]{TEXT2, TEXT5}, s.getStoredMessages());
        assertEquals("00:1:WHERETIME", s.getMessageHashes()[0]);
    }
    
     @Test
    public void deletingStoredMessageUpdatesJsonFile() throws IOException {
        new Message("0012345672", 1, "+27838884567", TEXT2).storeMessage();
        new Message("0012345675", 4, "+27838884567", TEXT5).storeMessage();

        MessageStore s = new MessageStore();
        s.loadStoredFromJson();
        s.deleteByHash("00:1:WHERETIME");

        MessageStore reloaded = new MessageStore();
        assertEquals(1, reloaded.loadStoredFromJson());
        assertArrayEquals(new String[]{TEXT5}, reloaded.getStoredMessages());
    }
}
