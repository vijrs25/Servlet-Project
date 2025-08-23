import React, { useState } from "react";

function SendEmailOtp() {
  const [email, setEmail] = useState("");
  const [otpSent, setOtpSent] = useState(false);

  const handleSendOtp = async () => {
    const res = await fetch("http://localhost:8080/ServletWebProject/sendEmailOtp", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ email }),
    });

    if (res.ok) {
      alert("✅ OTP Sent to your email!");
      setOtpSent(true);
    } else {
      alert("❌ Failed to send OTP");
    }
  };

  return (
    <div>
      <h2>📧 Email OTP Signup</h2>
      <input
        type="email"
        placeholder="Enter Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <button onClick={handleSendOtp}>Send OTP</button>

      {otpSent && <p>➡️ OTP sent to your email. Go to Verify Page.</p>}
    </div>
  );
}

export default SendEmailOtp;
