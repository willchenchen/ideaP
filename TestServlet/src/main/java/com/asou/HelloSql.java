package com.asou;

import java.sql.*;
import java.util.HashMap;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by will on 15/4/10.
 */
public class HelloSql {
    public JSONObject operateDB(String[] parm){
        String fd=parm[0];
        String ld=parm[1];
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        HashMap<String,String> tempMap=new HashMap<String,String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn= DriverManager.getConnection("jdbc:mysql://123.57.66.5:3306/asou_data","will","qweasd123");
            ps=cn.prepareStatement("");
            ps.setString(1,fd);
            ps.setString(2,ld);
            System.out.println(ps);
            rs=ps.executeQuery();
            while (rs.next()){
                tempMap.put(rs.getString(1),rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSONObject.fromObject(tempMap);
    }
}
