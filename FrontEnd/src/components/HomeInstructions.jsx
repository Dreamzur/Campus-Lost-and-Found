import './HomeInstructions.css';
import loginArt from '../assets/login-art.jpg';
import searchArt from '../assets/search.jpg';

export default function HomeInstructions() {
  return (
    <div className="instruction-wrapper">
      <div className="instruction-section">
        <div className="instruction-text">
          <h2>Access the Lost & Found</h2>
          <p>
            Log in as a student to access the campus lost and found, submit reports in seconds,
            and help create a more connected and helpful community on campus.
          </p>
        </div>
        <img src={loginArt} alt="Report" className="instruction-image" />
      </div>

      <div className="instruction-section">
        <img src={searchArt} alt="Check list" className="instruction-image" />
        <div className="instruction-text">
          <h2>Browse Lost Items</h2>
          <p>
            Check the list of recently reported items to see if yours has been found.
            You can view photos, locations, and descriptions.
          </p>
        </div>
      </div>
    </div>
  );
}
