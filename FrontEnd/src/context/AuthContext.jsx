import { jwtDecode } from 'jwt-decode';
import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState(null);

  const loginWithToken = (token) => {
    localStorage.setItem("token", token);
    try {
      const decoded = jwtDecode(token);
      setIsLoggedIn(true);
      setUserRole(decoded.roles?.[0]);
    } catch (err) {
      console.error("Invalid token on login");
      localStorage.removeItem("token");
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decoded = jwtDecode(token);
        console.log("Decoded JWT:", decoded);
        setIsLoggedIn(true);
        setUserRole(decoded.roles?.[0]);
      } catch (err) {
        console.error("Invalid token");
        localStorage.removeItem("token");
      }
    }
  }, []);

  const logout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    setUserRole(null);
  };

  return (
    <AuthContext.Provider value={{
      isLoggedIn,
      userRole,
      setIsLoggedIn,
      logout,
      loginWithToken
    }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
