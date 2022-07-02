/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
// import { useParams } from "react-router";
import {
  API_BASE_URL,
  API_STUDENT,
  STUDENT_FUNCTION,
} from "../../../../config";
import { useNavigate } from "react-router-dom";
import { Container, Button, Col, Row } from "react-bootstrap";
// import Header from "../react-form_220530/Header";

import "../../../tkgStyle.css";
import { ClassInfo } from "../initData";
import { addDeleteFrameManageArray } from "../RegistSpecialSchedule";

interface UpdateInfoProps {
  checkedSubjectId: string;
  // classesPeriod2: classesPeriodArray;
  // selectClassFramePeriod2: string[];
  // deleteClassFramePeriod2: string[];
  frameAddDeleteManage: addDeleteFrameManageArray;
  setFrameAddDeleteManage: React.Dispatch<
    React.SetStateAction<addDeleteFrameManageArray>
  >;
}

const UpdateDbSpecialSchedule: React.FC<UpdateInfoProps> = ({
  checkedSubjectId,
  // classesPeriod2,
  // selectClassFramePeriod2,
  // deleteClassFramePeriod2,
  frameAddDeleteManage,
  setFrameAddDeleteManage,
}) => {
  const navigate = useNavigate();

  //キャンセル実行
  const resetSchedule = () => {
    window.location.reload();
  };

  //登録実行
  const updateSchedule = () => {
    const studentId = "1";
    const subjectId = checkedSubjectId;
    let sendContent: string[] = []; //送信内容格納

    sendContent.push(studentId);
    sendContent.push(subjectId);

    //追加分
    // for (let date of selectClassFramePeriod2) {
    //   const filterClassInfo = classesPeriod2.filter(
    //     (classInfoPeriod2: ClassInfo) => classInfoPeriod2.classDate === date
    //   );
    //   if (date === "") {
    //     sendContent.push("save-period2");
    //   } else {
    //     sendContent.push(filterClassInfo[0].timeTableSpecialId);
    //   }
    // }

    // //削除分
    // for (let date of deleteClassFramePeriod2) {
    //   const filterClassInfo = classesPeriod2.filter(
    //     (classInfoPeriod2: ClassInfo) => classInfoPeriod2.classDate === date
    //   );
    //   if (date === "") {
    //     sendContent.push("delete-period2");
    //   } else {
    //     sendContent.push(filterClassInfo[0].timeTableSpecialId);
    //   }
    // }

    const options = {
      method: "PUT",
      body: sendContent.toString(),
    };
    fetch(`${API_STUDENT.SpecialSchedule}`, options)
      .then((response) => response.json())
      .then((updateTargetClass) => {
        alert(updateTargetClass);
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
        alert("スケジュールを更新できませんでした");
      });
  };
  return (
    <>
      <Row>
        <Col md={9}></Col>
        <Col md={3}>
          <Button onClick={resetSchedule} className={"btn btn-warning"}>
            キャンセル
          </Button>
          <span> </span>
          <Button onClick={updateSchedule} className={"btn btn-summary pl-4"}>
            登録
          </Button>
        </Col>
      </Row>
      <Row></Row>
    </>
  );
};

export default UpdateDbSpecialSchedule;
