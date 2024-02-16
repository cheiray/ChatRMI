import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface Chat extends Remote{

    public List<ChatMessage> getHistory() throws RemoteException;

    public void newMsg(Client_chat currentClient,String msg) throws RemoteException;
    public void connect(Client_chat client) throws RemoteException;
    public void disconnect(Client_chat client) throws RemoteException;
}
