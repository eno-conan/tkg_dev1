import React, { useState, useEffect } from "react";
import // Container,
// Button,
// Col,
// Row,
// CloseButton,
// Table,
"react-bootstrap";
import { SummaryInfo } from "./initData";
import { specialSummaryArray } from "./RegistSpecialSchedule";

interface CheckedCountInfo {
  specialSummary: specialSummaryArray;
  checkedSubjectId: string;
  checkSubjectCount: number;
  setCheckedSubjectId: React.Dispatch<React.SetStateAction<string>>;
  setCheckedSubjectName: React.Dispatch<React.SetStateAction<string>>;
  setCheckSubjectCount: React.Dispatch<React.SetStateAction<number>>;
}

//講習科目とコマ数表示
const SpecialScheduleSummary: React.FC<CheckedCountInfo> = ({
  specialSummary,
  checkedSubjectId,
  checkSubjectCount,
  setCheckedSubjectId,
  setCheckedSubjectName,
  setCheckSubjectCount,
}) => {
  useEffect(() => {}, []);

  // 科目選択
  const checkedSubject = (event: React.ChangeEvent<HTMLInputElement>) => {
    // 科目ID(チェックを外した時も取得)
    const summaryNumber: string = event.target.value;

    //初期のチェック処理
    if (checkedSubjectId === "0" && event.target.checked) {
      setCheckedSubjectId(summaryNumber);
      specialSummary.forEach((summary: SummaryInfo) => {
        if (summary.id === summaryNumber) {
        }
      });
      const filterSummary = specialSummary.filter(
        (summary: SummaryInfo) => summary.id === summaryNumber
      );
      //画面：コマ数表示を動的にできるように修正
      setCheckedSubjectName(filterSummary[0].subjectName);
      setCheckSubjectCount(Number(filterSummary[0].classCount));
    }

    //ある科目をチェック状態で、別科目を選択しようとした場合（させない方針で）
    if (
      checkedSubjectId !== "0" &&
      checkedSubjectId !== summaryNumber &&
      event.target.checked
    ) {
      console.log("科目チェック状態で、別科目のチェック時");
      alert("現在選択中の科目を操作を完了してください");
    }

    if (
      checkedSubjectId !== "0" &&
      checkedSubjectId === summaryNumber &&
      !event.target.checked
    ) {
      console.log("科目チェック状態で、同じ科目のチェックを外す");
      const filterSummary = specialSummary.filter(
        (summary: SummaryInfo) => summary.id === summaryNumber
      );

      //画面表示時のコマ数とチェックに応じて変動するカウントの一致を判定
      // 一致：特に警告なくチェックを外す
      //不一致：チェックを入れた箇所があるので、更新されないことを警告
      if (filterSummary[0].classCount === checkSubjectCount.toString()) {
        setCheckedSubjectId("0");
      } else {
        alert("現在チェックした日時の情報は更新されません");
        //OKと言われれば更新
        setCheckedSubjectId("0");
      }
    }
  };

  return (
    <>
      {specialSummary.map((eachSummary) => (
        <>
          <tr>
            <td className={"align-middle"}>
              <input
                type="checkbox"
                value={eachSummary.id}
                onChange={checkedSubject}
                checked={eachSummary.id === checkedSubjectId}
              />
            </td>
            <td className={"align-middle"}>{eachSummary.subjectName}</td>
            {eachSummary.id === checkedSubjectId ? (
              //チェックが入っている科目は、チェック時に連動させる
              <td className={"align-middle"}>{checkSubjectCount}</td>
            ) : (
              //チェックが入っていない科目は固定表示
              <td className={"align-middle"}>{eachSummary.classCount}</td>
            )}
          </tr>
        </>
      ))}
    </>
  );
};

export default SpecialScheduleSummary;
