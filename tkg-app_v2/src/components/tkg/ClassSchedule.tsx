/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
// import { useParams } from "react-router";
import { Link } from "react-router-dom"; //useNavigate
import DataTable from "react-data-table-component";
import Card from "@material-ui/core/Card";
import SortIcon from "@material-ui/icons/ArrowDownward";
import { API_BASE_URL } from "../../config";
import Modal from "react-modal";
import { Helmet } from "react-helmet";
import { Container, Button, Col, Row, CloseButton } from "react-bootstrap";
// import Header from "../react-form_220530/Header";

import "../tkgStyle.css";
import StudentScheduleModal from "./classSchedule/StudentScheduleModal";
import AlterDateModal from "./classSchedule/AlterDateModal";
import {
  ClassInfo,
  classScheduleList,
  classScheduleSampleData,
  customStyles,
} from "./classSchedule/initData";

//classScheduleSampleData Setting
// =======================-

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

Modal.setAppElement("#root");
//授業振替・講師変更、それぞれのモーダルウインドウ関連を別Componentにする
export const ClassSchedule = () => {
  // const navigate = useNavigate();

  //画面表示用
  const [classScheduleOrigin, setClassScheduleOrigin] =
    useState<classScheduleList>(classScheduleSampleData);
  const [classSchedule, setClassSchedule] = useState<classScheduleList>(
    classScheduleSampleData
  );
  //カレンダーで選択した日付を管理
  const [calender, setCalender] = useState<string>(
    new Date().toLocaleString().split(" ")[0].replace("/", "-")
  );
  //プルダウンで選択したコマの情報を管理
  const [frame, setFrame] = useState<string>("");
  //チェックを入れた授業のIDを管理
  const [targetClass, setTargetClass] = useState<string>("");

  //モーダル開閉管理==========================================
  let subtitle: HTMLHeadingElement | null;
  const [changeLecturerModalIsOpen, setChangeLecturerModalIsOpen] =
    useState<boolean>(false);
  // =========================================================

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

  // 指定した日付の全授業を取得
  const getTargetDateClassSchedule = (targetDate: string) => {
    setFrame(""); //コマのプルダウンを初期値に戻す
    const options = { method: "GET" };
    fetch(`${API_BASE_URL}/class-schedule?targetDate=${targetDate}`, options)
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

  //プルダウンで表示したい日付を選択（画面上部）
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

  //更新したい授業を選択（チェックを入れる）
  const selectChangeTarget = (state: any) => {
    if (state.selectedCount > 0) {
      setTargetClass(state.selectedRows[0].id);
      //チェック入れる度にDBアクセスは・・・
      // getSelectClassStudentSchedule(state.selectedRows[0].studentId);
    }
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
              {/* 生徒予定表示 */}
              <StudentScheduleModal
                classScheduleOrigin={classScheduleOrigin}
                targetClass={targetClass}
              />
              {/* 振替設定 */}
              <AlterDateModal
                classScheduleOrigin={classScheduleOrigin}
                setClassScheduleOrigin={setClassScheduleOrigin}
                targetClass={targetClass}
                setFrame={setFrame}
                setClassSchedule={setClassSchedule}
              />
              {/* 講師変更 */}
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
