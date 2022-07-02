/* eslint-disable array-callback-return */
import React from "react";
// import { useParams } from "react-router";
import { API_STUDENT } from "../../../../config";
import { useNavigate } from "react-router-dom";
import { Container, Button, Col, Row } from "react-bootstrap";
// import Header from "../react-form_220530/Header";

import "../../../tkgStyle.css";
import { ClassInfo } from "../initData";
import {
  addDeleteFrameManageArray,
  classesAllPeriodArray,
} from "../RegistSpecialSchedule";

interface UpdateInfoProps {
  checkedStudentId: string;
  checkedSubjectId: string;
  frameAddDeleteManage: addDeleteFrameManageArray;
  classesAllPeriod: classesAllPeriodArray;
}

const UpdateDbSpecialSchedule: React.FC<UpdateInfoProps> = ({
  checkedStudentId,
  checkedSubjectId,
  frameAddDeleteManage,
  classesAllPeriod,
}) => {
  // const navigate = useNavigate();

  //キャンセル実行
  const resetSchedule = () => {
    window.location.reload();
  };

  //登録実行
  const updateSchedule = () => {
    const studentId = checkedStudentId; //生徒ID
    const subjectId = checkedSubjectId; //科目キー;
    let sendContent: string[] = []; //送信内容格納

    //生徒ID
    sendContent.push(studentId);
    sendContent.push(subjectId);

    const sendContentFinally = extractAddDeleteTimeTableId(sendContent);

    console.log(sendContentFinally);

    const options = {
      method: "PUT",
      body: sendContentFinally.toString(),
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

  // 作業メソッド：送信内容生成
  const extractAddDeleteTimeTableId = (sendContent: string[]) => {
    let sendContentTmp: string[] = sendContent;
    // 追加・削除IDの管理：frameAddDeleteManage
    // 更新前の授業情報を管理：classesAllPeriod
    for (let periodInfo of frameAddDeleteManage) {
      let addDateList: any = periodInfo.addDelete.add;
      let deleteDateList: any = periodInfo.addDelete.delete;
      let periodIdx = Number(periodInfo.period) - 2;

      for (let date of addDateList) {
        // 追加分
        if (date === "") {
          sendContentTmp.push("save-period" + periodInfo.period);
        } else {
          // あるコマの授業情報
          const filterClassInfo = classesAllPeriod[periodIdx].classes.filter(
            (classInfo: ClassInfo) => classInfo.classDate === date
          );
          if (filterClassInfo) {
            sendContentTmp.push(filterClassInfo[0].timeTableSpecialId);
          }
        }
      }
      for (let date of deleteDateList) {
        // 削除分
        if (date === "") {
          sendContentTmp.push("delete-period" + periodInfo.period);
        } else {
          // あるコマの授業情報
          const filterClassInfo = classesAllPeriod[periodIdx].classes.filter(
            (classInfo: ClassInfo) => classInfo.classDate === date
          );
          if (filterClassInfo) {
            sendContentTmp.push(filterClassInfo[0].timeTableSpecialId);
          }
        }
      }
    }
    return sendContentTmp;
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
