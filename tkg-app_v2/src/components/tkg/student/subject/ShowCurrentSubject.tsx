/* eslint-disable array-callback-return */
import React from "react";
import { useNavigate } from "react-router-dom";
import { Col, Row, Button } from "react-bootstrap";
import "../../../tkgStyle.css";
import { STUDENT_FUNCTION } from "../../../../config";

interface ShowCurrentSubjectsProps {
  checkedStudentId: string;
}

const ShowCurrentSubject: React.FC<ShowCurrentSubjectsProps> = ({
  checkedStudentId,
}) => {
  const checkStudentSubject = () => {
    if (checkedStudentId) {
      window.open(
        `${STUDENT_FUNCTION.SearchStudent}/${checkedStudentId}`,
        "",
        "width=800,height=400,left=200,top=150"
      );
    } else {
      alert("対象の生徒を選択してください。");
    }
  };
  return (
    <>
      <Col md={2}>
        <Button onClick={checkStudentSubject} className={"btn btn-secondary"}>
          受講科目確認
        </Button>
      </Col>
    </>
  );
};

export default ShowCurrentSubject;
