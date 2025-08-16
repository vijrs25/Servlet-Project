package com.basic;

import javax.servlet.http.*;
import java.io.*;
import com.google.gson.Gson;
import com.pojo.MyReponse;
import com.pojo.MyRequest;

@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {

    private void addCors(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*"); // dev only
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCors(resp);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCors(resp);
        resp.setContentType("application/json");
        String q = req.getParameter("username");
        if (q == null) q = "";
        resp.getWriter().write("{\"response\":\"" + q.toUpperCase() + "\"}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        addCors(resp);
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        com.google.gson.Gson gson = new com.google.gson.Gson();
        MyRequest in = gson.fromJson(req.getReader(), MyRequest.class);
        if(in.email.equals("")) {
        	 throw new IllegalArgumentException("Email field is missing or null");
        }
        
        MyReponse out = new MyReponse();
        out.username = in.username.toUpperCase()+" Apended form request";
        out.email = in.email.toUpperCase()+ "Append from reque";
        out.age = in.age+22;
        		
        resp.getWriter().write(gson.toJson(out));
    }
}

