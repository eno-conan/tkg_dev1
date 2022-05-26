CREATE TABLE `time_table_normal` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  day_of_week CHAR(128) NOT null,
  period CHAR(128) NOT null
);

CREATE TABLE `time_table_special` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  day_of_week CHAR(128) NOT null,
  period CHAR(128) NOT null
);