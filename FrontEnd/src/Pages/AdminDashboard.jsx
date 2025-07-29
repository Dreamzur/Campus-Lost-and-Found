import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import './sharedPages.css';

export default function AdminDashboard() {
  const [items, setItems] = useState([]);
  const { userRole } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token || !userRole?.includes("ADMIN")) {
      console.error("Access denied: Only admins allowed");
      navigate("/");
      return;
    }

    axios.get("http://localhost:8080/api/admin/lost-items", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        console.log("Fetched items:", res.data);
        setItems(res.data);
      })
      .catch((err) => {
        console.error("Failed to fetch items:", err);
        if (err.response && err.response.status === 401) {
          console.error("Unauthorized: Token is invalid or expired");
        }
      });
  }, [userRole, navigate]);

  const claimItem = (id) => {
    const token = localStorage.getItem("token");

    axios.put(`http://localhost:8080/api/admin/claim/${id}`, {}, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(() => {
        setItems(prevItems =>
          prevItems.map(item =>
            item.id === id ? { ...item, claimed: true } : item
          )
        );
      })
      .catch(err => console.error("Failed to mark item as claimed:", err));
  };

  const deleteItem = (id) => {
    const token = localStorage.getItem("token");

    axios.delete(`http://localhost:8080/api/admin/delete/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(() => {
        setItems(prevItems => prevItems.filter(item => item.id !== id));
      })
      .catch(err => console.error("Failed to delete item:", err));
  };

  if (!userRole?.includes("ADMIN")) {
    return <p className="unauthorized-msg">Access denied: Admins only.</p>;
  }

  return (
    <div className="dashboard-container">
      <h2>Admin Dashboard</h2>
      {items.length === 0 ? (
        <p className="empty-msg">No items found</p>
      ) : (
        <div className="dashboard-grid">
          {items.map(item => (
            <div key={item.id} className="admin-item-card">
              <h3>{item.title}</h3>
              <p>{item.description}</p>
              <p><strong>Location:</strong> {item.location}</p>

              {item.imageUrl && (
                <img
                  src={item.imageUrl}
                  alt={item.title}
                  className="item-card-img"
                />
              )}

              <p className={`status ${!item.claimed ? 'pending' : ''}`}>
                <strong>Status:</strong> {item.claimed ? "Claimed" : "Pending"}
              </p>

              <div className="item-buttons">
                {!item.claimed && (
                  <button className="approve-btn" onClick={() => claimItem(item.id)}>
                    Mark as Claimed
                  </button>
                )}
                <button className="delete-btn" onClick={() => deleteItem(item.id)}>
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
