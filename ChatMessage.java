import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String author;
    private String message;
    private String time;

    // Constructor, getters, and setters
    // Constructor
    public ChatMessage(String author, String message, String time) {
        this.author = author;
        this.message = message;
        this.time = time;
    }

    // Getters and setters
    public String getSender() {
        return author;
    }

    public void setSender(String sender) {
        this.author = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return time;
    }

    public void setTimestamp(String timestamp) {
        this.time = timestamp;
    }

    @Override
    public String toString() {
        return "ChatMessage [author=" + author + ", message=" + message + ", time=" + time + "]";
    }
}
