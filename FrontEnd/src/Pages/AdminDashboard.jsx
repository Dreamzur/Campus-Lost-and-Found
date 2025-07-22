import { useEffect, useState } from "react";
import axios from "axios";
import './AdminDashboard.css';

// OLD CODE NEED TO WORK ON ADMIN PRIV WITH NEW BACKEND UPDATED CODE

export default function AdminDashboard() {
  const [items, setItems] = useState([]);

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      console.error("No token found. Redirect to login?");
      return;
    }

    axios.get("http://localhost:8080/api/admin/lost-items", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(res => setItems(res.data))
    .catch(err => {
      console.error("Failed to fetch items:", err);
      if (err.response && err.response.status === 401) {
        console.error("Unauthorized: Token is invalid or expired");
      }
    });
  }, [token]);

  const approveItem = (id) => {
    axios.put(`http://localhost:8080/api/admin/approve/${id}`, {}, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(() => {
      setItems(items.map(item => item.id === id ? { ...item, approved: true } : item));
    })
    .catch(err => console.error("Failed to approve item:", err));
  };

  const deleteItem = (id) => {
    axios.delete(`http://localhost:8080/api/admin/delete/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(() => {
      setItems(items.filter(item => item.id !== id));
    })
    .catch(err => console.error("Failed to delete item:", err));
  };

  return (
    <div className="dashboard-container">
      <h2>Admin Dashboard</h2>
      {items.length === 0 ? (
        <p className="empty-msg">No items found</p>
      ) : (
        <div className="dashboard-grid">
          {items.map(item => (
            <div key={item.id} className="item-card">
              <h3>{item.title}</h3>
              <p>{item.description}</p>
              <p><strong>Location:</strong> {item.location}</p>
              {item.imageUrl && <img src={item.imageUrl} alt={item.title} />}
              <p><strong>Status:</strong> {item.approved ? "Approved" : "Pending"}</p>
              <div className="item-buttons">
                {!item.approved && (
                  <button className="approve-btn" onClick={() => approveItem(item.id)}>Approve</button>
                )}
                <button className="delete-btn" onClick={() => deleteItem(item.id)}>Delete</button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
