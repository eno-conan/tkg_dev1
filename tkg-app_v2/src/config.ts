/* API URL*/
export const API_BASE_URL = "http://localhost:8080/tkg";
export const API_STUDENT = {
  //GET
  PrepareRegistClassroom: API_BASE_URL + "/student/regist-prepare-classroom", //新規登録に必要な教室取得
  PrepareRegistGrade: API_BASE_URL + "/student/regist-prepare-grade", //新規登録に必要なデータ取得
  SearchStudent: API_BASE_URL + "/student/search", //生徒検索
  SearchSubject: API_BASE_URL + "/student/search-subject", //現在の受講科目確認
  PrepareSubjectList: API_BASE_URL + "/student/regist-subject-prepare-grade", //対象生徒が受講できる科目一覧取得
  PrepareLecutererList:
    API_BASE_URL + "/student/regist-subject-prepare-lecturer", //講師一覧取得（生徒が在籍する教室の講師を先頭に表示）
  PrepareTimeTableNormal:
    API_BASE_URL + "/student/regist-subject-prepare-timetable", //タイムテーブル（通常）の取得
  SpecialSummary: API_BASE_URL + "/student/summary-special", //講習会スケジュール概要
  SpecialDateList: API_BASE_URL + "/student/date-list-special", //講習会日程情報
  SpecialAttendance: API_BASE_URL + "/student/attendance-special", //講習会出欠情報
  SpecialSchedule: API_BASE_URL + "/student/schedule-special", //講習スケジュール表示更新
  WholeSchedule: API_BASE_URL + "/student/schedule-whole", //スケジュール（通常・講習セット）
  //POST
  RegistStudent: API_BASE_URL + "/student/regist", //生徒登録
  RegistSubject: API_BASE_URL + "/student/regist-subject", //生徒科目登録
};

/* フロント */
//Routing
export const PASS_MAIN_FUNCTION = {
  Top: "/tkg",
  ClassSchedule: "/tkg/classSchedule",
  Student: "/tkg/student",
};

export const STUDENT_FUNCTION = {
  PrepareRegistClassroom: "/student/regist-prepare-classroom", //新規登録に必要なデータ取得
  PrepareRegistGrade: "/student/regist-prepare-grade", //新規登録に必要なデータ取得
  Regist: "/tkg/student/regist", //新規登録
  SearchStudent: "/tkg/student/search", //検索
  ShowSchedule: "/tkg/student/showSchedule", //授業予定
  ShowSubject: "/tkg/student/showSubject", //受講科目一覧
  InputSpecialAttendance: "/tkg/student/attendance-special", //受講科目登録
  RegistSubject: "/tkg/student/registSubject", //受講科目登録
  UpdateSpecialSchedule:
    PASS_MAIN_FUNCTION.Student + "/update-student-schedule", //講習スケジュール更新
};
