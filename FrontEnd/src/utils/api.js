const BASE_URL = "http://localhost:8080/api/lost";

export const fetchItems = async () => {
  const res = await fetch(BASE_URL);
  if (!res.ok) throw new Error('Failed to fetch items');
  return await res.json();
};

export const itemSubmitHandler = async (newItem) => {
  try {
    const res = await fetch(BASE_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newItem),
    });

    if (!res.ok) throw new Error('Failed to post item');
    return await res.json();
  } catch (err) {
    alert(err.message || 'Failed to post item');
  }
};
