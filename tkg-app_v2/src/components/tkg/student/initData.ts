/* 講習会授業予定表示;*/

//科目とコマ数
export interface SummaryInfo {
  id: string;
  studentId: string;
  subjectName: string;
  classCount: string;
}
export const eachSummaryData = [
  { id: "1", studentId: "1", subjectName: "数学IA", classCount: "14" },
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
