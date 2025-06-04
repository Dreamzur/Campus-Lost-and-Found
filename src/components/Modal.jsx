import './Modal.css';

export default function Modal({ item, onClose }) {
  if (!item) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-box" onClick={e => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>✖</button>
        <img src={item.image} alt={item.title} />
        <h2>{item.title}</h2>
        <p>{item.desc}</p>
        <p>{item.location}</p>
        <p style={{ fontStyle: 'italic' }}>Currently placeholder text on click.</p>
      </div>
    </div>
  );
}
