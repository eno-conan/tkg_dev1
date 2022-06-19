/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";
import "../../../tkgStyle.css";
import { API_STUDENT } from "../../../../config";

interface UpdateInfo {
  checkedStudentId: string;
  subject: string;
  timetable: string;
  lecturer: string;
  setRegistSubjectModalIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
}
const RegistSubjectModalInsertDb: React.FC<UpdateInfo> = ({
  checkedStudentId,
  subject,
  timetable,
  lecturer,
  setRegistSubjectModalIsOpen,
}) => {
  //データ追加
  const inserTableStudentSubject = () => {
    if (!subject || !timetable || !lecturer) {
      alert("科目・コマ・講師のいずれかが選択されていません");
    } else {
      //重複登録していないかチェックを実施/
      const sendContent: string[] = [];
      sendContent.push(checkedStudentId);
      sendContent.push(subject);
      sendContent.push(timetable);
      sendContent.push(lecturer);
      const options = {
        method: "POST",
        body: sendContent.toString(),
      };
      fetch(`${API_STUDENT.RegistSubject}`, options)
        .then((response) => response.json())
        .then((insertSubject) => {
          alert(
            "受講科目を追加しました。「受講科目確認」から登録内容を確認できます"
          );
          setRegistSubjectModalIsOpen(false);
        })
        .catch((error) => {
          console.log(error);
          alert("科目追加が行えませんでした");
        });
    }
  };
  return (
    <>
      <Row>
        <Col md={10}></Col>
        <Col md={2}>
          <button
            className="btn btn-success float-right w-100"
            name="abc"
            id="serachResult"
            onClick={inserTableStudentSubject}
          >
            登録
          </button>
        </Col>
      </Row>
    </>
  );
};

export default RegistSubjectModalInsertDb;
