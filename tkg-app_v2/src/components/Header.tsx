import React, { useState } from "react";
import { Row, Col, Container } from "react-bootstrap";

const Header = () => {
  return (
    <div className="App">
      <Row></Row>
      <div
        className={"py-2 mt-2"}
        style={{ textAlign: "center", backgroundColor: "royalblue" }}
      >
        <h3>TKG</h3>
      </div>
    </div>
  );
};

export default Header;
