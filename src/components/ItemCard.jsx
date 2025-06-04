export default function ItemCard({ item }) {
  return (
    <div className="item-card">
      <img src={item.image} alt={item.title} />
      <h3>{item.title}</h3>
      <p>{item.desc}</p>
      <p>{item.location}</p>
    </div>
  );
}
