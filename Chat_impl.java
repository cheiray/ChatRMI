import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Chat_impl implements Chat{
    ArrayList<String> history;
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
        history = new ArrayList<>();
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
        history.add(msg);
        System.out.println(getTime() +" - user: '"+ currentClient.getName()+"' said : "+msg);
        for(Client_chat client : clients){
            // ADD THE CHECK THAT IT IS NOT THE CURRENT CLIENT SENDING THE MESSAGE
            if(currentClient != client)
                client.receiveMessage(getTime() +" - user: '"+ currentClient.getName()+"' said : "+msg);
            else
                client.receiveMessage(getTime() +" - You have said : "+msg);
        }
    }

    

    @Override
    public void disconnect(Client_chat client) throws RemoteException {
        // TODO Auto-generated method stub
        clients.remove(client);
        for(Client_chat aclient : clients){
            // ADD THE CHECK THAT IT IS NOT THE CURRENT CLIENT SENDING THE MESSAGE
            if(client != aclient)
                aclient.receiveMessage(java.time.LocalDateTime.now() +" - user: '"+ client.getName()+"' has left the chat");
        }
        System.out.println(this.getTime() +" - user: '"+ client.getName()+"' has left the chat");
        
        //return 1;
    }

    @Override
    public void connect(Client_chat client) throws RemoteException {
        // Remember to add the login verifications to accept or refuse the incoming connexion
        for(Client_chat aclient : clients){
            // ADD THE CHECK THAT IT IS NOT THE CURRENT CLIENT SENDING THE MESSAGE
            if(client != aclient)
                aclient.receiveMessage(java.time.LocalDateTime.now() +" - user: '"+ client.getName()+"' has joined the chat");
            else
                client.receiveMessage("You have joined the chat");
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
                String[] parts = line.split(" ", 3);
                String timestamp = parts[0];
                String sender = parts[1];
                String message = parts[2];
                history.add(new ChatMessage(sender, message, timestamp));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }
}
