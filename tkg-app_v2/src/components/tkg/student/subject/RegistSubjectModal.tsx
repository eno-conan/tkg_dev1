import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import { Col, Row, Button, CloseButton } from "react-bootstrap";
import {
  customStyles,
  lecturerArray,
  lecturerData,
  subjByGradeArray,
  subjByGradeData,
  timeTableNormalArray,
  timeTableNormalData,
} from "../initData";
import "../../../tkgStyle.css";
import { API_STUDENT } from "../../../../config";
import RegistSubjectModalContent from "./RegistSubjectModalContent";
import RegistSubjectModalInsertDb from "./RegistSubjectModalInsertDb";

interface RegistSubjectProps {
  checkedStudentId: string;
}

const RegistSubjectModal: React.FC<RegistSubjectProps> = ({
  checkedStudentId,
}) => {
  //モーダル管理
  const [registSubjectModalIsOpen, setRegistSubjectModalIsOpen] =
    useState<boolean>(false);

  //科目一覧（既に受講済みの科目は除外）
  const [subjectsByGradeList, setSubjectsByGradeList] =
    useState<subjByGradeArray>(subjByGradeData);
  //講師一覧
  const [lecturerList, setLecturerList] = useState<lecturerArray>(lecturerData);
  //タイムテーブル一覧（既に授業が予定されたコマは除外）
  const [timeTableNormalList, setTimeTableNormal] =
    useState<timeTableNormalArray>(timeTableNormalData);

  //選択した値を管理
  const [subject, setSubject] = useState<string>("");
  const [timetable, setTimetable] = useState<string>("");
  const [lecturer, setLecturer] = useState<string>("");
  let subtitle: HTMLHeadingElement | null;

  //モーダル関連
  function openModalRegistSubject() {
    if (!checkedStudentId) {
      alert("対象の生徒を選択してください。");
    } else {
      getSubjectsByGrade();
      getTimeTableNormal();
      getLecturer();
      //モーダル表示
      setRegistSubjectModalIsOpen(true);
    }
  }
  function afterOpenModalRegistSubject() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalRegistSubject() {
    setRegistSubjectModalIsOpen(false);
  }

  //生徒の学年に応じて受講科目を取得
  const getSubjectsByGrade = () => {
    const options = { method: "GET" };
    fetch(`${API_STUDENT.PrepareSubjectList}/${checkedStudentId}`, options)
      .then((response) => response.json())
      .then((result) => {
        if (result === "0") {
          alert("受講科目が設定されていません");
        } else {
          setSubjectsByGradeList(result);
        }
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  };

  //タイムテーブルを取得
  const getTimeTableNormal = () => {
    const options = { method: "GET" };
    fetch(`${API_STUDENT.PrepareTimeTableNormal}/${checkedStudentId}`, options)
      .then((response) => response.json())
      .then((result) => {
        if (result === "0") {
          alert("受講科目が設定されていません");
        } else {
          setTimeTableNormal(result);
        }
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  };

  //講師を取得
  const getLecturer = () => {
    const options = { method: "GET" };
    fetch(`${API_STUDENT.PrepareLecutererList}/${checkedStudentId}`, options)
      .then((response) => response.json())
      .then((result) => {
        if (result === "0") {
          alert("受講科目が設定されていません");
        } else {
          setLecturerList(result);
        }
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  };

  return (
    <>
      <Col md={2}>
        <Button
          onClick={openModalRegistSubject}
          className={"btn btn-secondary ml-2"}
        >
          受講科目登録
        </Button>
        <Modal
          contentLabel="registSubject"
          isOpen={registSubjectModalIsOpen}
          style={customStyles}
          onAfterOpen={afterOpenModalRegistSubject}
          onRequestClose={closeModalRegistSubject}
        >
          <>
            <Row>
              <Col md={11}></Col>
              <Col md={1}>
                <CloseButton onClick={closeModalRegistSubject} />
              </Col>
            </Row>
            <RegistSubjectModalContent
              subjectsByGradeList={subjectsByGradeList}
              timeTableNormalList={timeTableNormalList}
              lecturerList={lecturerList}
              setSubject={setSubject}
              setTimetable={setTimetable}
              setLecturer={setLecturer}
            />
            <RegistSubjectModalInsertDb
              checkedStudentId={checkedStudentId}
              subject={subject}
              timetable={timetable}
              lecturer={lecturer}
              setRegistSubjectModalIsOpen={setRegistSubjectModalIsOpen}
            />
          </>
        </Modal>
      </Col>
    </>
  );
};
export default RegistSubjectModal;
