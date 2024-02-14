import java.rmi.RemoteException;
import java.util.ArrayList;

public class Chat_impl implements Chat{
    ArrayList<String> history;
    ArrayList<Client_chat> clients;

    public Chat_impl()
    {
        history = new ArrayList<>();
        clients = new ArrayList<>();
    }
    // This function is used to "broadcast" an incoming message from a client to every other clients
    @Override
    public void newMsg(String msg) throws RemoteException {
        if(msg.equals("Quit()"))
        {
            return;
        }
        history.add(msg);
        System.out.println(msg);
        for(Client_chat client : clients){
            // ADD THE CHECK THAT IT IS NOT THE CURRENT CLIENT SENDING THE MESSAGE
            client.receiveMessage(msg);
        }
    }

    

    @Override
    public void disconnect(Client_chat client) throws RemoteException {
        // TODO Auto-generated method stub
        clients.remove(client);
        //return 1;
    }

    @Override
    public void connect(Client_chat client) throws RemoteException {
        // Remember to add the login verifications to accept or refuse the incoming connexion
        clients.add(client);
        //return 1;
    }
}
