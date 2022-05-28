import React from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { useState, useEffect } from "react";

interface IFormInputs {
  firstName: string;
  lastName: string;
}

const onSubmit: SubmitHandler<IFormInputs> = (data: any) => {
  console.log(data);
  const api = async () => {
    const response = await fetch("http://localhost:8080/v1/tkg2", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: "Hubot",
        login: "hubot",
      }),
    });
    // const jsonData = await data.json();
    //console.log(jsonData);
  };
};

const Form = () => {
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm<IFormInputs>();

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register("firstName", { required: true })} />
      {errors.firstName && "First name is required"}
      <input {...register("lastName", { required: true })} />
      {errors.lastName && "Last name is required"}
      <input type="submit" />
    </form>
  );
};

export default Form;
