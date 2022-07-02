/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
// import { useParams } from "react-router";
import { useNavigate } from "react-router-dom";
import { Table } from "react-bootstrap";

import "../../../tkgStyle.css";
import { addDeleteFrameManageArray } from "../RegistSpecialSchedule";
import AlreadyClassExist from "./AlreadyClassExist";
import NoClassFrame from "./NoClassFrame";
import { AllClassInfo } from "../initData";

interface UpdateInfoProps {
  eachPeriodInfo: AllClassInfo;
  checkedSubjectId: string;
  checkedSubjectName: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
  frameAddDeleteManage: addDeleteFrameManageArray;
  setFrameAddDeleteManage: React.Dispatch<
    React.SetStateAction<addDeleteFrameManageArray>
  >;
}

const SpecialScheduleFrame: React.FC<UpdateInfoProps> = ({
  eachPeriodInfo,
  checkedSubjectId,
  checkedSubjectName,
  checkSubjectCount,
  setCheckSubjectCount,
  frameAddDeleteManage,
  setFrameAddDeleteManage,
}) => {
  //追加したコマの日付情報を管理（2コマ用）
  const [selectClassFrame, setSelectClassFrame] = useState<string[]>([""]);
  //削除したコマの日付情報を管理（2コマ用）
  const [deleteClassFrame, setDeleteClassFrame] = useState<string[]>([""]);
  useEffect(() => {
    // 各コマの数字
    let targetPeriod = eachPeriodInfo.period;
    // 現在の情報取得
    let currentData = [...frameAddDeleteManage];

    // 2コマの情報；配列の1要素目（idex:0）のため、2を引く
    let calcForIndex = Number(targetPeriod) - 2;
    // 更新
    currentData[calcForIndex].addDelete.add = selectClassFrame;
    currentData[calcForIndex].addDelete.delete = deleteClassFrame;
    setFrameAddDeleteManage(currentData);
  }, []);

  return (
    <>
      <tr>
        <td className={"align-middle"}>{eachPeriodInfo.period}</td>
        {eachPeriodInfo &&
          eachPeriodInfo.classes.map((eachData) => (
            <td className={"align-middle"}>
              {eachData.id ? (
                <AlreadyClassExist
                  subject={eachData.subject}
                  lecturerName={eachData.lecturerName}
                  dateInfo={eachData.classDate}
                  checkedSubjectId={checkedSubjectId}
                  checkedSubjectName={checkedSubjectName}
                  checkSubjectCount={checkSubjectCount}
                  setCheckSubjectCount={setCheckSubjectCount}
                  deleteClassFrame={deleteClassFrame}
                  setDeleteClassFrame={setDeleteClassFrame}
                />
              ) : eachData.timeTableSpecialId !== "stu-abs" ? (
                <NoClassFrame
                  subject={eachData.subject}
                  lecturerName={eachData.lecturerName}
                  dateInfo={eachData.classDate}
                  checkedSubjectId={checkedSubjectId}
                  checkedSubjectName={checkedSubjectName}
                  checkSubjectCount={checkSubjectCount}
                  setCheckSubjectCount={setCheckSubjectCount}
                  selectClassFrame={selectClassFrame}
                  setSelectClassFrame={setSelectClassFrame}
                />
              ) : (
                <>生徒都合</>
              )}
            </td>
          ))}
      </tr>
    </>
  );
};

export default SpecialScheduleFrame;
