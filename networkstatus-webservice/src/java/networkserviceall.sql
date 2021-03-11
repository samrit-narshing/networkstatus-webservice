-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.24-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema networkstatus_webservice
--

CREATE DATABASE IF NOT EXISTS networkstatus_webservice;
USE networkstatus_webservice;

--
-- Definition of table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`,`description`,`name`) VALUES 
 (1,'ADMINISTRATOR','ROLE_ADMINISTRATOR'),
 (2,'USER','ROLE_USER'),
 (3,'DEVICE','ROLE_DEVICE'),
 (4,'STUDENT','ROLE_STUDENT'),
 (5,'PARENT','ROLE_PARENT'),
 (6,'PROFESSOR','ROLE_PROFESSOR'),
 (7,'MOTORIST','ROLE_MOTORIST'),
 (8,'FRIEND','ROLE_FRIEND'),
 (9,'TRAVELER','ROLE_TRAVELER'),
 (10,'TRAVEL MANAGER','ROLE_TRAVEL_ADMINISTRATOR'),
 (11,'TRAVEL GUIDE','ROLE_TRAVEL_GUIDE'),
 (12,'SCHOOL MANAGER','ROLE_SCHOOL_ADMINISTRATOR'),
 (13,'SCHOOL USER','ROLE_SCHOOL_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_type` varchar(30) NOT NULL,
  `id` bigint(20) NOT NULL,
  `account_expiration` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `admin_operator_user_type` varchar(255) DEFAULT NULL,
  `admin_operator_username` varchar(255) DEFAULT NULL,
  `admin_user_type` varchar(255) DEFAULT NULL,
  `admin_username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `entryby_user_type` varchar(255) DEFAULT NULL,
  `entryby_username` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_modified_unix_time` bigint(20) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `last_updateby_user_type` varchar(255) DEFAULT NULL,
  `last_updateby_username` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `never_expire` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_expire` bit(1) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `session_timeout` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_type`,`id`,`account_expiration`,`address1`,`address2`,`admin_operator_user_type`,`admin_operator_username`,`admin_user_type`,`admin_username`,`email`,`enabled`,`entryby_user_type`,`entryby_username`,`first_name`,`gender`,`last_modified_unix_time`,`last_name`,`last_updateby_user_type`,`last_updateby_username`,`middle_name`,`mobile_number`,`never_expire`,`password`,`password_expire`,`phone_number`,`profile_image`,`session_timeout`,`username`) VALUES 
 ('SYSTEM_USER',1,'2020-07-06','Anonymous','Anonymous',NULL,NULL,NULL,NULL,'seemsserver.siamsecure@gmail.com',0x01,NULL,NULL,'Anonymous','person-male',1532363246,'Anonymous',NULL,NULL,'Anonymous','12345678',0x01,'f3992e5a0b9499e29161de243a8f6f39974ba911',0x00,'87654321',NULL,'28800','super-user'),
 ('SYSTEM_USER',2,'2018-12-24','Kha 18/89  Patan , Mangal Bazar , Lalitpur','','NO_OPERATOR_USER_TYPE','NO_OPERATOR_USER','SYSTEM_USER','sam','samrit_narshing@hotmail.com',0x01,'SYSTEM_USER','super-user','Samrit','person-male',1532407556,'Amatya','SYSTEM_USER','super-user','Narshing','9841249759',0x01,'895ddf8674f3613793581f3754b0eddc549b34d7',0x00,'9841249759','sam.jpg','28800','sam'),
 ('SYSTEM_USER',3,'2018-07-24','Anonymous','','NO_OPERATOR_USER_TYPE','NO_OPERATOR_USER','SYSTEM_USER','admin','samrit_narshing@hotmail.com',0x01,'SYSTEM_USER','super-user','Admin','person-male',1532407798,'Admin','SYSTEM_USER','super-user','','123456789',0x01,'d033e22ae348aeb5660fc2140aec35850c4da997',0x00,'123456789','admin.jpg','28800','admin'),
 ('SYSTEM_USER',4,'2018-07-24','Bangkok , Thailand','','NO_OPERATOR_USER_TYPE','NO_OPERATOR_USER','SYSTEM_USER','porntiva','porntivaj@gmail.com',0x01,'SYSTEM_USER','super-user','Porntiva','person-male',1532408025,'Sir','SYSTEM_USER','super-user','','0860440880',0x01,'a27672de735bc9918bd168311bb70dc49e43bbec',0x01,'66860440880','porntiva.jpg','28800','porntiva');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`,`role_id`) VALUES 
 (1,1),
 (2,1),
 (3,1),
 (4,1),
 (1,2),
 (2,2),
 (3,2),
 (4,2),
 (1,3),
 (2,3),
 (3,3),
 (4,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
