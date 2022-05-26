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