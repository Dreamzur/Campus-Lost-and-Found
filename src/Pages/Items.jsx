import React, { useState } from 'react';
import ItemsList from "../components/ItemsList"
import Items from "../data/items"
import './Items.css';

let newarr = Items;

export default function ShowItem() {
	const [inputValue, setInputValue] = useState('');

	const handleInput = (event) => {
		setInputValue(event.target.value);
		searchItems(event.target.value, Items);
	};

	function searchItems(input, Items){
		console.log(input)
		newarr = Items.filter((item) => (item.title.toLowerCase().includes(input.toLowerCase())) || (item.desc.toLowerCase().includes(input.toLowerCase())));
		console.log("Choices: ", newarr);
		return newarr;
	}

	return (
		<div className='blah'>
			<input placeholder="Search Lost Items" value={inputValue} onChange={handleInput} />
			{((newarr.length != 0) && !(inputValue === "")) && <ul>
				{
					newarr.map((i) => 
						<li key={i.id} onClick={() => {
							setInputValue(i.title);
							searchItems(i.title, Items);
						}}>{i.title}</li>
					)
				}
				</ul>
			}
			<ItemsList items={newarr} />
		</div>
	);
}