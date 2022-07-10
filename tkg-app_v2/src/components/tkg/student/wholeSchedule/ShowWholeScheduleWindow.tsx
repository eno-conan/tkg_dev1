/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { Container, Col, Row, Table } from "react-bootstrap";
import { studentScheduleArray, studentScheduleData } from "../initData";
import "../../../tkgStyle.css";
import { API_STUDENT, PASS_MAIN_FUNCTION } from "../../../../config";

type Params = {
  checkedStudentId?: string;
};

const ShowWholeScheduleWindow = () => {
  const { checkedStudentId } = useParams<Params>();
  const [studentscheduleList, setStudentscheduleList] =
    useState<studentScheduleArray>(studentScheduleData);

  useEffect(() => {
    const getCurrentSubjects = () => {
      const options = { method: "GET" };
      fetch(`${API_STUDENT.WholeSchedule}/${checkedStudentId}`, options)
        .then((response) => response.json())
        .then((studentScheduleResult) => {
          setStudentscheduleList(studentScheduleResult);
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    };
    getCurrentSubjects();
  }, [checkedStudentId]);

  return (
    <>
      <Container>
        <Row className={"mt-4"}>
          {/* <Col md={4}>
            <Link to={`${PASS_MAIN_FUNCTION.Student}`} className={"mx-4"}>
              閉じる
            </Link>
          </Col> */}
          <Col md={6}>
            <h3>生徒予定</h3>
          </Col>
        </Row>
        <Row className={"pt-4"}>
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                <th className="text-center text-nowrap">授業日</th>
                <th className="text-center text-nowrap">コマ</th>
                <th className="text-center text-nowrap">科目名</th>
                <th className="text-center text-nowrap">担当講師</th>
                <th className="text-center text-nowrap">通常・講習</th>
                <th className="text-center text-nowrap">振替開始日</th>
                <th className="text-center text-nowrap">振替終了日</th>
              </tr>
            </thead>
            <tbody>
              {studentscheduleList &&
                studentscheduleList.map((eachClass) => (
                  <>
                    <tr>
                      <td className={"align-middle"}>{eachClass.classDate}</td>
                      <td className={"align-middle"}>{eachClass.period}</td>
                      <td className={"align-middle"}>
                        {eachClass.subjectName}
                      </td>
                      <td className={"align-middle"}>{eachClass.lecturerId}</td>
                      {eachClass.normalSpecial === "special" ? (
                        <>
                          <td className={"align-middle"}>講習</td>
                        </>
                      ) : (
                        <>
                          <td className={"align-middle"}>通常</td>
                        </>
                      )}
                      <td className={"align-middle"}>
                        {eachClass.rescheduleDateStart}
                      </td>
                      <td className={"align-middle"}>
                        {eachClass.rescheduleDateLast}
                      </td>
                    </tr>
                  </>
                ))}
            </tbody>
          </Table>
        </Row>
      </Container>
    </>
  );
};
export default ShowWholeScheduleWindow;
