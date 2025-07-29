import ItemCard from "./ItemCard";

export default function ItemList({ items }) {
  return (
    <div className="items-row">
      {items.map(item => (
        <div className="items-row-individual-item" key={item.id}>
          <ItemCard item={item} />
        </div>
      ))}
    </div>
  );
}
