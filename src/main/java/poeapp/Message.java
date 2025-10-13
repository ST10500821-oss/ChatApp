
package poeapp;


public class Message {
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(int messageNumber, String recipient, String message) {
        this.messageId = generateMessageId();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    private String generateMessageId() {
        // Generate random 10-digit number
        return String.valueOf((long)(Math.random() * 1_000_000_0000L));
    }

    public boolean checkMessageID() {
        return this.messageId.length() == 10;
    }

    public boolean checkRecipientCell() {
         return recipient.trim().matches("^\\+\\d{10,13}$");
    }

    public String validateMessage() {
        int len = message.length();
        if (len > 250) {
            return "Message exceeds 250 characters by " + (len - 250) + ", please reduce size.";
        }
        return "Message ready to send.";
    }

    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return messageId.substring(0, 2) + ":" + String.format("%02d", messageNumber) + first.toUpperCase() + last.toUpperCase();
    }

    public String sendMessageOption(int choice) {
        switch (choice) {
            case 1: return "Message successfully sent.";
            case 2: return "Press 0 to delete message.";
            case 3: return "Message successfully stored.";
            default: return "Invalid option.";
        }
    }

    public String printMessageDetails() {
        return "Message ID: " + messageId + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + message;
    }

    // Getter methods
    public String getMessageId() { return messageId; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
}

