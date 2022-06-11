/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useParams } from "react-router";
import { useNavigate, Link } from "react-router-dom";
import DataTable from "react-data-table-component";
import Card from "@material-ui/core/Card";
import SortIcon from "@material-ui/icons/ArrowDownward";
import Header from "../react-form_220530/Header";
import { API_BASE_URL } from "../../config";
import Modal from "react-modal";
import { Helmet } from "react-helmet";
import {
  Container,
  Button,
  Col,
  Row,
  CloseButton,
  Table,
  // DropdownButton,
  // Dropdown,
} from "react-bootstrap";

import "../tkgStyle.css";
import StudentScheduleView from "./classSchedule/StudentScheduleView";

//classScheduleSampleData Setting
// =======================-
const classScheduleSampleData = [
  {
    id: "1",
    period: "6",
    grade: "h1",
    subject: "数学IA",
    studentId: "1",
    studentName: "山本由伸",
    lecturerName: "講師A",
    rescheduleDateStart: "2022-06-08",
    rescheduleDateEnd: "2022-06-22",
  },
];

const classScheduleTableColumns = [
  {
    name: "コマ",
    selector: (row: { period: string }) => row.period,
    sortable: false,
  },
  {
    name: "学年",
    selector: (row: { grade: string }) => row.grade,
    sortable: false,
  },
  {
    name: "科目",
    selector: (row: { subject: string }) => row.subject,
    sortable: false,
  },
  {
    name: "生徒氏名",
    selector: (row: { studentName: string }) => row.studentName,
    sortable: false,
  },
  {
    name: "講師名",
    selector: (row: { lecturerName: string }) => row.lecturerName,
    sortable: false,
  },
];

interface ClassInfo {
  id: string;
  period: string;
  grade: string;
  subject: string;
  lecturerName: string;
  studentId: string;
  studentName: string;
  rescheduleDateStart: string;
  rescheduleDateEnd: string;
}
export type classScheduleList = Array<ClassInfo>;

const studentScheduleSampleData = [
  {
    id: "1",
    period: "8",
    grade: "高校1年",
    subject: "数学IA",
    lecturerName: "講師1",
    studentId: "1",
    studentName: "Aさん",
    classDate: "2022/6/12",
  },
];
export interface StudentSchedule {
  id: string;
  period: string;
  grade: string;
  subject: string;
  lecturerName: string;
  studentId: string;
  studentName: string;
  classDate: string;
}

export type studentScheduleList = Array<StudentSchedule>;

const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    width: "60%",
    height: "50%",
  },
};

Modal.setAppElement("#root");

//授業振替・講師変更、それぞれのモーダルウインドウ関連を別Componentにする感じかな
export const ClassSchedule = () => {
  // const navigate = useNavigate();

  //画面表示用
  const [classScheduleOrigin, setClassScheduleOrigin] =
    useState<classScheduleList>(classScheduleSampleData);
  const [classSchedule, setClassSchedule] = useState<classScheduleList>(
    classScheduleSampleData
  );
  const [studentSchedule, setStudentSchedule] = useState<studentScheduleList>(
    studentScheduleSampleData
  );
  //カレンダーで選択した日付を管理
  const [calender, setCalender] = useState<string>(
    new Date().toLocaleString().split(" ")[0].replace("/", "-")
  );
  //プルダウンで選択したコマの情報を管理
  const [frame, setFrame] = useState<string>("");
  //チェックを入れた授業のIDを管理
  const [targetClass, setTargetClass] = useState<string>("");

  //振替期限を管理
  const [alterClassSpan, setAlterClassSpan] = useState<string>("");
  //モーダルで指定した振替情報を管理
  const [alterClassDate, setAlterClassDate] = useState<string>("");
  const [alterClassFrame, setAlterClassFrame] = useState<string>("");

  //モーダル開閉管理==========================================
  let subtitle: HTMLHeadingElement | null;
  const [studentScheduleModalIsOpen, setStudentScheduleModalIsOpen] =
    useState<boolean>(false);
  const [alterDateModalIsOpen, setAlterDateModalIsOpen] =
    useState<boolean>(false);
  const [changeLecturerModalIsOpen, setChangeLecturerModalIsOpen] =
    useState<boolean>(false);
  // =========================================================

  //get Data From Database
  // 初期表示：当日の授業全件パラメータ：targetDate
  useEffect(() => {
    const today = formatDate(new Date());
    getTargetDateClassSchedule(today);
    setCalender(today);
  }, []);

  //カレンダーに今日を初期表示（yyyy-mm-dd以外受付けしないみたい）
  const formatDate = (date: Date) => {
    const y = date.getFullYear();
    const m = ("00" + (date.getMonth() + 1)).slice(-2);
    const d = ("00" + date.getDate()).slice(-2);
    return y + "-" + m + "-" + d;
  };

  //生徒予定========================================
  function openModalStudentSchedule() {
    if (!targetClass) {
      alert("操作する授業にチェックを入れてください");
      return;
    }
    //チェックを入れた授業の情報取得
    const selectClassInfo = classScheduleOrigin.filter(
      (info: ClassInfo) => info.id.toString() === targetClass.toString()
    );
    getSelectClassStudentSchedule(selectClassInfo[0].studentId);
    setStudentScheduleModalIsOpen(true);
  }
  function afterOpenModalStudentSchedule() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalStudentSchedule() {
    setStudentScheduleModalIsOpen(false);
  }

  //alter Date======================================
  function openModalAlterClass() {
    if (!targetClass) {
      alert("操作する授業にチェックを入れてください");
      return;
    }
    //チェックを入れた授業の情報取得
    const selectClassInfo = classScheduleOrigin.filter(
      (info: ClassInfo) => info.id.toString() === targetClass.toString()
    );
    //振替のモーダルに振替期限を表示
    setAlterClassSpan(
      "※振替期限：" +
        selectClassInfo[0].rescheduleDateStart +
        " ~ " +
        selectClassInfo[0].rescheduleDateEnd
    );
    setAlterDateModalIsOpen(true);
  }
  function afterOpenModalAlterClass() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalAlterClass() {
    setAlterDateModalIsOpen(false);
  }
  //===================================================

  //change Lecturer======================================
  function openModalChangeLecturer() {
    setChangeLecturerModalIsOpen(true);
  }
  function afterOpenModalChangeLecturer() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalChangeLecturer() {
    setChangeLecturerModalIsOpen(false);
  }
  //===================================================

  //選択授業の生徒の予定取得
  const getSelectClassStudentSchedule = (studentId: string) => {
    const options = { method: "GET" };
    fetch(
      `${API_BASE_URL}/tkg/class-schedule/student-schedule/${studentId}`,
      options
    )
      .then((response) => response.json())
      .then((schedule) => {
        //授業予定に設定
        setStudentSchedule(schedule);
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  };

  // 指定した日付の全授業を取得
  const getTargetDateClassSchedule = (targetDate: string) => {
    setFrame(""); //コマのプルダウンを初期値に戻す
    const options = { method: "GET" };
    fetch(
      `${API_BASE_URL}/tkg/class-schedule?targetDate=${targetDate}`,
      options
    )
      .then((response) => response.json())
      .then((fetchClassSchedule) => {
        setClassScheduleOrigin(fetchClassSchedule);
        setClassSchedule(fetchClassSchedule);
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  };

  //プルダウンで表示したい日付を選択（画面上部）；これから実装
  const selectDateAtCalender = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCalender(event.target.value);
    getTargetDateClassSchedule(event.target.value);
  };

  //プルダウンで表示したいコマを選択（画面上部）
  const selectFrameAtPulldown = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    const receivedFrameNumber = event.target.value;
    if (receivedFrameNumber !== "") {
      const filteredList = classScheduleOrigin.filter(
        (iTask: ClassInfo) =>
          iTask.period.toString() === receivedFrameNumber.toString()
      );
      setFrame(event.target.value); //選択したコマ情報
      setClassSchedule(filteredList); //選択したコマに該当する授業一覧
      return;
    } else {
      setClassSchedule(classScheduleOrigin);
      return;
    }
  };

  //モーダルで更新したい日付の情報を受け取る
  const alterClassDateAtModal = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setAlterClassDate(event.target.value);
  };

  //モーダルで更新したいコマの情報を受け取る
  const alterClassFrameAtModal = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setAlterClassFrame(event.target.value);
  };

  //更新したい授業を選択（チェックを入れる）
  const selectChangeTarget = (state: any) => {
    if (state.selectedCount > 0) {
      setTargetClass(state.selectedRows[0].id);
      //チェック入れる度にDBアクセスは・・・
      // getSelectClassStudentSchedule(state.selectedRows[0].studentId);
    }
  };

  //振替確定
  const confirmAlterClassInfo = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    event.preventDefault();
    //振替情報
    const dateInfo = alterClassDate;
    if (!dateInfo) {
      alert("振替日付を選択してください");
      return;
    }
    const frameInfo = alterClassFrame;
    if (!frameInfo) {
      alert("コマを選択してください");
      return;
    }
    //データベース更新処理
    updateTargetClass();
  };

  //更新処理実施
  const updateTargetClass = () => {
    const options = {
      //  method: "POST",
      method: "PUT",
      body: targetClass + "," + alterClassDate + "," + alterClassFrame,
    };
    fetch(`${API_BASE_URL}/tkg/class-schedule/update`, options)
      .then((response) => response.json())
      .then((updateTargetClass) => {
        if (updateTargetClass.receiveErrorMessage) {
          alert(updateTargetClass.receiveErrorMessage.toString());
        } else {
          const today = formatDate(new Date());
          getTargetDateClassSchedule(today);
          alert("更新完了");
          closeModalAlterClass();
        }
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't add task");
      });
  };

  return (
    <>
      <div id="app">
        <Helmet>
          <title>ClassSchedule Page</title>
        </Helmet>
        <Container className={"tkgTop mt-4"}>
          <Row className={"tkgTopHeader"}>
            <Col md={5}></Col>
            <Col md={6}>
              <h2>授業一覧</h2>
            </Col>
          </Row>
          <Row className={"pt-4"}>
            <Col md={4} className={"pl-4"}>
              <span>
                <b>日付</b>
              </span>{" "}
              <span>
                <input
                  type="date"
                  value={calender}
                  defaultValue={calender}
                  onChange={selectDateAtCalender}
                ></input>
              </span>
            </Col>
            <Col md={4} className={"pl-4"}>
              <span>
                <b>コマ</b>
              </span>{" "}
              <select onChange={selectFrameAtPulldown} value={frame || ""}>
                <option value="" selected>
                  選択
                </option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
              </select>
            </Col>
          </Row>
          <br />
          <Row className={"pt-4"}>
            <Col md={12} className={"pb-6 pl-4"}>
              <Card>
                <DataTable
                  //content="Movies"
                  columns={classScheduleTableColumns}
                  data={classSchedule}
                  pagination
                  defaultSortFieldId="content"
                  sortIcon={<SortIcon />}
                  selectableRows
                  selectableRowsSingle
                  onSelectedRowsChange={selectChangeTarget}
                />
              </Card>
            </Col>
          </Row>
          <Row className={"pt-2"}>
            <div className={"my-4"}>
              <span>
                <Button
                  onClick={openModalStudentSchedule}
                  className={"btn btn-secondary ml-4"}
                >
                  生徒予定
                </Button>
                <Modal
                  contentLabel="Student-Schedule"
                  isOpen={studentScheduleModalIsOpen}
                  style={customStyles}
                  onAfterOpen={afterOpenModalStudentSchedule}
                  onRequestClose={closeModalStudentSchedule}
                >
                  <CloseButton onClick={closeModalStudentSchedule} />
                  <form>
                    <Row>
                      <h4>生徒予定</h4>
                    </Row>
                    <Row className={"pt-4"}>
                      <Col md={12}>
                        <Table striped bordered hover>
                          <thead>
                            <tr>
                              <th className="text-center">日付</th>
                              <th className="text-center">コマ</th>
                              <th className="text-center">科目</th>
                              <th className="text-center">担当講師</th>
                              <th className="text-center">備考</th>
                            </tr>
                          </thead>
                          {studentSchedule.map((schedule, index) => (
                            <StudentScheduleView
                              schedule={schedule}
                              key={index}
                            />
                          ))}
                        </Table>
                      </Col>
                    </Row>
                    <Row className={"pt-4"}>
                      <Col md={3}>
                        {/* <button
                          className="btn btn-success float-right"
                          name="abc"
                          id="selectClass"
                          // value={classroom?.id}
                          onClick={confirmAlterClassInfo}
                        >
                          確定
                        </button> */}
                      </Col>
                    </Row>
                  </form>
                </Modal>
              </span>
              <span>
                <Button
                  onClick={openModalAlterClass}
                  className={"btn btn-secondary mx-4"}
                >
                  振替設定
                </Button>
                <Modal
                  contentLabel="Alter Setting"
                  isOpen={alterDateModalIsOpen}
                  style={customStyles}
                  onAfterOpen={afterOpenModalAlterClass}
                  onRequestClose={closeModalAlterClass}
                >
                  <CloseButton onClick={closeModalAlterClass} />
                  {/* <h3 ref={(_subtitle) => (subtitle = _subtitle)}>振替設定</h3> */}
                  <form>
                    <Row>
                      <h4>振替設定</h4>
                      <p>{alterClassSpan}</p>
                      <span>
                        <b>日付</b>
                      </span>{" "}
                      <span>
                        <input
                          type="date"
                          // value={calender}
                          // defaultValue={calender}
                          onChange={alterClassDateAtModal}
                        ></input>
                      </span>
                    </Row>
                    <Row className={"pt-4"}>
                      <Col md={4}>
                        <div>
                          <b>コマ</b>
                        </div>
                        <div>
                          <select
                            // value={frame || ""}
                            onChange={alterClassFrameAtModal}
                          >
                            <option>選択</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                          </select>
                        </div>
                      </Col>
                    </Row>
                    <Row className={"pt-4"}>
                      <Col md={9}></Col>
                      <Col md={3}>
                        <button
                          className="btn btn-success float-right"
                          name="abc"
                          id="selectClass"
                          // value={classroom?.id}
                          onClick={confirmAlterClassInfo}
                        >
                          確定
                        </button>
                      </Col>
                    </Row>
                  </form>
                </Modal>
              </span>
              <span>
                <Button
                  onClick={openModalChangeLecturer}
                  className={"btn btn-secondary ml-4"}
                >
                  講師変更
                </Button>
                <Modal
                  contentLabel="change Lecturer"
                  isOpen={changeLecturerModalIsOpen}
                  style={customStyles}
                  onAfterOpen={afterOpenModalChangeLecturer}
                  onRequestClose={closeModalChangeLecturer}
                >
                  <CloseButton onClick={closeModalChangeLecturer} />
                  {/* <h3 ref={(_subtitle) => (subtitle = _subtitle)}>振替設定</h3> */}
                  <form>
                    <Row>
                      <Col md={12}>
                        <h4>講師変更</h4>
                        <p>プルダウンから講師を選択してください</p>
                      </Col>
                    </Row>
                    <Row className={"pt-4"}>
                      <Col md={12}>
                        <div>
                          <b>講師</b>
                        </div>
                        <div>
                          {/* <select onChange={onChangeHandler} value={frame || ""}></select> */}
                          <select>
                            <option value="" selected>
                              選択
                            </option>
                            <option value="2">山崎</option>
                            <option value="3">吉川</option>
                            <option value="4">大崎</option>
                            <option value="11">荒川</option>
                          </select>
                        </div>
                      </Col>
                    </Row>
                    <Row className={"pt-4"}>
                      <Col md={8}></Col>
                      <Col md={4}>
                        <button className="btn btn-success float-right">
                          確定
                        </button>
                      </Col>
                    </Row>
                  </form>
                </Modal>
              </span>
              <Link
                to="/router-breadcrumbs/1st/2nd"
                className={"btn btn-warning mx-4"}
              >
                振替不可
              </Link>
            </div>
          </Row>
        </Container>
      </div>
    </>
  );
};
