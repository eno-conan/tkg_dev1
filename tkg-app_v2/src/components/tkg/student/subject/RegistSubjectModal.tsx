import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-modal";
import {
  Container,
  Col,
  Row,
  Button,
  CloseButton,
  Table,
} from "react-bootstrap";
import {
  customStyles,
  lecturerArray,
  lecturerData,
  studentSubjArray,
  studentSubjData,
  subjByGradeArray,
  subjByGradeData,
  timeTableNormalArray,
  timeTableNormalData,
} from "../initData";
import "../../../tkgStyle.css";
import { API_STUDENT } from "../../../../config";

interface RegistSubjectProps {
  checkedStudentId: string;
}

const RegistSubjectModal: React.FC<RegistSubjectProps> = ({
  checkedStudentId,
}) => {
  //モーダル管理
  const [registSubjectModalIsOpen, setRegistSubjectModalIsOpen] =
    useState<boolean>(false);
  const [studentSubjectList, setStudentSubjectList] =
    useState<studentSubjArray>(studentSubjData);
  //科目情報
  const [subjectsByGradeList, setSubjectsByGradeList] =
    useState<subjByGradeArray>(subjByGradeData);
  //講師一覧
  const [lecturerList, setLecturerList] = useState<lecturerArray>(lecturerData);
  //タイムテーブル一覧
  const [timeTableNormalList, setTimeTableNormal] =
    useState<timeTableNormalArray>(timeTableNormalData);

  //選択した値を管理
  const [subject, setSubject] = useState<string>("");
  const [timetable, setTimetable] = useState<string>("");
  const [lecturer, setLecturer] = useState<string>("");
  let subtitle: HTMLHeadingElement | null;

  function openModalRegistSubject() {
    getSubjectsByGrade();
    getTimeTableNormal();
    getLecturer();
    //モーダル表示
    setRegistSubjectModalIsOpen(true);
  }
  function afterOpenModalRegistSubject() {
    if (subtitle) subtitle.style.color = "#f00";
  }
  function closeModalRegistSubject() {
    setRegistSubjectModalIsOpen(false);
  }

  //生徒の学年に応じて受講科目を取得
  const getSubjectsByGrade = () => {
    if (!checkedStudentId) {
      alert("対象の生徒を選択してください。");
    } else {
      const options = { method: "GET" };
      fetch(`${API_STUDENT.PrepareSubjectList}/${checkedStudentId}`, options)
        .then((response) => response.json())
        .then((result) => {
          if (result === "0") {
            alert("受講科目が設定されていません");
          } else {
            setSubjectsByGradeList(result);
          }
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }
  };

  //タイムテーブルを取得
  const getTimeTableNormal = () => {
    if (!checkedStudentId) {
      alert("対象の生徒を選択してください。");
    } else {
      const options = { method: "GET" };
      fetch(
        `${API_STUDENT.PrepareTimeTableNormal}/${checkedStudentId}`,
        options
      )
        .then((response) => response.json())
        .then((result) => {
          if (result === "0") {
            alert("受講科目が設定されていません");
          } else {
            setTimeTableNormal(result);
          }
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }
  };

  //講師を取得
  const getLecturer = () => {
    if (!checkedStudentId) {
      alert("対象の生徒を選択してください。");
    } else {
      const options = { method: "GET" };
      fetch(`${API_STUDENT.PrepareLecutererList}/${checkedStudentId}`, options)
        .then((response) => response.json())
        .then((result) => {
          if (result === "0") {
            alert("受講科目が設定されていません");
          } else {
            setLecturerList(result);
          }
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }
  };

  //プルダウンで値選択
  const selectSubject = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSubject(event.target.value);
  };
  const selectTimeTable = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setTimetable(event.target.value);
  };
  const selectLecturer = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setLecturer(event.target.value);
  };

  const inserTableStudentSubject = () => {
    if (!subject || !timetable || !lecturer) {
      alert("科目・コマ・講師のいずれかが選択されていません");
    } else {
      //重複登録していないかチェックを実施/
      const sendContent: string[] = [];
      sendContent.push(checkedStudentId);
      sendContent.push(subject);
      sendContent.push(timetable);
      sendContent.push(lecturer);
      const options = {
        method: "POST",
        body: sendContent.toString(),
      };
      fetch(`${API_STUDENT.RegistSubject}`, options)
        .then((response) => response.json())
        .then((insertSubject) => {
          // console.log(insertSubject);
          alert(
            "受講科目を追加しました。「受講科目確認」から登録内容を確認できます"
          );
          setRegistSubjectModalIsOpen(false);
        })
        .catch((error) => {
          console.log(error);
          alert("科目追加が行えませんでした");
        });
    }
  };

  return (
    <>
      <Col md={2}>
        <Button
          onClick={openModalRegistSubject}
          className={"btn btn-secondary ml-2"}
        >
          受講科目登録
        </Button>
        <Modal
          contentLabel="registSubject"
          isOpen={registSubjectModalIsOpen}
          style={customStyles}
          onAfterOpen={afterOpenModalRegistSubject}
          onRequestClose={closeModalRegistSubject}
        >
          <>
            <Row>
              <Col md={11}></Col>
              <Col md={1}>
                <CloseButton onClick={closeModalRegistSubject} />
              </Col>
            </Row>
            <Row>
              <Col md={4}></Col>
              <Col md={8} className={"mb-4"}>
                <h4>受講科目登録</h4>
              </Col>
            </Row>
            <Row>
              <Col md={4}></Col>
              <Col md={8} className={"mb-4"}>
                <label className={"mb-1"}>学年</label>
                <div>
                  <select onChange={selectSubject}>
                    <option value="" selected>
                      プルダウンから選択してください
                    </option>
                    {subjectsByGradeList.map((subject) => (
                      <option value={subject.subjectKey}>
                        {subject.subjectName}
                      </option>
                    ))}
                  </select>
                </div>
              </Col>
            </Row>
            <Row>
              <Col md={4}></Col>
              <Col md={8} className={"mb-4"}>
                <label className={"mb-1"}>定期コマ</label>
                <div>
                  <select onChange={selectTimeTable}>
                    <option value="" selected>
                      プルダウンから選択してください
                    </option>
                    {timeTableNormalList.map((timetable) => (
                      <option value={timetable.timeTableId}>
                        {timetable.dateOfWeekFrame}
                      </option>
                    ))}
                  </select>
                </div>
              </Col>
            </Row>
            <Row>
              <Col md={4}></Col>
              <Col md={8} className={"mb-4"}>
                <label className={"mb-1"}>担当講師</label>
                <div>
                  <select onChange={selectLecturer}>
                    <option value="" selected>
                      プルダウンから選択してください
                    </option>
                    {lecturerList.map((lec) => (
                      <option value={lec.lecturerId}>{lec.lecturerName}</option>
                    ))}
                  </select>
                </div>
              </Col>
            </Row>
            <Row>
              <Col md={10}></Col>
              <Col md={2}>
                <button
                  className="btn btn-success float-right w-100"
                  name="abc"
                  id="serachResult"
                  onClick={inserTableStudentSubject}
                >
                  登録
                </button>
              </Col>
            </Row>
          </>
        </Modal>
      </Col>
    </>
  );
};
export default RegistSubjectModal;
