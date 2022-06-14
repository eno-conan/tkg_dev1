import React, { useState, useEffect, useReducer } from "react";
import {
  Container,
  Button,
  Col,
  Row,
  CloseButton,
  Table,
  // DropdownButton,
  // Dropdown,
} from "react-bootstrap";

interface CheckedCountInfo {
  checkedSubjectId: string;
  dateInfo: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
}

const NoClassFrame: React.FC<CheckedCountInfo> = ({
  checkedSubjectId,
  dateInfo,
  checkSubjectCount,
  setCheckSubjectCount,
}) => {
  // const [date, setDate] = useState<string>("");
  const [keepSelectSubject, setkeepSelectSubject] = useState<string>("0");

  useEffect(() => {
    if (keepSelectSubject === "0") {
      setkeepSelectSubject(checkedSubjectId);
    } else {
      setkeepSelectSubject("0");
      setkeepSelectSubject(checkedSubjectId);
    }
  }, [checkedSubjectId, keepSelectSubject]);

  //スケジュール部分のチェックボックスに関する処理
  const checkOneCell = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (checkedSubjectId === "0") {
      event.target.checked = false;
      alert("科目を選択してください");
    } else {
      console.log("チェック科目ID:", keepSelectSubject);
      let cntWork: number = checkSubjectCount;
      if (event.target.checked) {
        // console.log("true");
        console.log(event.target.name);
        setCheckSubjectCount(cntWork - 1);
      } else {
        // console.log("false");
        setCheckSubjectCount(cntWork + 1);
      }
    }
  };

  return (
    <>
      <input
        className={"checkBox"}
        type="checkbox"
        name={dateInfo}
        value={dateInfo}
        onChange={checkOneCell}
        defaultChecked={false}
        // checked={checkedSubjectId !== "0"}
      />
    </>
  );
};

export default NoClassFrame;
