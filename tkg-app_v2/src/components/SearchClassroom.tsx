import React, { useState } from "react";
import { Row, Col, Container } from "react-bootstrap";
import {
  // Navigate,
  // Link,
  // BrowserRouter,
  // Route,
  // Routes,
  Outlet,
} from "react-router-dom";
import DetailClassroom from "./DetailClassroom";

import "./style.css";

// https://codesandbox.io/s/pedantic-fire-c07kb?file=/src/App.tsx:1127-1581
export interface classroom {
  id: string;
  prefecture: string;
  name: string;
}

export type classroomList = Array<classroom>;

const allClassrooms = [
  {
    id: "1",
    prefecture: "北海道",
    name: "札幌市",
  },
  {
    id: "2",
    prefecture: "北海道",
    name: "釧路",
  },
  {
    id: "3",
    prefecture: "北海道",
    name: "帯広",
  },
  {
    id: "4",
    prefecture: "青森",
    name: "盛岡",
  },
  {
    id: "5",
    prefecture: "岩手",
    name: "岩手",
  },
  {
    id: "6",
    prefecture: "秋田",
    name: "秋田",
  },
];

const SearchClassroom = () => {
  const [inputValue, setInputValue] = useState(""); //for textbox filtering classroom
  const [classroomList, setClassroomList] =
    useState<classroomList>(allClassrooms);
  const [selectClassroom, setSelectClassroom] = useState<string>("");
  const [selectRoomId, setSelectRoomId] = useState<string>("");

  const search = (value: string) => {
    if (value !== "") {
      const filteredList = allClassrooms.filter((classroom: classroom) =>
        Object.values(classroom).some(
          (item: string) =>
            item?.toUpperCase().indexOf(value.trim().toUpperCase()) !== -1
        )
      );
      setClassroomList(filteredList);
      return;
    }

    setClassroomList(allClassrooms);
    return;
  };
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
    search(e.target.value);
  };

  //いずれかの教室を選択した場合
  const onChangeHandler = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedOptions = event.target.value;
    setSelectClassroom(selectedOptions);
    //選択した値の情報取得
    // このクラスを親として、子供のcomponentに値を渡す流れか？
  };
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={4}>
            <h1>教室検索</h1>
            <div>
              <span style={{ marginRight: "10px" }}>
                教室名を入力してください
              </span>
              <input
                style={{ width: "300px" }}
                type="text"
                value={inputValue}
                onChange={handleChange}
              />
            </div>
            <br />
          </Col>
        </Row>
        <Row>
          <Col md={2}></Col>
          <Col md={6}>
            <p>【教室一覧】</p>
            <select
              style={{ width: "500px" }}
              size={5}
              onChange={onChangeHandler}
              className="select"
            >
              {classroomList.map((classroom, index) => {
                return (
                  <option value={classroom.id}>
                    {classroom.prefecture} / {classroom.name}
                    {"教室"}
                  </option>
                );
              })}
            </select>
          </Col>
        </Row>
        <Row>
          <DetailClassroom
            classroomList={classroomList}
            selectClassroom={selectClassroom}
            selectRoomId={selectRoomId}
          />
        </Row>
      </Container>
      <Outlet />
    </div>
  );
};

export default SearchClassroom;
