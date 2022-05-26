import React from "react";
import { useState, useEffect } from "react";
import "./style.css";

type resultProps = {
  id: number;
  prefectureName: string;
  createdAt: string;
  updatedAt: string;
};

const AreaInfo = () => {
  const [result, setResult] = useState<resultProps[]>([]);
  useEffect(() => {
    const api = async () => {
      const data = await fetch("http://localhost:8080/v1/tkg", {
        method: "GET",
      });
      const jsonData = await data.json();
      //console.log(jsonData);
      setResult(jsonData);
    };
    api();
  }, []);
  return (
    <div className="App">
      <table>
        <tr>
          <th>a</th>
        </tr>
        <tr>
          <th>b</th>
        </tr>
        {result.map((value, index) => {
          return (
            <tr key={index}>
              <th>{value.prefectureName}</th>
              <td>
                <input type="hidden" value={value.id} />
              </td>
            </tr>
          );
        })}
      </table>
    </div>
  );
};

export default AreaInfo;
