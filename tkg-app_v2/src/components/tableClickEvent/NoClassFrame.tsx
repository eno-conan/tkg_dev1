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

interface CheckedCountInfo {
  checkCount: number;
  setCheckCount: React.Dispatch<React.SetStateAction<number>>;
}

const NoClassFrame: React.FC<CheckedCountInfo> = ({
  checkCount,
  setCheckCount,
}) => {
  const [check1, setCheck1] = useState<boolean>(false);

  const checkOneCell = (event: React.ChangeEvent<HTMLInputElement>) => {
    let cntWork: number = checkCount;
    if (event.target.checked) {
      console.log("true");
      setCheckCount(cntWork + 1);
    } else {
      console.log("false");
      setCheckCount(cntWork - 1);
    }
  };

  return (
    <>
      <input
        className={"checkBox"}
        type="checkbox"
        name={"abc"}
        onChange={checkOneCell}
        defaultChecked={false}
      />
    </>
  );
};

export default NoClassFrame;
