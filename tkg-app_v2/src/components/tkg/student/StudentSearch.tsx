/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";
import DataTable from "react-data-table-component";
import Card from "@material-ui/core/Card";
import SortIcon from "@material-ui/icons/ArrowDownward";
import { Helmet } from "react-helmet";
import "../../tkgStyle.css";
import { API_STUDENT } from "../../../config";
import {
  classroomArray,
  classroomData,
  searchResultStudentData,
  searchStudentArray,
} from "./initData";

interface UpdateInfo {}

const StudentSearch: React.FC<UpdateInfo> = ({}) => {
  //入力項目の管理
  const [studentName, setStudentName] = useState<string>("");
  const [classroomName, setClassroomName] = useState<string>("");
  //DBからの教室情報を管理
  const [classroomList, setClassroomList] =
    useState<classroomArray>(classroomData);
  //教室名：テキストボックスの値を管理
  const [classroomFilterValue, setClassroomFilterValue] = useState<string>("");
  // 教室名：入力文字列と部分一致する教室の一覧を管理
  const [classroomFilterList, setClassroomFilterclassroomFilterList] =
    useState<classroomArray>(classroomList);

  const [studentList, setStudentList] = useState<searchStudentArray>(
    searchResultStudentData
  );

  useEffect(() => {
    prepareClassroom();
    setClassroomFilterclassroomFilterList(classroomList);
    //別コンポーネントにしないと、無限呼び出しになる
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
      })
      .catch((error) => {
        console.log(error);
        alert("教室情報取得できず");
      });
  };

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
        }
      })
      .catch((error) => {
        console.log(error);
        alert("生徒情報取得できず");
      });
  };

  //テスト用
  const checkStudentInfo = () => {
    console.log(studentList);
  };

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
            />{" "}
            <select onChange={selectClassroom}>
              <option value="" selected>
                プルダウンから選択してください
              </option>
              {classroomList.map((classroom) => (
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
        <Col md={2}>
          <button
            className="btn btn-secondary float-right w-100"
            name="abc"
            id="serachResult"
            onClick={checkStudentInfo}
          >
            確認
          </button>
        </Col>
        <Col md={10}>
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
      {/* <Row className={"pt-4"}>
        <Col md={12} className={"pb-6 pl-4"}>
          <Card>
            <DataTable
              //content="Movies"
              columns={classScheduleTableColumns}
              data={classSchedule}
              pagination
              defaultSortFieldId="content"
              sortIcon={<SortIcon />}
              selectableRows
              selectableRowsSingle
              onSelectedRowsChange={selectChangeTarget}
            />
          </Card>
        </Col>
      </Row> */}
    </div>
  );
};

export default StudentSearch;
