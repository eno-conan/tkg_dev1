/* eslint-disable array-callback-return */
import React from "react";
import { useNavigate } from "react-router-dom";
import { Col, Row, Button } from "react-bootstrap";
import DataTable from "react-data-table-component";
import Card from "@material-ui/core/Card";
import SortIcon from "@material-ui/icons/ArrowDownward";
import "../../../tkgStyle.css";
import { searchStudentArray } from "../initData";

interface ResultProps {
  studentList: searchStudentArray;
  displayStudentFlg: boolean;
  setCheckedStudentId: React.Dispatch<React.SetStateAction<string>>;
}
const StudentTableColumns = [
  {
    name: "生徒名",
    selector: (row: { studentName: string }) => row.studentName,
    sortable: false,
  },
  {
    name: "都道府県 | 教室名",
    selector: (row: { prefectureName: string; classroomName: string }) =>
      row.prefectureName + " | " + row.classroomName,
    sortable: false,
  },
];

const ResultTable: React.FC<ResultProps> = ({
  studentList,
  displayStudentFlg,
  setCheckedStudentId,
}) => {
  //生徒選択
  const selectStudent = (state: any) => {
    if (state.selectedCount > 0) {
      setCheckedStudentId(state.selectedRows[0].studentId);
    }
  };

  return (
    <>
      {displayStudentFlg ? (
        <Row className={"pt-4"}>
          <Col md={12} className={"pb-6 pl-4"}>
            <Card>
              <DataTable
                //content="Movies"
                columns={StudentTableColumns}
                data={studentList}
                pagination
                defaultSortFieldId="content"
                sortIcon={<SortIcon />}
                selectableRows
                selectableRowsSingle
                onSelectedRowsChange={selectStudent}
              />
            </Card>
          </Col>
        </Row>
      ) : (
        <></>
      )}
    </>
  );
};

export default ResultTable;
