import { useNavigate } from "react-router-dom";

export default function HeroSection({ onRecentClick }) {

  const navigate = useNavigate();
  return (
    <section className="hero">
      <div className="hero-darken"></div> {/* darken overlay background */}
      <div className="hero-overlay">
        <h2>Your campus item recovery hub</h2>
        <div className="hero-buttons">
          <button className="base-button lost-button" onClick={() => navigate('/report')}>Report Lost Item</button>
          <button className="base-button recent-post" onClick={() => navigate('/items')}>View Recent Posts</button>
        </div>
      </div>
    </section>
  );
}