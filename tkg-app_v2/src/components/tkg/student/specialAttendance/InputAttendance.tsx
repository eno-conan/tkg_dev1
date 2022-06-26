/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useParams } from "react-router";
import { Container, Col, Row, Table } from "react-bootstrap";
import { API_STUDENT } from "../../../../config";
import { Helmet } from "react-helmet";
import UpdateAttendance from "./UpdateAttendance";
import {
  currentCheckedStatusIF,
  dateAndFrame,
  dateFrameData,
  hopeShape,
  idAndCheckInfoIF,
} from "../initData";
import OperateCheckbox from "./OperateCheckbox";
import TableHeader from "./TableHeader";
import TableFooter from "./TableFooter";

//www.kindacode.com/article/react-typescript-multiple-dynamic-checkboxes/

//生徒一覧画面からチェックした生徒の生徒ID取得
type Params = {
  checkedStudentId: string;
};

const InputAttendance = () => {
  // https: stackoverflow.com/questions/69992370/why-react-router-v6-useparams-returns-object-with-properties-possibly-undefined
  const { checkedStudentId } = useParams<keyof Params>() as Params;
  // 講習日付一覧
  const [dateFrameList, setDateFrameList] =
    useState<Array<dateAndFrame>>(dateFrameData);

  // 画面のチェック表示管理
  const [currentCheckedStatusList, setCurrentCheckedStatusList] =
    useState<Array<currentCheckedStatusIF>>(hopeShape);

  //IDの値管理
  const [ids, setIds] = useState<Array<number>>([]);

  useEffect(() => {
    getSpecialDateList("1");

    // 講習期間日程を取得し、初期表示に必要な情報の設定
    //チェックが入っているコマは初期表示でチェックを入れた状態にする
    function getSpecialDateList(specialSeasonId: string) {
      const options = { method: "GET" };
      // const specialSeasonId: string = "1";
      fetch(
        `${API_STUDENT.SpecialAttendance}/${checkedStudentId}/?specialSeasonId=1`,
        options
      )
        .then((response) => response.json())
        .then((fetchDateList) => {
          // 講習会の日付一覧、各日付に紐づくtimeTableIdとそれぞれのチェック状態を取得
          const eachDateArray = [...fetchDateList];

          /*画面表示用に整形*/
          const currentCheckedStatus: any = [];
          let checkedIdAllDayList: number[] = [];

          /*日付単位で処理*/
          eachDateArray.forEach((info) => {
            currentCheckedStatus.push({
              date: info.date,
              idAndCheckInfo: info.idAndCheckInfo,
            });
            /*取得段階でチェックがついているコマの情報をチェックID一覧に追加*/
            const idAndCheckInfoArray: idAndCheckInfoIF[] = info.idAndCheckInfo; //ある日付のIDとチェック状態
            /*チェック済ID一覧を取得*/
            let checkedIdList = idAndCheckInfoArray.filter(
              (eachIdInfo) => eachIdInfo.checkedFlg === true
            );
            //全日程分のID一覧管理用の配列に追加
            checkedIdList.forEach((idAndCheck: idAndCheckInfoIF) =>
              checkedIdAllDayList.push(Number(idAndCheck.timeTableId))
            );
          });
          setIds(checkedIdAllDayList);
          setCurrentCheckedStatusList(currentCheckedStatus);

          //チェック状態を管理するための処理
          // 初期表示に必要な情報を格納する配列
          const manageCheckedIds: dateAndFrame[] = [];
          let frameIdStart = 1;
          eachDateArray.forEach((dateInfo) => {
            manageCheckedIds.push({
              date: dateInfo.date,
              ids: [
                frameIdStart,
                frameIdStart + 1,
                frameIdStart + 2,
                frameIdStart + 3,
                frameIdStart + 4,
                frameIdStart + 5,
                frameIdStart + 6,
              ],
            });
            frameIdStart = frameIdStart + 7;
          });
          setDateFrameList(manageCheckedIds);
        })
        .catch((error) => {
          console.log(error);
          alert("日程情報を取得できませんでした");
        });
    }
  }, []);

  return (
    <>
      <Helmet>
        <title>講習会出欠予定表</title>
      </Helmet>
      <Container>
        <Row>
          <Col md={4}></Col>
          <Col md={4}>
            <h2>通塾可能日程調整</h2>
          </Col>
        </Row>
        <Row>
          <Col md={2}></Col>
          <Col md={8}>
            <Table striped bordered hover responsive>
              <TableHeader />
              <OperateCheckbox
                ids={ids}
                dateFrameList={dateFrameList}
                currentCheckedStatusList={currentCheckedStatusList}
                setCurrentCheckedStatusList={setCurrentCheckedStatusList}
                setIds={setIds}
              />
              <TableFooter />
            </Table>
          </Col>
        </Row>
        <UpdateAttendance ids={ids} checkedStudentId={checkedStudentId} />
        <br />
      </Container>
    </>
  );
};

export default InputAttendance;
