CREATE TABLE `m_area` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  area_name CHAR(128) NOT null,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

CREATE TABLE `m_prefecture` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  area_id INTEGER not NULL,
  prefecture_name CHAR(128) not NULL,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (area_id) REFERENCES m_area(id)
);

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

CREATE TABLE `m_grade` (
  grade_key CHAR(32) PRIMARY KEY,
  grade_division CHAR(32) NOT null,
  display_name CHAR(32) NOT null
);

CREATE TABLE `m_subject` (
  subject_key CHAR(32) PRIMARY KEY,
  subject_division CHAR(32) NOT null,
  display_name CHAR(32) NOT null,
  delete_flg INTEGER default 0,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);

CREATE TABLE `m_subject_target_grade` (
  grade_key CHAR(32),
  subject_key CHAR(32),
  UNIQUE(grade_key,subject_key),
  FOREIGN KEY (grade_key) REFERENCES m_grade(grade_key),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key)
);

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

CREATE TABLE `time_table_normal` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  day_of_week CHAR(8) NOT null,
  period CHAR(8) NOT null,
  day_of_week_ja CHAR(16) NOT null
);

CREATE TABLE `special_season` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  year CHAR(16) NOT null,
  season_name CHAR(16) NOT null,
  UNIQUE(year,season_name)
);

CREATE TABLE `special_season_date_list` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  special_season_id INTEGER NOT null,
  class_date DATE not null,
  UNIQUE(special_season_id,class_date),
  FOREIGN KEY (special_season_id) REFERENCES special_season(id)
);

CREATE TABLE `time_table_special` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  special_season_date_list_id INTEGER NOT null,
  period CHAR(8) NOT null,
  FOREIGN KEY (special_season_date_list_id) REFERENCES special_season_date_list(id)
);

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

CREATE TABLE `lecturer_teach_subject` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  lecturer_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  teach_flg int default 0,
  comment VARCHAR(64) NOT null,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(lecturer_id,subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key)
);

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


CREATE TABLE `lecturer_workable_time` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  lecturer_id INTEGER NOT null,
  time_table_normal_id INTEGER NOT null,
  workable_flg int default 0,
  UNIQUE(lecturer_id,time_table_normal_id),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_normal_id) REFERENCES time_table_normal(id)
);

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

CREATE TABLE `student_class_special_summary` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  special_season_id INTEGER NOT null,
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  total_class_count INTEGER not null,
  unplace_class_count INTEGER not null,
  created_at timestamp,
  updated_at timestamp,
  UNIQUE(special_season_id,student_id,subject_key),
  FOREIGN KEY (special_season_id) REFERENCES special_season(id),
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key)
);

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
  UNIQUE(student_id,time_table_special_id,class_date),
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (time_table_special_id) REFERENCES time_table_special(id)
);

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

