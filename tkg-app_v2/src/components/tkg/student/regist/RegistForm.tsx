/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";
import "../../../tkgStyle.css";
import { classroomArray, gradeArray } from "../initData";

interface DisplayFormProps {
  studentName: string;
  setStudentName: React.Dispatch<React.SetStateAction<string>>;
  birthday: string;
  setBirthday: React.Dispatch<React.SetStateAction<string>>;
  setClassroomName: React.Dispatch<React.SetStateAction<string>>;
  setGradeName: React.Dispatch<React.SetStateAction<string>>;
  classroomList: classroomArray;
  gradeList: gradeArray;
}

const RegistForm: React.FC<DisplayFormProps> = ({
  studentName,
  setStudentName,
  birthday,
  setBirthday,
  setClassroomName,
  setGradeName,
  classroomList,
  gradeList,
}) => {
  //教室名：テキストボックスの値を管理
  const [classroomFilterValue, setClassroomFilterValue] = useState<string>("");
  // 教室名：入力文字列と部分一致する教室の一覧を管理
  const [classroomFilterList, setClassroomFilterclassroomFilterList] =
    useState<classroomArray>(classroomList);

  useEffect(() => {
    setClassroomFilterclassroomFilterList(classroomList);
  }, [classroomList]);

  //教室選択
  const selectClassroom = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setClassroomName(event.target.value);
  };

  //学年選択
  const selectGrade = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setGradeName(event.target.value);
  };

  // 教室名は候補が多くなるため、文字入力で選択肢を絞る処理を追加
  const filterClassroom = (event: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue: string = event.target.value;
    setClassroomFilterValue(inputValue);
    const filteringList: classroomArray = []; //フィルター後の教室一覧を格納
    classroomList.filter((list) => {
      if (list.name.indexOf(inputValue) !== -1) {
        filteringList.push(list);
      } else {
        console.log(list.name);
      }
    });
    setClassroomFilterclassroomFilterList(filteringList);
  };

  return (
    <>
      <Row className={"tkgTopHeader mb-4"}>
        <Col md={5}></Col>
        <Col md={6}>
          <h3>生徒登録</h3>
        </Col>
      </Row>
      <Row className={"pt-6"}>
        <Col md={6} className={"mb-4"}>
          <label>氏名</label>
          <input
            className={"w-100"}
            type="text"
            placeholder={"個別　太郎"}
            value={studentName}
            onChange={(event) => {
              setStudentName(event.target.value);
            }}
          />
        </Col>
        <Col md={6} className={"mb-4"}>
          <label className={"mb-1"}>生年月日</label>
          <input
            className={"w-100"}
            type="date"
            placeholder={"New Task"}
            value={birthday}
            onChange={(event) => setBirthday(event.target.value)}
          />
        </Col>
        <Col md={6} className={"mb-4"}>
          <label className={"mb-1"}>
            受講教室 <small>※文字入力で候補を絞ります</small>
          </label>
          <div>
            <input
              className={"w-50"}
              type="text"
              placeholder={"北海道、東京"}
              value={classroomFilterValue || ""}
              onChange={filterClassroom}
            />{" "}
            <select onChange={selectClassroom}>
              <option value="" selected>
                プルダウンから選択してください
              </option>
              {classroomFilterList.map((classroom) => (
                <option value={classroom.name}>{classroom.name} 教室</option>
              ))}
            </select>
          </div>
        </Col>
        <Col md={6} className={"mb-4"}>
          <label className={"mb-1"}>学年</label>
          <div>
            <select onChange={selectGrade}>
              <option value="" selected>
                プルダウンから選択してください
              </option>
              {gradeList.map((grade) => (
                <option value={grade.name}>{grade.name}</option>
              ))}
            </select>
          </div>
        </Col>
        <br />
      </Row>
    </>
  );
};

export default RegistForm;
