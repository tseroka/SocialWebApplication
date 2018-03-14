SET GLOBAL time_zone = '+1:00';

CREATE TABLE profiles
(
nickname varchar(25) NOT NULL UNIQUE,
sex varchar(1) NOT NULL DEFAULT '' ,
interests varchar(200) NOT NULL DEFAULT '',
city varchar(50) NOT NULL DEFAULT '',
allowSearching tinyint(1) NOT NULL DEFAULT 0,
allowEveryoneMessage tinyint(1) NOT NULL DEFAULT 0,
PRIMARY KEY (nickname)
);


CREATE TABLE users
( user_id int(12) NOT NULL UNIQUE auto_increment,
  username varchar(25) NOT NULL UNIQUE ,
  password varchar(100) NOT NULL,
  email varchar(40) NOT NULL UNIQUE,
  nickname varchar(25) NOT NULL UNIQUE,
  country varchar(3) NULL,
  role varchar(25) NOT NULL DEFAULT 'ROLE_USER' ,
  enabled tinyint(1) NOT NULL DEFAULT 0,
  notLocked tinyint(1) NOT NULL DEFAULT 1,
  creationDate timestamp NOT NULL,
  PRIMARY KEY (user_id),
   FOREIGN KEY (nickname)
        REFERENCES profiles(nickname) ON DELETE CASCADE
);



CREATE TABLE privateMessages
(
  message_id bigint(22) UNIQUE NOT NULL auto_increment,
  sender varchar(25) NOT NULL,
  recipient varchar(270) NOT NULL,
  subject varchar(100) NOT NULL,
  text varchar(2000) NOT NULL DEFAULT '',
  sentDate timestamp NOT NULL,
  anyAttachment tinyint(1) NOT NULL DEFAULT 0,
  anyoneRemoved tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (message_id)
);

CREATE TABLE Attachments 
(attachment_id bigint(22) UNIQUE NOT NULL PRIMARY KEY auto_increment, 
fileName varchar(100) NOT NULL,
fileType varchar(100) NOT NULL,
fileContent LONGBLOB NOT NULL,
fileSize bigint(15) NOT NULL,
message_id bigint(22) NOT NULL,
FOREIGN KEY (message_id)
REFERENCES privateMessages(message_id));


CREATE TABLE friends
(
nickname varchar(25) UNIQUE NOT NULL PRIMARY KEY,
friends varchar(4000) NOT NULL DEFAULT '',
invitationsSent varchar(4000) NOT NULL DEFAULT '',
invitationsReceived varchar(4000) NOT NULL DEFAULT '',
FOREIGN KEY (nickname)
REFERENCES profiles(nickname)
);



CREATE TABLE securityIssues
(
 user_id int(12) NOT NULL auto_increment,
 username varchar(25) UNIQUE NOT NULL,
 activationCode varchar(20) UNIQUE  NULL,
 unlockCode varchar(20) UNIQUE NULL DEFAULT NULL,
 resetPasswordCode varchar(30) UNIQUE NULL DEFAULT NULL,
 codeExpirationDate timestamp NULL DEFAULT NULL,
 numberOfLoginFails tinyint(1) DEFAULT 0 CHECK(numberOfLoginFails BETWEEN 0 AND 5),
 lockReason varchar(300) NULL DEFAULT NULL,
 unlockDate timestamp NULL DEFAULT NULL,
 PRIMARY KEY(user_id),
 FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE shortLinks
(
  id bigint(12) NOT NULL auto_increment PRIMARY KEY,
  url varchar(300) NOT NULL,
  shortened_url varchar(5) NOT NULL UNIQUE
);
