CREATE DATABASE IF NOT EXISTS db_myquiz;
USE db_myquiz;

DROP TABLE IF EXISTS User;

CREATE TABLE IF NOT EXISTS User (
	user_id int auto_increment PRIMARY KEY,
    username varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    firstname varchar(20) NOT NULL,
    lastname varchar(20) NOT NULL,
    address varchar(50),
    email varchar(30),
    phone varchar(20),
    status boolean NOT NULL,
    is_admin boolean NOT NULL
);


DROP TABLE IF EXISTS Contact;

CREATE TABLE IF NOT EXISTS Contact (
	contact_id int auto_increment PRIMARY KEY,
    user_id int,
    subject varchar(20) NOT NULL,
    message varchar(200) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES User(user_id)
);

DROP TABLE IF EXISTS Feedback;

CREATE TABLE IF NOT EXISTS Feedback (
	feedback_id int auto_increment PRIMARY KEY,
    user_id int,
    rating int NOT NULL,
    content varchar(200) NOT NULL,
    date timestamp NOT NULL,
	FOREIGN KEY (user_id) REFERENCES User(user_id)
);

DROP TABLE IF EXISTS Category;

CREATE TABLE IF NOT EXISTS Category (
	category_id int auto_increment PRIMARY KEY,
    name varchar(20) NOT NULL
);


DROP TABLE IF EXISTS Quiz;

CREATE TABLE IF NOT EXISTS Quiz (
	quiz_id int auto_increment PRIMARY KEY,
    category_id int,
    name varchar(20) NOT NULL,
	FOREIGN KEY (category_id) REFERENCES Category(category_id)
);


DROP TABLE IF EXISTS Question;

CREATE TABLE IF NOT EXISTS Question (
	question_id int auto_increment PRIMARY KEY,
    quiz_id int,
    content varchar(100) NOT NULL,
    status boolean NOT NULL,
	FOREIGN KEY (quiz_id) REFERENCES Quiz(quiz_id)
);

DROP TABLE IF EXISTS QuizRecord;

CREATE TABLE IF NOT EXISTS QuizRecord (
	record_id int auto_increment PRIMARY KEY,
    quiz_id int,
    user_id int,
    taken_date timestamp NOT NULL,
    score int NOT NULL,
	FOREIGN KEY (quiz_id) REFERENCES Quiz(quiz_id),
	FOREIGN KEY (user_id) REFERENCES User(user_id)
);


DROP TABLE IF EXISTS Options;

CREATE TABLE IF NOT EXISTS Options (
	option_id int auto_increment PRIMARY KEY,
    question_id int,
    content varchar(50) NOT NULL,
    is_solution boolean NOT NULL,
	FOREIGN KEY (question_id) REFERENCES Question(question_id)
);


DROP TABLE IF EXISTS QuestionRecord;

CREATE TABLE IF NOT EXISTS QuestionRecord (
	qr_id int auto_increment PRIMARY KEY,
	record_id int,
    question_id int,
    option_id int,
	FOREIGN KEY (record_id) REFERENCES QuizRecord(record_id),
	FOREIGN KEY (question_id) REFERENCES Question(question_id),
	FOREIGN KEY (option_id) REFERENCES Options(option_id)
);


DROP TABLE IF EXISTS ShortAnswer;

CREATE TABLE IF NOT EXISTS ShortAnswer (
	record_id int NOT NULL,
    question_id int NOT NULL,
    answer varchar(200) NOT NULL,
    FOREIGN KEY (record_id) REFERENCES QuizRecord(record_id),
	FOREIGN KEY (question_id) REFERENCES Question(question_id)
);

DROP TABLE IF EXISTS Permission;

CREATE TABLE IF NOT EXISTS Permission (
	permission_id int auto_increment PRIMARY KEY,
    name varchar(20) NOT NULL
);


DROP TABLE IF EXISTS UserPermission;

CREATE TABLE IF NOT EXISTS UserPermission (
	id int auto_increment PRIMARY KEY,
    user_id int,
    permission_id int,
    FOREIGN KEY(user_id) REFERENCES User(user_id),
    FOREIGN KEY(permission_id) REFERENCES Permission(permission_id)
);