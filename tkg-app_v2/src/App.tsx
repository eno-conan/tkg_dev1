import React from "react";
import "./App.css";
import TaskListComponent from "./components/sample/TaskListComponent";
import { Container } from "react-bootstrap";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  // unstable_HistoryRouter as HistoryRouter,
} from "react-router-dom";
import ActiveSearch from "./components/sample/ActiveSearch";
import SearchClassroom from "./components/SearchClassroom";
// import { createBrowserHistory } from "history";
import ClassroomParent from "./components/props/ClassroomParent";
import Sample4 from "./components/react-form_220530/Sample4";
import Top from "./components/breadcrumbs_220531/Top";
import FirstLayer from "./components/breadcrumbs_220531/FirstLayer";
import SecondLayer from "./components/breadcrumbs_220531/SecondLayer";
import TableBase from "./components/table/TableBase";
import { ReceivedPathParam } from "./components/pathParam/ReceivedPathParam";

//related tkgApp
import { TkgTop } from "./components/tkg/TkgTop";
import { PASS_ROUTING } from "./config";
import { ClassSchedule } from "./components/tkg/ClassSchedule";

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
              <Route path="/" element={<ActiveSearch />} />
              <Route path={routing.Top} element={<TkgTop />} />
              <Route path={routing.ClassSchedule} element={<ClassSchedule />} />
              {/* receievePathParam 220607 */}
              <Route
                path="/receive-param/:id"
                element={<ReceivedPathParam />}
              />
              {/* create table base 220531 */}
              <Route path="/table" element={<TableBase />} />
              {/* create breadcrumb 220531 */}
              <Route path="/router-breadcrumbs" element={<Top />}>
                <Route path="1st">
                  <Route path="" element={<FirstLayer />} />
                  <Route path="2nd">
                    <Route path="" element={<SecondLayer />} />
                  </Route>
                </Route>
              </Route>
              {/* form data post task to database 220530 */}
              <Route path="/sample4" element={<Sample4 />} />
              {/* pass data and change actively */}
              <Route path="/classroom" element={<SearchClassroom />}>
                {/* <Route path="id" element={<DetailClassroom />} /> */}
              </Route>
              {/* pass data parent to child... */}
              <Route path="/props" element={<ClassroomParent />} />
              {/* sample */}
              <Route path="/task" element={<TaskListComponent />} />
            </Routes>
          </Router>
        </Container>
      </main>
    </div>
  );
};

export default App;
