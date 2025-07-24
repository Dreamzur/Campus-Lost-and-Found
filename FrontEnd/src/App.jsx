import './App.css';
import { useEffect, useState, useRef } from 'react';
import HeroSection from './components/HeroSection';
import ItemList from './components/ItemsList';
import { fetchItems } from './utils/api';

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

  */
  return (
    <div className="container">

      <HeroSection onRecentClick={recentClickHandler} />

      {/* Temporarily on Home Page */}
      <div ref={lostItemsRef}>
        <ItemList items={itemsList} />
      </div>
      {/* Remove before showcase */}
    </div>
  );
}

export default App;