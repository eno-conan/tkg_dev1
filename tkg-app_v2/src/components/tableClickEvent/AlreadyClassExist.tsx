import React, { DetailedHTMLProps, InputHTMLAttributes, useState } from "react";
import {
  Container,
  Button,
  Col,
  Row,
  CloseButton,
  Table,
  // DropdownButton,
  // Dropdown,
} from "react-bootstrap";

const AlreadyClassExist = () => {
  return (
    <Container className={"tkgTop mt-4"}>
      <Row>
        <Col>
          <div>数学</div>
        </Col>
      </Row>
    </Container>
  );
};

export default AlreadyClassExist;
