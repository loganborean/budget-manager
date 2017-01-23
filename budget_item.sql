
use heroku_e2f822b1a6a96a9;

DROP TABLE IF EXISTS budget_item;
CREATE TABLE budget_item (
  id int(16) NOT NULL AUTO_INCREMENT,
  user_id int(16) NOT NULL,
  category_id int(16) NOT NULL,
  amount decimal(32,2) NOT NULL,
  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS category;
CREATE TABLE category (
  id int(16) NOT NULL AUTO_INCREMENT,
  user_id int(16) NOT NULL,
  name varchar(40) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS expense;
CREATE TABLE expense (
  id int(12) NOT NULL AUTO_INCREMENT,
  user_id int(12) NOT NULL,
  category_id int(12) NOT NULL,
  amount decimal(32,2) NOT NULL,
  note text,
  date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  username varchar(20) NOT NULL,
  password varchar(60) NOT NULL,
  id int(16) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

