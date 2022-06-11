import React, { DetailedHTMLProps, InputHTMLAttributes, useState } from "react";
import { API_BASE_URL } from "../../../config";
import { ITask } from "../../../App";
import { Button, Col, Row } from "react-bootstrap";
import { StudentSchedule } from "../ClassSchedule";

interface IProps {
  schedule: StudentSchedule;
  key: any;
}

const StudentScheduleView: React.FC<IProps> = ({ schedule, key }) => {
  return (
    <tbody>
      <tr>
        <td className="align-items-center justify-content-center">
          {schedule.classDate}
        </td>
        <td>{schedule.period}</td>
        <td>{schedule.subject}</td>
        <td>{schedule.lecturerName}</td>
        <td>{"備考"}</td>
      </tr>
    </tbody>
  );
};
export default StudentScheduleView;
