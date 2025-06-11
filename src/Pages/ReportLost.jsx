import './ReportLost.css';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { itemSubmitHandler } from '../utils/api';

export default function ReportLost() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    location: ''
  });

  const [uploading, setUploading] = useState(false);

  const changeHandler = (e) => {
    const { name, value } = e.target;
    setFormData(f => ({
      ...f,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setUploading(true);

      const submitPayload = {
        title: formData.title,
        description: formData.description,
        location: formData.location,
      };

      await itemSubmitHandler(submitPayload);

      setUploading(false);
      alert('Item submitted successfully!');
      navigate('/');
    } catch (err) {
      setUploading(false);
      alert(err.message || 'Submission error');
    }
  };

  return (
    <div className="report-form">
      <h2>Report Lost Item</h2>
      <form onSubmit={handleSubmit}>
        {/* --- Item Details --- */}
        <div className="form-grid">
          <div>
            <label>What was Lost *</label>
            <input 
            type="text"
            name="title" 
            placeholder="Headphones, Water Bottle, etc."
            value={formData.title}
            onChange={changeHandler}
            required 
            />
          </div>

          <div>
            <label>Date Lost *</label>
            <input
            type="date" 
            // required 
            />
          </div>

          <div>
            <label>Type *</label>
            <select name="type">
              <option value="">Select type...</option>
              <option value="Bag">Bag</option>
              <option value="Binder/Notebook">Binder/Notebook</option>
              <option value="Book">Book</option>
              <option value="Cable">Cable</option>
              <option value="Camera">Camera</option>
              <option value="Cash/Check">Cash/Check</option>
              <option value="Clothing">Clothing</option>
              <option value="Credit Card">Credit Card</option>
              <option value="Driver's License/Government or State-issued ID">Driver's License/Government or State-issued ID</option>
              <option value="Student ID">Student ID</option>
              <option value="Glasses">Glasses</option>
              <option value="Jewelry">Jewelry</option>
              <option value="Keys">Keys</option>
              <option value="Laptop">Laptop</option>
              <option value="Mobile Phone">Mobile Phone</option>
              <option value="Other">Other</option>
              <option value="Other Electronic Items">Other Electronic Items</option>
              <option value="Passport">Passport</option>
              <option value="Shoes">Shoes</option>
              <option value="Skateboard">Skateboard</option>
              <option value="USB">USB</option>
              <option value="Umbrella">Umbrella</option>
              <option value="Wallet">Wallet</option>
            </select>
          </div>

          <div>
            <label>Time Lost *</label>
            <input 
            type="time" 
            // required
            />
          </div>

          <div>
            <label>Upload Image</label>
            <input 
            type="file" 
            />
          </div>

          <div className="add-info full-width">
            <label>Additional Information</label>
            <textarea
            name="description" 
            placeholder="Any details, or information to help identify the item."
            value={formData.description}
            onChange={changeHandler}
            />
          </div>

          <div className="full-width">
            <label>Where was it lost? *</label>
            <input
              type="text"
              name="location"
              placeholder="Building, room, etc..."
              value={formData.location}
              onChange={changeHandler}
              required
              />
          </div>
        </div>
    
        {/* --- User Details --- */}
        <h2>Your Details</h2>
        <div className="form-grid">
          <div>
            <label>First Name *</label>
            <input 
            type="text" 
            name="firstName" 
            // required 
            />
          </div>

          <div>
            <label>Last Name *</label>
            <input 
            type="text" 
            name="lastName" 
            // required 
            />
          </div>

          <div>
            <label>Email *</label>
            <input 
            type="email" 
            name="email" 
            // // required 
            />
          </div>

          <div>
            <label>Phone Number</label>
            <input type="tel" name="phone" />
          </div>
        </div>

        <button 
        type="button" 
        className="back-button" 
        onClick={() => navigate('/')}>← Back to Home
        </button>

        <button 
        type="submit" 
        className="submit-button" disabled={uploading}>{uploading ? 'Submitting...' : 'Submit Lost Item'}
        </button>
      </form>
    </div>
  );
}