import React, { useState, useEffect, useReducer } from "react";
import {
  Container,
  Col,
  Row,
  //Button,
} from "react-bootstrap";

interface CheckedCountProps {
  subject: string;
  lecturerName: string;
  dateInfo: string;
  checkedSubjectId: string;
  checkedSubjectName: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
  selectClassFrame: string[];
  setSelectClassFrame: React.Dispatch<React.SetStateAction<string[]>>;
}

const NoClassFrame: React.FC<CheckedCountProps> = ({
  subject,
  lecturerName,
  dateInfo,
  checkedSubjectId,
  checkedSubjectName,
  checkSubjectCount,
  setCheckSubjectCount,
  selectClassFrame,
  setSelectClassFrame,
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
      if (checkSubjectCount === 0) {
        alert("残コマ数が0になっているため、これ以上追加できません");
        event.target.checked = false;
        return;
      }
      if (event.target.checked) {
        setRegistFlg(true); //登録フラグを立てる
        const tmpDateList: string[] = selectClassFrame;
        tmpDateList.push(event.target.name);
        setCheckSubjectCount(cntWork - 1);
        setSelectClassFrame(tmpDateList);
      }
    }
  };

  const deleteClass = (event: any) => {
    console.log("Delete:", event.target.value);
    const filterDate = selectClassFrame.filter(
      (date: string) => date !== event.target.value
    );
    setSelectClassFrame(filterDate);
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
