import { useEffect, useState } from "react";
import axios from "axios";
import './AdminDashboard.css';

export default function AdminDashboard() {
  const [items, setItems] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      console.error("No token found. Redirect to login?");
      return;
    }

    axios.get("http://localhost:8080/api/admin/lost-items", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(res => {
        console.log("Fetched items:", res.data);
        setItems(res.data);
      })
      .catch(err => {
        console.error("Failed to fetch items:", err);
        if (err.response && err.response.status === 401) {
          console.error("Unauthorized: Token is invalid or expired");
        }
      });
  }, []);

  const approveItem = (id) => {
    const token = localStorage.getItem("token");

    axios.put(`http://localhost:8080/api/admin/approve/${id}`, {}, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(() => {
        setItems(prevItems =>
          prevItems.map(item =>
            item.id === id ? { ...item, approved: true } : item
          )
        );
      })
      .catch(err => console.error("Failed to approve item:", err));
  };

  const deleteItem = (id) => {
    const token = localStorage.getItem("token");

    axios.delete(`http://localhost:8080/api/admin/delete/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(() => {
        setItems(prevItems => prevItems.filter(item => item.id !== id));
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
            <div key={item.id} className="admin-item-card">
              <h3>{item.title}</h3>
              <p>{item.description}</p>
              <p><strong>Location:</strong> {item.location}</p>

              {item.image && (
                <img
                  src={`http://localhost:8080/api/image/${item.id}`}
                  alt={item.title}
                  className="admin-item-image"
                />
              )}

              <p className={`status ${!item.approved ? 'pending' : ''}`}>
                <strong>Status:</strong> {item.approved ? "Approved" : "Pending"}
              </p>

              <div className="item-buttons">
                {!item.approved && (
                  <button className="approve-btn" onClick={() => approveItem(item.id)}>
                    Approve
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
