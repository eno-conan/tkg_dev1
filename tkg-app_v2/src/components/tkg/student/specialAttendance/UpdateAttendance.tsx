/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Button, CloseButton } from "react-bootstrap";
import "../../../tkgStyle.css";
import { API_STUDENT } from "../../../../config";

interface UpdateInfoProps {
  ids: Array<number>;
  checkedStudentId: string;
  // setBirthday: React.Dispatch<React.SetStateAction<string>>;
}
const UpdateAttendance: React.FC<UpdateInfoProps> = ({
  ids,
  checkedStudentId,
}) => {
  //updateするからPUTでいいか？
  const updateDatabase = () => {
    let sendContent: string[] = [];
    let sentIds: string = "";
    if (ids.length === 0) {
      sentIds = "0";
    } else {
      sentIds = ids.toString();
    }
    sendContent.push("1"); //specialSeasonId
    sendContent.push(checkedStudentId);
    sendContent.push(sentIds.toString());
    const options = {
      method: "PUT",
      body: sendContent.toString(),
    };
    fetch(`${API_STUDENT.UpdateSpecialAttendance}`, options)
      .then((response) => response.json())
      .then((updateTargetClass) => {
        alert("更新完了");
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
        alert("出欠予定を更新できませんでした");
      });
  };
  return (
    <>
      <Row>
        <Col md={8}></Col>
        <Col md={2}>
          <button
            className="btn btn-success float-right w-100"
            name="abc"
            id="selectClass"
            // value={classroom?.id}
            onClick={updateDatabase}
          >
            確定
          </button>
        </Col>
      </Row>
    </>
  );
};

export default UpdateAttendance;
