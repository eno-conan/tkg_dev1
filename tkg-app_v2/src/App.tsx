import React from "react";
import "./App.css";
import { Container } from "react-bootstrap";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  // unstable_HistoryRouter as HistoryRouter,
} from "react-router-dom";

// import { createBrowserHistory } from "history";
import Top from "./components/breadcrumbs_220531/Top";
import FirstLayer from "./components/breadcrumbs_220531/FirstLayer";
import SecondLayer from "./components/breadcrumbs_220531/SecondLayer";
import { ReceivedPathParam } from "./components/pathParam/ReceivedPathParam";

//related tkgApp
import { TkgTop } from "./components/tkg/TkgTop";
import { PASS_ROUTING } from "./config";
import { ClassSchedule } from "./components/tkg/ClassSchedule";
import RegistSpecialSchedule from "./components/tkg/student/RegistSpecialSchedule";

export interface ITask {
  id: number;
  content: string;
  done: boolean;
}

const App: React.FC = () => {
  const routing = PASS_ROUTING;
  return (
    <div className="App">
      <main>
        <Container>
          <Router>
            <Routes>
              <Route path={routing.Top} element={<TkgTop />} />
              <Route path={routing.ClassSchedule} element={<ClassSchedule />} />
              <Route
                path="click-button-table"
                element={<RegistSpecialSchedule />}
              />
              {/* receievePathParam 220607 */}
              <Route
                path="/receive-param/:id"
                element={<ReceivedPathParam />}
              />
              {/* create breadcrumb 220531 */}
              <Route path="/router-breadcrumbs" element={<Top />}>
                <Route path="1st">
                  <Route path="" element={<FirstLayer />} />
                  <Route path="2nd">
                    <Route path="" element={<SecondLayer />} />
                  </Route>
                </Route>
              </Route>
            </Routes>
          </Router>
        </Container>
      </main>
    </div>
  );
};

export default App;
