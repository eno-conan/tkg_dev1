/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Table } from "react-bootstrap";
import { API_STUDENT } from "../../../../config";

//www.kindacode.com/article/react-typescript-multiple-dynamic-checkboxes/

interface initData {
  // We only use these three ones
  date: string;
  ids: number[];
}

interface idAndCheckInfoIF {
  timeTableId: string;
  checkedFlg: boolean;
}

interface currentCheckedStatusIF {
  date: string;
  idAndCheckInfo: idAndCheckInfoIF[];
}

export const dateFrameData = [
  {
    date: "",
    ids: [1, 2, 3, 4, 5, 6, 7],
  },
];

export const hopeShape = [
  {
    date: "2022/07/19",
    idAndCheckInfo: [
      { timeTableId: "1", checkedFlg: true },
      { timeTableId: "2", checkedFlg: true },
      { timeTableId: "3", checkedFlg: true },
      { timeTableId: "4", checkedFlg: true },
      { timeTableId: "5", checkedFlg: true },
      { timeTableId: "6", checkedFlg: true },
      { timeTableId: "7", checkedFlg: true },
    ],
  },
];

const InputAttendance = () => {
  // 講習日付一覧
  const [dateFrameList, setDateFrameList] =
    useState<Array<initData>>(dateFrameData);

  const [currentCheckedStatusList, setCurrentCheckedStatusList] =
    useState<Array<currentCheckedStatusIF>>(hopeShape);

  // The ids of users who are removed from the list
  const [ids, setIds] = useState<Array<number>>([]);

  useEffect(() => {
    getSpecialDateList("1");

    // 講習期間日程を取得し、初期表示に必要な情報の設定
    //チェックが入っているコマは初期表示でチェックを入れた状態にする
    function getSpecialDateList(specialSeasonId: string) {
      const options = { method: "GET" };
      const studentId: string = "1";
      // const specialSeasonId: string = "1";
      fetch(
        `${API_STUDENT.SpecialAttendance}/${studentId}/?specialSeasonId=1`,
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
          // //初期表示に必要な情報を格納する配列
          const manageCheckedIds: initData[] = [];
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

  //ある1コマだけチェック/チェック解除
  const clickOneFrame = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedId: string = event.target.value;
    console.log(selectedId);
    //
    if (ids.includes(Number(selectedId))) {
      //チェック状態→非チェック状態
      const newIds = ids.filter((id) => id !== Number(selectedId));
      setIds(newIds); //ID更新

      /*画面のチェック状態更新*/
      const getCurrentInfo = [...currentCheckedStatusList];
      //「取得したtimeTableId / 7」の切り捨てで何日目のコマか、判断
      let dayIndex: number = Math.floor(Number(selectedId) / 7);

      let targetDateCurrentInfo = getCurrentInfo[dayIndex];

      //対象コマ情報取得
      const getDayInfo = targetDateCurrentInfo.idAndCheckInfo.filter(
        (info) => info.timeTableId === selectedId
      );
      //非チェック状態に更新
      getDayInfo[0].checkedFlg = false;

      /*画面の表示状態（チェック/非チェック）の更新*/
      setCurrentCheckedStatusList(getCurrentInfo);
    } else {
      //非チェック状態→チェック状態
      const newIds = [...ids];
      newIds.push(Number(selectedId));
      setIds(newIds); //ID更新
    }
  };

  // ある日、全コマ選択解除
  const removeAllFrameInDay = (event: any) => {
    /*ある日のtimetableIdとチェック状態を取得*/
    const unselectCertainDayAll =
      currentCheckedStatusList[Number(event.target.value)].idAndCheckInfo;
    console.log(unselectCertainDayAll);

    /*Idsから削除するtimetableId一覧情報を取得*/
    let extractIds: number[] = [];
    unselectCertainDayAll.forEach((each) => {
      extractIds.push(Number(each.timeTableId));
    });
    const currentIds = [...ids];
    let updateIds: number[] = [];
    updateIds = currentIds.filter((id) => !extractIds.includes(id));
    /*ID情報更新*/
    setIds(updateIds);

    const getCurrentInfo = [...currentCheckedStatusList];
    //クリックした「全削除」の日付に紐づく情報を取得
    let targetDateCurrentInfo = getCurrentInfo[event.target.value];

    //チェック状態を解除
    targetDateCurrentInfo.idAndCheckInfo.forEach((idAndCheck) => {
      idAndCheck.checkedFlg = false;
    });

    /*画面の表示状態（チェック/非チェック）の更新*/
    setCurrentCheckedStatusList(getCurrentInfo);
  };

  // ある日、全コマ選択
  const selectAllFrameInDay = (event: any) => {
    const selectCertainDayAll = dateFrameList[Number(event.target.value)].ids;
    const currentIds = [...ids];
    selectCertainDayAll.map((each) => {
      const newIds = currentIds.filter((checkedIds) => checkedIds === each);
      if (newIds.length === 0) {
        currentIds.push(each);
      }
    });
    setIds(currentIds);
  };

  //updateするからPUTでいいか？
  const updateDatabase = () => {
    let sendContent: string[] = [];
    let sentIds: string = "";
    if (ids.length === 0) {
      sentIds = "0";
    } else {
      sentIds = ids.toString();
    }
    sendContent.push("1"); //specialSeasonId
    sendContent.push("1"); //studentId
    sendContent.push(sentIds.toString());
    const options = {
      method: "PUT",
      body: sendContent.toString(),
    };
    fetch(`${API_STUDENT.UpdateSpecialAttendance}`, options)
      .then((response) => response.json())
      .then((updateTargetClass) => {
        alert("更新完了");
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
        alert("出欠予定を更新できませんでした");
      });
  };

  const booleanTest = (id: string) => {
    // console.log(id);
    // console.log(ids);
    return ids.includes(Number(id));
  };

  //動作確認用
  // const checkIds = () => {
  //   // console.log(currentCheckedStatusList);
  //   console.log(ids);
  // };

  return (
    <>
      <Container>
        <Row>
          <Col md={4}></Col>
          <Col md={4}>
            <h2>通塾可能日程調整</h2>
          </Col>
        </Row>
        {/* <button onClick={checkIds} className={"btn btn-secondary btn-sm"}>
          checkIds
        </button> */}
        {/* {users.length === 0 && <h3>Loading...</h3>} */}
        <Row>
          <Col md={2}></Col>
          <Col md={8}>
            <Table striped bordered hover responsive>
              <thead className={"sticky-top h-100"}>
                <th className="text-center text-nowrap">日付</th>
                <th className="text-center text-nowrap">2</th>
                <th className="text-center text-nowrap">3</th>
                <th className="text-center text-nowrap">4</th>
                <th className="text-center text-nowrap">5</th>
                <th className="text-center text-nowrap">6</th>
                <th className="text-center text-nowrap">7</th>
                <th className="text-center text-nowrap">8</th>
                <th className="text-center text-nowrap">全選択</th>
                <th className="text-center text-nowrap">全削除</th>
              </thead>
              <tbody>
                {dateFrameList.length > 0 &&
                  currentCheckedStatusList.map((info, index) => (
                    <>
                      <tr>
                        <td>{info.date}</td>
                        {info.idAndCheckInfo.map((frame) => (
                          <td className={"w-40"}>
                            <div key={frame.timeTableId}>
                              <span>
                                <input
                                  type="checkbox"
                                  value={frame.timeTableId}
                                  onChange={clickOneFrame}
                                  // checked={}
                                  checked={
                                    frame.checkedFlg
                                      ? true
                                      : booleanTest(frame.timeTableId)
                                  }
                                />
                              </span>
                            </div>
                          </td>
                        ))}
                        <td className={"w-30 mt-2"}>
                          <button
                            onClick={selectAllFrameInDay}
                            className={"btn btn-primary btn-sm"}
                            value={index}
                          >
                            ○
                          </button>
                        </td>
                        <td className={"w-30"}>
                          <button
                            onClick={removeAllFrameInDay}
                            className={"btn btn-secondary btn-sm"}
                            value={index}
                          >
                            ×
                          </button>
                        </td>
                      </tr>
                    </>
                  ))}
              </tbody>
              <tfoot className={"sticky-top h-100"}>
                <th className="text-center text-nowrap"></th>
                <th className="text-center text-nowrap">2</th>
                <th className="text-center text-nowrap">3</th>
                <th className="text-center text-nowrap">4</th>
                <th className="text-center text-nowrap">5</th>
                <th className="text-center text-nowrap">6</th>
                <th className="text-center text-nowrap">7</th>
                <th className="text-center text-nowrap">8</th>
                <th className="text-center text-nowrap"></th>
                <th className="text-center text-nowrap"></th>
              </tfoot>
            </Table>
          </Col>
        </Row>
        <Row>
          <Col md={8}></Col>
          <Col md={2}>
            <button
              className="btn btn-success float-right w-100"
              name="abc"
              id="selectClass"
              // value={classroom?.id}
              onClick={updateDatabase}
            >
              確定
            </button>
          </Col>
        </Row>
        <br />
      </Container>
    </>
  );
};

export default InputAttendance;
