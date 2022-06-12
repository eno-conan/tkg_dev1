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
    lecturerName: "A",
    classDate: "2022/07/29",
    subject: "math",
  },
  {
    id: "1",
    studentId: "1",
    lecturerName: "A",
    classDate: "2022/07/29",
    subject: "math",
  },
];

const receiveFromBackend = [
  {
    id: 1,
    classInfoByPeriod: [
      {
        id: "1",
        studentId: "1",
        lecturerName: "A",
        classDate: "2022/07/29",
        subject: "math",
      },
      {
        id: "1",
        studentId: "1",
        lecturerName: "A",
        classDate: "2022/07/29",
        subject: "math",
      },
    ],
  },
  {
    id: 2,
    classInfoByPeriod: [
      {
        id: "1",
        studentId: "1",
        lecturerName: "A",
        classDate: "2022/07/29",
        subject: "math",
      },
      {
        id: "1",
        studentId: "1",
        lecturerName: "A",
        classDate: "2022/07/29",
        subject: "math",
      },
    ],
  },
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
  const [classesPeriod4, setClassesPeriod4] =
    useState<classesPeriodArray>(eachClassData);

  // テスト取得
  useEffect(() => {
    getTargetDateClassSchedule("1");
  }, []);

  // 指定した生徒の講習機関スケジュールを取得
  const getTargetDateClassSchedule = (targetDate: string) => {
    // setFrame(""); //コマのプルダウンを初期値に戻す
    const options = { method: "GET" };
    fetch(`${API_BASE_URL}/tkg/student/current-special-schedule/1`, options)
      .then((response) => response.json())
      .then((fetchClassSchedule) => {
        setClassesPeriod2(fetchClassSchedule["2"]);
        setClassesPeriod4(fetchClassSchedule["4"]);
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  };

  const confirmData = () => {
    console.log("===period2===");
    classesPeriod2.map((each) => console.log(each.id));
    console.log("===period4===");
    classesPeriod4.map((each) => console.log(each.id));
  };

  return (
    <Container className={"tkgTop mt-4"}>
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
                {dataArray.map((data, index) => (
                  <tr>
                    <td>{index + 2}</td>
                    {data.detail?.map((each) => (
                      <>
                        <td className={"align-middle"}>
                          {each.checked ? (
                            <AlreadyClassExist />
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
                ))}
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
      <Row>
        <Col md={6}>
          <Button onClick={confirmData} className={"btn btn-secondary ml-4"}>
            コンソールでチェック
          </Button>
        </Col>
      </Row>
    </Container>
  );
};

export default TestClickEventInTable;
function data(data: any): any {
  throw new Error("Function not implemented.");
}
