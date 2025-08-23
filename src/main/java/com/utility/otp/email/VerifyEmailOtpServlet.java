package com.utility.otp.email;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/verifyEmailOtp")
public class VerifyEmailOtpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userOtp = Integer.parseInt(req.getParameter("otp"));
        HttpSession session = req.getSession();
        Integer actualOtp = (Integer) session.getAttribute("otp");

        if (actualOtp != null && userOtp == actualOtp) {
            session.setAttribute("otpVerified", true);
            resp.getWriter().write("✅ OTP Verified!");
        } else {
            resp.getWriter().write("❌ Invalid OTP");
        }
    }
}
