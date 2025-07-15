export default function ItemCard({ item }) {
  return (
    <div className="item-card">
      {item.image && 
      <a href={item.image} target="_blank" rel="noopener noreferrer">
        <img src={item.image} alt={item.title} onClick={() => {console.log("Image clicked")}} />
      </a>
      }
      <h3 className="item-card-title">{item.title}</h3>
      <p className="item-card-description">{item.description}</p>
      <div className="item-card-footer"> 
        <p className="item-card-footer-location">{item.location}</p>
        <button className="item-card-footer-contact" onClick={() => {
          // TODO SEND MESSAGE
          console.log("Send msg");
        }}>Contact</button>
      </div>
    </div>
  );
}