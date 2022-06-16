import { useParams } from "react-router";
import { Link, NavLink } from "react-router-dom";
import { Container, Button, Col, Row } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { PASS_MAIN_FUNCTION, STUDENT_FUNCTION } from "../../../config";

import "../../tkgStyle.css";

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
          <Col md={12} className={"pb-6 pl-4"}>
            <Link
              to={STUDENT_FUNCTION.Regist}
              className={"btn btn-success btn-lg"}
            >
              登録
            </Link>
            <ul>
              <li>授業予定表示</li>
            </ul>
          </Col>
        </Row>
        <br />
        <br />
        <Row className={"pt-4"}>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link
              to={STUDENT_FUNCTION.UpdateSpecialSchedule}
              className={"btn btn-success btn-lg"}
            >
              講習会予定作成
            </Link>
            <ul>
              <li>登録</li>
              <li>受講科目登録</li>
            </ul>
          </Col>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link
              to="/router-breadcrumbs/1st/2nd"
              className={"btn btn-success btn-lg"}
            >
              講師情報
            </Link>
            <ul>
              <li>登録</li>
              <li>指導科目登録</li>
            </ul>
          </Col>
        </Row>
      </Container>
    </>
  );
};
