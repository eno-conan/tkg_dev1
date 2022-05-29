import React, { useState, useEffect } from "react";
import { Row, Col, Container } from "react-bootstrap";
import { classroom, classroomList } from "./SearchClassroom";
// import { Navigate, Link } from "react-router-dom";

import "./style.css";

interface IfirstChildProps {
  classroomList: classroomList;
  selectClassroom: string;
}

const DetailClassroom: React.FC<IfirstChildProps> = ({
  classroomList,
  selectClassroom,
}) => {
  const [classroomId, setClassroomId] = useState<string>(selectClassroom);
  const [classroom, setClassroom] = useState<classroom>();

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
  return (
    <div className="App">
      <Col md={6}>
        <h3>教室詳細</h3>
        <p>教室を選択すると、教室の詳細が表示されます</p>
        <h5>都道府県：{classroom?.prefecture}</h5>
        <h5>教室名：{classroom?.name}</h5>
      </Col>
    </div>
  );
};

export default DetailClassroom;
