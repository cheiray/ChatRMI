import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {


    public static void main(String[] args)
    {

        try {
            Chat_impl chat = new Chat_impl();
            Chat chat_stub = (Chat) UnicastRemoteObject.exportObject(chat, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ChatService", chat_stub);

            System.out.println("Server Ready");

        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
        }
    }

    
}

