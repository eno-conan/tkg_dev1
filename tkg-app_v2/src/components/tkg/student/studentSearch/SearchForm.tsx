/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";
import "../../../tkgStyle.css";
import { classroomArray, searchStudentArray } from "../initData";
import { API_STUDENT } from "../../../../config";

interface SearchStudentForm {
  classroomList: classroomArray;
  classroomFilterList: classroomArray;
  setClassroomFilterclassroomFilterList: React.Dispatch<
    React.SetStateAction<classroomArray>
  >;
  setDisplayStudentFlg: React.Dispatch<React.SetStateAction<boolean>>;
  setStudentList: React.Dispatch<React.SetStateAction<searchStudentArray>>;
}

const SearchForm: React.FC<SearchStudentForm> = ({
  classroomList,
  classroomFilterList,
  setClassroomFilterclassroomFilterList,
  setDisplayStudentFlg,
  setStudentList,
}) => {
  //入力項目の管理
  const [studentName, setStudentName] = useState<string>("");
  const [classroomName, setClassroomName] = useState<string>("");
  //教室名：テキストボックスの値を管理
  const [classroomFilterValue, setClassroomFilterValue] = useState<string>("");

  //教室選択
  const selectClassroom = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setClassroomName(event.target.value);
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

  //生徒検索結果取得
  const searchStudentByClassroomAndName = () => {
    const options = {
      method: "GET",
    };
    fetch(
      `${API_STUDENT.SearchStudent}?classroomId=${classroomName}&studentName=${studentName}`,
      options
    )
      .then((response) => response.json())
      .then((studentInfo) => {
        if (studentInfo === "0") {
          alert("該当する生徒がいませんでした。");
        } else {
          setStudentList(studentInfo);
          setDisplayStudentFlg(true);
        }
      })
      .catch((error) => {
        console.log(error);
        alert("生徒情報取得できず");
      });
  };

  return (
    <>
      <Row className={"pt-6"}>
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
            />
            <select onChange={selectClassroom}>
              <option value="" selected>
                プルダウンから選択してください
              </option>
              {classroomFilterList.map((classroom) => (
                <option value={classroom.id}>{classroom.name} 教室</option>
              ))}
            </select>
          </div>
        </Col>
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
      </Row>
      <Row>
        <Col md={10}></Col>
        <Col md={2}>
          <button
            className="btn btn-success float-right w-100"
            name="abc"
            id="serachResult"
            onClick={searchStudentByClassroomAndName}
          >
            検索
          </button>
        </Col>
      </Row>
    </>
  );
};

export default SearchForm;
