package com.utility.otp.email;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Random;

@WebServlet("/sendEmailOtp")
public class SendEmailOtpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");

        // Generate OTP
        int otp = 100000 + new Random().nextInt(900000);

        // Send email
        EmailUtil.sendEmail(email, "Your OTP Code", "Your OTP is: " + otp);

        // Save OTP in session
        HttpSession session = req.getSession();
        session.setAttribute("otp", otp);

        resp.getWriter().write("✅ OTP sent to " + email);
    }
}
