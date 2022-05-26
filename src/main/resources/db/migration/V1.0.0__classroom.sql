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
  delete_flg INTEGER default 0,
  created_at timestamp,
  updated_at timestamp,
  FOREIGN KEY (prefecture_id) REFERENCES m_prefecture(id)
);