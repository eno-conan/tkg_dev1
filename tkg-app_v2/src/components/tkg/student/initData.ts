/* 生徒登録*/
export interface Classroom {
  id: string;
  name: string;
}
export interface Grade {
  key: string;
  name: string;
}

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
