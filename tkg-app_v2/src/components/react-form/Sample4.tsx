import React from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { Row, Col, Container } from "react-bootstrap";
import { API_BASE_URL } from "../../config";

type ValuesType = {
  name: string;
  age: string;
  phone: string;
};

const Sample4 = () => {
  const { register, handleSubmit, resetField } = useForm<ValuesType>({
    mode: "onSubmit",
    reValidateMode: "onChange",
    defaultValues: {
      name: "",
      age: "",
      phone: "",
    },
  });

  const handleOnSubmit: SubmitHandler<ValuesType> = (data) => {
    console.log(data);
    const options = {
      method: "POST",
      body: JSON.stringify(data),
    };

    fetch(`${API_BASE_URL}/tasks`, options)
      .then(() => {
        resetField("name");
        resetField("age");
        resetField("phone");
      })
      .catch((error) => {
        console.log(error);
        alert("couldn't add task");
      });
  };

  return (
    <div className="App">
      <Container>
        <Row className={"add-task-item mt-5 mb-5"}>
          <Col md={6}>
            <form onSubmit={handleSubmit(handleOnSubmit)}>
              <input
                className={"w-100 mb-2"}
                type="text"
                {...register("name")}
              />
              <input
                className={"w-100 mb-2"}
                type="number"
                {...register("age")}
              />
              <input
                className={"w-100 mb-2"}
                type="number"
                {...register("phone")}
              />
              <input type="submit" />
            </form>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Sample4;