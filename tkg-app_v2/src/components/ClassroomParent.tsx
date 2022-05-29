import React, { useState } from "react";
import { Row, Col, Container } from "react-bootstrap";
import ClassroomChild from "./ClassroomChild";
import ClassroomChild3 from "./ClassroomChild3";
// import { Navigate, Link } from "react-router-dom";
// import ClassroomChild2 from "./ClassroomChild2";

import "./style.css";

const data = [
  {
    title: "A",
    id: 1,
  },
  {
    title: "B",
    id: 1,
  },
];

const items = [
  {
    title: "A",
  },
  {
    title: "B",
  },
];

const ClassroomParent = () => {
  // const [someState, setSomeState] = useState("");
  // const toggleState = (e: React.MouseEvent, title: string) => {
  //   setSomeState(title);
  // };
  const [parentName, setParentName] = useState<string>("John Obi");
  const updateName = (name: string): void => {
    setParentName(name);
  };
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={6}>
            <ClassroomChild items={data} />
            <hr />
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
