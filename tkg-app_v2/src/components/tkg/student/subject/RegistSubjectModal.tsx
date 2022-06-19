import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import {
  Container,
  Col,
  Row,
  Button,
  CloseButton,
  Table,
} from "react-bootstrap";
import { customStyles, studentSubjArray, studentSubjData } from "../initData";
import "../../../tkgStyle.css";
import { API_STUDENT } from "../../../../config";

interface RegistSubjectProps {
  checkedStudentId: string;
}

const RegistSubjectModal: React.FC<RegistSubjectProps> = ({
  checkedStudentId,
}) => {
  //モーダル管理
  const [registSubjectModalIsOpen, setRegistSubjectModalIsOpen] =
    useState<boolean>(false);
  const [studentSubjectList, setStudentSubjectList] =
    useState<studentSubjArray>(studentSubjData);
  let subtitle: HTMLHeadingElement | null;

  function openModalRegistSubject() {
    getCurrentSubjects();
  }
  function afterOpenModalRegistSubject() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalRegistSubject() {
    setRegistSubjectModalIsOpen(false);
  }

  //現在の受講状況を取得
  const getCurrentSubjects = () => {
    if (!checkedStudentId) {
      alert("対象の生徒を選択してください。");
    } else {
      const options = { method: "GET" };
      fetch(`${API_STUDENT.SearchSubject}/${checkedStudentId}`, options)
        .then((response) => response.json())
        .then((studentSubjResult) => {
          if (studentSubjResult === "0") {
            alert("受講科目が設定されていません");
          } else {
            setStudentSubjectList(studentSubjResult);
            setRegistSubjectModalIsOpen(true);
          }
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }
  };

  return (
    <>
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
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                <th className="text-center text-nowrap">選択</th>
                <th className="text-center text-nowrap">科目名</th>
                <th className="text-center text-nowrap">担当講師</th>
                <th className="text-center text-nowrap">定期コマ</th>
              </tr>
            </thead>
            <tbody>
              {studentSubjectList.map((eachSubj) => (
                <>
                  <tr>
                    <td className={"align-middle"}>
                      <input
                        type="checkbox"
                        value={eachSubj.studentId}
                        // onChange={checkedSubject}
                        // checked={eachSubj.id === checkedSubjectId}
                      />
                    </td>
                    <td className={"align-middle"}>{eachSubj.subjectName}</td>
                    <td className={"align-middle"}>{eachSubj.lecturerName}</td>
                    <td className={"align-middle"}>
                      {eachSubj.dateOfweekFrame}
                    </td>
                  </tr>
                </>
              ))}
            </tbody>
          </Table>
        </>
      </Modal>
    </>
  );
};
export default RegistSubjectModal;
