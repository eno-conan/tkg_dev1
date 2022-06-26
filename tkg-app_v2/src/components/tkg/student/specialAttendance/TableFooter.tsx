/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";

const TableFooter = () => {
  return (
    <>
      <tfoot className={"sticky-top h-100"}>
        <th className="text-center text-nowrap"></th>
        <th className="text-center text-nowrap">2</th>
        <th className="text-center text-nowrap">3</th>
        <th className="text-center text-nowrap">4</th>
        <th className="text-center text-nowrap">5</th>
        <th className="text-center text-nowrap">6</th>
        <th className="text-center text-nowrap">7</th>
        <th className="text-center text-nowrap">8</th>
        <th className="text-center text-nowrap"></th>
        <th className="text-center text-nowrap"></th>
      </tfoot>
    </>
  );
};

export default TableFooter;
