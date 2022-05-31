import { Link } from "react-router-dom";
import Top from "./Top";

const SecondLayer = (): JSX.Element => {
  return (
    <>
      <Top />
      <h1>Second Layer</h1>
      <Link to="/router-breadcrumbs/1st/2nd/3rd">To 3rd Layer</Link>
    </>
  );
};
export default SecondLayer;
