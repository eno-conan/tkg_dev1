import React, { useState, useEffect } from "react";
import { API_BASE_URL, PASS_ROUTING } from "../../../config";
import {
  ClassInfo,
  eachClassData,
  SummaryInfo,
  eachSummaryData,
} from "./initData";
// import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Table, Button } from "react-bootstrap";
import AlreadyClassExist from "./AlreadyClassExist";
import NoClassFrame from "./NoClassFrame";
import SpecialScheduleSummary from "./SpecialScheduleSummary";
import UpdateDbSpecialSchedule from "./UpdateDbSpecialSchedule";
import SpecialScheduleFrame from "./SpecialScheduleFrame";

export type classesPeriodArray = Array<ClassInfo>;
export type specialSummaryArray = Array<SummaryInfo>;

const RegistSpecialSchedule = () => {
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
        <Col md={10}></Col>
        <Col md={2}>{checkSubjectCount}</Col>
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
