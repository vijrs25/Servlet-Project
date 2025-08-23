package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.Random;

@WebServlet("/testOtp")
public class TestOtpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Send OTP to a hardcoded email for testing
        String email = req.getParameter("email");
        if (email == null || email.isEmpty()) {
            email = "linuxusir@gmail.com"; // default test email
        }

        int otp = 100000 + new Random().nextInt(900000);

        // send OTP
        EmailUtil.sendEmail(email, "Test OTP", "Your OTP is: " + otp);

        // store OTP in session
        HttpSession session = req.getSession();
        session.setAttribute("otp", otp);

        resp.getWriter().write("✅ OTP sent to: " + email + " (check your inbox)");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userOtp = Integer.parseInt(req.getParameter("otp"));
        HttpSession session = req.getSession();
        Integer actualOtp = (Integer) session.getAttribute("otp");

        if (actualOtp != null && userOtp == actualOtp) {
            session.setAttribute("otpVerified", true);
            resp.getWriter().write("✅ OTP Verified (Test Mode)!");
        } else {
            resp.getWriter().write("❌ Wrong OTP, try again.");
        }
    }
}
