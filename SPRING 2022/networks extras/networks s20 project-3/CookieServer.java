import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.io.OutputStream;


public class CookieServer
{

    private static final int PORT = 3800;

    public static ServerSocket server;
    //all fortunes
    public static void main (String[] args)
    {
        //filling the fortune database
        Fortune.loadFortunes();

        try
        {
            ServerSocket server = new ServerSocket(PORT);
            Socket connectionSocket = server.accept();

            System.out.println("Connection established");

            OutputStream outputStr = connectionSocket.getOutputStream();


            //creating a random fortune cookie
            Fortune randomFortune = new Fortune();
            System.out.println("Sending the fortune...");

            outputStr.write(randomFortune.fortuneBytes);
            //Byte a = 100;
            //System.out.println(a.toString());
            System.out.println("Fortune Sent");

            // connectionSocket.close();
            // server.close();
            System.out.println("Exiting");
        }
        catch(Exception e)
        {
            System.out.println("CONNECTION ERROR TO CLIENT");
        }
        
    }


    /**
     * Creates a server socket at a given port
     * @param port
     */
    private static void createSocket (int port)
    {
         
    }
}




class Fortune
{
    //all fortunes
    private static LinkedList<String> fortuneDataBase = new LinkedList<>();

    public String fortuneMsg;
    public byte[] fortuneBytes;

    public Fortune()
    {
        Random rand = new Random();
        int randPos = rand.nextInt(Fortune.fortuneDataBase.size());

        this.fortuneMsg = fortuneDataBase.get(randPos);

        this.fortuneBytes = this.toBytesArray(this.fortuneMsg);
    }

    private byte[] toBytesArray(String msg)
    {
        char[] msgChars = msg.toCharArray();
        
        byte[] toReturn = new byte[msgChars.length];

        for (int i = 0; i < msgChars.length; i++)
        {
            toReturn[i] = (byte) msgChars[i];
        }

        return toReturn;
    }

    /**
     * This will be called in server's main method to load
     * all fortunes into the vector
     */
    public static void loadFortunes()
    {
        Fortune.fortuneDataBase.add("I am Dhyey");
        Fortune.fortuneDataBase.add("I love Math");
        Fortune.fortuneDataBase.add("I love CS");
        Fortune.fortuneDataBase.add("I love Badminton");
        Fortune.fortuneDataBase.add("I love Archery");
        Fortune.fortuneDataBase.add("I am from India");
        Fortune.fortuneDataBase.add("My SSN is ................");
        Fortune.fortuneDataBase.add("I am an international student on F1 visa");
        Fortune.fortuneDataBase.add("I study at Amherst College");
        Fortune.fortuneDataBase.add("I am in the class of 2025");
        Fortune.fortuneDataBase.add("I love traveling");
        Fortune.fortuneDataBase.add("I love software engineering and quantitative trading");
        Fortune.fortuneDataBase.add("I love consulting too :)");
    }

    @Override
    public String toString()
    {
        return this.fortuneMsg;
    }
}