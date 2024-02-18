import java.io.Serializable;
import java.rmi.RemoteException;

public class Client_chat_impl  extends java.rmi.server.UnicastRemoteObject implements Client_chat {
    

    private String pseudo;
    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public Client_chat_impl() throws RemoteException{
        super();
    }

    // this method is used to receive a message on the local client it's not registered in the registery but just passed as a reference
    @Override
    public void receiveMessage(ChatMessage message) throws RemoteException {
        this.gui.addMessage(message );
        System.out.println(message.toString());
        //return 1;
    }

    @Override
    public void receiveNotification(String message) throws RemoteException {
        this.gui.addNotification(message );
        System.out.println(message.toString());
        //return 1;
    }

    @Override
    public String getName() throws RemoteException {
        // TODO 
        // We shouldn't rely on the user giving us his pseudonym as he can easily change it it's not trustable
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    
}
