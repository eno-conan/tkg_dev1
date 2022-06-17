export interface ClassInfo {
  id: string;
  period: string;
  grade: string;
  subject: string;
  lecturerName: string;
  studentId: string;
  studentName: string;
  rescheduleDateStart: string;
  rescheduleDateEnd: string;
}
export type classScheduleList = Array<ClassInfo>;

export const classScheduleSampleData = [
  {
    id: "1",
    period: "6",
    grade: "h1",
    subject: "数学IA",
    studentId: "1",
    studentName: "山本由伸",
    lecturerName: "講師A",
    rescheduleDateStart: "2022-06-08",
    rescheduleDateEnd: "2022-06-22",
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
