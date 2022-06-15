import React, { useState, useEffect, useReducer } from "react";
import {
  Container,
  Col,
  Row,
  //Button,
} from "react-bootstrap";

interface CheckedCountInfo {
  subject: string;
  lecturerName: string;
  dateInfo: string;
  checkedSubjectId: string;
  checkedSubjectName: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
  selectClassFramePeriod2: string[];
  setSelectClassFramePeriod2: React.Dispatch<React.SetStateAction<string[]>>;
}

const NoClassFrame: React.FC<CheckedCountInfo> = ({
  subject,
  lecturerName,
  dateInfo,
  checkedSubjectId,
  checkedSubjectName,
  checkSubjectCount,
  setCheckSubjectCount,
  selectClassFramePeriod2,
  setSelectClassFramePeriod2,
}) => {
  const [selectSubject, setSelectSubject] = useState<string>("0");
  const [registFlg, setRegistFlg] = useState<boolean>(false);
  let cntWork: number = checkSubjectCount;

  useEffect(() => {
    if (selectSubject === "0") {
      setSelectSubject(checkedSubjectId);
    } else {
      setSelectSubject("0");
      setSelectSubject(checkedSubjectId);
    }
  }, [checkedSubjectId, selectSubject]);

  //スケジュール部分のチェックボックスに関する処理
  const checkOneCell = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (checkedSubjectId === "0") {
      event.target.checked = false;
      alert("科目を選択してください");
    } else {
      if (event.target.checked) {
        setRegistFlg(true); //登録フラグを立てる
        const tmpDateList: string[] = selectClassFramePeriod2;
        tmpDateList.push(event.target.name);
        setCheckSubjectCount(cntWork - 1);
        setSelectClassFramePeriod2(tmpDateList);
      }
    }
  };

  const deleteClass = (event: any) => {
    console.log("Delete:", event.target.value);
    const filterDate = selectClassFramePeriod2.filter(
      (date: string) => date !== event.target.value
    );
    setSelectClassFramePeriod2(filterDate);
    setCheckSubjectCount(cntWork + 1);
    setRegistFlg(false);
  };

  return (
    <>
      <Container className={"tkgTop mt-4"}>
        <div>
          {registFlg ? (
            <>
              <div className={"text-right"}>
                <button
                  type={"button"}
                  value={dateInfo}
                  className={"btn btn-outline-dark btn-sm"}
                  onClick={deleteClass}
                >
                  X
                </button>
              </div>
              <div>{checkedSubjectName}</div>
              <div>{"====="}</div>
              <div>{"lecturerName"}</div>
            </>
          ) : (
            <>
              <Row>
                <Col>
                  <input
                    className={"checkBox"}
                    type="checkbox"
                    name={dateInfo}
                    value={dateInfo}
                    onChange={checkOneCell}
                    defaultChecked={false}
                  />
                </Col>
              </Row>
            </>
          )}
        </div>
      </Container>
    </>
  );
};

export default NoClassFrame;
