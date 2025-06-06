import './App.css';
import { useEffect } from 'react';
import Navbar from './components/Navbar';
import HeroSection from './components/HeroSection';
import { useState } from 'react';
import ItemList from './components/ItemsList';
import ModalManager from './components/ModalManager';

/* 
  - selectedItem: tracks what you click on to open as a Modal, the popout
  - showPostModal: is for the 'Post an Item' modal
  - itemsList: is for the display of items in the row, temp items are in the ./data/items.js

  - itemSubmitHandler: is for the new item submissions, updates the items row with new       submission and closes the popup 
*/
function App() {
  const [selectedItem, setSelectedItem] = useState(null);
  const [showPostModal, setShowPostModal] = useState(false);
  const [itemsList, setItemsList] = useState([]);

  useEffect(() => {
    async function fetchItems() {
      try {
        const res = await fetch('/api/items');
        if (!res.ok) throw new Error('Failed to fetch items');
        const data = await res.json();
        setItemsList(data);
      } catch (err) {
        console.error(err);
      }
    }
    fetchItems();
  }, []);

    const itemSubmitHandler = async (newItem) => {
    try {
      const res = await fetch('/api/items', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newItem),
      });
      if (!res.ok) throw new Error('Failed to post item');

      const savedItem = await res.json();

      setItemsList(prev => [...prev, savedItem]);
      setShowPostModal(false);
    } catch (err) {
      alert(err.message || 'Failed to post item');
    }
  };

  /* 
    --------------- COMPONENTS ---------------
    - <Navbar />: is the navigation menu on top right, currently has a 'working' Post an Item popup (found in /Navbar.jsx)
    - <HeroSection />: is the big text in the middle of the page witht he placeholder text (/HeroSection.jsx)
    - <ItemList />: is for the list of item cards, currently itemsList is the array of the items and setSelectedItems is what is shown in the Modal (popup). (/ItemList.jsx)
    - <ModalManager />: is to manage which Modal (popup) is open (/ModalManager.jsx)

  */
  return (
    <div className="container">
      <Navbar onPostClick={() => setShowPostModal(true)} />

      <HeroSection />

      <ItemList items={itemsList} onItemClick={setSelectedItem} />

      <ModalManager
        selectedItem={selectedItem}
        onCloseItem={() => setSelectedItem(null)}
        showPostModal={showPostModal}
        onClosePost={() => setShowPostModal(false)}
        onSubmitPost={itemSubmitHandler}
        />
    </div>
  );
}

export default App;
