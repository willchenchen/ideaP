package will;

import apriori4j.AnalysisResult;
import apriori4j.AssociationRule;
import apriori4j.Transaction;
import java.io.FileWriter;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by will on 15-1-7.
 */
public class AssoRule {

    public Map<String,String> rdfile(String fadress){
        BufferedReader br=null;
        Map<String,String> result=new HashMap<String,String>();
        try{
            br=new BufferedReader(new FileReader(fadress));
            String r;
            while((r=br.readLine())!=null){
                String[] r1=r.split("\t");
                if(!result.containsKey(r1[1])){
                    result.put(r1[1],r1[0]);
                }
                else{
                    System.out.println("匹配字典重复");
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                br.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public ArrayList<Transaction> cntdbsql(Map<String,String> args1){
        ArrayList<Transaction> result=new ArrayList<Transaction>();
        Connection cn=null;
        Statement st=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn= DriverManager.getConnection("jdbc:mysql://123.57.66.5:3306/asou_data","will","qweasd123");
            st=cn.createStatement();
            rs=st.executeQuery("select distinct user_id,package_name from fs_user_package;");
            Map<String,Transaction> ups=new HashMap<String, Transaction>();
            while(rs.next()){
                String r1=rs.getString(1);
                String r2=rs.getString(2);
                if(args1.containsKey(r2)){
                    if(!ups.containsKey(r1)){
                        Set<String> t = new HashSet<String>();
                        t.add(args1.get(r2));
                        Transaction tt = new Transaction(t);
                        ups.put(r1,tt);
                    }
                    else{
                        ups.get(r1).getItems().add(args1.get(r2));
                    }
                }
                else{
                    System.out.println(r2);
                }
            }
            this.excsk(ups);
            for(Transaction value:ups.values()){
                result.add(value);
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                rs.close();
                st.close();
                cn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public void wtfile(AnalysisResult result){
        try {
            FileWriter fw = new FileWriter("/home/will/r_pg/jresult.txt");
            for (AssociationRule rule : result.getAssociationRules()) {
                String s="";
                for (String lhs : rule.getLeftHandSide()) {
                    s = s + lhs + ",";
                }
                s = s + "==>>";
                for (String rhs : rule.getRightHandSide()) {
                    s = s + rhs + ",";
                }
                s = s + "==>>" + rule.getConfidence()+"\n";
                fw.write(s);
            }
            fw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void excsk(Map<String,Transaction> inptdata){
        Connection cn=null;
        Statement st=null;
        ResultSet rs=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn=DriverManager.getConnection("jdbc:mysql://123.57.66.5:3306/asou_data","will","qweasd123");
            st=cn.createStatement();
            rs=st.executeQuery("select distinct user_id,appid_or_friendid from actual_completion_table where classify=0;");
            while (rs.next()){
                String r1=rs.getString(1);
                String r2=rs.getString(2);
                if(inptdata.containsKey(r1)){
                    if(inptdata.get(r1).getItems().contains(r2)){
                        System.out.println("drop");
                        inptdata.get(r1).getItems().remove(r2);
                    }
                }
            }
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Map<String,String> pnappid(){
        Map<String,String> result=new HashMap<String, String>();
        Connection cn=null;
        Statement st=null;
        ResultSet rs=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn= DriverManager.getConnection("jdbc:mysql://123.57.66.5:3306/asou_data", "will", "qweasd123");
            st=cn.createStatement();
            rs=st.executeQuery("select distinct package_name,appid from app_info_cn;");
            while(rs.next()){
                String r1=rs.getString(1);
                String r2=rs.getString(2);
                if(!result.containsKey(r1)){
                    result.put(r1,r2);
                }
                else{
                    System.out.println("匹配字典重复");
                }
            }

        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                rs.close();
                st.close();
                cn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
