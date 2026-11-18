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
    
     // ------------------------------------------------------------------
    // Adding messages
    // ------------------------------------------------------------------

    /** Adds a message to the correct arrays. Returns false if the arrays are full. */
    public boolean addMessage(Message msg, String flag) {
        return addEntry(msg.getMessageID(), msg.getMessageHash(),
                msg.getRecipient(), msg.getMessageText(), flag);
    }
}
