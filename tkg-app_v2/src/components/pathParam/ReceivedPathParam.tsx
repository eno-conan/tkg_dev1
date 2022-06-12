import { useParams } from "react-router";
// import { useNavigate } from "react-router-dom";

type Params = {
  id?: string;
};
export const ReceivedPathParam = () => {
  const { id } = useParams<Params>();

  return (
    <>
      <h1>MyPageDetail</h1>
      <h2>idは{id}</h2>
    </>
  );
};
