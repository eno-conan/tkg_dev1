/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
// import { useParams } from "react-router";
import { API_BASE_URL, PASS_ROUTING } from "../../../config";
import { useNavigate } from "react-router-dom";
import { Container, Button, Col, Row, CloseButton } from "react-bootstrap";
// import Header from "../react-form_220530/Header";

import "../../tkgStyle.css";
import { ClassInfo } from "./initData";
import { classesPeriodArray } from "./RegistSpecialSchedule";

interface UpdateInfo {
  checkedSubjectId: string;
  classesPeriod2: classesPeriodArray;
  selectClassFramePeriod2: string[];
  deleteClassFramePeriod2: string[];
}

const UpdateDbSpecialSchedule: React.FC<UpdateInfo> = ({
  checkedSubjectId,
  classesPeriod2,
  selectClassFramePeriod2,
  deleteClassFramePeriod2,
}) => {
  const navigate = useNavigate();

  const updateSchedule = () => {
    const studentId = "1";
    const subjectId = checkedSubjectId; //本当は科目ごとに保持できるといい=>大がかり
    let sendContent: string[] = [];

    sendContent.push(studentId);
    sendContent.push(subjectId);

    //追加分
    for (let date of selectClassFramePeriod2) {
      const filterClassInfo = classesPeriod2.filter(
        (classInfoPeriod2: ClassInfo) => classInfoPeriod2.classDate === date
      );
      if (date === "") {
        sendContent.push("period2-save");
      } else {
        sendContent.push(filterClassInfo[0].timeTableSpecialId);
      }
    }

    //削除分
    for (let date of deleteClassFramePeriod2) {
      const filterClassInfo = classesPeriod2.filter(
        (classInfoPeriod2: ClassInfo) => classInfoPeriod2.classDate === date
      );
      if (date === "") {
        sendContent.push("period2-delete");
      } else {
        sendContent.push(filterClassInfo[0].timeTableSpecialId);
      }
    }

    const options = {
      method: "PUT",
      body: sendContent.toString(),
    };
    fetch(`${API_BASE_URL}/student/update-special-schedule`, options)
      .then((response) => response.json())
      .then((updateTargetClass) => {
        console.log(updateTargetClass);
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't add task");
      });
    navigate(`${PASS_ROUTING.Top}`);
  };
  return (
    <>
      <Row>
        <Col md={10}></Col>
        <Col>
          <Button onClick={updateSchedule} className={"btn btn-summary ml-4"}>
            登録
          </Button>
        </Col>
      </Row>
      <Row></Row>
    </>
  );
};

export default UpdateDbSpecialSchedule;
