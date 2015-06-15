import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by will on 14-12-18.
 */
public class Main{
    public static void main(String[] args){
        URL url;
        try{
            String a="http://115.159.25.211:9200/apple_search_result_cn_phone/_search?size=2000";
            url=new URL(a);
            URLConnection conn=url.openConnection();

            conn.setDoOutput(true);
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            JSONObject j0=new JSONObject();
            JSONObject j00=new JSONObject();
            JSONObject j1=new JSONObject();
            JSONObject j11=new JSONObject();
            JSONObject j111=new JSONObject();
            JSONObject j2=new JSONObject();
            JSONObject j22=new JSONObject();
            JSONObject j222=new JSONObject();
            JSONArray must=new JSONArray();
            JSONObject must1=new JSONObject();
            JSONObject bool=new JSONObject();
            JSONObject query=new JSONObject();

            j0.put("word","二手车");
            j00.put("term",j0);

            j1.put("gte","2014-12-21");
            j1.put("lte","2014-12-21");
            j11.put("date",j1);
            j111.put("range",j11);

            j2.put("lte",10);
            j22.put("rank",j2);
            j222.put("range",j22);

            must.put(j00);
            must.put(j111);
            must.put(j222);
            must1.put("must",must);
            bool.put("bool",must1);
            query.put("query",bool);
            writer.write(query.toString().getBytes());

            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String br1=new String(br.readLine());
            JSONObject br2=new JSONObject(br1);
            JSONObject o1=br2.getJSONObject("hits");
            JSONArray o2=o1.getJSONArray("hits");
            for(int i=0;i<o2.length();i++){
                System.out.println(o2.getJSONObject(i).getJSONObject("_source"));
            }

            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
