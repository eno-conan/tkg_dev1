import React, { useState, useEffect } from "react";
import { Row, Col, Container } from "react-bootstrap";
// import { Navigate, Link } from "react-router-dom";

import "./style.css";

// 親Componentから受け取る情報を定義
interface IfirstChildProps {
  name: string;
  updateName: (arg: string) => void;
}

const ClassroomChild3: React.FC<IfirstChildProps> = ({ name, updateName }) => {
  const [firstChildName, setFirstChildName] = useState<string>("");
  useEffect(() => {
    setFirstChildName(name);
  }, [name]);
  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={6}>
            <section>
              <h3> {firstChildName} </h3>
              <button onClick={() => updateName("Micheal")}>update</button>
            </section>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default ClassroomChild3;
