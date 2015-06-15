package OlivePractice;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String url="https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
        HttpClient client= HttpClientBuilder.create().build();
        HttpGet request=new HttpGet(url);
        try {
            HttpResponse responses=client.execute(request);
            System.out.println("Respinse Code:"+responses.getStatusLine());

            BufferedReader rd=new BufferedReader(new InputStreamReader(responses.getEntity().getContent()));
            String line=null;
            while((line=rd.readLine())!=null){
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
