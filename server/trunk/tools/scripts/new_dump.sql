-- MySQL dump 10.13  Distrib 5.5.28, for Win64 (x86)
--
-- Host: 127.0.0.1    Database: rms
-- ------------------------------------------------------
-- Server version	5.5.28-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access_history`
--

DROP TABLE IF EXISTS `access_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access_history` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `ACCESS_DATE` datetime NOT NULL,
  `AUTHENTICATION_STATUS` varchar(255) NOT NULL,
  `USER_IP` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_history`
--

LOCK TABLES `access_history` WRITE;
/*!40000 ALTER TABLE `access_history` DISABLE KEYS */;
INSERT INTO `access_history` VALUES ('1','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'2012-12-09 12:00:00','AUTHENTICATION_OK','127.0.0.1','admin',NULL,NULL);
/*!40000 ALTER TABLE `access_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address_data`
--

DROP TABLE IF EXISTS `address_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address_data` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `CITY` varchar(50) DEFAULT NULL,
  `COUNTRY` varchar(50) DEFAULT NULL,
  `FIRST_NAME` varchar(250) DEFAULT NULL,
  `HOUSE_NUMBER` varchar(10) DEFAULT NULL,
  `LAST_NAME` varchar(250) DEFAULT NULL,
  `MIDDLE_NAME` varchar(250) DEFAULT NULL,
  `STREET` varchar(250) DEFAULT NULL,
  `STREET_NUMBER` varchar(10) DEFAULT NULL,
  `TITLE` varchar(250) DEFAULT NULL,
  `ZIP_CODE` varchar(10) DEFAULT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_data`
--

LOCK TABLES `address_data` WRITE;
/*!40000 ALTER TABLE `address_data` DISABLE KEYS */;
INSERT INTO `address_data` VALUES ('1','2012-12-09 12:00:00','2012-12-09 12:00:00',1,NULL,'','PL',NULL,'1',NULL,NULL,'B','2',NULL,'11-111',NULL,NULL),('10','2012-12-12 12:00:00','2012-12-12 12:00:00',1,NULL,'Łódź','PL','Jan',NULL,'Kowalski',NULL,NULL,NULL,NULL,NULL,'1','1'),('11','2012-12-12 12:00:00','2012-12-12 12:00:00',1,NULL,'Łódź','PL',NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,'1','1'),('12','2012-12-12 12:00:00','2012-12-12 12:00:00',2,NULL,'Warszawa','PL','Michał',NULL,'Jaworski',NULL,'Sejmowa','3',NULL,'55-412','1','1'),('13','2012-12-12 12:00:00','2012-12-12 12:00:00',1,NULL,'Miśkowice','PL',NULL,'79',NULL,NULL,NULL,NULL,NULL,'33-412','1','1'),('14','2012-12-12 12:00:00','2012-12-12 12:00:00',2,NULL,NULL,NULL,'Janek',NULL,'Staszewski',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('2','2012-12-09 12:00:00','2012-12-09 12:00:00',1,NULL,'Warszawa','PL',NULL,'2',NULL,NULL,'Pi','4',NULL,'22-234','1','1'),('3','2012-12-09 12:00:00','2012-12-09 12:00:00',2,NULL,'','PL',NULL,'6',NULL,NULL,'Sierotki Marysi','7',NULL,'65-783','1','1'),('4','2012-12-09 12:00:00','2012-12-09 12:00:00',1,NULL,'Kraków','PL',NULL,'65',NULL,NULL,'Rzgowska','11',NULL,'92-564','1','1'),('5','2012-12-09 12:00:00','2012-12-09 12:00:00',1,NULL,'Lublin','PL',NULL,'12',NULL,NULL,'Jutrzenki','34',NULL,'85-521','1','1');
/*!40000 ALTER TABLE `address_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address_data_contacts`
--

DROP TABLE IF EXISTS `address_data_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address_data_contacts` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `TYPE` varchar(255) NOT NULL,
  `VALUE` varchar(250) NOT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  `ADDRESS_DATA` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK6826501D44184EBA` (`ADDRESS_DATA`),
  CONSTRAINT `FK6826501D44184EBA` FOREIGN KEY (`ADDRESS_DATA`) REFERENCES `address_data` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_data_contacts`
--

LOCK TABLES `address_data_contacts` WRITE;
/*!40000 ALTER TABLE `address_data_contacts` DISABLE KEYS */;
INSERT INTO `address_data_contacts` VALUES ('1','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'EMAIL','gogo@gmail.com',NULL,NULL,'1'),('10','2012-12-09 12:00:00','2012-12-09 12:00:00',2,'FACEBOOK','jackie.jackie@facebook.com','1','1','5'),('11','2012-12-12 12:00:00','2012-12-12 12:00:00',1,'PHONE','+48 663 666 555','1','1','14'),('2','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'PHONE','555-555-555',NULL,NULL,'1'),('3','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'FACEBOOK','gogo@gmail.com',NULL,NULL,'1'),('4','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'PHONE','444-444-444','1','1','2'),('5','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'FAX','444-444-333','1','1','2'),('6','2012-12-09 12:00:00','2012-12-09 12:00:00',2,'SKYPE','jackie@wp.pl','1','1','2'),('7','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'EMAIL','misiek@onet.pl','1','1','3'),('8','2012-12-09 12:00:00','2012-12-09 12:00:00',3,'EMAIL','outlander@gmail.com','1','1','4'),('9','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'PHONE','+48-699-666-444','1','1','5');
/*!40000 ALTER TABLE `address_data_contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c3p0_test_table`
--

DROP TABLE IF EXISTS `c3p0_test_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c3p0_test_table` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c3p0_test_table`
--

LOCK TABLES `c3p0_test_table` WRITE;
/*!40000 ALTER TABLE `c3p0_test_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `c3p0_test_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devices` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `DELETED` tinyint(1) NOT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(250) NOT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES ('1','2012-12-09 12:00:00','2012-12-09 12:00:00',1,0,'Dyktafon Sony DCT-H32, ','Dyktafon Sony DCT-H32','1','1'),('2','2012-12-09 12:00:00','2012-12-09 12:00:00',1,0,'Aparat Nikon D800, ','Aparat Nikon D800','1','1'),('3','2012-12-09 12:00:00','2012-12-09 12:00:00',1,1,'Aparat Zenith, pokrowiec','Aparat Zenith','1','1'),('4','2012-12-09 12:00:00','2012-12-09 12:00:00',2,0,'Dyktafon Panasonic AX322, pokrowiec','Dyktafon Panasonic AX322','1','1'),('5','2012-12-09 12:00:00','2012-12-09 12:00:00',1,0,'Kamera LG GH-332-A, ','Kamera LG GH-332-A','1','1'),('6','2012-12-09 12:00:00','2012-12-09 12:00:00',4,1,'Kamera VHS Philips SHA-888, ','Kamera VHS Philips SHA-888','1','1');
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `ARCHIVED` tinyint(1) NOT NULL,
  `DELETED` tinyint(1) NOT NULL,
  `DESCRIPTION` longtext,
  `END_DATE` datetime NOT NULL,
  `LOCKED` tinyint(1) NOT NULL,
  `START_DATE` datetime NOT NULL,
  `TITLE` varchar(250) NOT NULL,
  `TYPE` varchar(255) NOT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  `ADDRESS` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `ADDRESS` (`ADDRESS`),
  KEY `FK7A9AD519EFF68599` (`ADDRESS`),
  CONSTRAINT `FK7A9AD519EFF68599` FOREIGN KEY (`ADDRESS`) REFERENCES `address_data` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES ('1','2012-12-12 12:00:00','2012-12-12 12:00:00',1,0,0,'Wywiad z jakimś ziomkiem.','2012-12-12 18:00:00',0,'2012-12-12 12:00:00','Wywiad z Galerii Łódzkiej','INTERVIEW','1','1','10'),('2','2012-12-12 12:00:00','2012-12-12 12:00:00',1,0,0,'Spotkanie z zespołem Happysad','2012-12-13 20:00:00',0,'2012-12-13 10:00:00','Spotkanie z Happysad','MEETING','1','1','11'),('3','2012-12-12 12:00:00','2012-12-12 12:00:00',1,1,0,'Wywiad z Lady Pank','2012-12-10 12:00:00',0,'2012-12-10 10:00:00','Wywiad z Lady Pank','INTERVIEW','1','1','12'),('4','2012-12-12 12:00:00','2012-12-12 12:00:00',1,0,1,'Spotkanie z Metallica. Pamiętać: po koncercie i innych wywiadach.','2013-02-01 21:00:00',1,'2013-02-02 02:00:00','Wywiad z Metallica','INTERVIEW','1','1','13'),('5','2012-12-12 12:00:00','2012-12-12 12:00:00',2,0,0,'Pamiętać o omówieniu finansów','2013-01-12 12:00:00',0,'2013-01-12 15:00:00','Spotkanie ze Staszkiem','MEETING','1','1','14');
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_devices_link`
--

DROP TABLE IF EXISTS `events_devices_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events_devices_link` (
  `EVENT_ID` varchar(255) NOT NULL,
  `DEVICE_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`EVENT_ID`,`DEVICE_ID`),
  KEY `FKB3B07BA2202EBD61` (`EVENT_ID`),
  KEY `FKB3B07BA2BE7B1899` (`DEVICE_ID`),
  CONSTRAINT `FKB3B07BA2BE7B1899` FOREIGN KEY (`DEVICE_ID`) REFERENCES `devices` (`ID`),
  CONSTRAINT `FKB3B07BA2202EBD61` FOREIGN KEY (`EVENT_ID`) REFERENCES `events` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_devices_link`
--

LOCK TABLES `events_devices_link` WRITE;
/*!40000 ALTER TABLE `events_devices_link` DISABLE KEYS */;
INSERT INTO `events_devices_link` VALUES ('1','1'),('2','5'),('3','2'),('3','5'),('4','2'),('4','4'),('4','5');
/*!40000 ALTER TABLE `events_devices_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_users_link`
--

DROP TABLE IF EXISTS `events_users_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events_users_link` (
  `EVENT_ID` varchar(255) NOT NULL,
  `USER_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`EVENT_ID`,`USER_ID`),
  KEY `FK43F8C2D7202EBD61` (`EVENT_ID`),
  KEY `FK43F8C2D7473F93B9` (`USER_ID`),
  CONSTRAINT `FK43F8C2D7473F93B9` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`ID`),
  CONSTRAINT `FK43F8C2D7202EBD61` FOREIGN KEY (`EVENT_ID`) REFERENCES `events` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_users_link`
--

LOCK TABLES `events_users_link` WRITE;
/*!40000 ALTER TABLE `events_users_link` DISABLE KEYS */;
INSERT INTO `events_users_link` VALUES ('1','1'),('2','2'),('3','1'),('3','2'),('4','1'),('5','4');
/*!40000 ALTER TABLE `events_users_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_recipents`
--

DROP TABLE IF EXISTS `message_recipents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_recipents` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `ARCHIVED_BY_RECIPENT` tinyint(1) NOT NULL,
  `DELETED_BY_RECIPENT` tinyint(1) NOT NULL,
  `READ_DATE` datetime DEFAULT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  `RECIPENT` varchar(255) NOT NULL,
  `MESSAGE` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FKBDDAE4A747C43B35` (`MESSAGE`),
  KEY `FKBDDAE4A7200BDD7E` (`RECIPENT`),
  CONSTRAINT `FKBDDAE4A7200BDD7E` FOREIGN KEY (`RECIPENT`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKBDDAE4A747C43B35` FOREIGN KEY (`MESSAGE`) REFERENCES `messages` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_recipents`
--

LOCK TABLES `message_recipents` WRITE;
/*!40000 ALTER TABLE `message_recipents` DISABLE KEYS */;
INSERT INTO `message_recipents` VALUES ('1','2012-12-12 12:00:00','2012-12-12 12:00:00',1,0,1,'2012-12-12 16:00:00','1','3','3','1'),('2','2012-12-12 12:00:00','2012-12-12 12:00:00',1,1,0,'2012-12-12 10:00:00','1','2','2','2'),('3','2012-12-12 12:00:00','2012-12-12 12:00:00',1,0,0,'2012-12-12 12:00:00','1','2','2','3'),('4','2012-12-12 12:00:00','2012-12-12 12:00:00',1,0,0,'2012-12-12 11:00:00','1','5','5','3');
/*!40000 ALTER TABLE `message_recipents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `ARCHIVED_BY_SENDER` tinyint(1) NOT NULL,
  `CONTENT` longtext NOT NULL,
  `DATE_SENT` datetime NOT NULL,
  `DELETED_BY_SENDER` tinyint(1) NOT NULL,
  `SUBJECT` varchar(250) NOT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  `SENDER` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK131AF14C1A04BFDF` (`SENDER`),
  CONSTRAINT `FK131AF14C1A04BFDF` FOREIGN KEY (`SENDER`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES ('1','2012-12-12 12:00:00','2012-12-12 12:00:00',1,0,'Twoje konto zostanie zablokowane jeżeli nie zaczniesz zachowywać się normalnie.','2012-12-12 12:00:00',0,'Ostrzeżenie','1','1','1'),('2','2012-12-12 12:00:00','2012-12-12 12:00:00',2,1,'Pamiętaj o zwrocie sprzętu.','2012-12-11 13:00:00',0,'Przypominajka','1','1','1'),('3','2012-12-12 12:00:00','2012-12-12 12:00:00',2,0,'Nie wiem czy pamiętacie ale potrzebni są ludzie żeby podjechać do Happysadu. Zapiszcie się na event jeżeli macie wtedy wolne plz.','2012-12-08 21:30:00',0,'Prośba','1','1','1');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privileges`
--

DROP TABLE IF EXISTS `privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privileges` (
  `ROLE_ID` varchar(255) NOT NULL,
  `PRIVILEGE` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`PRIVILEGE`),
  KEY `FKBA4DCBE29B9F6399` (`ROLE_ID`),
  CONSTRAINT `FKBA4DCBE29B9F6399` FOREIGN KEY (`ROLE_ID`) REFERENCES `roles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privileges`
--

LOCK TABLES `privileges` WRITE;
/*!40000 ALTER TABLE `privileges` DISABLE KEYS */;
INSERT INTO `privileges` VALUES ('1','MANAGE_ACCESS_HISTORY'),('1','MANAGE_DEVICES'),('1','MANAGE_EVENTS'),('1','MANAGE_MESSAGES'),('1','MANAGE_PROFILE'),('1','MANAGE_ROLES'),('1','MANAGE_USERS'),('2','CLEAN_UP_MESSAGES'),('2','LOOK_UP_DEVICE_EVENTS'),('2','MANAGE_PROFILE'),('2','MARK_DELETE_MESSAGES'),('2','MARK_DELETE_MY_EVENTS'),('2','POST_EVENTS_TO_WAITING_ROOM'),('2','SEND_MESSAGES'),('2','SIGN_UP_FOR_EVENT'),('2','UPDATE_MY_EVENTS'),('2','VIEW_DEVICES'),('2','VIEW_EVENTS'),('2','VIEW_MESSAGES'),('2','VIEW_PROFILE'),('3','SEND_MESSAGES'),('3','VIEW_DEVICES'),('3','VIEW_EVENTS'),('3','VIEW_MESSAGES'),('3','VIEW_PROFILE');
/*!40000 ALTER TABLE `privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('1','2012-12-09 12:00:00','2012-12-09 12:00:00',2,'Administrator',NULL,NULL),('2','2012-12-09 13:00:00','2012-12-09 13:00:00',1,'User','1','1'),('3','2012-12-09 15:00:00','2012-12-09 16:00:00',1,'Viewer','1','1');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` varchar(255) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `MODIFICATION_DATE` datetime DEFAULT NULL,
  `VERSION` bigint(20) NOT NULL,
  `COMMENT` longtext,
  `DELETED` tinyint(1) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `LOCKED` tinyint(1) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `USERNAME` varchar(32) NOT NULL,
  `CREATED_BY_ID` varchar(255) DEFAULT NULL,
  `MODIFIED_BY_ID` varchar(255) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `ROLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `EMAIL` (`EMAIL`),
  UNIQUE KEY `USERNAME` (`USERNAME`),
  UNIQUE KEY `ADDRESS` (`ADDRESS`),
  KEY `FK4D495E8EFF68599` (`ADDRESS`),
  KEY `FK4D495E849C34E8B` (`ROLE`),
  CONSTRAINT `FK4D495E849C34E8B` FOREIGN KEY (`ROLE`) REFERENCES `roles` (`ID`),
  CONSTRAINT `FK4D495E8EFF68599` FOREIGN KEY (`ADDRESS`) REFERENCES `address_data` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'Główny admin',0,'superadmin@admin.pl',0,'PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==','admin',NULL,NULL,'1','1'),('2','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'Jeden z użytkowników',0,'user1@user.pl',0,'PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==','user1','1','1','2','2'),('3','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'Zablokowany użytkownik',0,'user2@user.pl',1,'PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==','user2','1','1','3','2'),('4','2012-12-09 12:00:00','2012-12-09 12:00:00',2,'Usunięty użytkownik',1,'user3@user.pl',0,'PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==','user3','1','1','4','2'),('5','2012-12-09 12:00:00','2012-12-09 12:00:00',1,'Wizytator',0,'viewer1@viewer.pl',0,'PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==','viewer1','1','1','5','3');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-12 19:37:32
