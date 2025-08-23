import React, { useState } from "react";

function Login({ onLoginSuccess }) {
  const [username, setUsername] = useState(""); 
  const [password, setPassword] = useState(""); 

  const handleLogin = async () => {
    const res = await fetch("http://localhost:8080/ServletWebProject/login", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      credentials: "include", // send cookies/session
      body: new URLSearchParams({ username, password }),
    });

    if (res.ok) {
      const dash = await fetch("http://localhost:8080/ServletWebProject/dashboard", {
        credentials: "include",
      });
      const data = await dash.json();
      onLoginSuccess(data);
    } else {
      alert("❌ Invalid login");
    }
  };

  return (
    <div>
      <h2>Student Login</h2>
      <input
        placeholder="User ID"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default Login;
