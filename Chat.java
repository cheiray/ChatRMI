import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote{

    //public ArrayList<String> getHistory() throws RemoteException;

    public void newMsg(String msg) throws RemoteException;
}
