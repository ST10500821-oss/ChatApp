package poeapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Registration process
        System.out.println("=== Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

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

                            // Validate recipient number
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
                                manager.addMessage(msg, "Sent");
                            } else if (opt == 2) {
                                manager.addMessage(msg, "Disregard");
                            } else if (opt == 3) {
                                manager.addMessage(msg, "Stored");
                                manager.storeMessagesToJson("messages.json");
                            }
                        }

                        System.out.println("\nAll messages processed. Total: " + manager.returnTotalMessages());

                        // ===== PART 3 TASKS =====
                        System.out.println("\n--- PART 3 TASKS ---");

                        // a) Display all sent messages
                        manager.displaySentMessages();

                        // b) Display the longest sent message
                        manager.displayLongestMessage();

                        // c) Search for a message by ID
                        System.out.print("\nEnter a Message ID to search: ");
                        String searchId = scanner.nextLine();
                        manager.searchByMessageID(searchId);

                        // d) Search all messages sent/stored to a recipient
                        System.out.print("\nEnter recipient number to view all messages: ");
                        String searchRecipient = scanner.nextLine();
                        manager.searchByRecipient(searchRecipient);

                        // e) Delete a message using its hash
                        Message firstMessage = manager.getFirstSentMessage();
                        if (firstMessage != null) {
                            String hashToDelete = firstMessage.getMessageHash();
                            manager.deleteMessageByHash(hashToDelete);
                        }

                        // f) Display a full report
                        manager.displayReport();
                        break;

                    case 2:
                        if (manager.returnTotalMessages() == 0) {
                            System.out.println("No messages have been sent yet.");
                        } else {
                            System.out.println("\n=== Recently Sent Messages ===");
                            manager.displaySentMessages();
                            System.out.println("Total messages: " + manager.returnTotalMessages());
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Access denied. Exiting application.");
        }

        scanner.close();
    }
}
