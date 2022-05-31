import DataTable from "react-data-table-component";
import Card from "@material-ui/core/Card";
import SortIcon from "@material-ui/icons/ArrowDownward";
import Header from "../react-form_220530/Header";

// https://pgmemo.tokyo/data/archives/2111.html
const columns = [
  {
    name: "Title",
    selector: (row: { title: string }) => row.title,
    sortable: true,
  },
  {
    name: "Year",
    selector: (row: { year: string }) => row.year,
    sortable: true,
  },
];

const data = [
  {
    id: 1,
    title: "Beetlejuice",
    year: "1988",
  },
  {
    id: 2,
    title: "Ghostbusters",
    year: "1984",
  },
];

const TableBase = () => {
  return (
    <div className="App">
      <Header />
      <Card>
        <DataTable
          title="Movies"
          columns={columns}
          data={data}
          pagination
          selectableRows
          defaultSortFieldId="title"
          sortIcon={<SortIcon />}
        />
      </Card>
    </div>
  );
};

export default TableBase;
