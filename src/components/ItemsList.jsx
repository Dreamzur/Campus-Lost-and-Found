import ItemCard from "./ItemCard";

export default function ItemList({ items, onItemClick }) {

  return (
    <>
      <div className="items-row">
        {items.map(item => (
          <div className="items-row-individual-item" onClick={() => onItemClick(item)} key={item.id}>
            <ItemCard item={item} />
          </div>
        ))}
      </div>
    </>
  );
}