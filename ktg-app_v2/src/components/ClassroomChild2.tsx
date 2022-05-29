import React from "react";
import { Row, Col, Container } from "react-bootstrap";
// import { Navigate, Link } from "react-router-dom";

import "./style.css";

type Item = {
  title: string;
};

type ChildProps = {
  items: Item[];
  toggleState: (e: React.MouseEvent, title: string) => void;
};

const ClassroomChild2: React.FC<ChildProps> = (props) => {
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={6}>
            {props.items.map((item) => (
              <button onClick={(e) => props.toggleState(e, item.title)}>
                {item.title} / clicked item title to parent
              </button>
            ))}
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default ClassroomChild2;
