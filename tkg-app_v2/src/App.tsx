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
import { PASS_MAIN_FUNCTION, STUDENT_FUNCTION } from "./config";
import { ClassSchedule } from "./components/tkg/ClassSchedule";
import RegistSpecialSchedule from "./components/tkg/student/RegistSpecialSchedule";
import { StudentTop } from "./components/tkg/student/StudentTop";
import StudentRegist from "./components/tkg/student/StudentRegist";
import StudentSearch from "./components/tkg/student/StudentSearch";
import SubjectRegist from "./components/tkg/student/subject/ShowCurrentSubject";
import ShowSubjectWindow from "./components/tkg/student/subject/ShowSubjectWindow";
import MultiCheckbox from "./components/pathParam/MultiCheckbox";

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
              <Route path="/" element={<TkgTop />} />
              <Route path={PASS_MAIN_FUNCTION.Top} element={<TkgTop />} />
              <Route
                path={PASS_MAIN_FUNCTION.ClassSchedule}
                element={<ClassSchedule />}
              />
              {/* 生徒トップ */}
              <Route
                path={PASS_MAIN_FUNCTION.Student}
                element={<StudentTop />}
              />
              {/* 生徒登録 */}
              <Route
                path={STUDENT_FUNCTION.Regist}
                element={<StudentRegist />}
              />
              {/* 生徒登録 */}
              <Route
                path={STUDENT_FUNCTION.SearchStudent}
                element={<StudentSearch />}
              />
              {/* 受講科目表示 */}
              <Route
                path={`${STUDENT_FUNCTION.SearchStudent}/:checkedStudentId`}
                element={<ShowSubjectWindow />}
              />

              {/* 講習スケジュール登録 */}
              <Route
                path={STUDENT_FUNCTION.UpdateSpecialSchedule}
                element={<RegistSpecialSchedule />}
              />
              <Route path="/multi-check" element={<MultiCheckbox />} />
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
