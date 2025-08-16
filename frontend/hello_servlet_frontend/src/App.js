import React, { useState } from "react";

export default function App() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [age, setAge] = useState("");
  const [output, setOutput] = useState("");

  const send = async () => {
    try {
      const res = await fetch("http://localhost:8080/ServletWebProject/hello", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: username,email: email, age: age}),
      });
      const data = await res.json();
      setOutput(data.username+data.email+data.age);
      setUsername("");
      setEmail("");
      setAge("");

    } catch (e) {
      setOutput("Error: " + e);
    }
  };

  return (
    <div>
      <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="user" />
      <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="email" />
      <input value={age} onChange={(e) => setAge(e.target.value)} placeholder="age" />
      <button type="button" onClick={send}>Send</button> {/* <- type="button" */}
      <div><b>Server response:</b> {output}</div>
    </div>
  );
}
