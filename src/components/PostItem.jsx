import { useState } from 'react';
import './Modal.css';

export default function PostItem({ onClose, onSubmit }) {
  const [formData, setFormData] = useState({
    title: '',
    desc: '',
    location: '',
    image: null,
  });

  const changeHandler = (e) => {
    const { name, value, files } = e.target;
    setFormData({
      ...formData,
      [name]: files ? URL.createObjectURL(files[0]) : value,
    });
  };

  const submitHandler = (e) => {
    e.preventDefault();
    onSubmit(formData);
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
            name="desc"
            placeholder="Enter item description..."
            value={formData.desc}
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
          <input
            name="image"
            type="file"
            accept="image/*"
            onChange={changeHandler}
            required
          />
          <button type="submit">Post Item</button>
        </form>
      </div>
    </div>
  );
}
