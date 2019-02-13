DROP SCHEMA IF EXISTS `daaexample` ;
CREATE DATABASE `daaexample`;

CREATE TABLE `daaexample`.`people` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`surname` varchar(100) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `daaexample`.`users` (
	`login` varchar(100) NOT NULL,
	`password` varchar(64) NOT NULL,
	`role` varchar(10) NOT NULL,
	PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `daaexample`.`pet` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`breed` varchar(50) NOT NULL,
	`idOwner` int NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`idOwner`) REFERENCES `people`(`id`) 

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

GRANT ALL ON `daaexample`.* TO 'daa'@'localhost' IDENTIFIED BY 'daa';

INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Antón','Pérez');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Manuel','Martínez');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Laura','Reboredo');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Perico','Palotes');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Ana','María');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'María','Nuevo');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Alba','Fernández');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Asunción','Jiménez');

-- The password for each user is its login suffixed with "pass". For example, user "admin" has the password "adminpass".
INSERT INTO `daaexample`.`users` (`login`,`password`,`role`)
VALUES ('admin', '713bfda78870bf9d1b261f565286f85e97ee614efe5f0faf7c34e7ca4f65baca','ADMIN');
INSERT INTO `daaexample`.`users` (`login`,`password`,`role`)
VALUES ('normal', '7bf24d6ca2242430343ab7e3efb89559a47784eea1123be989c1b2fb2ef66e83','USER');

INSERT INTO `daaexample`.`pet` (`id`,`name`,`breed`,`idOwner`) VALUES (0,'Rex','Pastor Aleman',1);
INSERT INTO `daaexample`.`pet` (`id`,`name`,`breed`,`idOwner`) VALUES (0,'Idefix','Terrier',2);
INSERT INTO `daaexample`.`pet` (`id`,`name`,`breed`,`idOwner`) VALUES (0,'Snoopy','Beagle',3);