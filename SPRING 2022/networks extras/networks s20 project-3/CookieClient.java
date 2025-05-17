import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;

public class CookieClient
{
    public static void main (String[] args)
    {
        try
        {
            Socket client = new Socket("localhost", 3800);

            InputStream clientInput = client.getInputStream();

            System.out.println("Connection established");

            LinkedList<Byte> retrievedBytes = new LinkedList<>(); 
            int lookedUpByte = clientInput.read();

            System.out.println("Looking for input...");
            while (lookedUpByte != -1)
            {
                retrievedBytes.add( (byte) lookedUpByte);
                lookedUpByte = clientInput.read();
            }

            for(int i = 0; i < retrievedBytes.size(); i++)
            {
                byte retrievedByte = retrievedBytes.get(i);
                System.out.print((char) retrievedByte);
            }
            System.out.println();
            System.out.println("Exiting");
        }
        catch(Exception e)
        {
            System.out.println("CANNOT CONNECT TO SERVER");
        }
    }

}



