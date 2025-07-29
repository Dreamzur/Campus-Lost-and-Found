import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./sharedPages.css";

export default function AuthForm() {
  const [isRegistering, setIsRegistering] = useState(false);
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const { loginWithToken } = useAuth();

  const toggleMode = () => {
    setIsRegistering((prev) => !prev);
    setMessage("");
    setError("");
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setMessage("");

    const endpoint = isRegistering ? "/register" : "/login";

    try {
      const res = await fetch(`http://localhost:8080/api${endpoint}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      const isJson = res.headers.get("content-type")?.includes("application/json");
      const data = isJson ? await res.json() : {};

      if (!res.ok) {
        const errorMsg = isJson ? data.message : await res.text();
        throw new Error(errorMsg || "Something went wrong");
      }

      // login automatically after user registration
      if (isRegistering) {
        const loginRes = await fetch("http://localhost:8080/api/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(formData),
        });

        const loginData = await loginRes.json();

        if (!loginRes.ok || !loginData.token) {
          throw new Error("Registered, but auto-login failed.");
        }

        loginWithToken(loginData.token, loginData.role);
        navigate("/");
        return;
      }

      // Login
      if (data.token) {
        loginWithToken(data.token, data.role);
        navigate("/");
      }

    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="auth-form-container">
      <h2>{isRegistering ? "Register" : "Login"}</h2>
      <form onSubmit={handleSubmit} className="auth-form">
        <label>Username</label>
        <input
          name="username"
          value={formData.username}
          onChange={handleChange}
          required
        />

        <label>Password</label>
        <input
          name="password"
          type="password"
          value={formData.password}
          onChange={handleChange}
          required
        />

        <button type="submit">{isRegistering ? "Register" : "Login"}</button>

        {message && <p className="success-message">{message}</p>}
        {error && <p className="error-message">{error}</p>}
      </form>

      <p>
        {isRegistering ? "Already have an account?" : "Don't have an account?"}{" "}
        <button onClick={toggleMode} className="toggle-btn">
          {isRegistering ? "Login" : "Register"}
        </button>
      </p>
    </div>
  );
}
