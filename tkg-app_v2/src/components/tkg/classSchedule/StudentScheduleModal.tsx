import React, { useState } from "react";
import { API_BASE_URL } from "../../../config";
import { ClassInfo, classScheduleList, customStyles } from "./initData";
import StudentScheduleView from "./StudentScheduleView";
import Modal from "react-modal";
import {
  Button,
  Col,
  Row,
  CloseButton,
  Table,
  // DropdownButton,
  // Dropdown,
} from "react-bootstrap";

interface ReceiveClassSchedule {
  classScheduleOrigin: classScheduleList;
  targetClass: string;
}

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

const StudentScheduleModal: React.FC<ReceiveClassSchedule> = ({
  classScheduleOrigin,
  targetClass,
}) => {
  const [studentScheduleModalIsOpen, setStudentScheduleModalIsOpen] =
    useState<boolean>(false);
  const [studentSchedule, setStudentSchedule] = useState<studentScheduleList>(
    studentScheduleSampleData
  );
  let subtitle: HTMLHeadingElement | null;
  //選択授業の生徒の予定取得
  const getSelectClassStudentSchedule = (studentId: string) => {
    const options = { method: "GET" };
    fetch(
      `${API_BASE_URL}/class-schedule/student-schedule/${studentId}`,
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
  return (
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
        <form>
          <Row>
            <Col md={11}></Col>
            <Col md={1}>
              <CloseButton onClick={closeModalStudentSchedule} />
            </Col>
          </Row>
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
                  <StudentScheduleView schedule={schedule} key={index} />
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
  );
};
export default StudentScheduleModal;
