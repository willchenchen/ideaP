package olive;

import java.net.Socket;

/**
 * Created by will on 15-2-27.
 */
public class MyClient {
    public static void main(String[] args){
        try{
            Socket s=new Socket("127.0.0.1",8000);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
