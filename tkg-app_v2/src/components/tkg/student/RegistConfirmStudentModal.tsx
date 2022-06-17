/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";
import { customStyles } from "../ClassSchedule";
import "../../tkgStyle.css";
import { API_STUDENT } from "../../../config";
import { Classroom, Grade } from "./initData";
import { classroomArray, gradeArray } from "./initData";

interface RegistStudentModal {
  studentName: string;
  birthday: string;
  classroomName: string;
  gradeName: string;
  classroomList: classroomArray;
  gradeList: gradeArray;
}

const RegistConfirmStudentModal: React.FC<RegistStudentModal> = ({
  studentName,
  birthday,
  classroomName,
  gradeName,
  classroomList,
  gradeList,
}) => {
  //モーダル管理
  const [registStudentModalIsOpen, setRegistStudentModalIsOpen] =
    useState<boolean>(false);
  let subtitle: HTMLHeadingElement | null;

  function openModalRegistStudent() {
    //バリデーションチェック
    if (!studentName || !birthday || !classroomName || !gradeName) {
      alert("未入力の項目があります");
    } else {
      setRegistStudentModalIsOpen(true);
    }
  }
  function afterOpenModalRegistStudent() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalRegistStudent() {
    setRegistStudentModalIsOpen(false);
  }

  //テーブルへの追加
  const registStudentTable = () => {
    //選択した教室の情報変換
    const selectClassroom = classroomList.filter(
      (info: Classroom) => info.name.toString() === classroomName
    );
    const selectGrade = gradeList.filter(
      (info: Grade) => info.name.toString() === gradeName
    );
    console.log(selectClassroom[0].id);
    console.log(selectGrade[0].key);

    const sendContent: string[] = [];
    sendContent.push(studentName);
    sendContent.push(birthday);
    sendContent.push(selectClassroom[0].id);
    sendContent.push(selectGrade[0].key);

    const options = {
      method: "POST",
      body: sendContent.toString(),
    };
    fetch(`${API_STUDENT.RegistStudent}`, options)
      .then((response) => response.json())
      .then((insertResult) => {
        alert(insertResult);
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't add task");
      });
  };

  return (
    <>
      <Row>
        <Col md={10}></Col>
        <Col md={2}>
          <Button onClick={openModalRegistStudent}>内容確認</Button>
        </Col>
        <Modal
          contentLabel="Alter Setting"
          isOpen={registStudentModalIsOpen}
          style={customStyles}
          onAfterOpen={afterOpenModalRegistStudent}
          onRequestClose={closeModalRegistStudent}
        >
          <Row>
            <Col md={11}></Col>
            <Col md={1}>
              <CloseButton onClick={closeModalRegistStudent} />
            </Col>
          </Row>
          <Row className={"pb-2"}>
            <Col md={4}></Col>
            <Col md={4}>
              <h4>登録内容確認</h4>
            </Col>
          </Row>
          <Row className={"pb-2"}>
            <Col md={4}></Col>
            <Col md={4}>
              <label>氏名：</label>
              <span>{studentName}</span>
            </Col>
          </Row>
          <Row className={"pb-2"}>
            <Col md={4}></Col>
            <Col md={4}>
              <label>誕生日：</label>
              <span>{birthday}</span>
            </Col>
          </Row>
          <Row className={"pb-2"}>
            <Col md={4}></Col>
            <Col md={4}>
              <label>教室名：</label>
              <span>{classroomName}</span>
            </Col>
          </Row>
          <Row className={"pb-2"}>
            <Col md={4}></Col>
            <Col md={4}>
              <label>学年：</label>
              <span>{gradeName}</span>
            </Col>
          </Row>
          <Row>
            <Col md={10}></Col>
            <Col md={2}>
              <button
                className="btn btn-success float-right w-100"
                name="abc"
                id="selectClass"
                // value={classroom?.id}
                onClick={registStudentTable}
              >
                確定
              </button>
            </Col>
          </Row>
        </Modal>
      </Row>
    </>
  );
};

export default RegistConfirmStudentModal;
