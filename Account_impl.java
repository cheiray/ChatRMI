import java.rmi.RemoteException;
import java.util.ArrayList;

public class Account_impl implements Account {
    String pseudo;

    public Account_impl(String pseudo, String pw)
    {
        this.pseudo = pseudo;
    }

    @Override
    public int connect(ArrayList<String> connection_list) throws RemoteException
    {
        String connection = pseudo ;
        if(connection_list.contains(connection))
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }

    @Override
    public String send(String msg) throws RemoteException
    {
        return null;
    }

    public int disconnect() throws RemoteException 
    {
        return 0;
    }
}
