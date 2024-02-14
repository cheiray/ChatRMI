import java.rmi.Remote;
import java.rmi.RemoteException;

// Remote defines the interface that is available remotely
//These functions defined here are the only ones that will be accessible remotely
public interface Client_chat extends Remote {


    public void receiveMessage(String message) throws RemoteException;
}