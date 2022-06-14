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
  selectClassFramePeriod2: string[];
  setSelectClassFramePeriod2: React.Dispatch<React.SetStateAction<string[]>>;
  checkedSubjectId: string;
  dateInfo: string;
  checkSubjectCount: number;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
}

const NoClassFrame: React.FC<CheckedCountInfo> = ({
  selectClassFramePeriod2,
  setSelectClassFramePeriod2,
  checkedSubjectId,
  dateInfo,
  checkSubjectCount,
  setCheckSubjectCount,
}) => {
  // const [date, setDate] = useState<string>("");
  const [selectSubject, setSelectSubject] = useState<string>("0");

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
      // console.log("チェック科目ID:", keepSelectSubject);
      let cntWork: number = checkSubjectCount;
      //チェックを付けた場合
      if (event.target.checked) {
        const tmpDateList: string[] = selectClassFramePeriod2;
        tmpDateList.push(event.target.name);
        setCheckSubjectCount(cntWork - 1);
        setSelectClassFramePeriod2(tmpDateList);
      } else {
        //外した場合
        const filterDate = selectClassFramePeriod2.filter(
          (date: string) => date !== event.target.value
        );
        setSelectClassFramePeriod2(filterDate);
        setCheckSubjectCount(cntWork + 1);
      }
    }
  };

  const watchCheckedFrame = () => {
    console.log(selectClassFramePeriod2);
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
      <br />
      <br />
      <br />
      <Button onClick={watchCheckedFrame} className={"btn btn-secondary ml-4"}>
        コマ数
      </Button>
    </>
  );
};

export default NoClassFrame;
