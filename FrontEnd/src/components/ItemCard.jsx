export default function ItemCard({ item }) {
  return (
    <div className="item-card">
      {item.image && <img src={item.image} alt={item.title} />}
      <h3>{item.title}</h3>
      <p>{item.description}</p>
      <p>{item.location}</p>
    </div>
  );
}
