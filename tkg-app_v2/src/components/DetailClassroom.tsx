import React, { useState, useEffect } from "react";
import { Col } from "react-bootstrap";
import { classroom, classroomList } from "./SearchClassroom";
// import { Navigate, Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import "./style.css";

interface IfirstChildProps {
  classroomList: classroomList;
  selectClassroom: string;
  selectRoomId: string;
}

const DetailClassroom: React.FC<IfirstChildProps> = ({
  classroomList,
  selectClassroom,
  selectRoomId,
}) => {
  const [classroomId, setClassroomId] = useState<string>(selectClassroom);
  const [classroom, setClassroom] = useState<classroom>();
  // const [clickedButton, setClickedButton] = useState(selectRoomId);
  const navigate = useNavigate();

  // eslint-disable-next-line react-hooks/exhaustive-deps
  const search = (classroomId: string) => {
    console.log(classroomId);
    const filteredList = classroomList.filter(
      (classroom: classroom) => classroom.id === classroomId
    );
    setClassroom(filteredList[0]);
  };
  useEffect(() => {
    setClassroomId(selectClassroom);
    search(selectClassroom);
  }, [search, selectClassroom]);

  //詳細ボタンを押した時の挙動
  const clickDetail = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    event.preventDefault();
    const button: HTMLButtonElement = event.currentTarget;
    const id = button.value;
    navigate(`/receive-param/${id}`);
    // setClickedButton(button.value); //ここで設定した値を、別ページ表示のために使いたい
    // console.log(clickedButton);
    // navigate("/sample4", { state: { id: button.value } });
  };

  return (
    <div className="App">
      <Col md={6}>
        <h3>教室詳細</h3>
        <p>教室を選択すると、教室の詳細が表示されます</p>
        <h5>都道府県：{classroom?.prefecture}</h5>
        <h5>教室名：{classroom?.name}</h5>
        <button
          name="abc"
          id="selectClass"
          value={classroom?.id}
          onClick={clickDetail}
        >
          詳細表示
        </button>
      </Col>
    </div>
  );
};

export default DetailClassroom;
