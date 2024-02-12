import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client {
    public static void main(String [] args)
    {
        try {
            if(args.length < 1)
            {
                System.out.println("Usage: java Client <rmiregistry>");
                return;
            }

            String host = args[0];

            Registry registry = LocateRegistry.getRegistry(host);

            Chat chat = (Chat) registry.lookup("ChatService");

            String enter = "";
            Scanner scanner = new Scanner(System.in);
            while(!enter.equals("Quit()"))
            {
                enter = scanner.nextLine();
                chat.newMsg(enter);
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
