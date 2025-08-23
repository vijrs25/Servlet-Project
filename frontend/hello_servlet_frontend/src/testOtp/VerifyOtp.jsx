import React, { useState } from "react";

function VerifyEmailOtp() {
  const [otp, setOtp] = useState("");

  const handleVerifyOtp = async () => {
    const res = await fetch("http://localhost:8080/ServletWebProject/verifyEmailOtp", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      credentials: "include", // session ke liye
      body: new URLSearchParams({ otp }),
    });

    const text = await res.text();
    alert(text);
  };

  return (
    <div>
      <h2>🔑 Verify Email OTP</h2>
      <input
        type="text"
        placeholder="Enter OTP"
        value={otp}
        onChange={(e) => setOtp(e.target.value)}
      />
      <button onClick={handleVerifyOtp}>Verify</button>
    </div>
  );
}

export default VerifyEmailOtp;
