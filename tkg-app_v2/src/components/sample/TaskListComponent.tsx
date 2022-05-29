import React, {
  useEffect,
  useState,
  DetailedHTMLProps,
  InputHTMLAttributes,
} from "react";
import { ITask } from "../../App";
import { Container, Button, Col, Row } from "react-bootstrap";
import { API_BASE_URL } from "../../config";
import TaskComponent from "./TaskComponent";
// import { useNavigate } from "react-router-dom";

// interface IProps {
//   tasks: ITask[];
//   setTasks: React.Dispatch<React.SetStateAction<ITask[]>>;
// }

const TaskListComponent = () => {
  const [tasks, setTasks] = useState<ITask[]>([]);
  const [newTaskInput, setNewTaskInput] = useState<string>("");

  const handleKeyPress = (
    event: DetailedHTMLProps<
      InputHTMLAttributes<HTMLInputElement>,
      HTMLInputElement
    >
  ) => {
    if (event.key === "Enter") {
      addTask();
      setNewTaskInput("");
    }
  };

  const addTask = () => {
    const options = {
      method: "POST",
      body: newTaskInput,
    };

    fetch(`${API_BASE_URL}/tasks`, options)
      .then((response) => response.json())
      .then((newTask) => setTasks((prevState) => [...prevState, newTask]))
      .catch((error) => {
        console.log(error);
        alert("couldn't add task");
      });
  };

  useEffect(() => {
    const options = { method: "GET" };

    fetch(`${API_BASE_URL}/tasks`, options)
      .then((response) => response.json())
      .then((fetchedTasks) => setTasks(fetchedTasks))
      .catch((error) => {
        console.log(error);
        alert("couldn't fetch tasks");
      });
  }, []);

  return (
    <div className="App">
      <Container>
        <Row className={"add-task-item mt-5 mb-5"}>
          <Col md={10}>
            <input
              className={"w-100"}
              type="text"
              placeholder={"New Task"}
              value={newTaskInput}
              onChange={(event) => setNewTaskInput(event.target.value)}
              onKeyPress={handleKeyPress}
            />
          </Col>
          <Col md={2}>
            <Button onClick={addTask}>Add</Button>
          </Col>
        </Row>
        <h2 className={"display-4"}>Tasks</h2>
        {tasks.map((task, index) => (
          <TaskComponent setTasks={setTasks} task={task} key={index} />
        ))}
      </Container>
    </div>
  );
};
// const TaskListComponent: React.FC<IProps> = ({ tasks, setTasks }) => {
//   return (
//     <Container>
//       <h1 className={"display-1 text-center"}>Todo App</h1>
//       <hr />
//       <h2 className={"display-4"}>Tasks</h2>
//       {tasks.map((task, index) => (
//         <TaskComponent setTasks={setTasks} task={task} key={index} />
//       ))}
//     </Container>
//   );
// };

export default TaskListComponent;
