/* API URL*/
export const API_BASE_URL = "http://localhost:8080/tkg";
export const API_STUDENT = {
  //GET
  PrepareRegistClassroom: API_BASE_URL + "/student/regist-prepare-classroom", //新規登録に必要な教室取得
  PrepareRegistGrade: API_BASE_URL + "/student/regist-prepare-grade", //新規登録に必要なデータ取得
  SpecialSummary: API_BASE_URL + "/student/special-summary",
  SpecialDateList: API_BASE_URL + "/student/special-date-list",
  SpecialSchedule: API_BASE_URL + "/student/special-schedule",
  //POST
  RegistStudent: API_BASE_URL + "/student/regist",
  //PUT
  UpdateSpecialSchedule: API_BASE_URL + "/student/update-special-schedule", //講習スケジュール更新
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
  UpdateSpecialSchedule:
    PASS_MAIN_FUNCTION.Student + "/update-student-schedule", //講習スケジュール更新
};
