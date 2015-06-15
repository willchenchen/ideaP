package com.asou;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] sqlAgrs=new String[3];
        sqlAgrs[0]="2015-04-15";
        sqlAgrs[1]="10";
        Testsql sqlEnqury=new Testsql();
        /*HashMap<String,ArrayList<String>>sqlOut=sqlEnqury.allUser(sqlAgrs);
        for(String i:sqlOut.keySet()){
            System.out.println(i+"\t"+sqlOut.get(i).get(0)+"\t"+sqlOut.get(i).get(1));
        }*/

        sqlAgrs[0]="0";
        sqlAgrs[1]="2015-04-15";
        sqlAgrs[2]="10";
        HashMap<String,String> sqlOut1=sqlEnqury.retainedUser(sqlAgrs);
        for(String i:sqlOut1.keySet()){
            System.out.println(i+"\t"+sqlOut1.get(i));
        }
    }
}
