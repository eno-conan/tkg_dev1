/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { currentCheckedStatusIF, dateAndFrame } from "../initData";

interface UpdateInfo {
  ids: Array<number>;
  dateFrameList: Array<dateAndFrame>;
  currentCheckedStatusList: Array<currentCheckedStatusIF>;
  setIds: React.Dispatch<React.SetStateAction<Array<number>>>;
  setCurrentCheckedStatusList: React.Dispatch<
    React.SetStateAction<Array<currentCheckedStatusIF>>
  >;
  // setBirthday: React.Dispatch<React.SetStateAction<string>>;
}
const OperateCheckbox: React.FC<UpdateInfo> = ({
  ids,
  dateFrameList,
  currentCheckedStatusList,
  setCurrentCheckedStatusList,
  setIds,
}) => {
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

  const booleanTest = (id: string) => {
    return ids.includes(Number(id));
  };
  return (
    <>
      <tbody>
        {currentCheckedStatusList.map((info, index) => (
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
    </>
  );
};

export default OperateCheckbox;
