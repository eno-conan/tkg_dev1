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

