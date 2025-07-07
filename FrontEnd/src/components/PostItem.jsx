import { useState } from 'react';
import './Modal.css';

export default function PostItem({ onClose, onSubmit }) {
  const [uploading, setUploading] = useState(false);
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    location: '',
  });

  const changeHandler = (e) => {
    const { name, value } = e.target;
    setFormData(f => ({
      ...f,
      [name]: value,
    }));
  };

  const submitHandler = async (e) => {
    e.preventDefault();

    try {
      setUploading(true);

      const submitPayload = {
        title: formData.title,
        description: formData.description,
        location: formData.location,
      };

      await onSubmit(submitPayload);

      setUploading(false);
      onClose();
    } catch (err) {
      setUploading(false);
      alert(err.message || 'Submission error');
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-box" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>✖</button>
        <h2>What did you find?</h2>
        <form onSubmit={submitHandler} className="post-form">
          <input
            name="title"
            type="text"
            placeholder="Type item name..."
            value={formData.title}
            onChange={changeHandler}
            required
          />
          <textarea
            name="description"
            placeholder="Enter item description..."
            value={formData.description}
            onChange={changeHandler}
            required
          />
          <input
            name="location"
            type="text"
            placeholder="Where did you find it?"
            value={formData.location}
            onChange={changeHandler}
            required
          />
          <button type="submit" disabled={uploading}>
            {uploading ? 'Submitting...' : 'Post Item'}
          </button>
        </form>
      </div>
    </div>
  );
}
