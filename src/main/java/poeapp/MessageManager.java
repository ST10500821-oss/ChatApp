package poeapp;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class MessageManager {
    private List<Message> sentMessages = new ArrayList<>();

    public void addMessage(Message m) {
        sentMessages.add(m);
    }

    public int returnTotalMessages() {
        return sentMessages.size();
    }

    public void printAllMessages() {
        for (Message m : sentMessages) {
            javax.swing.JOptionPane.showMessageDialog(null, m.printMessageDetails());
        }
    }

    // ? Paste this method exactly here ???????????????????????????????
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
                jsonBuilder.append(","); // Add comma between objects
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
    
}

//Takudzwa  Meja  Takudzwa_21  Meja@2602!   +27680311267
//Enter first name: Takudzwa
//Enter last name: Meja
//Enter username: Takudzwa_21
//Enter password: Meja@2602!
//Enter cellphone (+countrycode): +27680311267
//Username invalid: must contain underscore and be 5-15 characters.
