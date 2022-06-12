-- エリア
CREATE TABLE `m_area` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  area_name CHAR(128) NOT null,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

-- 都道府県
CREATE TABLE `m_prefecture` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  area_id INTEGER not NULL,
  prefecture_name CHAR(128) not NULL,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (area_id) REFERENCES m_area(id)
);

-- 教室
CREATE TABLE `m_classroom` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  prefecture_id INTEGER not NULL,
  classroom_name CHAR(128) not NULL,
  address VARCHAR(256) not NULL,
  delete_flg INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(classroom_name),
  FOREIGN KEY (prefecture_id) REFERENCES m_prefecture(id)
);

-- 社員
CREATE TABLE `m_employee` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  classroom_id INTEGER NOT null,
  name CHAR(128) NOT null,
  birthday DATE NOT null,
  delete_flg int default 0,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (classroom_id) REFERENCES m_classroom(id)
);

-- 室長
CREATE TABLE `classroom_director` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  classroom_id INTEGER NOT null,
  employee_id INTEGER NOT null,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  UNIQUE(classroom_id,employee_id),
  FOREIGN KEY (classroom_id) REFERENCES m_classroom(id),
  FOREIGN KEY (employee_id) REFERENCES m_employee(id)
);

-- 講師
CREATE TABLE `m_lecturer` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  classroom_id INTEGER NOT null,
  name CHAR(128) not NULL,
  birthday DATE NOT null,
  delete_flg INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (classroom_id) REFERENCES m_classroom(id)
);

-- 学年
CREATE TABLE `m_grade` (
  grade_key CHAR(32) PRIMARY KEY,
  grade_division CHAR(32) NOT null,
  display_name CHAR(32) NOT null
);

-- 科目
CREATE TABLE `m_subject` (
  subject_key CHAR(32) PRIMARY KEY,
  subject_division CHAR(32) NOT null,
  display_name CHAR(32) NOT null,
  delete_flg INTEGER default 0,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

-- 科目対象学年
CREATE TABLE `m_subject_target_grade` (
  grade_key CHAR(32),
  subject_key CHAR(32),
  UNIQUE(grade_key,subject_key),
  FOREIGN KEY (grade_key) REFERENCES m_grade(grade_key),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key)
);

-- 生徒
CREATE TABLE `m_student` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  classroom_id INTEGER NOT null,
  grade_key CHAR(32) NOT null,
  name CHAR(128) NOT null,
  birthday DATE NOT null,
  delete_flg int default 0,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (classroom_id) REFERENCES m_classroom(id),
  FOREIGN KEY (grade_key) REFERENCES m_grade(grade_key)
);

-- 通常期間コマ管理
CREATE TABLE `time_table_normal` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  day_of_week CHAR(8) NOT null,
  period CHAR(8) NOT null
);

-- 講習期間コマ管理
CREATE TABLE `time_table_special` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  day_count CHAR(8) NOT null,
  period CHAR(8) NOT null
);

-- 通常期間スケジュール
CREATE TABLE `student_schedule_normal` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  lecturer_id INTEGER NOT null,
  time_table_normal_id INTEGER NOT null,
  class_date DATE not null,
  class_date_origin DATE,
  reschedule_date_start DATE not null,
  reschedule_date_last DATE not null,
  reschedule_flg INTEGER default 0,
  status INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_normal_id) REFERENCES time_table_normal(id)
);

-- 講師指導科目
CREATE TABLE `lecturer_teach_subject` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  lecturer_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  classroom_id INTEGER NOT null,
  teach_flg int default 0,
  comment VARCHAR(64) NOT null,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(lecturer_id,subject_key),
  FOREIGN KEY (classroom_id) REFERENCES m_classroom(id),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key)
);

-- 生徒受講科目
CREATE TABLE `student_subject` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  assigned_lecturer_id INTEGER NOT null,
  time_table_normal_id INTEGER NOT null,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(student_id,subject_key),
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key),
  FOREIGN KEY (assigned_lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_normal_id) REFERENCES time_table_normal(id)
);

-- 講師出勤可能時間
CREATE TABLE `lecturer_workable_time` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  classroom_id INTEGER NOT null,
  lecturer_id INTEGER NOT null,
  time_table_normal_id INTEGER NOT null,
  workable_flg int default 0,
  UNIQUE(classroom_id,lecturer_id,time_table_normal_id),
  FOREIGN KEY (classroom_id) REFERENCES m_student(id),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_normal_id) REFERENCES time_table_normal(id)
);

-- 通常期間講師スケジュール
CREATE TABLE `lecturer_schedule_normal` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  lecturer_id INTEGER NOT null,
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  time_table_normal_id INTEGER NOT null,
  class_date DATE not null,
  reschedule_flg INTEGER default 0,
  status INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(lecturer_id,student_id,time_table_normal_id,class_date),
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_normal_id) REFERENCES time_table_normal(id)
);

-- 講習期間講師スケジュール
CREATE TABLE `lecturer_schedule_special` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  lecturer_id INTEGER NOT null,
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  time_table_special_id INTEGER NOT null,
  class_date DATE not null,
  reschedule_flg INTEGER default 0,
  status INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(lecturer_id,student_id,time_table_special_id,class_date),
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_special_id) REFERENCES time_table_special(id)
);

-- 講習季節
CREATE TABLE `special_season` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  year CHAR(16) NOT null,
  season_name CHAR(16) NOT null,
  UNIQUE(year,season_name)
);

-- 夏期講習概要
CREATE TABLE `student_class_special_summary` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  special_season_id INTEGER NOT null,--summer:1,winter:2,spring:3
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(special_season_id,student_id,subject_key),
  FOREIGN KEY (special_season_id) REFERENCES special_season(id),
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key)
);

-- 講習期間スケジュール
CREATE TABLE `student_schedule_special` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  lecturer_id INTEGER NOT null,
  time_table_special_id INTEGER NOT null,
  class_date DATE not null,
  class_date_origin DATE,
  reschedule_date_start DATE not null,
  reschedule_date_last DATE not null,
  reschedule_flg INTEGER default 0,
  status INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_special_id) REFERENCES time_table_special(id)
);

-- ポータルユーザ
CREATE TABLE `portal_user` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  classroom_id INTEGER NOT null,
  employee_id INTEGER NOT null,
  username CHAR(128) NOT null,
  password CHAR(128) NOT null,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(username),
  UNIQUE(password),
  FOREIGN KEY (classroom_id) REFERENCES m_classroom(id),
  FOREIGN KEY (employee_id) REFERENCES m_employee(id)
);

