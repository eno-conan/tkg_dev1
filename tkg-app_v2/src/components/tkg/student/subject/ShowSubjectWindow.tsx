/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useParams } from "react-router";
import { Container, Col, Row, Table } from "react-bootstrap";
import { studentSubjArray, studentSubjData } from "../initData";
import "../../../tkgStyle.css";
import { API_STUDENT } from "../../../../config";

type Params = {
  checkedStudentId?: string;
};

const ShowSubjectWindow = () => {
  const { checkedStudentId } = useParams<Params>();
  const [studentSubjectList, setStudentSubjectList] =
    useState<studentSubjArray>(studentSubjData);

  useEffect(() => {
    const getCurrentSubjects = () => {
      const options = { method: "GET" };
      fetch(`${API_STUDENT.SearchSubject}/${checkedStudentId}`, options)
        .then((response) => response.json())
        .then((studentSubjResult) => {
          if (studentSubjResult === "0") {
            alert("受講科目が設定されていません");
            window.close(); //別ウインドウを閉じる
          } else {
            setStudentSubjectList(studentSubjResult);
          }
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
          <Col md={4}></Col>
          <Col md={6}>
            <h3>生徒受講科目</h3>
          </Col>
        </Row>
        <Row className={"pt-4"}>
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                <th className="text-center text-nowrap">科目名</th>
                <th className="text-center text-nowrap">担当講師</th>
                <th className="text-center text-nowrap">定期コマ</th>
              </tr>
            </thead>
            <tbody>
              {studentSubjectList.map((eachSubj) => (
                <>
                  <tr>
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
        </Row>
      </Container>
    </>
  );
};
export default ShowSubjectWindow;
