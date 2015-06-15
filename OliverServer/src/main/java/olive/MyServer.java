package olive;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class MyServer
{
    public static void main( String[] args )
    {
        try{
            ServerSocket ss=new ServerSocket(8000);
            System.out.println("监听前...");
            Socket s=ss.accept();
            System.out.println("监听后...");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
