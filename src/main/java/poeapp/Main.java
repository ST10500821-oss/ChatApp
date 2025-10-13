package poeapp;

import java.util.Scanner;
import poeapp.MessageManager;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Registration process
        System.out.println("=== Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        // Use the firstName and lastName during registration
        Login login = new Login();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter cellphone (+countrycode): ");
        String cellPhone = scanner.nextLine();

        // Register the user
        String registerResult = login.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println(registerResult);

        // Step 2: Login process
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        // Show login status
        String loginStatus = login.returnLoginStatus(loginUser, loginPass);
        System.out.println(loginStatus);

        // Only proceed if login successful
        if (loginStatus.startsWith("Welcome")) {
            System.out.println("\nWelcome to QuickChat.");
            int choice = -1;
            MessageManager manager = new MessageManager();

            while (choice != 3) {
                System.out.println("\nChoose an option:");
                System.out.println("1) Send Messages");
                System.out.println("2) Show Recently Sent Messages");
                System.out.println("3) Quit");

                // Input validation for menu choice
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    scanner.nextLine();
                    continue;
                }

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("How many messages do you want to send? ");
                        int total = scanner.nextInt();
                        scanner.nextLine();

                        for (int i = 1; i <= total; i++) {
                            System.out.println("\nMessage #" + i);
                            System.out.print("Enter recipient number (+...): ");
                            String recipient = scanner.nextLine();

                            System.out.print("Enter message text: ");
                            String text = scanner.nextLine();

                            Message msg = new Message(i, recipient, text);

                            // Validate recipient
                            if (!msg.checkRecipientCell()) {
                                System.out.println("Cell phone number is incorrectly formatted...");
                                i--;
                                continue;
                            }

                            // Validate message length
                            String valid = msg.validateMessage();
                            System.out.println(valid);
                            if (valid.startsWith("Message exceeds")) {
                                i--;
                                continue;
                            }

                            System.out.println("Choose:");
                            System.out.println("1) Send Message");
                            System.out.println("2) Disregard");
                            System.out.println("3) Store to send later");
                            int opt = scanner.nextInt();
                            scanner.nextLine();

                            String result = msg.sendMessageOption(opt);
                            System.out.println(result);

                            if (opt == 1) {
                                manager.addMessage(msg);
                            } else if (opt == 3) {
                                manager.addMessage(msg);
                                manager.storeMessagesToJson("messages.json");
                            }
                        }

                        System.out.println("\nAll messages sent. Total: " + manager.returnTotalMessages());
                        manager.printAllMessages();
                        break;

                    case 2:
                        // ? Fixed "Show Recently Sent Messages"
                        if (manager.returnTotalMessages() == 0) {
                            System.out.println("No messages have been sent yet.");
                        } else {
                            System.out.println("\n=== Recently Sent Messages ===");
                            manager.printAllMessages();
                            System.out.println("Total messages: " + manager.returnTotalMessages());
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Access denied. Exiting application.");
        }

        scanner.close();
    }
}
