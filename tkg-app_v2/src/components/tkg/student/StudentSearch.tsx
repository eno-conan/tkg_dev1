/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Col, Row } from "react-bootstrap";
import { Helmet } from "react-helmet";
import "../../tkgStyle.css";
import { API_STUDENT } from "../../../config";
import {
  classroomArray,
  classroomData,
  searchResultStudentData,
  searchStudentArray,
} from "./initData";
import SearchForm from "./studentSearch/SearchForm";
import ResultTable from "./studentSearch/ResultTable";
import RegistSubjectModal from "./subject/RegistSubjectModal";
import ShowCurrentSubject from "./subject/ShowCurrentSubject";

const StudentSearch = () => {
  //DBからの教室情報を管理
  const [classroomList, setClassroomList] =
    useState<classroomArray>(classroomData);

  // 教室名：入力文字列と部分一致する教室の一覧を管理
  const [classroomFilterList, setClassroomFilterclassroomFilterList] =
    useState<classroomArray>(classroomList);

  //生徒一覧管理
  const [studentList, setStudentList] = useState<searchStudentArray>(
    searchResultStudentData
  );
  //生徒一覧の表示管理
  const [displayStudentFlg, setDisplayStudentFlg] = useState<boolean>(false);
  //チェックした生徒のID管理
  const [checkedStudentId, setCheckedStudentId] = useState<string>("");

  useEffect(() => {
    prepareClassroom();
  }, []);

  //教室検索
  const prepareClassroom = () => {
    const options = {
      method: "GET",
    };
    fetch(`${API_STUDENT.PrepareRegistClassroom}`, options)
      .then((response) => response.json())
      .then((classroomInfo) => {
        setClassroomList(classroomInfo);
        setClassroomFilterclassroomFilterList(classroomInfo);
      })
      .catch((error) => {
        console.log(error);
        alert("教室情報取得できず");
      });
  };

  // //テスト用
  // const checkStudentInfo = () => {
  //   // navigate(`/receive-param/${id}`);
  //   // window.open(
  //   //   `${STUDENT_FUNCTION.SearchStudent}/${checkedStudentId}`,
  //   //   "",
  //   //   "width=600,height=400,left=200,top=200"
  //   // );
  // };

  return (
    <div id="app">
      <Helmet>
        <title>生徒検索</title>
      </Helmet>
      <Row className={"tkgTopHeader mb-4"}>
        <Col md={5}></Col>
        <Col md={6}>
          <h3>生徒検索</h3>
        </Col>
      </Row>
      <SearchForm
        classroomList={classroomList}
        classroomFilterList={classroomFilterList}
        setClassroomFilterclassroomFilterList={
          setClassroomFilterclassroomFilterList
        }
        setDisplayStudentFlg={setDisplayStudentFlg}
        setStudentList={setStudentList}
      />
      <ResultTable
        studentList={studentList}
        displayStudentFlg={displayStudentFlg}
        setCheckedStudentId={setCheckedStudentId}
      />
      {displayStudentFlg ? (
        <>
          <Row className={"pt-4"}>
            {/* 受講科目表示 */}
            <ShowCurrentSubject checkedStudentId={checkedStudentId} />
            {/* 受講科目登録 */}
            <RegistSubjectModal checkedStudentId={checkedStudentId} />
          </Row>
          <Row className={"pt-4"}></Row>
        </>
      ) : (
        <></>
      )}
    </div>
  );
};

export default StudentSearch;
