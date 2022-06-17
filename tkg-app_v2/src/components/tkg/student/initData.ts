/* 生徒登録*/
export interface Classroom {
  id: string;
  name: string;
}
export interface Grade {
  key: string;
  name: string;
}
export type classroomArray = Array<Classroom>;
export type gradeArray = Array<Grade>;

export const classroomData = [
  {
    id: "1",
    name: "東京",
  },
];

export const gradeData = [
  {
    key: "j2",
    name: "中学2年生",
  },
];

/* 生徒検索*/
export interface SearchResultStudent {
  studentId: string;
  studentName: string;
  classroomName: string;
  prefectureName: string;
}

export type searchStudentArray = Array<SearchResultStudent>;

export const searchResultStudentData = [
  {
    studentId: "",
    studentName: "",
    classroomName: "",
    prefectureName: "",
  },
];

/* 講習会授業予定表示*/
//科目とコマ数
export interface SummaryInfo {
  id: string;
  studentId: string;
  subjectName: string;
  totalClassCount: string;
  unplaceClassCount: string;
}
export const eachSummaryData = [
  {
    id: "1",
    studentId: "1",
    subjectName: "数学IA",
    totalClassCount: "14",
    unplaceClassCount: "4",
  },
];

//各コマ表示
export interface ClassInfo {
  id: string;
  studentId: string;
  lecturerName: string;
  classDate: string;
  timeTableSpecialId: string;
  subject: string;
}
export const eachClassData = [
  {
    id: "1",
    studentId: "1",
    lecturerName: "",
    timeTableSpecialId: "1",
    classDate: "2022/07/29",
    subject: "",
  },
];

export const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    width: "60%",
    height: "50%",
  },
};
