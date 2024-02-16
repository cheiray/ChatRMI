import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Chat_history_service extends Remote {
    List<ChatMessage> retrieveChatHistory(Client client) throws RemoteException;
}
