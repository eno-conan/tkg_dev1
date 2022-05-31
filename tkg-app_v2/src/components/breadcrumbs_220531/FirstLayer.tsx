import { Link } from "react-router-dom";
import Top from "./Top";

const FirstLayer = (): JSX.Element => {
  return (
    <>
      <Top />
      <h1>First Layer</h1>
      <Link to="/router-breadcrumbs/1st/2nd">To 2nd Layer</Link>
    </>
  );
};
export default FirstLayer;
