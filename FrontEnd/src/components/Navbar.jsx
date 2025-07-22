import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <header className="navbar">
      <div className="navbar-content">
        <h1 className="navbar-title">
          <Link to="/">Campus Lost and Found</Link>
        </h1>
        <div className="navbar-links">
          {isLoggedIn && <Link to="/admin-dashboard">Dashboard</Link>}
          {!isLoggedIn && <Link to="/auth">Sign In</Link>}
          {isLoggedIn && (
            <button className="logout-btn" onClick={handleLogout}>
              Logout
            </button>
          )}
        </div>
      </div>
    </header>
  );
}
