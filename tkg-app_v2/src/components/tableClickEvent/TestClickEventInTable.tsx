import React, { useState, useEffect } from "react";
import { API_BASE_URL } from "../../config";
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

const dataArray = [
  {
    id: "1",
    detail: [
      { id: "1", date: "2022/07/29", checked: false },
      { id: "2", date: "2022/07/28", checked: false },
    ],
  },
  {
    id: "2",
    detail: [
      { id: "1", date: "2022/07/29", checked: true },
      { id: "2", date: "2022/07/28", checked: false },
    ],
  },
];

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

const dateListSummer: string[] = [
  "2022/07/19",
  "2022/07/20",
  "2022/07/21",
  "2022/07/22",
];

type classesPeriodArray = Array<ClassInfo>;

// export interface AllInfo {
//   classInfoByPeriod: Array<ClassInfo>;
// }

const TestClickEventInTable = () => {
  const [visible, setVisible] = useState(true);
  const [checkCount, setCheckCount] = useState<number>(0);
  //3-8も作成することになりそう
  const [classesPeriod2, setClassesPeriod2] =
    useState<classesPeriodArray>(eachClassData);
  const [judgeClassPeriod2, setJudgeClassPeriod2] =
    useState<classesPeriodArray>(eachClassData);

  const [classesPeriod4, setClassesPeriod4] =
    useState<classesPeriodArray>(eachClassData);

  // テスト取得
  useEffect(() => {
    getTargetDateClassSchedule("1");

    // 指定した生徒の講習機関スケジュールを取得
    function getTargetDateClassSchedule(targetDate: string) {
      const options = { method: "GET" };
      fetch(`${API_BASE_URL}/tkg/student/special-schedule/1`, options)
        .then((response) => response.json())
        .then((fetchClassSchedule) => {
          setClassesPeriod2(fetchClassSchedule["2"]);
          setClassesPeriod4(fetchClassSchedule["4"]);
        })
        .catch((error) => {
          console.log(error);
          alert("couldn't fetch tasks");
        });
    }
  }, []);

  function judgeExistClassOrNot() {
    const result: classesPeriodArray = [];
    classesPeriod2.forEach((each) => console.log(each.classDate));
    for (let date of dateListSummer) {
      let filteredList: classesPeriodArray = classesPeriod2.filter(
        (info: ClassInfo) => info.classDate === date
      );
      if (filteredList.length > 0) {
        console.log("exist:", date);
        result.push(filteredList[0]);
      } else {
        console.log("not exist:", date);
        result.push({
          id: "",
          studentId: "",
          lecturerName: "",
          classDate: date,
          subject: "",
        });
      }
    }
    setJudgeClassPeriod2(result);
    console.log(result);
  }

  // const confirmData = () => {
  //   console.log("===period2===");
  //   classesPeriod2.map((each) => console.log(each.id));
  //   console.log("===period4===");
  //   classesPeriod4.map((each) => console.log(each.id));
  // };

  return (
    <Container className={"tkgTop mt-4"}>
      <br />
      <Row>
        <Col md={6}></Col>
        <Col md={6}>{checkCount}</Col>
      </Row>
      <br />
      <Row>
        <Col md={6}>
          <Button
            onClick={judgeExistClassOrNot}
            className={"btn btn-secondary ml-4"}
          >
            取得
          </Button>
        </Col>
      </Row>
      <br />
      <Row>
        <Col>
          <div>
            <Table striped bordered hover responsive>
              <thead>
                {/* ヘッダー（日付） */}
                <tr>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                  <th className="text-center text-nowrap">コマ</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td className={"align-middle"}>2</td>
                  {judgeClassPeriod2.map((eachData) => (
                    <>
                      <td className={"align-middle"}>
                        {eachData.id ? (
                          <AlreadyClassExist
                            subject={eachData.subject}
                            lecturerName={eachData.lecturerName}
                          />
                        ) : (
                          <NoClassFrame
                            checkCount={checkCount}
                            setCheckCount={setCheckCount}
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
    </Container>
  );
};

export default TestClickEventInTable;
