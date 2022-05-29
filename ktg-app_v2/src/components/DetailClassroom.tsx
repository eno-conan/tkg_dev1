import React from "react";
import { Row, Col, Container } from "react-bootstrap";
// import { Navigate, Link } from "react-router-dom";

import "./style.css";

const DetailClassroom = () => {
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={4}>
            <h1>教室検索</h1>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default DetailClassroom;
