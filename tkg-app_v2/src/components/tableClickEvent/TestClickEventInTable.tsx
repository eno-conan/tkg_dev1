import React, { DetailedHTMLProps, InputHTMLAttributes, useState } from "react";
import {
  Container,
  Button,
  Col,
  Row,
  CloseButton,
  Table,
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

const TestClickEventInTable = () => {
  const [visible, setVisible] = useState(true);
  const [checkCount, setCheckCount] = useState<number>(0);

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
    </Container>
  );
};

export default TestClickEventInTable;
