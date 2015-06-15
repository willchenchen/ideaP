import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by will on 14-12-3.
 */
public class Main {
    public static void main(String[] args) {
        Process_Text r=new Process_Text();
        LinkedList<String> rd_txt;
        ArrayList<String> rd_txt1,rd_txt2,rd_txt3;
        String[] r1=null;
        /*读取不同版块数据（汽车、二手、房产、招聘）*/
        rd_txt=r.read_text("/home/will/data/olive/car/car.txt","<#>\r\n");
        /*读取区域数据(四个版块均需区域挖掘)*/
        rd_txt1=r.read_text1("/home/will/data/olive/dic_area.txt",","," New South Wales");
        /*读取汽车品牌数据(only汽车)*/
        rd_txt2=r.read_text2("/home/will/data/olive/car/汽车估价数据.txt","<#>");
        /*读取二手商品列表数据(only二手商品)*/
        rd_txt3=r.read_text3("/home/will/data/olive/dic_es.txt");
        /*标定汽车售价的关键字列表（only汽车）*/
        String[] ps={"$","售价","价格"};
        /*标定房租金额的关键字列表（only房产）*/
        String[] rent={"$","刀","单人","双人","rent","租金","房租","per","全包","一周","/人","room","每周","/周","bill","/w","1人"};
        /*标定变速箱的关键字列表（only汽车）*/
        String[] trans={"手自一体","手动","自动"};
        ArrayList<String> trans1=new ArrayList<String>(Arrays.asList(trans));
        /*标定公里数的关键字列表（only汽车）*/
        String[] mile={"公里","km"};
        /*标定是否送货*/
        String[] is_deliver={"送货","delivery"};
        /*标定是否全职*/
        String[] is_ft={"全职","full-time","full time"};

        int p_start=0;
        int p_end=10;
        int m_start=-10;
        int m_end=1;
        ArrayList<String> result=new ArrayList();
        /*for循环读取原始数据，各个版块都一样*/
        for(String each:rd_txt){
            r1=each.split("<#>");
            String r2=r1[0]+r1[2];
            /*汽车*/
            /*区域挖掘*/
            /*r2：是标题+内容，rd_txt1:区域字典*/
            String r_area=r.area_analy(r2,rd_txt1);
            if(!r_area.equals("")){r1[8]=r_area;}
            /*价格挖掘*/
            /*ps:标定价格的关键字，p_start:关键字位置的前几位，P_end:关键字位置的后几位*/
            String r_price=r.price_analy(r2,ps,p_start,p_end);
            if(!r_price.equals("")){r1[11]=r_price;}
            /*品牌挖掘*/
            /*rd_txt2:汽车品牌数据*/
            String r_brand=r.area_analy(r2,rd_txt2);
            if(!r_brand.equals("")){r1[9]=r_brand;}
            /*变速箱种类挖掘*/
            /*trans1:变速箱关键字*/
            String r_trans=r.area_analy(r2,trans1);
            if(!r_trans.equals("")){r1[17]=r_trans;}
            /*公里数挖掘*/
            /*mile:公里数关键字,m_start:关键字位置前几位，m_end：关键字位置后几位*/
            String r_mile=r.price_analy(r2,mile,m_start,m_end);
            if(!r_mile.equals("")){r1[18]=r_mile;}
            /*二手商品*/
            /*区域挖掘跟汽车一样*/
            /*商品大类、小类挖掘*/
            /*rd_txt3:二手商品列表*/
            String[] r_prod=r.prod_analy(r2,rd_txt3);
            if(!r_prod[1].equals("")){
                r1[15]=r_prod[0];
                r1[16]=r_prod[1];
            }
            /*商品是否送货*/
            String r_deliver=r.deliver_analy(r2,is_deliver,"送货","不送货");
            r1[17]=r_deliver;

            /*房产*/
            /*区域挖掘跟汽车一样*/
            /*租金挖掘跟汽车价格挖掘方法一样*/

            /*招聘*/
            /*区域挖掘跟汽车一样*/
            /*薪水挖掘跟汽车价格挖掘方法一样*/
            /*是否全职跟二手是否送货方法一样*/


            String r3="";
            for(String each1:r1){
                r3=r3+each1+"<#>";
            }
            result.add(r3);
        }
    }
}


class Process_Text{
    /*args1:抓取源数据地址，args2:分隔符*/
    static LinkedList<String> read_text(String args1,String args2){
        BufferedReader in=null;
        LinkedList<String> rd_out=new LinkedList<String>();
        try{
            in=new BufferedReader(new FileReader(args1));
            char[] rd=new char[8192];
            int n;
            String popstr="";
            while((n=in.read(rd))!=-1){
                String rd1=String.valueOf(rd);
                rd1=popstr+rd1;
                String[] rd2=rd1.split(args2);
                for(String each:rd2){
                    rd_out.push(each);
                }
                popstr=rd_out.pop();
                rd=new char[8192];
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                in.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return rd_out;
    }
    /*args:区域文本地址，args1:分隔符，args2:省份名称*/
    static ArrayList<String> read_text1(String args,String args1,String args2){
        BufferedReader in=null;
        ArrayList<String> out=new ArrayList<String>();
        try{
            in=new BufferedReader(new FileReader(args));
            String r;
            while((r=in.readLine())!=null){
                String[] r1=r.split(args1);
                if(r1[1].equals(args2)){
                    out.add(r1[0]);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                in.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return out;
    }
    /*args:汽车估价数据,args1:分隔符*/
    static ArrayList<String> read_text2(String args,String args1){
        BufferedReader in=null;
        ArrayList<String> out=new ArrayList<String>();
        try{
            in=new BufferedReader(new FileReader(args));
            String r;
            while((r=in.readLine())!=null){
                String[] r1=r.split(args1);
                if(!out.contains(r1[0])){
                    out.add(r1[0]);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                in.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return out;
    }
    /*商品列表数据地址*/
    static ArrayList<String> read_text3(String args){
        BufferedReader in=null;
        ArrayList out=new ArrayList();
        try{
            in=new BufferedReader(new FileReader(args));
            String r;
            while ((r=in.readLine())!=null){
                out.add(r);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                in.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return out;
    }
    /*args：是标题+内容，args1:区域列表*/
    static String area_analy(String args,ArrayList<String> args1){
        String area="";
        for(String each:args1){
            int n;
            if((n=args.toLowerCase().indexOf(each.toLowerCase()))!=-1){
                area=area+":"+each;
            }
        }
        return area;
    }
    /*args:标题+内容，args1:价格的关键字列表，args2:关键字位置的前几位，args3:关键字位置的后几位*/
    static String price_analy(String args,String[] args1,int args2,int args3){
        String price="";
        Pattern p=Pattern.compile("[0-9]+(万|w)?",Pattern.CASE_INSENSITIVE);
        int n;
        for(String each:args1){
            if((n=args.toLowerCase().indexOf(each.toLowerCase()))!=-1){
                String sub=args.substring(n+args2,n+args3);
                Matcher m=p.matcher(sub);
                if(m.find()){
                    price=m.group();
                    break;
                }
            }
        }
        return price;
    }
    /*args:标题+内容，args1:二手商品列表*/
    static String[] prod_analy(String args,ArrayList<String> args1){
        Map<String,String> prod=new HashMap();
        int n;
        String[] r;
        String[] out=new String[2];
        for(String each:args1){
            r=each.split("\t");
            if((n=args.toLowerCase().indexOf(r[0].toLowerCase()))!=-1){
                if(!prod.containsKey(r[1])){
                    prod.put(r[1],r[0]);
                }
                else{
                    prod.put(r[1],prod.get(r[1])+":"+r[0]);
                }

            }
        }
        String o1="";
        String o2="";
        for(Map.Entry<String,String> entry:prod.entrySet()){
            o1=o1+":"+entry.getKey();
            o2=o2+":"+entry.getValue();
        }
        out[0]=o1;
        out[1]=o2;
        return out;
    }
    /*args:标题+内容，args1:是否送货关键字列表，args2:“送货”输出结果（写死的），args3:“不送货”输出结果（写死的）*/
    static String deliver_analy(String args,String[] args1,String args2,String args3){
        Pattern p=Pattern.compile("不|not");
        int n;
        String out="";
        for(String each:args1){
            if((n=args.toLowerCase().indexOf(each.toLowerCase()))!=-1){
                String sub=args.substring(n-5,n+1);
                Matcher m=p.matcher(sub);
                if(m.find()){
                    out=args2;
                }
            }
        }
        if(out.equals("")){
            out=args3;
        }
        return out;
    }
}