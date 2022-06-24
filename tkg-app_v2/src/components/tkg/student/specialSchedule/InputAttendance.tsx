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

export const dateFrameData = [{ date: "", ids: [1, 2, 3] }];

const InputAttendance = () => {
  // 講習日付一覧
  const [dateFrameList, setDateFrameList] =
    useState<Array<initData>>(dateFrameData);

  // The ids of users who are removed from the list
  const [ids, setIds] = useState<Array<number>>([]);

  useEffect(() => {
    getSpecialDateList("1");

    // 講習期間日程を取得し、初期表示に必要な情報の設定
    //チェックが入っているコマは初期表示でチェックを入れた状態にする
    function getSpecialDateList(specialSeasonId: string) {
      const options = { method: "GET" };
      specialSeasonId = "1";
      fetch(`${API_STUDENT.SpecialDateList}/${specialSeasonId}`, options)
        .then((response) => response.json())
        .then((fetchDateList) => {
          const eachDateArray = [...fetchDateList];
          //初期表示に必要な情報を格納する配列
          const dateFrames: initData[] = [];
          let frameIdStart = 1;
          eachDateArray.forEach((dateInfo) => {
            dateFrames.push({
              date: dateInfo,
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
          console.log(dateFrames);
          setDateFrameList(dateFrames);
        })
        .catch((error) => {
          console.log(error);
          alert("日程情報を取得できませんでした");
        });
    }
  }, []);

  //ある1コマだけチェック/チェック解除
  const clickOneFrame = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedId = parseInt(event.target.value);

    if (ids.includes(selectedId)) {
      const newIds = ids.filter((id) => id !== selectedId);
      setIds(newIds);
    } else {
      const newIds = [...ids];
      newIds.push(selectedId);
      setIds(newIds);
    }
  };

  // ある日、全コマ選択解除
  const removeAllFrameInDay = (event: any) => {
    const unselectCertainDayAll = dateFrameList[Number(event.target.value)].ids;
    let extractIds: number[] = [];
    unselectCertainDayAll.map((each) => {
      extractIds.push(each);
    });
    const currentIds = [...ids];
    let updateIds: number[] = [];
    updateIds = currentIds.filter((id) => !extractIds.includes(id));

    setIds(updateIds);
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
      })
      .catch((error) => {
        console.log(error);
        alert("出欠予定を更新できませんでした");
      });
  };

  return (
    <>
      <Container>
        <Row>
          <Col md={4}></Col>
          <Col md={4}>
            <h2>通塾可能日程調整</h2>
          </Col>
        </Row>
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
                  dateFrameList.map((info, index) => (
                    <>
                      <tr>
                        <td>{info.date}</td>
                        {info.ids.map((frame) => (
                          <td className={"w-40"}>
                            <div key={frame}>
                              <span>
                                <input
                                  type="checkbox"
                                  value={frame}
                                  onChange={clickOneFrame}
                                  checked={ids.includes(frame) ? true : false}
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

// const styles: { [key: string]: React.CSSProperties } = {
//   container: {
//     width: 500,
//     margin: "10px auto",
//     display: "flex",
//     flexDirection: "column",
//   },
//   userItem: {
//     width: "100%",
//     display: "flex",
//     justifyContent: "space-between",
//     margin: "6px 0",
//     padding: "8px 15px",
//     backgroundColor: "#fff9c4",
//   },
//   userId: {
//     width: "5%",
//   },
//   userName: {
//     width: "30%",
//   },
//   userEmail: {
//     width: "40%",
//   },
//   button: {
//     marginTop: 30,
//     padding: "15px 30px",
//     backgroundColor: "red",
//     color: "white",
//     fontWeight: "bold",
//     border: "none",
//     cursor: "pointer",
//   },
// };

export default InputAttendance;
