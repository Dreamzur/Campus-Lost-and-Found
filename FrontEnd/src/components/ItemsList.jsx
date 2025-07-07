import ItemCard from "./ItemCard";

export default function ItemList({ items, onItemClick }) {
  console.log(items);

  return (
    <>
      <h2>Lost Items</h2>
      <div className="items-row">
        {items.map(item => (
          <div onClick={() => onItemClick(item)} key={item.id}>
            <ItemCard item={item} />
          </div>
        ))}
      </div>
    </>
  );
}