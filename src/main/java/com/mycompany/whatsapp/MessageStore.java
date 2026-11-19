/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.whatsapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Student
 */
class MessageStore {
    
    public static final int MAX_MESSAGES = 100;
    public static final String SENT = "Sent";
    public static final String STORED = "Stored";
    public static final String DISREGARD = "Disregard";
    
     private final String[] sentMessages = new String[MAX_MESSAGES];
    private final String[] disregardedMessages = new String[MAX_MESSAGES];
    private final String[] storedMessages = new String[MAX_MESSAGES];
    private final String[] messageHashes = new String[MAX_MESSAGES];
    private final String[] messageIDs = new String[MAX_MESSAGES];
    
    private final String[] messageRecipients = new String[MAX_MESSAGES];
    private final String[] messageTexts = new String[MAX_MESSAGES];
    private final String[] messageFlags = new String[MAX_MESSAGES];
    
    private int sentCount = 0;
    private int disregardedCount = 0;
    private int storedCount = 0;
    private int totalCount = 0;
    
    
    // Adding messages
    

    /** Adds a message to the correct arrays. Returns false if the arrays are full. */
    public boolean addMessage(Message msg, String flag) {
        return addEntry(msg.getMessageID(), msg.getMessageHash(),
                msg.getRecipient(), msg.getMessageText(), flag);
    }
    
     public boolean addMessage(String id, int number, String recipient, String text, String flag) {
        return addMessage(new Message(id, number, recipient, text), flag);
    }
     
     private boolean addEntry(String id, String hash, String recipient, String text, String flag) {
        if (totalCount >= MAX_MESSAGES) {
            return false;
        }
        messageIDs[totalCount] = id;
        messageHashes[totalCount] = hash;
        messageRecipients[totalCount] = recipient;
        messageTexts[totalCount] = text;
        messageFlags[totalCount] = flag;
        totalCount++;

        if (flag.equals(SENT)) {
            sentMessages[sentCount++] = text;
        } else if (flag.equals(STORED)) {
            storedMessages[storedCount++] = text;
        } else {
            disregardedMessages[disregardedCount++] = text;
        }
        return true;
    }
     
    
    // Reading the arrays
    

    public String[] getSentMessages() { return Arrays.copyOf(sentMessages, sentCount); }
    public String[] getDisregardedMessages() { return Arrays.copyOf(disregardedMessages, disregardedCount); }
    public String[] getStoredMessages() { return Arrays.copyOf(storedMessages, storedCount); }
    public String[] getMessageHashes() { return Arrays.copyOf(messageHashes, totalCount); }
    public String[] getMessageIDs() { return Arrays.copyOf(messageIDs, totalCount); }
    public int getStoredCount() { return storedCount; }
    
    /** a) Sender and recipient of all stored messages. */
     public String displayStoredSendersAndRecipients(String sender) {
        if (storedCount == 0) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals(STORED)) {
                sb.append("Sender: ").append(sender)
                  .append(" | Recipient: ").append(messageRecipients[i]).append("\n");
            }
        }
        return sb.toString();
    }
     /** b) The longest stored message. */
     public String longestStoredMessage() {
        if (storedCount == 0) {
            return "No stored messages.";
        }
        String longest = storedMessages[0];
        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        return longest;
    }
     
      /** c) Message text for an ID, or null if the ID is not found. */
    public String getMessageTextByID(String id) {
        for (int i = 0; i < totalCount; i++) {
            if (messageIDs[i].equals(id)) {
                return messageTexts[i];
            }
        }
        return null;
    }
    
    /** c) Search by message ID and show the recipient and message. */
    public String searchMessageID(String id) {
        for (int i = 0; i < totalCount; i++) {
            if (messageIDs[i].equals(id)) {
                return "Recipient: " + messageRecipients[i] + "\nMessage: " + messageTexts[i];
            }
        }
        return "Message ID not found.";
    }

    /** d) All sent or stored message texts for one recipient. */
    public String[] getMessagesForRecipient(String recipient) {
        int count = 0;
        for (int i = 0; i < totalCount; i++) {
            if (messageRecipients[i].equals(recipient) && !messageFlags[i].equals(DISREGARD)) {
                count++;
            }
        }
        String[] result = new String[count];
        int position = 0;
        for (int i = 0; i < totalCount; i++) {
            if (messageRecipients[i].equals(recipient) && !messageFlags[i].equals(DISREGARD)) {
                result[position++] = messageTexts[i];
            }
        }
        return result;
    }

    /** d) Printable version of getMessagesForRecipient. */
    public String searchRecipient(String recipient) {
        String[] found = getMessagesForRecipient(recipient);
        if (found.length == 0) {
            return "No messages found for " + recipient + ".";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < found.length; i++) {
            sb.append(i + 1).append(") ").append(found[i]).append("\n");
        }
        return sb.toString();
    }

    /** e) Delete a message using its hash. */
    public String deleteByHash(String hash) {
        int index = -1;
        for (int i = 0; i < totalCount && index == -1; i++) {
            if (messageHashes[i].equals(hash)) {
                index = i;
            }
        }
        if (index == -1) {
            return "Message hash not found.";
        }
        
         String text = messageTexts[index];
        String flag = messageFlags[index];

        // remove from the category array
        if (flag.equals(SENT)) {
            sentCount = removeText(sentMessages, sentCount, text);
        } else if (flag.equals(STORED)) {
            storedCount = removeText(storedMessages, storedCount, text);
        } else {
            disregardedCount = removeText(disregardedMessages, disregardedCount, text);
        }

        // remove from the parallel arrays
        shiftLeft(messageIDs, totalCount, index);
        shiftLeft(messageHashes, totalCount, index);
        shiftLeft(messageRecipients, totalCount, index);
        shiftLeft(messageTexts, totalCount, index);
        shiftLeft(messageFlags, totalCount, index);
        totalCount--;

      if (flag.equals(STORED)) {
            saveStoredToJson();
        }
        return "Message: \"" + text + "\" successfully deleted.";
    }

    /** f) Report of all stored messages (full details). */
    public String displayStoredReport() {
        return buildReport(STORED, "STORED MESSAGES REPORT");
    }

    /** Report of all sent messages: ID, hash, recipient and message. */
    public String displayReport() {
        return buildReport(SENT, "SENT MESSAGES REPORT");
    }
    
    private String buildReport(String flag, String title) {
        StringBuilder sb = new StringBuilder("===== " + title + " =====\n");
        int number = 0;
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals(flag)) {
                number++;
                sb.append("\nMessage ").append(number).append("\n")
                  .append("Message ID: ").append(messageIDs[i]).append("\n")
                  .append("Message Hash: ").append(messageHashes[i]).append("\n")
                  .append("Recipient: ").append(messageRecipients[i]).append("\n")
                  .append("Message: ").append(messageTexts[i]).append("\n");
            }
        }
        if (number == 0) {
            sb.append("\nNo messages.\n");
        }
        return sb.toString();
    }
    
    public int loadStoredFromJson() {
        Path path = Path.of(Message.JSON_FILE);
        if (!Files.exists(path)) {
            return 0;
        }
        int loaded = 0;
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (!line.contains("\"messageID\"")) {
                    continue;
                }
                String id = extractField(line, "messageID");
                if (containsID(id)) {
                    continue; // already loaded
                }
                if (addEntry(id, extractField(line, "messageHash"),
                        extractField(line, "recipient"), extractField(line, "message"), STORED)) {
                    loaded++;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read JSON file: " + e.getMessage());
        }
        return loaded;
    }
    
    /** Rewrites messages.json from the stored messages (used after a delete). */
    public void saveStoredToJson() {
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals(STORED)) {
                if (body.length() > 0) {
                    body.append(",\n");
                }
                body.append(Message.toJsonObject(messageIDs[i], messageHashes[i],
                        messageRecipients[i], messageTexts[i]));
            }
        }
        Message.writeJsonFile(body.toString());
    }
    
    /** Pulls the value of "key":"value" out of one JSON line (handles \" and \\). */
    private static String extractField(String line, String key) {
        String marker = "\"" + key + "\":\"";
        int start = line.indexOf(marker);
        if (start == -1) {
            return "";
        }
        start += marker.length();
        StringBuilder value = new StringBuilder();
        for (int i = start; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\\' && i + 1 < line.length()) {
                value.append(line.charAt(i + 1));
                i++;
            } else if (c == '"') {
                break;
            } else {
                value.append(c);
            }
        }
        return value.toString();
    }
    
     private boolean containsID(String id) {
        for (int i = 0; i < totalCount; i++) {
            if (messageIDs[i].equals(id)) {
                return true;
            }
        }
        return false;
    }

    
    // Array helpers
    

    /** Removes the first element equal to text; returns the new count. */
    private static int removeText(String[] array, int count, String text) {
        for (int i = 0; i < count; i++) {
            if (array[i].equals(text)) {
                shiftLeft(array, count, i);
                return count - 1;
            }
        }
        return count;
    }
    
     /** Moves everything after index one place left and clears the last slot. */
    private static void shiftLeft(String[] array, int count, int index) {
        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
        }
        array[count - 1] = null;
    }
}
    

