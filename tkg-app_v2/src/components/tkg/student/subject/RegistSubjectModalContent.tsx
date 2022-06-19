/* eslint-disable array-callback-return */
import React from "react";
import { Col, Row, Button } from "react-bootstrap";
import "../../../tkgStyle.css";
import {
  lecturerArray,
  subjByGradeArray,
  timeTableNormalArray,
} from "../initData";

interface RegistSubjectModalContentProps {
  subjectsByGradeList: subjByGradeArray;
  timeTableNormalList: timeTableNormalArray;
  lecturerList: lecturerArray;
  setSubject: React.Dispatch<React.SetStateAction<string>>;
  setTimetable: React.Dispatch<React.SetStateAction<string>>;
  setLecturer: React.Dispatch<React.SetStateAction<string>>;
}

const RegistSubjectModalContent: React.FC<RegistSubjectModalContentProps> = ({
  subjectsByGradeList,
  timeTableNormalList,
  lecturerList,
  setSubject,
  setTimetable,
  setLecturer,
}) => {
  //プルダウンで値選択
  const selectSubject = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSubject(event.target.value);
  };
  const selectTimeTable = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setTimetable(event.target.value);
  };
  const selectLecturer = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setLecturer(event.target.value);
  };

  return (
    <>
      {" "}
      <Row>
        <Col md={4}></Col>
        <Col md={8} className={"mb-4"}>
          <h4>受講科目登録</h4>
        </Col>
      </Row>
      <Row>
        <Col md={4}></Col>
        <Col md={8} className={"mb-4"}>
          <label className={"mb-1"}>学年</label>
          <div>
            <select onChange={selectSubject}>
              <option value="" selected>
                プルダウンから選択してください
              </option>
              {subjectsByGradeList.map((subject) => (
                <option value={subject.subjectKey}>
                  {subject.subjectName}
                </option>
              ))}
            </select>
          </div>
        </Col>
      </Row>
      <Row>
        <Col md={4}></Col>
        <Col md={8} className={"mb-4"}>
          <label className={"mb-1"}>定期コマ</label>
          <div>
            <select onChange={selectTimeTable}>
              <option value="" selected>
                プルダウンから選択してください
              </option>
              {timeTableNormalList.map((timetable) => (
                <option value={timetable.timeTableId}>
                  {timetable.dateOfWeekFrame}
                </option>
              ))}
            </select>
          </div>
        </Col>
      </Row>
      <Row>
        <Col md={4}></Col>
        <Col md={8} className={"mb-4"}>
          <label className={"mb-1"}>担当講師</label>
          <div>
            <select onChange={selectLecturer}>
              <option value="" selected>
                プルダウンから選択してください
              </option>
              {lecturerList.map((lec) => (
                <option value={lec.lecturerId}>{lec.lecturerName}</option>
              ))}
            </select>
          </div>
        </Col>
      </Row>
    </>
  );
};

export default RegistSubjectModalContent;
