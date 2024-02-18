import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;


public class Client {
    private static Client_chat client;

    public static void displayChatHistory(List<ChatMessage> chatHistory) {
        for (ChatMessage message : chatHistory) {
            System.out.println(message);
        }
    }

    public static void main(String [] args)
    {
        try {
           /*  if(args.length < 1)
            {
                System.out.println("Usage: java Client <rmiregistry>");
                return;
            }*/

            int host = 0;//args[0];

            Registry registry = LocateRegistry.getRegistry(host);

            Chat chat = (Chat) registry.lookup("ChatService");

            // we also need to pass the reference of the current client so the remote server gain access to it during the registering process
            client = new Client_chat_impl();
            GUI gui = new GUI(chat,client);
             
            if (chat == null) {
                System.out.println("Error: chat or client object null");
            }
            String connexionStatus = chat.connect(client);
            System.out.println(connexionStatus);
            
            while (!connexionStatus.equals("success")) {
                connexionStatus = gui.reconnect();
                System.out.println(connexionStatus);
            }
                
            
            
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Code to execute when the program is shutting down
                try {
                    chat.disconnect(client);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Shutting down...");
                // Additional cleanup or tasks can be performed here
            }));
            //List<ChatMessage> history = chat.getHistory();
            //displayChatHistory(history);

            String enter = "";
            Scanner scanner = new Scanner(System.in);
            while(!enter.equals("Quit()"))
            {
                enter = scanner.nextLine();
                chat.newMsg(client,enter);
            }
            scanner.close();
            //String res = h.sayHello(info);
            //System.out.println(res);
        } catch(Exception e)
        {
            System.err.println("Error on client: "+ e);
        }
    }
}
