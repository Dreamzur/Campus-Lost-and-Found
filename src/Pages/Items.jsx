import ItemsList from "../components/ItemsList"
import Items from "../data/items"
import './Items.css';

export default function ShowItem() {
	return (
		<div>
			<h2>Lost Items | Search</h2>
			<ItemsList items={Items} />
		</div>
	);
}