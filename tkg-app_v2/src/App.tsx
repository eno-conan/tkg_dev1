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
import ClassroomParent from "./components/ClassroomParent";
import Sample1 from "./components/react-form/Sample1";
import Sample2 from "./components/react-form/Sample2";
import Sample3 from "./components/react-form/Sample3";
import Sample4 from "./components/react-form/Sample4";

export interface ITask {
  id: number;
  content: string;
  done: boolean;
}

const App: React.FC = () => {
  return (
    <div className="App">
      <main>
        <Container>
          <Router>
            <Routes>
              <Route path="/" element={<ActiveSearch />} />
              <Route path="/sample" element={<Sample1 />}>
                <Route path="2" element={<Sample2 />} />
                <Route path="3" element={<Sample3 />} />
              </Route>
              <Route path="/sample4" element={<Sample4 />} />
              <Route path="/task" element={<TaskListComponent />} />
              <Route path="/props" element={<ClassroomParent />} />
              <Route path="/classroom" element={<SearchClassroom />}>
                {/* <Route path="id" element={<DetailClassroom />} /> */}
              </Route>
              {/* <Route path="nested" element={<div>Nested!</div>} /> */}
              {/*          <Route path="/courses" element={<Courses />}>
            <Route path="search" element={<Search />} />
            <Route path="list" element={<List/>}/>
          </Route> */}
            </Routes>
          </Router>
        </Container>
      </main>
    </div>
  );
};

export default App;
