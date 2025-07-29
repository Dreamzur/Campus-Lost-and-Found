export default function ItemCard({ item }) {
  return (
    <div className="item-card">
      {item.imageUrl && (
        <img
          src={item.imageUrl}
          alt={item.title}
          className="item-card-img"
        />
      )}
      
      <div className={`item-status-badge ${item.claimed ? 'claimed' : ''}`}>
        {item.claimed ? 'Claimed' : ''}
      </div>

      <h3 className="item-card-title">{item.title}</h3>
      <p className="item-card-description">{item.description}</p>
      <div className="item-card-footer">
        <p className="item-card-footer-location">{item.location}</p>
        <button className="item-card-footer-contact" onClick={() => console.log("Send msg")}>
          Contact
        </button>
      </div>
    </div>
  );
}
