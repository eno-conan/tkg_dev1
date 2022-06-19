/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";
import { customStyles } from "./student/initData";
import "../../../tkgStyle.css";

interface RegistStudentModal {}

const ModalTempate: React.FC<RegistStudentModal> = ({}) => {
  //モーダル管理
  const [registStudentModalIsOpen, setRegistStudentModalIsOpen] =
    useState<boolean>(false);
  let subtitle: HTMLHeadingElement | null;

  function openModalRegistStudent() {
    //バリデーションチェック
    // if (!studentName || !birthday || !classroomName || !gradeName) {
    //   alert("未入力の項目があります");
    // } else {
    //   setRegistStudentModalIsOpen(true);
    // }
  }
  function afterOpenModalRegistStudent() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalRegistStudent() {
    setRegistStudentModalIsOpen(false);
  }

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
          <>
            <Row>
              <Col md={11}></Col>
              <Col md={1}>
                <CloseButton onClick={closeModalRegistStudent} />
              </Col>
            </Row>
          </>
        </Modal>
      </Row>
    </>
  );
};

export default ModalTempate;
