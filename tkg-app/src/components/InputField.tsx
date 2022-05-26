import React from "react";
import "./style.css";

const InputField = () => {
  return (
    <form action="" className="input">
      <input type="input" placeholder="Enter value" className="input__box" />
      <button className="input__submit" type="submit">
        Go
      </button>
    </form>
  );
};

export default InputField;
