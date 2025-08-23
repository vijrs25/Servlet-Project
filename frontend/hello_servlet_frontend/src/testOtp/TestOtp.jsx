import React, { useState } from "react";

function TestOtp() {
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [otpSent, setOtpSent] = useState(false);
  const [message, setMessage] = useState("");

  // ✅ Send OTP
  const handleSendOtp = async () => {
    try {
      const res = await fetch("http://localhost:8080/ServletWebProject/testOtp?email=" + email, {
        method: "GET",
      });
      const data = await res.text();
      setMessage(data);
      if (res.ok) setOtpSent(true);
    } catch (err) {
      console.error(err);
      setMessage("❌ Failed to send OTP");
    }
  };

  // ✅ Verify OTP
  const handleVerifyOtp = async () => {
    try {
      const res = await fetch("http://localhost:8080/ServletWebProject/testOtp", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ otp }).toString(),
      });
      const data = await res.text();
      setMessage(data);
    } catch (err) {
      console.error(err);
      setMessage("❌ OTP verification failed");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>📧 Test Email OTP</h2>

      <div>
        <input
          type="email"
          placeholder="Enter your email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <button onClick={handleSendOtp}>Send OTP</button>
      </div>

      {otpSent && (
        <div style={{ marginTop: "10px" }}>
          <input
            type="text"
            placeholder="Enter OTP"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
          />
          <button onClick={handleVerifyOtp}>Verify OTP</button>
        </div>
      )}

      {message && (
        <div style={{ marginTop: "20px", fontWeight: "bold" }}>{message}</div>
      )}
    </div>
  );
}

export default TestOtp;
