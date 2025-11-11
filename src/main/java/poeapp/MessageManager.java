package poeapp;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class MessageManager {

    // ====== ARRAYS REQUIRED BY TASK ======
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> disregardedMessages = new ArrayList<>();
    private List<Message> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIDs = new ArrayList<>();

    // ====== ADD MESSAGE BASED ON FLAG ======
    public void addMessage(Message m, String flag) {
        switch (flag.toLowerCase()) {
            case "sent":
                sentMessages.add(m);
                break;
            case "disregard":
                disregardedMessages.add(m);
                break;
            case "stored":
                storedMessages.add(m);
                break;
            default:
                System.out.println("Invalid flag!");
                return;
        }

        // Always store ID and hash
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageId());
    }

    // ====== SHOW ALL SENT MESSAGES ======
    public void displaySentMessages() {
        System.out.println("\n=== SENT MESSAGES ===");
        for (Message m : sentMessages) {
            System.out.println("Sender: QuickChat User");
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessage());
            System.out.println("------------------------------------");
        }
    }

    // ====== FIND LONGEST SENT MESSAGE ======
    public void displayLongestMessage() {
        String longest = "";
        for (Message m : sentMessages) {
            if (m.getMessage().length() > longest.length()) {
                longest = m.getMessage();
            }
        }
        System.out.println("\nLongest Sent Message:\n" + longest);
    }

    // ====== SEARCH BY MESSAGE ID ======
    public void searchByMessageID(String id) {
        System.out.println("\nSearching for Message ID: " + id);
        for (Message m : sentMessages) {
            if (m.getMessageId().equals(id)) {
                System.out.println("Recipient: " + m.getRecipient());
                System.out.println("Message: " + m.getMessage());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    // ====== SEARCH BY RECIPIENT ======
    public void searchByRecipient(String recipient) {
        System.out.println("\nMessages sent or stored to: " + recipient);

        for (Message m : sentMessages) {
            if (m.getRecipient().equals(recipient)) {
                System.out.println(m.getMessage());
            }
        }
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(recipient)) {
                System.out.println(m.getMessage());
            }
        }
    }

    // ====== DELETE USING HASH ======
    public void deleteMessageByHash(String hash) {
        for (Message m : sentMessages) {
            if (m.getMessageHash().equals(hash)) {
                sentMessages.remove(m);
                System.out.println("\nMessage '" + m.getMessage() + "' successfully deleted.");
                return;
            }
        }
        System.out.println("\nMessage not found for hash: " + hash);
    }

    // ====== REPORT ======
    public void displayReport() {
        System.out.println("\n=== SENT MESSAGES REPORT ===");
        for (Message m : sentMessages) {
            System.out.println("Message ID: " + m.getMessageId());
            System.out.println("Message Hash: " + m.getMessageHash());
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessage());
            System.out.println("-----------------------------------");
        }
    }

    // ====== OPTIONAL HELPERS ======
    public int returnTotalMessages() {
        return sentMessages.size();
    }

    public void storeMessagesToJson(String filePath) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < sentMessages.size(); i++) {
            Message m = sentMessages.get(i);
            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"MessageID\": \"").append(m.getMessageId()).append("\",\n");
            jsonBuilder.append("    \"MessageHash\": \"").append(m.getMessageHash()).append("\",\n");
            jsonBuilder.append("    \"Recipient\": \"").append(m.getRecipient()).append("\",\n");
            jsonBuilder.append("    \"Message\": \"").append(m.getMessage()).append("\"\n");
            jsonBuilder.append("  }");

            if (i != sentMessages.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]");
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonBuilder.toString());
            System.out.println("Messages saved to JSON file.");
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // ====== FIX FOR MAIN.JAVA ======
    public Message getFirstSentMessage() {
        if (!sentMessages.isEmpty()) {
            return sentMessages.get(0);
        }
        return null;
    }
}

//Takudzwa  Meja  Takudzwa_21  Meja@2602!   +27680311267 
//Enter first name: Takudzwa 
//Enter last name: Meja 
//Enter username: Takudzwa_21 
//Enter password: Meja@2602! 
//Enter cellphone (+countrycode): +27680311267 
//Username invalid: must contain underscore and be 5-15 characters. 
//Enter recipent number (+countrycode): +27680311277 +27680313421 +2768036578 


