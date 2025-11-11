package poeapp;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // Step 1: Registration process
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        Login login = new Login();
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        String cellPhone = JOptionPane.showInputDialog("Enter cellphone (+countrycode):");

        // Register the user
        String registerResult = login.registerUser(username, password, cellPhone, firstName, lastName);
        JOptionPane.showMessageDialog(null, registerResult);

        // Step 2: Login process
        String loginUser = JOptionPane.showInputDialog("Enter username to login:");
        String loginPass = JOptionPane.showInputDialog("Enter password to login:");
        String loginStatus = login.returnLoginStatus(loginUser, loginPass);
        JOptionPane.showMessageDialog(null, loginStatus);

        if (loginStatus.startsWith("Welcome")) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            MessageManager manager = new MessageManager();
            int choice = 0;

            do {
                String choiceStr = JOptionPane.showInputDialog(
                        "Choose an option:\n1) Send Messages\n2) Show Recently Sent Messages\n3) Quit");
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input, please enter a number between 1-3.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        String totalStr = JOptionPane.showInputDialog("How many messages do you want to send?");
                        int total = Integer.parseInt(totalStr);

                        for (int i = 1; i <= total; i++) {
                            String recipient = JOptionPane.showInputDialog("Message #" + i + "\nEnter recipient number (+...):");
                            String text = JOptionPane.showInputDialog("Enter message text:");

                            Message msg = new Message(i, recipient, text);

                            if (!msg.checkRecipientCell()) {
                                JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted...");
                                i--;
                                continue;
                            }

                            String valid = msg.validateMessage();
                            JOptionPane.showMessageDialog(null, valid);
                            if (valid.startsWith("Message exceeds")) {
                                i--;
                                continue;
                            }

                            String optStr = JOptionPane.showInputDialog(
                                    "Choose:\n1) Send Message\n2) Disregard\n3) Store to send later");
                            int opt = Integer.parseInt(optStr);
                            String result = msg.sendMessageOption(opt);
                            JOptionPane.showMessageDialog(null, result);

                            if (opt == 1)
                                manager.addMessage(msg, "Sent");
                            else if (opt == 2)
                                manager.addMessage(msg, "Disregard");
                            else if (opt == 3) {
                                manager.addMessage(msg, "Stored");
                                manager.storeMessagesToJson("messages.json");
                            }
                        }

                        JOptionPane.showMessageDialog(null, "All messages processed. Total: " + manager.returnTotalMessages());

                        // ===== PART 3 TASKS =====
                        manager.displaySentMessages();
                        manager.displayLongestMessage();

                        String searchId = JOptionPane.showInputDialog("Enter a Message ID to search:");
                        manager.searchByMessageID(searchId);

                        String searchRecipient = JOptionPane.showInputDialog("Enter recipient number to view all messages:");
                        manager.searchByRecipient(searchRecipient);

                        Message firstMessage = manager.getFirstSentMessage();
                        if (firstMessage != null) {
                            String hashToDelete = firstMessage.getMessageHash();
                            manager.deleteMessageByHash(hashToDelete);
                        }

                        manager.displayReport();
                        break;

                    case 2:
                        if (manager.returnTotalMessages() == 0) {
                            JOptionPane.showMessageDialog(null, "No messages have been sent yet.");
                        } else {
                            manager.displaySentMessages();
                            JOptionPane.showMessageDialog(null, "Total messages: " + manager.returnTotalMessages());
                        }
                        break;

                    case 3:
                        JOptionPane.showMessageDialog(null, "Exiting...");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                }

            } while (choice != 3);

        } else {
            JOptionPane.showMessageDialog(null, "Access denied. Exiting application.");
        }
    }
}
