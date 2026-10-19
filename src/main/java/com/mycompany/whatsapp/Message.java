/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.whatsapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 *
 * @author uhone
 */
class Message {
     private static final String JSON_FILE = "messages.json";
    private static final SecureRandom RANDOM = new SecureRandom();

    // Running state for the life of the program
    private static int totalSent = 0;
    private static final StringBuilder sentLog = new StringBuilder();
    private static final StringBuilder storedJson = new StringBuilder();
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;

    public Message(String messageID, int messageNumber, String recipient, String text) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = createMessageHash();
    }
    /** Creates a random ten-digit ID (leading zeros allowed). */
    public static String generateMessageID() {
        long value = Math.floorMod(RANDOM.nextLong(), 10_000_000_000L);
        return String.format("%010d", value);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /** Same rule as Part 1: '+' country code then up to ten digits. */
    public String checkRecipientCell() {
        if (Pattern.matches("^\\+\\d{1,3}\\d{1,10}$", recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public static String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int over = message.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }
    /** Format: first two ID digits : message number : FIRSTWORD + LASTWORD (caps). */
    public String createMessageHash() {
        String trimmed = text.trim();
        int firstSpace = trimmed.indexOf(' ');
        int lastSpace = trimmed.lastIndexOf(' ');
        String first = (firstSpace == -1) ? trimmed : trimmed.substring(0, firstSpace);
        String last = (lastSpace == -1) ? trimmed : trimmed.substring(lastSpace + 1);
        first = first.replaceAll("[^A-Za-z0-9]", "");
        last = last.replaceAll("[^A-Za-z0-9]", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }
    /** 1 = send, 2 = disregard, 3 = store. */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                totalSent++;
                sentLog.append(printDetails()).append("\n");
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
     /** Details in the required order: ID, Hash, Recipient, Message. */
    public String printDetails() {
        return "Message ID: " + messageID + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient + "\nMessage: " + text + "\n";
    }

    public static String printMessages() {
        return sentLog.toString();
    }

    public static int returnTotalMessages() {
        return totalSent;
    }
    public void storeMessage() {
        if (storedJson.length() > 0) {
            storedJson.append(",\n");
        }
        storedJson.append("  {\"messageID\":\"").append(messageID)
                .append("\",\"messageHash\":\"").append(messageHash)
                .append("\",\"recipient\":\"").append(recipient)
                .append("\",\"message\":\"").append(escape(text)).append("\"}");
        try {
            Files.writeString(Path.of(JSON_FILE), "[\n" + storedJson + "\n]\n");
        } catch (IOException e) {
            System.out.println("Could not write JSON file: " + e.getMessage());
        }
    }
    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public String getMessageHash() { return messageHash; }
    public String getMessageID() { return messageID; }

    /** Used by unit tests to start from a clean state. */
    public static void resetState() {
        totalSent = 0;
        sentLog.setLength(0);
        storedJson.setLength(0);
    }
}
