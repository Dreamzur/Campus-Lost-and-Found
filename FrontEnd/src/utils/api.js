export const fetchItems = async () => {
  const res = await fetch('/api/items');
  if (!res.ok) throw new Error('Failed to fetch items');
  const data = await res.json();
  return data;
};

export const itemSubmitHandler = async (newItem) => {
  try {
    const res = await fetch('/api/items', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newItem),
    });

    if (!res.ok) throw new Error('Failed to post item');

    const savedItem = await res.json();

    return savedItem;
    // setShowPostModal(false);
  } catch (err) {
    alert(err.message || 'Failed to post item');
  }
};