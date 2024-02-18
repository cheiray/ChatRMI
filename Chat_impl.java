import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Chat_impl implements Chat{
    ArrayList<ChatMessage> historyBuffer; // this is used to buffer the current history, it won't be saved in the history file until the next server shutdown for performance reason
    ArrayList<Client_chat> clients;

    private static final String HISTORY_FILE_PATH = "chat_history.txt";

    public String getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }

    public Chat_impl()
    {
        historyBuffer = new ArrayList<>();
        clients = new ArrayList<>();
    }
    // This function is used to "broadcast" an incoming message from a client to every other clients
    @Override
    public void newMsg(Client_chat currentClient,String msg) throws RemoteException {
        if(msg.equals("Quit()"))
        {
            disconnect(currentClient);
            return;// replace return with clients.remove(currentClients))
        }
        //history.add(getTime() +" - user: '"+ currentClient.getName()+"' said : "+msg);
        ChatMessage theMessage = new ChatMessage(currentClient.getName(), msg, getTime());
        historyBuffer.add(theMessage);
        System.out.println(getTime() +" - user: '"+ currentClient.getName()+"' said : "+msg);
        for(Client_chat client : clients){
            // ADD THE CHECK THAT IT IS NOT THE CURRENT CLIENT SENDING THE MESSAGE
            if(currentClient != client)
                client.receiveMessage(theMessage);
            else
                client.receiveMessage(theMessage);
        }
    }

    

    @Override
    public void disconnect(Client_chat client) throws RemoteException {
        // TODO Auto-generated method stub
        clients.remove(client);
        for(Client_chat aclient : clients){
            // ADD THE CHECK THAT IT IS NOT THE CURRENT CLIENT SENDING THE MESSAGE
            if(client != aclient)
                aclient.receiveNotification(getTime() +" - user: '"+ client.getName()+"' has left the chat");
        }
        System.out.println(getTime() +" - user: '"+ client.getName()+"' has left the chat");
        
        //return 1;
    }

    @Override
    public void connect(Client_chat client) throws RemoteException {
        // Remember to add the login verifications to accept or refuse the incoming connexion
        for(Client_chat aclient : clients){
            // ADD THE CHECK THAT IT IS NOT THE CURRENT CLIENT SENDING THE MESSAGE
            if(client != aclient)
                aclient.receiveNotification(getTime() +" - user: '"+ client.getName()+"' has joined the chat");
            else
                client.receiveNotification("You have joined the chat");
            System.out.println(java.time.LocalDateTime.now() +" - user: '"+ client.getName()+"' has joined the chat");
        }
        clients.add(client);
        //return 1;
    }

    @Override
    public synchronized List<ChatMessage> getHistory() throws RemoteException {
       List<ChatMessage> history = new ArrayList<>();
        try (FileReader reader = new FileReader(HISTORY_FILE_PATH);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
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
        history.addAll(this.historyBuffer);
        return history;
    }

    public synchronized void saveMessages() throws RemoteException {
        try (FileWriter writer = new FileWriter(HISTORY_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (ChatMessage message : historyBuffer) {
                bw.write(message.getTimestamp() + " - " + message.getAuthor() + ": " + message.getMessage());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
