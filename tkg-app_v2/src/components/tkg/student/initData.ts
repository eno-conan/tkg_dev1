import { StudentSchedule } from "../classSchedule/StudentScheduleModal";
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

/* 生徒予定表示*/
export interface StudentScheduleWhole {
  classDate: string;
  id: string;
  lecturerId: string;
  normalSpecial: string;
  period: string;
  rescheduleDateLast: string;
  rescheduleDateStart: string;
  rescheduleFlg: boolean;
  studentId: string;
  subjectKey: string;
  subjectName: string;
  timeTableId: string;
}
export type studentScheduleArray = Array<StudentScheduleWhole>;
export const studentScheduleData = [
  {
    classDate: "2022/07/19",
    id: "70",
    lecturerId: "1",
    normalSpecial: "special",
    period: "2",
    rescheduleDateLast: "2022/09/14",
    rescheduleDateStart: "2022/09/14",
    rescheduleFlg: false,
    studentId: "1",
    subjectKey: "sc2",
    subjectName: "sc2",
    timeTableId: "1",
  },
];
/* 生徒科目表示*/
export interface StudentSubject {
  studentId: string;
  subjectName: string;
  lecturerName: string;
  dateOfweekFrame: string;
}

export type studentSubjArray = Array<StudentSubject>;
export const studentSubjData = [
  {
    studentId: "",
    subjectName: "",
    lecturerName: "",
    dateOfweekFrame: "",
  },
];

/* 生徒科目登録*/
//学年に応じた科目一覧
export interface SubjectsByGrade {
  subjectKey: string;
  subjectName: string;
}
export type subjByGradeArray = Array<SubjectsByGrade>;
export const subjByGradeData = [{ subjectKey: "m3", subjectName: "数学IA" }];

//講師一覧
export interface LecturerWithStudentClassroom {
  lecturerId: string;
  lecturerName: string;
}
export type lecturerArray = Array<LecturerWithStudentClassroom>;
export const lecturerData = [{ lecturerId: "1", lecturerName: "講師" }];
//タイムテーブル一覧
export interface TimeTableNormal {
  timeTableId: string;
  dateOfWeekFrame: string;
}
export type timeTableNormalArray = Array<TimeTableNormal>;
export const timeTableNormalData = [
  { timeTableId: "1", dateOfWeekFrame: "月" },
];

/* */
export interface dateAndFrame {
  // We only use these three ones
  date: string;
  ids: number[];
}

export interface idAndCheckInfoIF {
  timeTableId: string;
  checkedFlg: boolean;
  notOperateFlg: boolean;
}

export interface currentCheckedStatusIF {
  date: string;
  idAndCheckInfo: idAndCheckInfoIF[];
}

export const dateFrameData = [
  {
    date: "",
    ids: [1, 2, 3, 4, 5, 6, 7],
  },
];

export const hopeShape = [
  {
    date: "2022/07/19",
    idAndCheckInfo: [
      { timeTableId: "1", checkedFlg: true, notOperateFlg: false },
      { timeTableId: "2", checkedFlg: true, notOperateFlg: false },
      { timeTableId: "3", checkedFlg: true, notOperateFlg: false },
      { timeTableId: "4", checkedFlg: true, notOperateFlg: false },
      { timeTableId: "5", checkedFlg: true, notOperateFlg: false },
      { timeTableId: "6", checkedFlg: true, notOperateFlg: false },
      { timeTableId: "7", checkedFlg: true, notOperateFlg: false },
    ],
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
export interface AllClassInfo {
  period: string;
  classes: Array<ClassInfo>;
}

export const classesAllData = [
  {
    period: "2",
    classes: [
      {
        id: "1",
        studentId: "1",
        lecturerName: "",
        timeTableSpecialId: "1",
        classDate: "2022/07/29",
        subject: "",
      },
    ],
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
