/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
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

    
}
