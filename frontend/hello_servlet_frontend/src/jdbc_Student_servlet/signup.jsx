import React, { useState } from "react";
import CryptoJS from "crypto-js";

function Signup({ onSignupSuccess }) {
  const [form, setForm] = useState({
    userid: "",
    name: "",
    phone: "",
    email: "",
    countrycode: "+91",
    password: "",
  });

  const [otp, setOtp] = useState("");
  const [otpSent, setOtpSent] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);

  const countries = [
    { name: "India", dial_code: "+91" },
    { name: "USA", dial_code: "+1" },
    { name: "UK", dial_code: "+44" },
    { name: "Japan", dial_code: "+81" },
  ];

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // ✅ Step 1: Send OTP
  const handleSendOtp = async () => {
    const res = await fetch("http://localhost:8080/ServletWebProject/sendEmailOtp", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ email: form.email }),
      credentials: "include", // session ke liye
    });

    if (res.ok) {
      alert("✅ OTP sent to " + form.email);
      setOtpSent(true);
    } else {
      alert("❌ Failed to send OTP");
    }
  };

  // ✅ Step 2: Verify OTP
  const handleVerifyOtp = async () => {
    const res = await fetch("http://localhost:8080/ServletWebProject/verifyEmailOtp", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ otp }),
      credentials: "include",
    });

    const text = await res.text();
    if (text.includes("OTP Verified")) {
      alert("✅ Email Verified");
      setOtpVerified(true);
    } else {
      alert("❌ Invalid OTP");
    }
  };

  // ✅ Step 3: Final Signup (only if OTP verified)
  const handleSignup = async () => {
    if (!otpVerified) {
      alert("❌ Please verify OTP before signup");
      return;
    }

    const secretKey = "MySecretKey12345";
    const encryptedPassword = CryptoJS.AES.encrypt(
      form.password,
      secretKey
    ).toString();

    const payload = { ...form, password: encryptedPassword };

    const res = await fetch("http://localhost:8080/ServletWebProject/signup", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams(payload).toString(),
    });

    const data = await res.json();
    alert(data.message);

    if (res.ok) {
      onSignupSuccess();
    }
  };

  return (
    <div>
      <h2>📝 Student Signup with Email OTP</h2>
      <input name="userid" placeholder="User ID" onChange={handleChange} />
      <input name="name" placeholder="Full Name" onChange={handleChange} />
      <input name="phone" placeholder="Phone" onChange={handleChange} />
      <input name="email" placeholder="Email" onChange={handleChange} />

      <select name="countrycode" value={form.countrycode} onChange={handleChange}>
        {countries.map((c) => (
          <option key={c.dial_code} value={c.dial_code}>
            {c.name} ({c.dial_code})
          </option>
        ))}
      </select>

      <input
        type="password"
        name="password"
        placeholder="Password"
        onChange={handleChange}
      />

      {/* OTP Section */}
      <div style={{ marginTop: "10px" }}>
        {!otpSent ? (
          <button onClick={handleSendOtp}>📧 Send OTP</button>
        ) : (
          <>
            <input
              type="text"
              placeholder="Enter OTP"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
            />
            <button onClick={handleVerifyOtp}>Verify OTP</button>
          </>
        )}
      </div>

      <br />
      <button onClick={handleSignup}>Signup</button>
    </div>
  );
}

export default Signup;
