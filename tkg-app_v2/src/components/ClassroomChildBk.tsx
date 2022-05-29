import React from "react";
import { Row, Col, Container } from "react-bootstrap";
// import { Navigate, Link } from "react-router-dom";

import "./style.css";
//props: { items: any[] }
const ClassroomChildBk = () => {
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={6}>
            <h3>Parent to Children</h3>
            {/* {props.items.map((item) => (
              <p>{item.title}</p>
            ))} */}
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default ClassroomChildBk;
