package com.asou;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by will on 15/4/11.
 */
public class Testsql {
    public HashMap<String,ArrayList<String>> allUser(String[] parm) {
        HashMap<String,ArrayList<String>> finRsut = new HashMap<String, ArrayList<String>>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://123.57.66.5:3306/asou_data", "will", "qweasd123");
            for(int i=0;i<Integer.parseInt(parm[1]);i++){
                ps = cn.prepareStatement("select date_sub(?,interval ? day),count(distinct c.userid),count(case when c.classify=1 then 1 end) from (select b.user_id userid,b.classify classify,max(user_status) mxs from (select user_id,max(start_date) mxd from user_dictionary_table where start_date<=date_sub(?,interval ? day) group by user_id) a, user_dictionary_table b where start_date<=date_sub(?,interval ? day) and a.user_id=b.user_id and b.start_date=a.mxd group by b.user_id) c where c.mxs=2");
                ps.setString(1, parm[0]);
                ps.setInt(2, i);
                ps.setString(3, parm[0]);
                ps.setInt(4, i);
                ps.setString(5, parm[0]);
                ps.setInt(6, i);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ArrayList<String> tempList=new ArrayList<String>();
                    tempList.add(rs.getString(2));
                    tempList.add(rs.getString(3));
                    finRsut.put(rs.getString(1), tempList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return finRsut;
    }

    public HashMap<String,String> retainedUser(String[] parm){
        HashMap<String,String> finrsut=new HashMap<String, String>();
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://123.57.66.5:3306/asou_data", "will", "qweasd123");
            ps=cn.prepareStatement("select start_date,count(distinct user_id) from user_dictionary_table where user_status=? and start_date between date_sub(?,interval ? day)  and ?  group by start_date");
            ps.setInt(1,Integer.parseInt(parm[0]));
            ps.setString(2,parm[1]);
            ps.setInt(3, Integer.parseInt(parm[2]));
            ps.setString(4,parm[1]);
            rs=ps.executeQuery();
            while(rs.next()){
                finrsut.put(rs.getString(1),rs.getString(2));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                ps.close();
                cn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return finrsut;
    }

}
