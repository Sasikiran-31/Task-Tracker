import {Routes, Route, BrowserRouter} from "react-router-dom";
import NavigationBar from "./components/NavigationBar.jsx";
import HomePage from "./Pages/HomePage.jsx";
import LoginPage from "./Pages/LoginPage.jsx";
import "./App.css";
import Layout from "./components/Layout.jsx";

function App() {

  return (
      <BrowserRouter>
          <Routes>
              <Route index element={<LoginPage />} />

              <Route element={<Layout />}>
                  <Route path='/home' element={<HomePage />} />
              </Route>
          </Routes>

      </BrowserRouter>
  );
}

export default App;
