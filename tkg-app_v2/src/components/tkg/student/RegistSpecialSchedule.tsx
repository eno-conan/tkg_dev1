import React, { useState, useEffect } from "react";
import { API_BASE_URL, API_STUDENT } from "../../../config";
import { useParams } from "react-router";
import {
  ClassInfo,
  eachClassData,
  SummaryInfo,
  eachSummaryData,
  classesAllData,
  AllClassInfo,
} from "./initData";
// import { useNavigate } from "react-router-dom";
import { Container, Col, Row, Table, Button } from "react-bootstrap";
import AlreadyClassExist from "./specialSchedule/AlreadyClassExist";
import NoClassFrame from "./specialSchedule/NoClassFrame";
import SpecialScheduleSummary from "./specialSchedule/SpecialScheduleSummary";
import UpdateDbSpecialSchedule from "./specialSchedule/UpdateDbSpecialSchedule";
import SpecialScheduleFrame from "./specialSchedule/SpecialScheduleFrame";

export type specialSummaryArray = Array<SummaryInfo>;
// export type classesPeriodArray = Array<ClassInfo>;
export type classesAllPeriodArray = Array<AllClassInfo>;
export type addDeleteFrameManageArray = Array<addDeleteFrameManage>;

//生徒一覧画面からチェックした生徒の生徒ID取得
type Params = {
  checkedStudentId: string;
};

//initData移動対象
export type addDeleteFrameManage = {
  period: string;
  addDelete: {
    add: string[];
    delete: string[];
  };
};

//initData移動対象
export const addDeleteFrameDataArray = [
  {
    period: "2",
    addDelete: {
      add: [],
      delete: [],
    },
  },
  {
    period: "3",
    addDelete: {
      add: [],
      delete: [],
    },
  },
  {
    period: "4",
    addDelete: {
      add: [],
      delete: [],
    },
  },
];

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

  // 全コマの増減を一括で管理する
  const [frameAddDeleteManage, setFrameAddDeleteManage] =
    useState<addDeleteFrameManageArray>(addDeleteFrameDataArray);

  //全コマの授業予定
  const [classesAllPeriod, setClassesAllPeriod] =
    useState<classesAllPeriodArray>(classesAllData);

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
          // 全コマの授業情報格納
          let setAllData: classesAllPeriodArray = [];
          for (let period = 2; period < 5; period++) {
            // 各コマの授業情報格納
            let setEachData: AllClassInfo = {
              period: period.toString(),
              classes: fetchClassSchedule[period.toString()],
            };
            setAllData.push(setEachData);
          }
          setClassesAllPeriod(setAllData);
        })
        .catch((error) => {
          console.log(error);
          alert("講習会スケジュールが取得できませんでした。");
        });
    }
  }, []);

  const checkFrameId = () => {
    console.log(frameAddDeleteManage);
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
                {classesAllPeriod &&
                  classesAllPeriod.map((eachPeriod) => (
                    <SpecialScheduleFrame
                      eachPeriodInfo={eachPeriod}
                      checkedSubjectId={checkedSubjectId}
                      checkedSubjectName={checkedSubjectName}
                      checkSubjectCount={checkSubjectCount}
                      setCheckSubjectCount={setCheckSubjectCount}
                      frameAddDeleteManage={frameAddDeleteManage}
                      setFrameAddDeleteManage={setFrameAddDeleteManage}
                    />
                  ))}
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
      <br />
      <br />
      <UpdateDbSpecialSchedule
        checkedStudentId={checkedStudentId}
        checkedSubjectId={checkedSubjectId}
        frameAddDeleteManage={frameAddDeleteManage}
        classesAllPeriod={classesAllPeriod}
      />
      <Button onClick={checkFrameId} className={"btn btn-summary pl-4"}>
        コマ数確認（動確用）
      </Button>
      <Row>
        <Col md={5}></Col>
        <Col md={4}>footerをつくりたい</Col>
        <Col md={2}></Col>
      </Row>
    </Container>
  );
};

export default RegistSpecialSchedule;
