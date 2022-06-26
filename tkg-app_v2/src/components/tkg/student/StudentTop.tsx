import { useParams } from "react-router";
import { Link, NavLink } from "react-router-dom";
import { Container, Button, Col, Row } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { PASS_MAIN_FUNCTION, STUDENT_FUNCTION } from "../../../config";

import "../../tkgStyle.css";
import { SearchResultStudent } from "./initData";

// type Params = {
//   id?: string;
// };
export const StudentTop = () => {
  // const { id } = useParams<Params>();

  return (
    <>
      <Helmet>
        <title>Student Top Page</title>
      </Helmet>
      <Container className={"tkgTop mt-4"}>
        <Row className={"tkgTopHeader"}>
          <Col md={5}></Col>
          <Col md={6}>
            <h2>生徒関連</h2>
          </Col>
        </Row>
        <Row className={"pt-4"}>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link
              to={STUDENT_FUNCTION.SearchStudent}
              className={"btn btn-success"}
            >
              検索
            </Link>
          </Col>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link to={STUDENT_FUNCTION.Regist} className={"btn btn-warning"}>
              登録
            </Link>
          </Col>
        </Row>
        <br />
        <br />
        <Row className={"pt-4"}>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link
              to={STUDENT_FUNCTION.UpdateSpecialSchedule}
              className={"btn btn-success"}
            >
              講習会授業予定？？（検索画面から遷移）
            </Link>
          </Col>
        </Row>
        <br />
        <br />
        <Row className={"pt-4"}></Row>
      </Container>
    </>
  );
};
