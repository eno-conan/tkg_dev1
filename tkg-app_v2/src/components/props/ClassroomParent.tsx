import React, { useState } from "react";
import { Row, Col, Container } from "react-bootstrap";
import ClassroomChild from "./ClassroomChild";
import ClassroomChild3 from "./ClassroomChild3";
// import { Navigate, Link } from "react-router-dom";
// import ClassroomChild2 from "./ClassroomChild2";

import "../style.css";

const data = [
  {
    id: 1,
    title: "A",
  },
  {
    id: 2,
    title: "B",
  },
];

// const items = [
//   {
//     title: "A",
//   },
//   {
//     title: "B",
//   },
// ];

export interface TitleBase {
  id: number;
  title: string;
}

const ClassroomParent = () => {
  // const [someState, setSomeState] = useState("");
  // const toggleState = (e: React.MouseEvent, title: string) => {
  //   setSomeState(title);
  // };

  const [titles, setTitles] = useState<TitleBase[]>(data);
  //子Componentからの値を受け取るためのuseState
  const [parentName, setParentName] = useState<string>("John Obi");
  //子Componentからの値を受け取けて、値を更新する処理
  const updateName = (name: string): void => {
    setParentName(name);
  };
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={6}>
            {/* 親から子にデータを渡すだけ */}
            {titles.map((eachTitle) => (
              <ClassroomChild eachTitle={eachTitle} />
            ))}
            {/* <ClassroomChild items={data} /> */}
            <hr />
            {/* 子から親にデータを返す */}
            <ClassroomChild3 name={parentName} updateName={updateName} />
            {/* <ClassroomChild2
              toggleState={(e, title) => toggleState(e, title)}
              items={items}
            /> */}
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default ClassroomParent;
