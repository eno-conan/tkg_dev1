import React from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { Row, Col, Container } from "react-bootstrap";

type ValuesType = {
  name: string;
  age: number;
  phone: number;
};

const Sample2 = () => {
  const { register, handleSubmit } = useForm<ValuesType>({
    mode: "onSubmit",
    reValidateMode: "onChange",
  });

  const handleOnSubmit: SubmitHandler<ValuesType> = (data) => {
    console.log(data);
  };

  return (
    <div className="App">
      <Container>
        <Row>
          <Col md={6}>
            <form onSubmit={handleSubmit(handleOnSubmit)}>
              <input type="text" {...register("name")} />
              <input type="number" {...register("age")} />
              <input type="number" {...register("phone")} />
              <input type="submit" />
            </form>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Sample2;
