/* API URL*/
export const API_BASE_URL = "http://localhost:8080/tkg";

export const API_STUDENT = {
  //GET
  SpecialSummary: API_BASE_URL + "/student/special-summary",
  SpecialDateList: API_BASE_URL + "/student/special-date-list",
  SpecialSchedule: API_BASE_URL + "/student/special-schedule",
  //PUT
  UpdateSpecialSchedule: API_BASE_URL + "/student/update-special-schedule",
};

//Routing
export const PASS_MAIN_FUNCTION = {
  Top: "/tkg",
  ClassSchedule: "/tkg/classSchedule",
  Student: "/tkg/student",
};

export const STUDENT_FUNCTION = {
  Regist: "/tkg/student/regist",
  UpdateSpecialSchedule:
    PASS_MAIN_FUNCTION.Student + "/update-student-schedule",
};
