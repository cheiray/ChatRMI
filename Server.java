import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server {


    public static void main(String[] args)
    {

        try {
            Chat_impl chat = new Chat_impl();
            //Chat_history_service chatHistoryService = new Chat_history_service_impl();

            Chat chat_stub = (Chat) UnicastRemoteObject.exportObject(chat, 0);
            // we should consider the following service
            //Chat_history_service chatHistoryServiceStub = (Chat_history_service) UnicastRemoteObject.exportObject(chatHistoryService, 0);
            // We should consider implementing a basic administrating service to ban users

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ChatService", chat_stub);
            //registry.rebind("ChathistoryService", chatHistoryServiceStub);

            System.out.println("Server Ready");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Code to execute when the program is shutting down
                System.out.println("Shutting down...");
                try {
                    chat.saveMessages();
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // Additional cleanup or tasks can be performed here
            }));

           


        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
        }
    }

    
}

