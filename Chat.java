import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Chat extends Remote{

    //public ArrayList<String> getHistory() throws RemoteException;

    public void newMsg(String msg) throws RemoteException;
    public void connect(Client_chat client) throws RemoteException;
    public void disconnect(Client_chat client) throws RemoteException;
}
