import React, { useState, useEffect } from "react";
import { API_BASE_URL, API_STUDENT } from "../../../config";
import { useParams } from "react-router";
import {
  ClassInfo,
  eachClassData,
  SummaryInfo,
  eachSummaryData,
} from "./initData";
// import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Table, Button } from "react-bootstrap";
import AlreadyClassExist from "./specialSchedule/AlreadyClassExist";
import NoClassFrame from "./specialSchedule/NoClassFrame";
import SpecialScheduleSummary from "./specialSchedule/SpecialScheduleSummary";
import UpdateDbSpecialSchedule from "./specialSchedule/UpdateDbSpecialSchedule";
import SpecialScheduleFrame from "./specialSchedule/SpecialScheduleFrame";

export type classesPeriodArray = Array<ClassInfo>;
export type specialSummaryArray = Array<SummaryInfo>;

//生徒一覧画面からチェックした生徒の生徒ID取得
type Params = {
  checkedStudentId: string;
};

const RegistSpecialSchedule = () => {
  const { checkedStudentId } = useParams<keyof Params>() as Params;
  // 講習日付一覧
  const [dateList, setDateList] = useState<string[]>(["1", "2"]);
  // 講習の授業概要
  const [specialSummary, setSpecialSummary] =
    useState<specialSummaryArray>(eachSummaryData);

  /*チェック科目関連*/
  // 受講コマ数（トータルコマ数）
  const [checkSubjectTotalCount, setCheckSubjectTotalCount] =
    useState<number>(0);
  // 残りコマ数
  const [checkSubjectCount, setCheckSubjectCount] = useState<number>(0);
  //科目名
  const [checkedSubjectName, setCheckedSubjectName] = useState<string>("0");
  // テーブルID
  const [checkedSubjectId, setCheckedSubjectId] = useState<string>("0");

  /* DB更新*/
  //追加したコマの日付情報を管理（2コマ用）
  const [selectClassFramePeriod2, setSelectClassFramePeriod2] = useState<
    string[]
  >([""]);
  //削除したコマの日付情報を管理（2コマ用）
  const [deleteClassFramePeriod2, setDeleteClassFramePeriod2] = useState<
    string[]
  >([""]);

  //コマごとの授業予定を管理
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
    getSpecialSummary(checkedStudentId, "1");
    getSpecialDateList("1");
    getTargetDateClassSchedule(checkedStudentId, "1");

    // 講習期間概要取得
    function getSpecialSummary(studentId: string, specialSeasonId: string) {
      const options = { method: "GET" };
      fetch(
        `${API_STUDENT.SpecialSummary}/${studentId}?specialSeasonId=${specialSeasonId}`,
        options
      )
        .then((response) => response.json())
        .then((fetchSummary) => {
          setSpecialSummary(fetchSummary);
        })
        .catch((error) => {
          console.log(error);
          alert("講習会科目コマ数が取得できませんでした。");
        });
    }

    // 講習期間日程を取得
    function getSpecialDateList(specialSeasonId: string) {
      const options = { method: "GET" };
      specialSeasonId = "1";
      fetch(`${API_STUDENT.SpecialDateList}/${specialSeasonId}`, options)
        .then((response) => response.json())
        .then((fetchDateList) => {
          setDateList(fetchDateList);
        })
        .catch((error) => {
          console.log(error);
          alert("講習会日程が取得できませんでした。");
        });
    }
    // 指定した生徒の講習期間スケジュールを取得
    function getTargetDateClassSchedule(
      studentId: string,
      specialSeasonId: string
    ) {
      const options = { method: "GET" };
      fetch(
        `${API_STUDENT.SpecialSchedule}/${studentId}?specialSeasonId=${specialSeasonId}`,
        options
      )
        .then((response) => response.json())
        .then((fetchClassSchedule) => {
          console.log(fetchClassSchedule["2"]);
          setClassesPeriod2(fetchClassSchedule["2"]);
          setClassesPeriod3(fetchClassSchedule["3"]);
        })
        .catch((error) => {
          console.log(error);
          alert("講習会スケジュールが取得できませんでした。");
        });
    }
  }, []);

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
          <SpecialScheduleSummary
            specialSummary={specialSummary}
            checkedSubjectId={checkedSubjectId}
            setCheckSubjectTotalCount={setCheckSubjectTotalCount}
            checkSubjectCount={checkSubjectCount}
            setCheckedSubjectId={setCheckedSubjectId}
            setCheckedSubjectName={setCheckedSubjectName}
            setCheckSubjectCount={setCheckSubjectCount}
          />
        </Col>
      </Row>

      <Row className={"tkgTop mt-4"}>
        <Col>
          <div>
            <SpecialScheduleFrame
              dateList={dateList}
              classesPeriod2={classesPeriod2}
              checkedSubjectId={checkedSubjectId}
              checkedSubjectName={checkedSubjectName}
              checkSubjectCount={checkSubjectCount}
              setCheckSubjectCount={setCheckSubjectCount}
              selectClassFramePeriod2={selectClassFramePeriod2}
              setSelectClassFramePeriod2={setSelectClassFramePeriod2}
              deleteClassFramePeriod2={deleteClassFramePeriod2}
              setDeleteClassFramePeriod2={setDeleteClassFramePeriod2}
            />
          </div>
        </Col>
      </Row>
      <br />
      <br />
      <UpdateDbSpecialSchedule
        checkedSubjectId={checkedSubjectId}
        classesPeriod2={classesPeriod2}
        selectClassFramePeriod2={selectClassFramePeriod2}
        deleteClassFramePeriod2={deleteClassFramePeriod2}
      />
      <Row>
        <Col md={5}></Col>
        <Col md={4}>footerをつくりたい</Col>
        <Col md={2}></Col>
      </Row>
    </Container>
  );
};

export default RegistSpecialSchedule;
