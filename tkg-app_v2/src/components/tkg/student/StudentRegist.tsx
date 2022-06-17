/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import {
  gradeArray,
  classroomArray,
  classroomData,
  gradeData,
} from "./initData";
import "../../tkgStyle.css";
import { API_STUDENT } from "../../../config";
import RegistForm from "./regist/RegistForm";
import RegistConfirmStudentModal from "./regist/RegistConfirmStudentModal";

const StudentRegist = () => {
  //入力項目の管理
  const [studentName, setStudentName] = useState<string>("");
  const [birthday, setBirthday] = useState<string>("");
  const [classroomName, setClassroomName] = useState<string>("");
  const [gradeName, setGradeName] = useState<string>("");
  //DBからの教室情報を管理
  const [classroomList, setClassroomList] =
    useState<classroomArray>(classroomData);
  //DBからの学年情報を管理
  const [gradeList, setGradeList] = useState<gradeArray>(gradeData);

  useEffect(() => {
    prepareClassroom();
    prepareGrade();
  }, []);

  const prepareClassroom = () => {
    const options = {
      method: "GET",
    };
    fetch(`${API_STUDENT.PrepareRegistClassroom}`, options)
      .then((response) => response.json())
      .then((classroomInfo) => {
        setClassroomList(classroomInfo);
      })
      .catch((error) => {
        console.log(error);
        alert("教室情報取得できず");
      });
  };
  const prepareGrade = () => {
    const options = {
      method: "GET",
    };
    fetch(`${API_STUDENT.PrepareRegistGrade}`, options)
      .then((response) => response.json())
      .then((gradeInfo) => {
        setGradeList(gradeInfo);
      })
      .catch((error) => {
        console.log(error);
        alert("学年情報取得できず");
      });
  };

  return (
    <Container>
      <RegistForm
        studentName={studentName}
        setStudentName={setStudentName}
        birthday={birthday}
        setBirthday={setBirthday}
        setClassroomName={setClassroomName}
        setGradeName={setGradeName}
        classroomList={classroomList}
        gradeList={gradeList}
      />
      <RegistConfirmStudentModal
        studentName={studentName}
        birthday={birthday}
        classroomName={classroomName}
        gradeName={gradeName}
        classroomList={classroomList}
        gradeList={gradeList}
      />
    </Container>
  );
};

export default StudentRegist;
