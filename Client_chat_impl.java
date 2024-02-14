import java.io.Serializable;
import java.rmi.RemoteException;

public class Client_chat_impl  extends java.rmi.server.UnicastRemoteObject implements Client_chat {
    

    private String pseudo;

    public Client_chat_impl(String pseudo) throws RemoteException{
        super();
        this.pseudo = pseudo;
    }

    // this method is used to receive a message on the local client it's not registered in the registery but just passed as a reference
    @Override
    public void receiveMessage(String message) throws RemoteException {
        // TODO Auto-generated method stub
        System.out.println(message);
        //return 1;
    }

    @Override
    public String getName() throws RemoteException {
        // TODO Auto-generated method stub
        return this.pseudo;
    }


    
}