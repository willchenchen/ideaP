package com.asou;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by will on 15/4/10.
 */
public class HelloServlet extends HttpServlet {
    public void service(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out=response.getWriter();
        HelloSql sqlQuery=new HelloSql();
        String[] queryArgs=new String[2];
        queryArgs[0]=request.getParameter("starDate");
        queryArgs[1]=request.getParameter("endDate");
        JSONObject queryOut=sqlQuery.operateDB(queryArgs);

        JSONObject jo=new JSONObject();

        jo.put("result",queryOut);
        jo.put("errorCode",0);

        out.println(jo.toString());
        out.close();
    }
}
