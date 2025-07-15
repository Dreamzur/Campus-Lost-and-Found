import './App.css';
import { useEffect, useState } from 'react';
import HeroSection from './components/HeroSection';
import ItemList from './components/ItemsList';
import { fetchItems } from './utils/api';
import { useRef } from 'react';

/* 
  - itemsList: is for the display of items in the row, temp items are in the ./data/items.js
  - ##### temp items are outdated and not used anymore // we have an db for it now #####
  - ##### have to figure out a way to link submissions to the /items page #####

*/
function App() {
  const [itemsList, setItemsList] = useState([]);
  const lostItemsRef = useRef(null);

  const recentClickHandler = () => {
    lostItemsRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

    useEffect(() => {
      async function loadItems() {
        try {
          const data = await fetchItems();
          setItemsList(data);
        } catch (err) {
          console.error('Failed to load items:', err.message);
        }
      }

      loadItems();
    }, []);

  /* 
    --------------- COMPONENTS ---------------
    - <HeroSection />: is the big text in the middle of the page witht he placeholder text (/HeroSection.jsx)
    - <ItemList />: is for the list of item cards, currently itemsList is the array of the items and setSelectedItems is what is shown in the Modal (popup). (/ItemList.jsx)

  */
  return (
    <div className="container">

      <HeroSection onRecentClick={recentClickHandler} />

      <div ref={lostItemsRef}>
        <ItemList items={itemsList} />
      </div>

    </div>
  );
}

export default App;