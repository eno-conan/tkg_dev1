import React from "react";
import { Row, Col, Container } from "react-bootstrap";
import { TitleBase } from "./ClassroomParent";
// import { Navigate, Link } from "react-router-dom";

import "./style.css";

interface EachTitle {
  eachTitle: TitleBase;
  // key: any;
  // setTasks: React.Dispatch<React.SetStateAction<ITask[]>>;
}

// const ClassroomChild = (props: { items: any[] }) => {
const ClassroomChild: React.FC<EachTitle> = ({ eachTitle }) => {
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={6}>
            <p>{eachTitle.title}</p>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default ClassroomChild;
