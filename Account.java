import java.lang.reflect.Array;
import java.rmi.RemoteException;

public interface Account extends Remote{
    
    public int connect(ArrayList<String> connection_list) throws RemoteException;

    public String send(String msg) throws RemoteException;

    public int disconnect() throws RemoteException;
}
