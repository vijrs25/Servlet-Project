package com.utility.otp.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sendSms")
public class SendSmsServlet extends HttpServlet {
    private static final String AUTH_KEY = "465847Ah0X4OuCX468a9dfeeP1"; 
    private static final String SENDER_ID = "arnease"; // 👈 apna sender id (MSG91 dashboard se)
    private static final String ROUTE = "4"; // transactional route

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phone = req.getParameter("phone");
        String otp = String.valueOf((int)(Math.random()*900000) + 100000);

        try {
            String url = "https://api.msg91.com/api/sendhttp.php?" +
                    "mobiles=" + URLEncoder.encode(phone, "UTF-8") +
                    "&authkey=" + URLEncoder.encode(AUTH_KEY, "UTF-8") +
                    "&route=" + ROUTE +
                    "&sender=" + URLEncoder.encode(SENDER_ID, "UTF-8") +
                    "&message=" + URLEncoder.encode("Your OTP is " + otp, "UTF-8") +
                    "&country=91";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder responseMsg = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseMsg.append(inputLine);
            }
            in.close();

            resp.setContentType("application/json");
            resp.getWriter().write("{\"status\":\"SUCCESS\",\"otp\":\"" + otp + "\",\"msgid\":\"" + responseMsg + "\"}");

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"status\":\"FAIL\",\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}

