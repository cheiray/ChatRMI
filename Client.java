import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client {

    public static void main(String [] args)
    {
        try {
           /*  if(args.length < 1)
            {
                System.out.println("Usage: java Client <rmiregistry>");
                return;
            }*/

            String host = "0";//args[0];

            Registry registry = LocateRegistry.getRegistry(host);

            Chat chat = (Chat) registry.lookup("ChatService");

            // we also need to pass the reference of the current client so the remote server gain access to it during the registering process
            Client_chat client = new Client_chat_impl("test"); // Maybe we should just make the Client class inherit the Client_chat_impl class as we don't really benefit from this.
            if (chat == null || client == null) {
                System.out.println("Error: chat or client object null");
            }
            chat.connect(client);

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
