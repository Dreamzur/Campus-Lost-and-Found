import React, { useState, useEffect } from 'react';
import ItemsList from "../components/ItemsList";
import { fetchItems } from '../utils/api';
import './Items.css';

export default function ShowItem() {
  const [items, setItems] = useState([]);
  const [filteredItems, setFilteredItems] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [searched, setSearched] = useState('');

  useEffect(() => {
    const loadItems = async () => {
      try {
        const data = await fetchItems();
        setItems(data);
        setFilteredItems(data);
      } catch (err) {
        console.error("Failed to fetch items:", err);
      }
    };

    loadItems();
  }, []);

  const handleInput = ({ target: { value } }) => {
    setInputValue(value);
    searchItems(value);
    if (value.trim() === '') {
      setSearched('');
    } else if (filteredItems.length > 1) {
      setSearched(false);
    }
  };

  function searchItems(input) {
    const filtered = items.filter((item) =>
      item.title.toLowerCase().includes(input.toLowerCase()) ||
      item.description?.toLowerCase().includes(input.toLowerCase())
    );
    setFilteredItems(filtered);
  }

  return (
    <div>
      <div className='search-div'>
        <input
          className='search-bar'
          placeholder="Search Lost Items"
          value={inputValue}
          onChange={handleInput}
        />
        {((filteredItems.length > 1) && inputValue && !searched) && (
          <ul className='search-result'>
            {filteredItems.map((i) => (
              <li
                className='search-individual-result'
                tabIndex="0"
                key={i.id}
                onKeyDown={({ key }) => {
                  if (key === "Enter") {
                    setInputValue(i.title);
                    searchItems(i.title);
                    setSearched(true);
                  }
                }}
                onClick={() => {
                  setInputValue(i.title);
                  searchItems(i.title);
                  setSearched(true);
                }}
              >
                {i.title}
              </li>
            ))}
          </ul>
        )}
      </div>

      <ItemsList items={filteredItems} />
    </div>
  );
}
