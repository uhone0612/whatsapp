/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.Scanner;
/**
 *
 * @author Student
 */
public class QuickChat {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Login login = register();
        if (!doLogin(login)) {
            return;
        }
         System.out.println("\nWelcome to QuickChat.");
        int numMessages = readInt("How many messages do you wish to enter? ", 1, Integer.MAX_VALUE);
        int entered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
            int option = readInt("Choose an option: ", 1, 3);

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
            } else {
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
                running = false;
            }
        }
    }
     private static class Login {

        public Login() {
             System.out.print("First name: ");
        String first = in.nextLine().trim();
        System.out.print("Last name: ");
        String last = in.nextLine().trim();
        Login login = new Login(first, last);
        boolean ok = false;
        while (!ok) {
            System.out.print("Username: ");
            String user = in.nextLine().trim();
            System.out.print("Password: ");
            String pass = in.nextLine();
            System.out.print("Cell number (e.g. +27838968976): ");
            String cell = in.nextLine().trim();
            String result = login.registerUser(user, pass, cell);
            System.out.println(result);
            ok = result.startsWith("Username successfully");
        }
         return login;
    }
        private static boolean doLogin(Login login) {
        System.out.println("\n--- Login ---");
        for (int attempt = 0; attempt < 3; attempt++) {
            System.out.print("Username: ");
            String user = in.nextLine().trim();
            System.out.print("Password: ");
            String pass = in.nextLine();
            System.out.println(login.returnLoginStatus(user, pass));
            if (login.loginUser(user, pass)) {
                return true;
            }
        }
        System.out.println("Too many failed attempts.");
        return false;
        }
        private static void handleMessage(int number) {
        String recipient;
        Message check;
        do {
            System.out.print("Recipient cell number (with international code): ");
            recipient = in.nextLine().trim();
            check = new Message(Message.generateMessageID(), number, recipient, "x");
            String result = check.checkRecipientCell();
            System.out.println(result);
            if (!result.startsWith("Cell phone number successfully")) {
                check = null;
            }
        } while (check == null);
        
        String text;
        while (true) {
            System.out.print("Message (max 250 characters): ");
            text = in.nextLine();
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

        if (choice == 2) {
            readInt("", 0, 0); // user must press 0 to delete
            System.out.println("Message deleted.");
        } else if (choice == 1) {
            System.out.println("\n" + msg.printDetails());
        }
    }
        
         private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(in.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to message below
            }
            System.out.println("Please enter a number between " + min + " and "
                    + (max == Integer.MAX_VALUE ? "a larger value" : String.valueOf(max)) + ".");
        }
    }

    
}
}


   