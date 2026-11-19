package com.mycompany.whatsapp;

import java.util.Scanner;

/**
 * Console app: registration/login (Part 1), QuickChat messages (Part 2)
 * and stored message features (Part 3).
 * @author Student
 */
public class QuickChat {

    private static final Scanner input = new Scanner(System.in);
    private static final MessageStore store = new MessageStore();

    public static void main(String[] args) {
        // ---------- registration (Part 1) ----------
        System.out.println("enter first name: ");
        String firstname = input.nextLine().trim();

        System.out.println("enter last name: ");
        String lastname = input.nextLine().trim();

        loginTest user;
        do {
            System.out.println("enter username: ");
            String username = input.nextLine().trim();

            System.out.println("enter password: ");
            String password = input.nextLine();

            System.out.println("enter your south african cell number: ");
            String cellphonenumber = input.nextLine().trim();

            user = new loginTest(username, password, cellphonenumber);
            System.out.println(user.registerUser());
        } while (!(user.checkUserName() && user.checkPasswordComplexity() && user.checkCellPhoneNumber()));

        // ---------- login (Part 1) - messages only after a successful login ----------
        boolean loggedIn = false;
        for (int attempt = 0; attempt < 3 && !loggedIn; attempt++) {
            System.out.println("enter username to login: ");
            String enteredusername = input.nextLine().trim();

            System.out.println("enter password to login: ");
            String enteredpassword = input.nextLine();

            loggedIn = user.loginUser(enteredusername, enteredpassword);
            System.out.println(user.returnLoginStatus(loggedIn, firstname, lastname));
        }

        if (loggedIn) {
            runQuickChat(firstname + " " + lastname);
        } else {
            System.out.println("Too many failed login attempts.");
        }
        input.close();
    }

    private static void runQuickChat(String senderName) {
        System.out.println("\nWelcome to QuickChat.");

        int loaded = store.loadStoredFromJson();
        if (loaded > 0) {
            System.out.println(loaded + " stored message(s) loaded from file.");
        }

        int numMessages = readInt("How many messages do you wish to enter? ", 1, Integer.MAX_VALUE);
        int entered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit\n4) Stored Messages");
            int option = readInt("Choose an option: ", 1, 4);

            if (option == 1) {
                if (entered >= numMessages) {
                    System.out.println("You have already entered all " + numMessages + " messages.");
                }
                for (int i = entered; i < numMessages; i++) {
                    System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");
                    handleMessage(i);
                    entered++;
                }
                if (entered >= numMessages) {
                    System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
                }
            } else if (option == 2) {
                System.out.println("Coming Soon.");
            } else if (option == 3) {
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
                running = false;
            } else {
                storedMessagesMenu(senderName);
            }
        }
    }

    private static void handleMessage(int number) {
        String recipient;
        boolean recipientOk;
        do {
            System.out.println("Recipient cell number (with international code): ");
            recipient = input.nextLine().trim();
            String result = new Message(Message.generateMessageID(), number, recipient, "x").checkRecipientCell();
            System.out.println(result);
            recipientOk = result.startsWith("Cell phone number successfully");
        } while (!recipientOk);

        String text;
        while (true) {
            System.out.println("Message (max 250 characters): ");
            text = input.nextLine();
            if (text.trim().isEmpty()) {
                System.out.println("Message cannot be empty.");
            } else if (text.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters.");
                System.out.println(Message.checkMessageLength(text));
            } else {
                System.out.println("Message sent");
                break;
            }
        }

        Message msg = new Message(Message.generateMessageID(), number, recipient, text);
        System.out.println("Message ID generated: " + msg.getMessageID());

        System.out.println("1) Send Message\n2) Disregard Message\n3) Store Message to send later");
        int choice = readInt("Choose: ", 1, 3);
        System.out.println(msg.sentMessage(choice));

        boolean saved;
        if (choice == 1) {
            saved = store.addMessage(msg, MessageStore.SENT);
            System.out.println("\n" + msg.printDetails());
        } else if (choice == 2) {
            readInt("Enter 0 to delete: ", 0, 0);
            System.out.println("Message deleted.");
            saved = store.addMessage(msg, MessageStore.DISREGARD);
        } else {
            saved = store.addMessage(msg, MessageStore.STORED);
        }
        if (!saved) {
            System.out.println("Message storage is full.");
        }
    }

    /** Part 3 menu: features that work on the arrays. */
    private static void storedMessagesMenu(String senderName) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Stored Messages ---");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search messages for a recipient");
            System.out.println("5) Delete a message using its hash");
            System.out.println("6) Display report of all stored messages");
            System.out.println("7) Display report of all sent messages");
            System.out.println("0) Back to main menu");
            int choice = readInt("Choose: ", 0, 7);

            switch (choice) {
                case 1:
                    System.out.println(store.displayStoredSendersAndRecipients(senderName));
                    break;
                case 2:
                    System.out.println(store.longestStoredMessage());
                    break;
                case 3:
                    System.out.println("Enter message ID: ");
                    System.out.println(store.searchMessageID(input.nextLine().trim()));
                    break;
                case 4:
                    System.out.println("Enter recipient cell number: ");
                    System.out.println(store.searchRecipient(input.nextLine().trim()));
                    break;
                case 5:
                    System.out.println("Enter message hash: ");
                    System.out.println(store.deleteByHash(input.nextLine().trim().toUpperCase()));
                    break;
                case 6:
                    System.out.println(store.displayStoredReport());
                    break;
                case 7:
                    System.out.println(store.displayReport());
                    break;
                default:
                    back = true;
            }
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.println(prompt);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Invalid input, please try again.");
        }
    }
}
