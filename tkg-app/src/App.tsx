import React from "react";
import "./App.css";
import AreaInfo from "./components/AreaInfo";
import InputField from "./components/InputField";
import ActiveSearch from "./components/ActiveSearch";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Form from "./components/Form";

const App: React.FC = () => {
  // https://yumegori.com/react_router_v6_method : <-routing
  return (
    // <div className="App">
    //   <span className="heading">Header--</span>
    //   {/* <InputField /> */}
    //   {/* <AreaInfo /> */}
    //   <ActiveSearch />
    // </div>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<InputField />} />
        <Route path="/search" element={<ActiveSearch />} />
        <Route path="/form" element={<Form />} />
        {/* <Route path="page1" element={<Layout />}>
          <Route index element={<Page1 />} />
          <Route path="page1Child" element={<Page1Child />} />
        </Route> */}
      </Routes>
    </BrowserRouter>
  );
};

export default App;
