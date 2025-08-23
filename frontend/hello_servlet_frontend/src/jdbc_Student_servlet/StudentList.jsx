import React, { useEffect, useState } from "react";

function StudentList() {
  const [students, setStudents] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/ServletWebProject/api/students")
      .then((res) => res.json())
      .then((data) => setStudents(data))
      .catch((err) => console.error("Error fetching students:", err));
  }, []);

  return (
    <div>
      <h2>Student List</h2>
      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Country</th>
          </tr>
        </thead>
        <tbody>
          {students.map((s) => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.name}</td>
              <td>{s.age}</td>
              <td>{s.country}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default StudentList;
