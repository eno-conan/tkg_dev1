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

interface PlanClassInfo {
  subject: string;
  lecturerName: string;
}

const AlreadyClassExist: React.FC<PlanClassInfo> = ({
  subject,
  lecturerName,
}) => {
  return (
    <Container className={"tkgTop mt-4"}>
      <Row>
        <Col>
          <div>{subject}</div>
          {subject ? <div>{"=========="}</div> : <div></div>}
          <div>{lecturerName}</div>
        </Col>
      </Row>
    </Container>
  );
};

export default AlreadyClassExist;
