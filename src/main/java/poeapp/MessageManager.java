package poeapp;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class MessageManager {
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> disregardedMessages = new ArrayList<>();
    private List<Message> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIDs = new ArrayList<>();

    public void addMessage(Message m, String flag) {
        switch (flag.toLowerCase()) {
            case "sent": sentMessages.add(m); break;
            case "disregard": disregardedMessages.add(m); break;
            case "stored": storedMessages.add(m); break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid flag!");
                return;
        }
        messageHashes.add(m.getMessageHash());
        messageIDs.add(m.getMessageId());
    }

    public void displaySentMessages() {
        StringBuilder sb = new StringBuilder("=== SENT MESSAGES ===\n");
        for (Message m : sentMessages) {
            sb.append("Sender: QuickChat User\n")
              .append("Recipient: ").append(m.getRecipient()).append("\n")
              .append("Message: ").append(m.getMessage()).append("\n")
              .append("------------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void displayLongestMessage() {
        String longest = "";
        for (Message m : sentMessages) {
            if (m.getMessage().length() > longest.length()) longest = m.getMessage();
        }
        JOptionPane.showMessageDialog(null, "Longest Sent Message:\n" + longest);
    }

    public void searchByMessageID(String id) {
        for (Message m : sentMessages) {
            if (m.getMessageId().equals(id)) {
                JOptionPane.showMessageDialog(null,
                        "Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessage());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    public void searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages sent or stored to: " + recipient + "\n");
        for (Message m : sentMessages) if (m.getRecipient().equals(recipient)) sb.append(m.getMessage()).append("\n");
        for (Message m : storedMessages) if (m.getRecipient().equals(recipient)) sb.append(m.getMessage()).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void deleteMessageByHash(String hash) {
        for (Message m : sentMessages) {
            if (m.getMessageHash().equals(hash)) {
                sentMessages.remove(m);
                JOptionPane.showMessageDialog(null, "Message '" + m.getMessage() + "' successfully deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message not found for hash: " + hash);
    }

    public void displayReport() {
        StringBuilder sb = new StringBuilder("=== SENT MESSAGES REPORT ===\n");
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.getMessageId()).append("\n")
              .append("Message Hash: ").append(m.getMessageHash()).append("\n")
              .append("Recipient: ").append(m.getRecipient()).append("\n")
              .append("Message: ").append(m.getMessage()).append("\n")
              .append("-----------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public int returnTotalMessages() { return sentMessages.size(); }

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
            if (i != sentMessages.size() - 1) jsonBuilder.append(",");
            jsonBuilder.append("\n");
        }
        jsonBuilder.append("]");
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonBuilder.toString());
            JOptionPane.showMessageDialog(null, "Messages saved to JSON file.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error writing to file: " + e.getMessage());
        }
    }

    public Message getFirstSentMessage() {
        if (!sentMessages.isEmpty()) return sentMessages.get(0);
        return null;
    }
}
