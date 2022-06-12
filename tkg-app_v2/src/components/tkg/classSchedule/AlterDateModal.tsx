import React, { useState } from "react";
import { API_BASE_URL } from "../../../config";
import { ClassInfo, classScheduleList, customStyles } from "../ClassSchedule";
import Modal from "react-modal";
import {
  Button,
  Col,
  Row,
  CloseButton,
  // DropdownButton,
  // Dropdown,
} from "react-bootstrap";

interface ReceiveClassSchedule {
  classScheduleOrigin: classScheduleList;
  setClassScheduleOrigin: React.Dispatch<
    React.SetStateAction<classScheduleList>
  >;
  targetClass: string;
  setFrame: React.Dispatch<React.SetStateAction<string>>;
  setClassSchedule: React.Dispatch<React.SetStateAction<classScheduleList>>;
}

const AlterDateModal: React.FC<ReceiveClassSchedule> = ({
  classScheduleOrigin,
  setClassScheduleOrigin,
  targetClass,
  setFrame,
  setClassSchedule,
}) => {
  //振替期限を管理
  const [alterClassSpan, setAlterClassSpan] = useState<string>("");
  //モーダルで指定した振替情報を管理
  const [alterClassDate, setAlterClassDate] = useState<string>("");
  const [alterClassFrame, setAlterClassFrame] = useState<string>("");
  const [alterDateModalIsOpen, setAlterDateModalIsOpen] =
    useState<boolean>(false);
  let subtitle: HTMLHeadingElement | null;

  //モーダルで更新したい日付の情報を受け取る
  const alterClassDateAtModal = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setAlterClassDate(event.target.value);
  };

  //モーダルで更新したいコマの情報を受け取る
  const alterClassFrameAtModal = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setAlterClassFrame(event.target.value);
  };

  //モーダル操作
  function openModalAlterClass() {
    if (!targetClass) {
      alert("操作する授業にチェックを入れてください");
      return;
    }
    //チェックを入れた授業の情報取得
    const selectClassInfo = classScheduleOrigin.filter(
      (info: ClassInfo) => info.id.toString() === targetClass.toString()
    );
    //振替のモーダルに振替期限を表示
    setAlterClassSpan(
      "※振替期限：" +
        selectClassInfo[0].rescheduleDateStart +
        " ~ " +
        selectClassInfo[0].rescheduleDateEnd
    );
    setAlterDateModalIsOpen(true);
  }
  function afterOpenModalAlterClass() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalAlterClass() {
    setAlterDateModalIsOpen(false);
  }

  //振替確定
  const confirmAlterClassInfo = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    event.preventDefault();
    //振替情報
    const dateInfo = alterClassDate;
    if (!dateInfo) {
      alert("振替日付を選択してください");
      return;
    }
    const frameInfo = alterClassFrame;
    if (!frameInfo) {
      alert("コマを選択してください");
      return;
    }
    //データベース更新処理
    updateTargetClass();
  };

  //更新処理実施
  const updateTargetClass = () => {
    const options = {
      //  method: "POST",
      method: "PUT",
      body: targetClass + "," + alterClassDate + "," + alterClassFrame,
    };
    fetch(`${API_BASE_URL}/tkg/class-schedule/update`, options)
      .then((response) => response.json())
      .then((updateTargetClass) => {
        if (updateTargetClass.receiveErrorMessage) {
          alert(updateTargetClass.receiveErrorMessage.toString());
        } else {
          const today = formatDate(new Date());
          getTargetDateClassSchedule(today);
          alert("更新完了");
          closeModalAlterClass();
        }
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't add task");
      });
  };

  //以下2つは、ClassSchedule側にもあるぞ・・
  const getTargetDateClassSchedule = (targetDate: string) => {
    setFrame(""); //コマのプルダウンを初期値に戻す
    const options = { method: "GET" };
    fetch(
      `${API_BASE_URL}/tkg/class-schedule?targetDate=${targetDate}`,
      options
    )
      .then((response) => response.json())
      .then((fetchClassSchedule) => {
        setClassScheduleOrigin(fetchClassSchedule);
        setClassSchedule(fetchClassSchedule);
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  };

  //カレンダーに今日を初期表示（yyyy-mm-dd以外受付けしないみたい）
  const formatDate = (date: Date) => {
    const y = date.getFullYear();
    const m = ("00" + (date.getMonth() + 1)).slice(-2);
    const d = ("00" + date.getDate()).slice(-2);
    return y + "-" + m + "-" + d;
  };

  return (
    <span>
      <Button
        onClick={openModalAlterClass}
        className={"btn btn-secondary mx-4"}
      >
        振替設定
      </Button>
      <Modal
        contentLabel="Alter Setting"
        isOpen={alterDateModalIsOpen}
        style={customStyles}
        onAfterOpen={afterOpenModalAlterClass}
        onRequestClose={closeModalAlterClass}
      >
        <CloseButton onClick={closeModalAlterClass} />
        <form>
          <Row>
            <h4>振替設定</h4>
            <p>{alterClassSpan}</p>
            <span>
              <b>日付</b>
            </span>{" "}
            <span>
              <input
                type="date"
                // value={calender}
                // defaultValue={calender}
                onChange={alterClassDateAtModal}
              ></input>
            </span>
          </Row>
          <Row className={"pt-4"}>
            <Col md={4}>
              <div>
                <b>コマ</b>
              </div>
              <div>
                <select
                  // value={frame || ""}
                  onChange={alterClassFrameAtModal}
                >
                  <option>選択</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                  <option value="6">6</option>
                  <option value="7">7</option>
                  <option value="8">8</option>
                </select>
              </div>
            </Col>
          </Row>
          <Row className={"pt-4"}>
            <Col md={9}></Col>
            <Col md={3}>
              <button
                className="btn btn-success float-right"
                name="abc"
                id="selectClass"
                // value={classroom?.id}
                onClick={confirmAlterClassInfo}
              >
                確定
              </button>
            </Col>
          </Row>
        </form>
      </Modal>
    </span>
  );
};
export default AlterDateModal;
