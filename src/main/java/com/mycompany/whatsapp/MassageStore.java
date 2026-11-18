/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.whatsapp;

/**
 *
 * @author Student
 */
public class MassageStore {
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
}
