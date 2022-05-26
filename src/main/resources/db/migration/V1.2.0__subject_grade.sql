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

CREATE TABLE `lecturer_teach_subject` (
  lecturer_id INTEGER NOT null,
  subject_key CHAR(32) NOT null,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  UNIQUE(lecturer_id,subject_key),
  FOREIGN KEY (lecturer_id) REFERENCES m_lecturer(id),
  FOREIGN KEY (subject_key) REFERENCES m_subject(subject_key)
);
