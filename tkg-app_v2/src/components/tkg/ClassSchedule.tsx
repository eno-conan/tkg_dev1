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
  // DropdownButton,
  // Dropdown,
} from "react-bootstrap";

import "../tkgStyle.css";

//taskData Setting
// =======================-
const taskData = [
  {
    id: 1,
    frame: 6,
    grade: "h1",
    subject: "数学IA",
    studentName: "山本由伸",
    lecturerName: "講師A",
    date: "2022-06-01",
  },
  {
    id: 2,
    frame: 6,
    grade: "h2",
    subject: "数学2B",
    studentName: "古田敦也",
    lecturerName: "講師B",
    date: "2022-06-01",
  },
  {
    id: 3,
    frame: 6,
    grade: "h2",
    subject: "科学",
    studentName: "柳田悠岐",
    lecturerName: "講師B",
    date: "2022-06-01",
  },
  {
    id: 4,
    frame: 7,
    grade: "j3",
    subject: "英語",
    studentName: "山田哲人",
    lecturerName: "講師B",
    date: "2022-06-02",
  },
  // {
  //   id: 1,
  //   frame_id: 6,
  //   content: "Beetlejuice",
  //   done: false,
  // },
];
const columns = [
  {
    name: "コマ",
    selector: (row: { frame: number }) => row.frame,
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
// ========================-

interface ITask {
  id: number;
  frame: number;
  grade: string;
  subject: string;
  studentName: string;
  lecturerName: string;
  date: string;
}
export type taskList = Array<ITask>;

const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
  },
};

Modal.setAppElement("#root");

export const ClassSchedule = () => {
  const navigate = useNavigate();

  //manage frames（授業一覧を管理）
  const [tasks, setTasks] = useState<taskList>(taskData);
  //set select frame at view (プルダウンで選択したコマの情報を管理)
  const [frame, setFrame] = useState<string>("");
  const [targetClass, setTargetClass] = useState<string>("");
  //モーダルで指定した振替情報を管理
  const [alterClassDate, setAlterClassDate] = useState<string>("");
  const [alterClassFrame, setAlterClassFrame] = useState<string>("");

  //モーダル開閉管理==========================================
  let subtitle: HTMLHeadingElement | null;
  const [alterDateModalIsOpen, setAlterDateModalIsOpen] =
    useState<boolean>(false);
  const [changeLecturerModalIsOpen, setChangeLecturerModalIsOpen] =
    useState<boolean>(false);
  // =========================================================

  //get Data From Database
  // useEffect(() => {
  //   const options = { method: "GET" };

  //   fetch(`${API_BASE_URL}/tasks`, options)
  //     .then((response) => response.json())
  //     .then((fetchedTasks) => setTasks(fetchedTasks))
  //     .catch((error) => {
  //       console.log(error);
  //       alert("couldn't fetch tasks");
  //     });
  // }, []);

  //alter Date======================================
  function openModalAlterDate() {
    setAlterDateModalIsOpen(true);
  }
  function afterOpenModalAlterDate() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalAlterDate() {
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

  //プルダウンで表示したい日付を選択（画面上部）

  //プルダウンで表示したいコマを選択（画面上部）
  const selectFrameAtPulldown = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    const receivedFrameNumber = event.target.value;
    if (receivedFrameNumber != null) {
      const filteredList = tasks.filter(
        (iTask: ITask) =>
          iTask.frame.toString() === receivedFrameNumber.toString()
      );
      setFrame(event.target.value); //選択したコマ情報
      setTasks(filteredList); //選択したコマに該当する授業一覧
    }
  };

  //モーダルで更新したい日付の情報を受け取る
  const alterClassDateAtModal = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    console.log("alterClass Date:", event.target.value);
    setAlterClassDate(event.target.value);
  };

  //モーダルで更新したいコマの情報を受け取る
  const alterClassFrameAtModal = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    console.log("alterClass Frame:", event.target.value);
    setAlterClassFrame(event.target.value);
  };

  //更新したい授業を選択
  const selectChangeTarget = (state: any) => {
    if (state.selectedCount > 0) {
      console.log(state.selectedRows[0].id);
      setTargetClass(state.selectedRows[0].id);
    }
  };

  //振替確定
  const confirmAlterClassInfo = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    event.preventDefault();
    //更新対象授業（チェックを入れた授業）の情報
    const changeTargetClassId = targetClass;
    //振替情報
    const dateInfo = alterClassDate;
    const frameInfo = alterClassFrame;
    console.log(dateInfo);
    console.log(frameInfo);
    navigate(`/receive-param/${changeTargetClassId}`);
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
              <h2>授業予定</h2>
            </Col>
          </Row>
          <Row className={"pt-4"}>
            <Col md={4} className={"pl-4"}>
              <span>
                <b>日付</b>
              </span>{" "}
              <span>
                <input type="date"></input>
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
                  columns={columns}
                  data={tasks}
                  pagination
                  defaultSortFieldId="content"
                  sortIcon={<SortIcon />}
                  selectableRows
                  onSelectedRowsChange={selectChangeTarget}
                />
              </Card>
            </Col>
          </Row>
          <Row className={"pt-2"}>
            <div className={"my-4"}>
              <span>
                <Button
                  onClick={openModalAlterDate}
                  className={"btn btn-secondary ml-4"}
                >
                  振替設定
                </Button>
                <Modal
                  contentLabel="Alter Setting"
                  isOpen={alterDateModalIsOpen}
                  style={customStyles}
                  onAfterOpen={afterOpenModalAlterDate}
                  onRequestClose={closeModalAlterDate}
                >
                  <CloseButton onClick={closeModalAlterDate} />
                  {/* <h3 ref={(_subtitle) => (subtitle = _subtitle)}>振替設定</h3> */}
                  <form>
                    <Row>
                      <h4>振替設定</h4>
                      <span>
                        <b>日付</b>
                      </span>{" "}
                      <span>
                        <input
                          type="date"
                          // value={inputValue}
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
                            <option value="11">11</option>
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
                  className={"btn btn-secondary mx-4"}
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
                className={"btn btn-warning ml-4"}
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
