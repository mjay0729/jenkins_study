import React from 'react';
import ReactDOM from 'react-dom/client';

// Style
import "./assets/global.css"

// Main Components
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
