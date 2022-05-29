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
import ActiveSearch from "./components/ActiveSearch";
import SearchClassroom from "./components/SearchClassroom";
import DetailClassroom from "./components/DetailClassroom";
// import { createBrowserHistory } from "history";
import ClassroomParent from "./components/ClassroomParent";

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
              <Route path="/task" element={<TaskListComponent />} />
              <Route path="/checkprops" element={<ClassroomParent />} />
              <Route path="/classroom" element={<SearchClassroom />}>
                <Route path="id" element={<DetailClassroom />} />
                <Route path="nested" element={<div>Nested!</div>} />
              </Route>
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
