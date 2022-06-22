/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Col,
  Row,
  Button,
  CloseButton,
  Table,
} from "react-bootstrap";

//www.kindacode.com/article/react-typescript-multiple-dynamic-checkboxes/

interface User {
  // We only use these three ones
  id: number;
  name: string;
}

export const userData = [
  { id: 1, name: "B" },
  { id: 2, name: "C" },
  { id: 3, name: "D" },
  { id: 4, name: "E" },
  { id: 5, name: "F" },
  { id: 6, name: "G" },
  { id: 7, name: "A" },
];

export const userData2 = [
  { id: 8, name: "B" },
  { id: 9, name: "C" },
  { id: 10, name: "D" },
  { id: 11, name: "E" },
  { id: 12, name: "F" },
  { id: 13, name: "G" },
  { id: 14, name: "A" },
];

export const dateFrameArray = [
  { date: "2022/07/19", userData: userData },
  { date: "2022/07/20", userData: userData2 },
];

const MultiCheckbox = () => {
  // The users who are displayed in the list
  const [users, setUsers] = useState<Array<User>>(userData);

  // The ids of users who are removed from the list
  const [ids, setIds] = useState<Array<number>>([]);

  const selectUser = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedId = parseInt(event.target.value);

    if (ids.includes(selectedId)) {
      const newIds = ids.filter((id) => id !== selectedId);
      setIds(newIds);
    } else {
      const newIds = [...ids];
      newIds.push(selectedId);
      setIds(newIds);
    }
  };

  const removeUsers = (event: any) => {
    const unselectCertainDayAll =
      dateFrameArray[Number(event.target.value)].userData;
    let extractIds: number[] = [];
    unselectCertainDayAll.map((each) => {
      extractIds.push(each.id);
    });
    const currentIds = [...ids];
    let updateIds: number[] = [];
    updateIds = currentIds.filter((id) => !extractIds.includes(id));

    setIds(updateIds);
  };

  const selectAll = (event: any) => {
    const selectCertainDayAll =
      dateFrameArray[Number(event.target.value)].userData;
    const currentIds = [...ids];
    selectCertainDayAll.map((each) => {
      const newIds = currentIds.filter((checkedIds) => checkedIds === each.id);
      if (newIds.length === 0) {
        currentIds.push(each.id);
      }
    });
    setIds(currentIds);
  };

  const currentcheckIds = () => {
    console.log(ids);
  };

  return (
    <>
      <Container>
        <Row>
          <Col md={4}></Col>
          <Col md={4}>
            <h2>通塾可能日程調整</h2>
          </Col>
        </Row>
        {users.length === 0 && <h3>Loading...</h3>}
        <Row>
          <Col md={2}></Col>
          <Col md={8}>
            <Table striped bordered hover responsive>
              <thead>
                <th className="text-center text-nowrap">日付</th>
                <th className="text-center text-nowrap">2</th>
                <th className="text-center text-nowrap">3</th>
                <th className="text-center text-nowrap">4</th>
                <th className="text-center text-nowrap">5</th>
                <th className="text-center text-nowrap">6</th>
                <th className="text-center text-nowrap">7</th>
                <th className="text-center text-nowrap">8</th>
                <th className="text-center text-nowrap">全選択</th>
                <th className="text-center text-nowrap">全削除</th>
              </thead>
              <tbody>
                {dateFrameArray.length > 0 &&
                  dateFrameArray.map((user, index) => (
                    <>
                      <tr>
                        <td>{user.date}</td>
                        {user.userData.map((frame) => (
                          <td className={"w-40"}>
                            <div key={frame.id}>
                              <span>
                                <input
                                  type="checkbox"
                                  value={frame.id}
                                  onChange={selectUser}
                                  checked={
                                    ids.includes(frame.id) ? true : false
                                  }
                                />
                              </span>
                            </div>
                          </td>
                        ))}
                        <td className={"w-30 mt-2"}>
                          <button
                            onClick={selectAll}
                            className={"btn btn-primary btn-sm"}
                            value={index}
                          >
                            ○
                          </button>
                        </td>
                        <td className={"w-30"}>
                          <button
                            onClick={removeUsers}
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
            </Table>
            <button
              onClick={currentcheckIds}
              className={"btn btn-secondary btn-sm"}
            >
              ids
            </button>
          </Col>
        </Row>
        <br />
      </Container>
    </>
  );
};

const styles: { [key: string]: React.CSSProperties } = {
  container: {
    width: 500,
    margin: "10px auto",
    display: "flex",
    flexDirection: "column",
  },
  userItem: {
    width: "100%",
    display: "flex",
    justifyContent: "space-between",
    margin: "6px 0",
    padding: "8px 15px",
    backgroundColor: "#fff9c4",
  },
  userId: {
    width: "5%",
  },
  userName: {
    width: "30%",
  },
  userEmail: {
    width: "40%",
  },
  button: {
    marginTop: 30,
    padding: "15px 30px",
    backgroundColor: "red",
    color: "white",
    fontWeight: "bold",
    border: "none",
    cursor: "pointer",
  },
};

export default MultiCheckbox;
