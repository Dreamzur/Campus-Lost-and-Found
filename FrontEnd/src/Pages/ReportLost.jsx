import './sharedPages.css';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { itemSubmitHandler } from '../utils/api';
import { useAuth } from '../context/AuthContext';

export default function ReportLost() {
  const navigate = useNavigate();
  const { isLoggedIn } = useAuth();

  useEffect(() => {
    if (!isLoggedIn) {
      navigate('/auth');
    }
  }, [isLoggedIn, navigate]);

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    location: '',
    imageUrl: '',
  });

  const [uploading, setUploading] = useState(false);
  const [imageError, setImageError] = useState("");

  const isValidImageUrl = (url) => {
    return /^https?:\/\/.+\.(jpg|jpeg|png|webp|avif|gif|svg)$/i.test(url);
  };

  const changeHandler = (e) => {
    const { name, value } = e.target;

    if (name === "imageUrl") {
      if (value === "") {
        setImageError("");
      } else if (!isValidImageUrl(value)) {
        setImageError("Please enter a valid image URL ending in .jpg, .png, etc.");
      } else {
        setImageError("");
      }
    }

    setFormData(f => ({
      ...f,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.imageUrl && !isValidImageUrl(formData.imageUrl)) {
      setImageError("Image URL is invalid. Please fix it before submitting.");
      return;
    }

    try {
      setUploading(true);

      const payload = {
        title: formData.title,
        description: formData.description,
        location: formData.location,
        image_url: formData.imageUrl,
      };

      await itemSubmitHandler(payload);

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
        <div className="form-grid">
          <div className="full-span">
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

          <div className="full-span">
            <label>Additional Information</label>
            <textarea
              name="description" 
              placeholder="Any details or identifying info"
              value={formData.description}
              onChange={changeHandler}
              maxLength={240}
            />
            <small>{formData.description.length}/240 characters</small>
          </div>

          <div className="full-span">
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

          <div className="full-span">
            <label>Image URL</label>
            <input 
              type="text"
              name="imageUrl"
              placeholder="Paste image link (e.g. https://...)"
              value={formData.imageUrl}
              onChange={changeHandler}
            />
            {imageError && <small className="image-error">{imageError}</small>}
            {isValidImageUrl(formData.imageUrl) && (
              <div className="image-preview">
                <img src={formData.imageUrl} alt="Preview" />
              </div>
            )}
          </div>
        </div>

        <div className="button-wrapper">
          <div className="back-button-container">
            <button 
              type="button" 
              className="back-button" 
              onClick={() => navigate('/')}>
              ← Back to Home
            </button>
          </div>

          <div className="submit-button-container">
            <button 
              type="submit" 
              className="submit-button" 
              disabled={uploading}>
              {uploading ? 'Submitting...' : 'Submit Lost Item'}
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}
