import React, { useState } from "react";
import StudentList from "./jdbc_Student_servlet/StudentList";
import Login from "./jdbc_Student_servlet/Login";
import Signup from "./jdbc_Student_servlet/signup"; // 👈 new component

function App() {
  const [dashboard, setDashboard] = useState(null);
  const [showSignup, setShowSignup] = useState(false);

  return (
    <div>
      <h1>🎓 Student Management Portal</h1>

      {!dashboard ? (
        <>
          {showSignup ? (
            <Signup onSignupSuccess={() => setShowSignup(false)} />
          ) : (
            <Login onLoginSuccess={setDashboard} />
          )}

          <button
            style={{ marginTop: "10px" }}
            onClick={() => setShowSignup(!showSignup)}
          >
            {showSignup
              ? "Already have an account? Login"
              : "New user? Signup here"}
          </button>
        </>
      ) : (
        <>
          <p>✅ Welcome {dashboard.lastUser}</p>
          <StudentList /> {/* Show student list only after login */}
        </>
      )}
    </div>
  );
}

export default App;
