/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
// import { useParams } from "react-router";
import { useNavigate } from "react-router-dom";
import { Table } from "react-bootstrap";
// import Header from "../react-form_220530/Header";

import "../../../tkgStyle.css";
import {
  addDeleteFrameManage,
  addDeleteFrameManageArray,
} from "../RegistSpecialSchedule";
import AlreadyClassExist from "./AlreadyClassExist";
import NoClassFrame from "./NoClassFrame";
import { AllClassInfo } from "../initData";

interface UpdateInfoProps {
  eachPeriodInfo: AllClassInfo;
  // classesPeriod2: classesPeriodArray;
  checkedSubjectId: string;
  checkedSubjectName: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
  // selectClassFramePeriod2: string[];
  // setSelectClassFramePeriod2: React.Dispatch<React.SetStateAction<string[]>>;
  // deleteClassFramePeriod2: string[];
  // setDeleteClassFramePeriod2: React.Dispatch<React.SetStateAction<string[]>>;
  frameAddDeleteManage: addDeleteFrameManageArray;
  setFrameAddDeleteManage: React.Dispatch<
    React.SetStateAction<addDeleteFrameManageArray>
  >;
}

const SpecialScheduleFrame: React.FC<UpdateInfoProps> = ({
  eachPeriodInfo,
  // classesPeriod2,
  checkedSubjectId,
  checkedSubjectName,
  checkSubjectCount,
  setCheckSubjectCount,
  // selectClassFramePeriod2,
  // setSelectClassFramePeriod2,
  // deleteClassFramePeriod2,
  // setDeleteClassFramePeriod2,
  frameAddDeleteManage,
  setFrameAddDeleteManage,
}) => {
  //追加したコマの日付情報を管理（2コマ用）
  const [selectClassFrame, setSelectClassFrame] = useState<string[]>([""]);
  //削除したコマの日付情報を管理（2コマ用）
  const [deleteClassFrame, setDeleteClassFrame] = useState<string[]>([""]);
  useEffect(() => {
    let targetPeriod = eachPeriodInfo.period;
    let currentData = [...frameAddDeleteManage];
    let setData: addDeleteFrameManage = {
      period: eachPeriodInfo.period,
      addDelete: {
        add: ["1", "2"],
        delete: ["1", "2"],
      },
    };
    setData.addDelete.add = selectClassFrame;
    setData.addDelete.delete = deleteClassFrame;
    // indexは0からなので、「コマ数-2」する
    currentData[Number(targetPeriod) - 2] = setData;
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
