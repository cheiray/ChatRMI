import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Chat_history_service_impl implements Chat_history_service {

    private static final String HISTORY_FILE_PATH = "chat_history.txt";

    /*@Override
    public void saveMessage(ChatMessage message) throws RemoteException {
        try (FileWriter writer = new FileWriter(HISTORY_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(message.getTimestamp() + " " + message.getSender() + ": " + message.getMessage());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    private static ChatMessage parseChatMessage(String line) {
        int timestampEndIndex = line.indexOf(" - ");
        String timestamp = line.substring(0, timestampEndIndex);

        int usernameEndIndex = line.indexOf(": ");
        String username = line.substring(timestampEndIndex + 3, usernameEndIndex);

        String message = line.substring(usernameEndIndex + 2);

        return new ChatMessage(timestamp, username, message);
    }
    // We actually just send the file
    @Override
    public synchronized List<ChatMessage> retrieveChatHistory(Client client) throws RemoteException {
        List<ChatMessage> history = new ArrayList<>();
        try (FileReader reader = new FileReader(HISTORY_FILE_PATH);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                ChatMessage chatMessage = parseChatMessage(line);
                int timestampEndIndex = line.indexOf(" - ");
                String timestamp = line.substring(0, timestampEndIndex);

                int usernameEndIndex = line.indexOf(": ");
                String username = line.substring(timestampEndIndex + 3, usernameEndIndex);

                String message = line.substring(usernameEndIndex + 2);

                history.add(new ChatMessage(username, message, timestamp));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }
}
