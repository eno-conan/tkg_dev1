/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
// import { useParams } from "react-router";
import { useNavigate } from "react-router-dom";
import { Table } from "react-bootstrap";
// import Header from "../react-form_220530/Header";

import "../../../tkgStyle.css";
import { classesPeriodArray } from "../RegistSpecialSchedule";
import AlreadyClassExist from "./AlreadyClassExist";
import NoClassFrame from "./NoClassFrame";

interface UpdateInfoProps {
  dateList: string[];
  classesPeriod2: classesPeriodArray;
  checkedSubjectId: string;
  checkedSubjectName: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
  selectClassFramePeriod2: string[];
  setSelectClassFramePeriod2: React.Dispatch<React.SetStateAction<string[]>>;
  deleteClassFramePeriod2: string[];
  setDeleteClassFramePeriod2: React.Dispatch<React.SetStateAction<string[]>>;
}

const SpecialScheduleFrame: React.FC<UpdateInfoProps> = ({
  dateList,
  classesPeriod2,
  checkedSubjectId,
  checkedSubjectName,
  checkSubjectCount,
  setCheckSubjectCount,
  selectClassFramePeriod2,
  setSelectClassFramePeriod2,
  deleteClassFramePeriod2,
  setDeleteClassFramePeriod2,
}) => {
  return (
    <>
      <Table striped bordered hover responsive>
        <thead>
          {/* ヘッダー（日付） */}
          <tr>
            <th></th>
            {dateList.map((eachDate) => (
              <>
                <th className="text-center text-nowrap">{eachDate}</th>
              </>
            ))}
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className={"align-middle"}>2</td>
            {classesPeriod2 &&
              classesPeriod2.map((eachData) => (
                <>
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
                        deleteClassFramePeriod2={deleteClassFramePeriod2}
                        setDeleteClassFramePeriod2={setDeleteClassFramePeriod2}
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
                        selectClassFramePeriod2={selectClassFramePeriod2}
                        setSelectClassFramePeriod2={setSelectClassFramePeriod2}
                      />
                    ) : (
                      <>生徒都合</>
                    )}
                  </td>
                </>
              ))}
          </tr>
        </tbody>
      </Table>
    </>
  );
};

export default SpecialScheduleFrame;
