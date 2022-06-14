import React, { useState, useEffect } from "react";
import { API_BASE_URL } from "../../../config";
import { PASS_ROUTING } from "../../../config";
import { useNavigate, Link } from "react-router-dom";
import {
  Container,
  Col,
  Row,
  Table,
  Button,
  // DropdownButton,
  // Dropdown,
} from "react-bootstrap";
import AlreadyClassExist from "./AlreadyClassExist";
import NoClassFrame from "./NoClassFrame";

export interface ClassInfo {
  id: string;
  studentId: string;
  lecturerName: string;
  classDate: string;
  subject: string;
}
const eachClassData = [
  {
    id: "1",
    studentId: "1",
    lecturerName: "",
    classDate: "2022/07/29",
    subject: "",
  },
];
type classesPeriodArray = Array<ClassInfo>;

export interface SummaryInfo {
  id: string;
  studentId: string;
  subject: string;
  classCount: string;
}
const eachSummaryData = [
  { id: "1", studentId: "1", subject: "数学IA", classCount: "14" },
];
type specialSummaryArray = Array<SummaryInfo>;

const RegistSpecialSchedule = () => {
  const navigate = useNavigate();
  // 日付一覧を管理
  const [dateList, setDateList] = useState<string[]>(["1", "2"]);
  // 講習の授業概要を管理
  const [specialSummary, setSpecialSummary] =
    useState<specialSummaryArray>(eachSummaryData);

  //チェックした科目のコマ数を管理
  const [checkSubjectCount, setCheckSubjectCount] = useState<number>(0);
  // チェックした科目の行に付与したIDを管理;
  const [checkedSubjectId, setCheckedSubjectId] = useState<string>("0");

  //選択したコマの日付情報を管理（2コマ用）
  const [selectClassFramePeriod2, setSelectClassFramePeriod2] = useState<
    string[]
  >([""]);

  //コマごとの授業を管理
  const [classesPeriod2, setClassesPeriod2] =
    useState<classesPeriodArray>(eachClassData);
  const [classesPeriod3, setClassesPeriod3] =
    useState<classesPeriodArray>(eachClassData);
  // const [classesPeriod4, setClassesPeriod4] =
  //   useState<classesPeriodArray>(eachClassData);
  // const [classesPeriod5, setClassesPeriod5] =
  //   useState<classesPeriodArray>(eachClassData);
  // const [classesPeriod6, setClassesPeriod6] =
  //   useState<classesPeriodArray>(eachClassData);
  // const [classesPeriod7, setClassesPeriod7] =
  //   useState<classesPeriodArray>(eachClassData);

  // テスト取得
  useEffect(() => {
    getSpecialSummary("1", "1");
    getSpecialDateList("1");
    getTargetDateClassSchedule("1", "1");

    // 講習期間概要取得
    function getSpecialSummary(studentId: string, specialSeasonId: string) {
      const options = { method: "GET" };
      fetch(
        `${API_BASE_URL}/student/special-summary/${studentId}?specialSeasonId=${specialSeasonId}`,
        options
      )
        .then((response) => response.json())
        .then((fetchSummary) => {
          setSpecialSummary(fetchSummary);
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }

    // 講習期間日程を取得
    function getSpecialDateList(specialSeasonId: string) {
      const options = { method: "GET" };
      specialSeasonId = "1";
      fetch(
        `${API_BASE_URL}/student/special-date-list/${specialSeasonId}`,
        options
      )
        .then((response) => response.json())
        .then((fetchDateList) => {
          setDateList(fetchDateList);
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }
    // 指定した生徒の講習期間スケジュールを取得
    function getTargetDateClassSchedule(
      studentId: string,
      specialSeasonId: string
    ) {
      const options = { method: "GET" };
      fetch(
        `${API_BASE_URL}/student/special-schedule/${studentId}?specialSeasonId=${specialSeasonId}`,
        options
      )
        .then((response) => response.json())
        .then((fetchClassSchedule) => {
          setClassesPeriod2(fetchClassSchedule["2"]);
          setClassesPeriod3(fetchClassSchedule["3"]);
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }
  }, []);

  // 科目選択
  const checkedSubject = (event: React.ChangeEvent<HTMLInputElement>) => {
    // 科目ID(チェックを外した時も取得)
    const summaryNumber: string = event.target.value;

    //初期のチェック処理
    if (checkedSubjectId === "0" && event.target.checked) {
      console.log("初回選択、チェック");
      setCheckedSubjectId(summaryNumber);
      specialSummary.forEach((summary: SummaryInfo) => {
        if (summary.id === summaryNumber) {
        }
      });
      const filterSummary = specialSummary.filter(
        (summary: SummaryInfo) => summary.id === summaryNumber
      );
      setCheckSubjectCount(Number(filterSummary[0].classCount));
    }

    //既にいずれかの科目を選択済かつチェックを外したとき
    if (checkedSubjectId !== "0" && !event.target.checked) {
      console.log("既に選択済、チェック解除");
      setCheckedSubjectId("0");
    }
  };

  const registSelectFrameInfo = () => {
    const studentId = "1";
    const specialSeasonId = "1";
    const subjectId = checkedSubjectId; //本当は科目ごとに保持できるといい
    let sendContent: string[] = [];

    sendContent.push(studentId);
    sendContent.push(specialSeasonId);
    sendContent.push(subjectId);

    for (let date of selectClassFramePeriod2) {
      if (date === "") {
        sendContent.push("period2");
      } else {
        sendContent.push(date);
      }
    }

    const options = {
      method: "PUT",
      body: sendContent.toString(),
    };
    fetch(`${API_BASE_URL}/student/update-special-schedule`, options)
      .then((response) => response.json())
      .then((updateTargetClass) => {
        console.log(updateTargetClass);
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't add task");
      });
    navigate(`${PASS_ROUTING.Top}`);
  };

  return (
    <Container>
      <br />
      <Row className={"align-center"}>
        <Col md={6}>
          <h3>講習会授業予定作成</h3>
        </Col>
      </Row>
      <br />
      <Row>
        <Col md={12}>
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                <th className="text-center text-nowrap">選択</th>
                <th className="text-center text-nowrap">科目</th>
                <th className="text-center text-nowrap">コマ数</th>
              </tr>
            </thead>
            <tbody>
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
                    <td className={"align-middle"}>{eachSummary.subject}</td>
                    {eachSummary.id === checkedSubjectId ? (
                      <td className={"align-middle"}>{checkSubjectCount}</td>
                    ) : (
                      <td className={"align-middle"}>
                        {eachSummary.classCount}
                      </td>
                    )}
                  </tr>
                </>
              ))}
            </tbody>
          </Table>
        </Col>
        <Col md={10}></Col>
        <Col md={2}>{checkSubjectCount}</Col>
      </Row>

      <Row className={"tkgTop mt-4"}>
        <Col>
          <div>
            <Table striped bordered hover responsive>
              <thead>
                {/* ヘッダー（日付） */}
                <tr>
                  <th></th>
                  {dateList.map((eachDate) => (
                    <>
                      <th className="text-center text-nowrap">{eachDate}</th>
                    </>
                  ))}
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td className={"align-middle"}>2</td>
                  {classesPeriod2.map((eachData) => (
                    <>
                      <td className={"align-middle"}>
                        {eachData.id ? (
                          <AlreadyClassExist
                            subject={eachData.subject}
                            lecturerName={eachData.lecturerName}
                          />
                        ) : (
                          <NoClassFrame
                            selectClassFramePeriod2={selectClassFramePeriod2}
                            setSelectClassFramePeriod2={
                              setSelectClassFramePeriod2
                            }
                            checkedSubjectId={checkedSubjectId}
                            dateInfo={eachData.classDate}
                            checkSubjectCount={checkSubjectCount}
                            setCheckSubjectCount={setCheckSubjectCount}
                          />
                        )}
                      </td>
                    </>
                  ))}
                </tr>
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
      <br />
      <Row>
        <Col md={10}></Col>
        <Col>
          <Button
            onClick={registSelectFrameInfo}
            className={"btn btn-summary ml-4"}
          >
            登録
          </Button>
        </Col>
      </Row>
      <Row>
        <Col md={5}></Col>
        <Col md={4}>footerをつくりたい</Col>
        <Col md={2}></Col>
      </Row>
    </Container>
  );
};

export default RegistSpecialSchedule;
