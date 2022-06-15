import React, { useState } from "react";
import {
  Container,
  Col,
  Row,
  //Button,
} from "react-bootstrap";
import { SummaryInfo } from "./initData";

interface PlanClassInfo {
  subject: string;
  lecturerName: string;
  dateInfo: string;
  checkedSubjectId: string;
  checkedSubjectName: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
  deleteClassFramePeriod2: string[];
  setDeleteClassFramePeriod2: React.Dispatch<React.SetStateAction<string[]>>;
}

const AlreadyClassExist: React.FC<PlanClassInfo> = ({
  subject,
  lecturerName,
  dateInfo,
  checkedSubjectId,
  checkedSubjectName,
  checkSubjectCount,
  setCheckSubjectCount,
  deleteClassFramePeriod2,
  setDeleteClassFramePeriod2,
}) => {
  const [deleteFlg, setDeleteFlg] = useState<boolean>(false);
  let cntWork: number = checkSubjectCount;

  // [X]をクリック
  const deleteClass = (event: any) => {
    if (checkedSubjectId === "0") {
      alert("科目を選択してください");
    } else {
      console.log("Delete:", event.target.value);
      setDeleteFlg(true);
      const tmpDateList: string[] = deleteClassFramePeriod2;
      tmpDateList.push(event.target.value);
      setCheckSubjectCount(cntWork + 1);
      setDeleteClassFramePeriod2(tmpDateList);
    }
  };

  //再チェック
  const cancelDelete = (event: React.ChangeEvent<HTMLInputElement>) => {
    console.log("cancelDelete:", event.target.value);
    //外した場合
    const filterDate = deleteClassFramePeriod2.filter(
      (date: string) => date !== event.target.value
    );
    setDeleteClassFramePeriod2(filterDate);
    setCheckSubjectCount(cntWork - 1);
    setDeleteFlg(false);
  };

  return (
    <Container className={"tkgTop mt-4"}>
      <div>
        {deleteFlg ? (
          <>
            <input
              className={"checkBox"}
              type="checkbox"
              name={dateInfo}
              value={dateInfo}
              onChange={cancelDelete}
              defaultChecked={false}
            />
          </>
        ) : (
          <>
            {subject.toString() === checkedSubjectName.toString() ? (
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
                <Row>
                  <Col>
                    <div>{subject}</div>
                    {subject ? <div>{"====="}</div> : <div></div>}
                    <div>{lecturerName}</div>
                  </Col>
                </Row>
              </>
            ) : (
              <>
                <Row>
                  <Col>
                    <div>{subject}</div>
                    {subject ? <div>{"====="}</div> : <div></div>}
                    <div>{lecturerName}</div>
                  </Col>
                </Row>
              </>
            )}
          </>
        )}
      </div>
    </Container>
  );
};

export default AlreadyClassExist;
