import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { Container, Button, Col, Row } from "react-bootstrap";
import { Helmet } from "react-helmet";
import { PASS_MAIN_FUNCTION, STUDENT_FUNCTION } from "../../config";

import "../tkgStyle.css";

type Params = {
  id?: string;
};
export const TkgTop = () => {
  const { id } = useParams<Params>();

  return (
    <>
      <Helmet>
        <title>Top Page</title>
      </Helmet>
      <Container className={"tkgTop mt-4"}>
        <Row className={"tkgTopHeader"}>
          <Col md={5}></Col>
          <Col md={6}>
            <h2>TKGサイト</h2>
          </Col>
        </Row>
        <Row className={"pt-4"}>
          <Col md={12} className={"pb-6 pl-4"}>
            <Link
              to={PASS_MAIN_FUNCTION.ClassSchedule}
              className={"btn btn-success btn-lg"}
            >
              授業予定
            </Link>
            <ul>
              <li>授業予定表示：実装中</li>
              <li>振替設定：完了</li>
              <li>講師変更：未対応</li>
            </ul>
          </Col>
        </Row>
        <br />
        <br />
        <Row className={"pt-4"}>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link
              to={PASS_MAIN_FUNCTION.Student}
              className={"btn btn-success btn-lg"}
            >
              生徒情報
            </Link>
            <ul>
              <li>登録：完了</li>
              <li>受講科目登録/確認：完了</li>
              <li>通塾可能時間設定：未対応</li>
              <li>予定表：対応中</li>
              <li>受講科目担当講師設定：未対応</li>
              <li>講習期間予定表示/更新：残→講習会季節IDだけ</li>
              <li>講習期間時間割作成</li>
              <li>スポット授業登録</li>
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
              <li>通勤可能時間設定</li>
              <li>予定表</li>
              <li>実績登録</li>
            </ul>
          </Col>
        </Row>
        <Row className={"pt-4"}>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link
              to="/router-breadcrumbs/1st/2nd"
              className={"btn btn-warning btn-lg"}
            >
              教室情報
            </Link>
            <i className="{onlyEmployee}">*社員のみ表示</i>
            <ul>
              <li>生徒確認</li>
              <li>講師確認</li>
              <li>室長割り当て</li>
            </ul>
          </Col>
          <Col md={6} className={"pb-6 pl-4"}>
            <Link
              to="/router-breadcrumbs/1st/2nd"
              className={"btn btn-warning btn-lg"}
            >
              承認機能
            </Link>
            <i className="{onlyEmployee}">*社員のみ表示</i>
            <ul>
              <li>担当講師設定承認</li>
              <li>通勤時間申請承認</li>
            </ul>
          </Col>
        </Row>
      </Container>
    </>
  );
};
