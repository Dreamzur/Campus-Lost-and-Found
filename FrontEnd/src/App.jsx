import './App.css';
import HeroSection from './components/HeroSection';
import HomeInstructions from './components/HomeInstructions';

function App() {
  /* 
    --------------- COMPONENTS ---------------
    - <HeroSection />: is the text in the middle of the page with the report and recent posting button (/HeroSection.jsx)
    - <HomeInstructions />: are the instructions under the hero section (/HomeInstructions.jsx)

  */
  return (
    <div className="container">

      <HeroSection />
      <HomeInstructions />

    </div>
  );
}

export default App;