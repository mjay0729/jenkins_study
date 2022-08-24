import { BrowserRouter, Route, Routes } from "react-router-dom"

// Pages
import Features from "./pages/Features";
import Main from "./pages/Main";
import Error from "./pages/Error";

// Layout
import SharedLayout from "./components/Layout/SharedLayout";

// Styles 
// eslint-disable-next-line
import 'bootstrap/dist/css/bootstrap.min.css';

// Languages Service (import whole Lang config file)
import "./lang.js"


const App = () => {
  return (
    <BrowserRouter>
      <Routes>

        {/* Features */}
        <Route path="/" element={<SharedLayout />} >
          <Route index element={<Features />} />
          <Route path="/features" element={<Main />} />
        </Route >

        {/* Error Page */}
        <Route path="*" element={<Error />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
