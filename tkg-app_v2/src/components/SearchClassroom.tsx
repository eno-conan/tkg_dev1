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

import "./style.css";

// https://codesandbox.io/s/pedantic-fire-c07kb?file=/src/App.tsx:1127-1581
type member = {
  id: string;
  name: string;
  country: string;
  food: string;
};

type MemberList = Array<member>;

const allMemberList = [
  {
    id: "1",
    name: "太郎",
    country: "Japan",
    food: "焼肉",
  },
  {
    id: "2",
    name: "花子",
    country: "Japan",
    food: "ケーキ",
  },
];

const SearchClassroom = () => {
  const [inputValue, setInputValue] = useState("");
  const [memberList, setMemberList] = useState<MemberList>(allMemberList);

  const search = (value: string) => {
    if (value !== "") {
      const filteredList = allMemberList.filter((member: member) =>
        Object.values(member).some(
          (item: string) =>
            item?.toUpperCase().indexOf(value.trim().toUpperCase()) !== -1
        )
      );
      setMemberList(filteredList);
      return;
    }

    setMemberList(allMemberList);
    return;
  };
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
    search(e.target.value);
  };

  const onChangeHandler = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedOptions = event.target.value;
    console.log(selectedOptions);
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
              {memberList.map((member, index) => {
                return (
                  <option value={member.name}>
                    {member.name} / {member.country} / {member.food}
                  </option>
                );
              })}
            </select>
          </Col>
        </Row>
      </Container>
      <Outlet />
    </div>
  );
};

export default SearchClassroom;
