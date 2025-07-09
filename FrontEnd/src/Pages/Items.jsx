import React, { useState } from 'react';
import ItemsList from "../components/ItemsList"
import Items from "../data/items"
import './Items.css';

let newarr = Items;

export default function ShowItem() {
	const [inputValue, setInputValue] = useState('');
	const [searched, setSearched] = useState('');

	const handleInput = ({target: {value}}) => {
		setInputValue(value);
		searchItems(value, Items);
		if(newarr.length >1){
			setSearched(false);
		}
	};

	function searchItems(input, Items){
		console.log(input)
		newarr = Items.filter((item) => (item.title.toLowerCase().includes(input.toLowerCase())) || (item.desc.toLowerCase().includes(input.toLowerCase())));
		return newarr;
	}

	return (
		<div>
			<div  className='search-div'>
				<input className='search-bar' placeholder="Search Lost Items" value={inputValue} onChange={handleInput} />
				{(((newarr.length > 1) && !(inputValue === "") && !searched))  && <ul className='search-result'>
					{
						newarr.map((i) => 
							<li className='search-individual-result' tabIndex="0" onKeyDown={({key}) => {
								if(key == "Enter"){
									setInputValue(i.title);
									searchItems(i.title, Items);
									setSearched(true);
								}
							}} key={i.id} onClick={() => {
								setInputValue(i.title);
								searchItems(i.title, Items);
								setSearched(true);
							}}>{i.title}</li>
						)
					}
					</ul>
				}
			</div>
			<ItemsList items={newarr} />
		</div>
	);
}