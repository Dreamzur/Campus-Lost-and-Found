import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import App from './App.jsx';
import ReportLost from './Pages/ReportLost.jsx';
import Items from './Pages/Items.jsx';
import Layout from './components/Layout.jsx';
import { itemSubmitHandler } from './utils/api.js';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<App />} />
          <Route path="report" element={<ReportLost onSubmitPost={itemSubmitHandler} />} />
          <Route path="items" element={<Items/>} />
        </Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
