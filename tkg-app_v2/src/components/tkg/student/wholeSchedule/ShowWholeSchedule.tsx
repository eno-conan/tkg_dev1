/* eslint-disable array-callback-return */
import React from "react";
import { useNavigate } from "react-router-dom";
import { Col, Row, Button } from "react-bootstrap";
import "../../../tkgStyle.css";
import { STUDENT_FUNCTION } from "../../../../config";

interface ShowCurrentSubjectsProps {
  checkedStudentId: string;
}

const ShowWholeSchedule: React.FC<ShowCurrentSubjectsProps> = ({
  checkedStudentId,
}) => {
  const checkStudentSchedule = () => {
    // if (checkedStudentId) {
    //   window.open(
    //     `${STUDENT_FUNCTION.SearchStudent}/${checkedStudentId}`,
    //     "",
    //     "width=800,height=400,left=200,top=150"
    //   );
    // } else {
    //   alert("対象の生徒を選択してください。");
    // }
  };
  return (
    <>
      <Col md={2}>
        <Button
          onClick={checkStudentSchedule}
          className={"btn btn-secondary ml-2"}
        >
          授業予定表示
        </Button>
      </Col>
    </>
  );
};

export default ShowWholeSchedule;
