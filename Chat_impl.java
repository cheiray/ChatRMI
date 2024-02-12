import java.rmi.RemoteException;
import java.util.ArrayList;

public class Chat_impl implements Chat{
    ArrayList<String> history;

    public Chat_impl()
    {
        history = new ArrayList<>();
    }

    @Override
    public void newMsg(String msg) throws RemoteException {
        if(msg.equals("Quit()"))
        {
            return;
        }
        history.add(msg);
        System.out.println(msg);
    }
}
