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

CREATE TABLE `student_schedule_normal` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  student_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  lecturer_id INTEGER NOT null,
  class_date DATE not null,
  reschedule_date_start DATE not null,
  reschedule_date_last DATE not null,
  reschedule_flg INTEGER default 0,
  status INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (student_id) REFERENCES m_student(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id)
);

