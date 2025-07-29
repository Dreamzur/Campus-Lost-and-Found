const BASE_URL = "http://localhost:8080/api/lost";

export const fetchItems = async () => {
  try {
    const res = await fetch(BASE_URL);
    if (!res.ok) throw new Error('Failed to fetch items');
    return await res.json();
  } catch (err) {
    alert(err.message || 'Failed to fetch items');
  }
};

export const itemSubmitHandler = async (data) => {
  try {
    const token = localStorage.getItem("token");

    const res = await fetch("http://localhost:8080/api/lost", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...(token && { Authorization: `Bearer ${token}` }),
      },
      body: JSON.stringify(data),
    });

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || 'Failed to post item');
    }

    return await res.json();
  } catch (err) {
    alert(err.message || 'Failed to post item');
    console.error("Error submitting item:", err);
  }
};


