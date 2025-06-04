export default function Navbar({ onPostClick }) {
  return (
    <header className="navbar">
      <h1>Campus Lost and Found</h1>
      <nav>
        <a href="#">Home</a>
        <a href="#" onClick={onPostClick}>Post an Item</a>
        <a href="#">Sign In</a>
      </nav>
    </header>
  );
}
