/* eslint-disable array-callback-return */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Form, Container, Col, Row, Button } from "react-bootstrap";
import "../../tkgStyle.css";

interface UpdateInfo {}

const StudentRegist: React.FC<UpdateInfo> = ({}) => {
  const [newTaskInput, setNewTaskInput] = useState<string>("");

  const addTask = () => {
    console.log(newTaskInput);
    //     const options = {
    //       method: "POST",
    //       body: newTaskInput,
    //     };

    //     fetch(`${API_BASE_URL}/tasks`, options)
    //       .then((response) => response.json())
    //       .then((newTask) => setTask((prevState) => [...prevState, newTask]))
    //       .catch((error) => {
    //         console.log(error);
    //         alert("couldn't add task");
    //       });
  };

  return (
    <Container>
      <br />
      <Row className={"tkgTopHeader"}>
        <Col md={5}></Col>
        <Col md={6}>
          <h3>生徒登録</h3>
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <label>Name</label>
          <input
            className={"w-100"}
            type="text"
            placeholder={"New Task"}
            value={newTaskInput}
            onChange={(event) => setNewTaskInput(event.target.value)}
            // onKeyPress={handleKeyPress}
          />
        </Col>
        <Col md={2}>
          <Button onClick={addTask}>Submit</Button>
        </Col>
      </Row>
    </Container>
  );
};

export default StudentRegist;
// https://github.com/eno-conan/tkg_dev1/blob/b386b5a43b7c7346d7e66c4f26581470c5113577/tkg-app_v2/src/components/sample/InputComponent.tsx
// const [newTaskInput, setNewTaskInput] = useState<string>("");

//   const handleKeyPress = (
//     event: DetailedHTMLProps<
//       InputHTMLAttributes<HTMLInputElement>,
//       HTMLInputElement
//     >
//   ) => {
//     if (event.key === "Enter") {
//       addTask();
//       setNewTaskInput("");
//     }
//   };

//   return (
//     <Row className={"add-task-item mt-5 mb-5"}>
//       <Col md={10}>
//         <input
//           className={"w-100"}
//           type="text"
//           placeholder={"New Task"}
//           value={newTaskInput}
//           onChange={(event) => setNewTaskInput(event.target.value)}
//           onKeyPress={handleKeyPress}
//         />
//       </Col>
//     </Row>
