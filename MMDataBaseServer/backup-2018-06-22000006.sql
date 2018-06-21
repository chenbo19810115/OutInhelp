-- MySQL dump 10.13  Distrib 5.5.22, for Win32 (x86)
--
-- Host: localhost    Database: help
-- ------------------------------------------------------
-- Server version	5.5.22

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
-- Table structure for table `back`
--

DROP TABLE IF EXISTS `back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `back` (
  `backid` varchar(37) NOT NULL,
  `backfile` varchar(128) NOT NULL,
  PRIMARY KEY (`backid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `back`
--

LOCK TABLES `back` WRITE;
/*!40000 ALTER TABLE `back` DISABLE KEYS */;
/*!40000 ALTER TABLE `back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jizhangday`
--

DROP TABLE IF EXISTS `jizhangday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jizhangday` (
  `jizhangid` varchar(37) NOT NULL,
  `id` varchar(18) NOT NULL,
  `jizhangriqi` date NOT NULL,
  `xinzengtouzie` double(20,0) NOT NULL,
  `tixiane` double(20,0) NOT NULL,
  `shouyie` double(20,0) NOT NULL,
  `touzitianshu` int(20) NOT NULL,
  PRIMARY KEY (`jizhangid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jizhangday`
--

LOCK TABLES `jizhangday` WRITE;
/*!40000 ALTER TABLE `jizhangday` DISABLE KEYS */;
/*!40000 ALTER TABLE `jizhangday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jizhangsum`
--

DROP TABLE IF EXISTS `jizhangsum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jizhangsum` (
  `jizhangid` varchar(37) NOT NULL DEFAULT '',
  `id` varchar(18) DEFAULT NULL,
  `jizhangriqi` date DEFAULT NULL,
  `zongtouzie` double(20,0) DEFAULT NULL,
  `zongshouyie` double(20,0) DEFAULT NULL,
  `shouyie` double(20,0) DEFAULT NULL,
  `nianhuashouyilv` double DEFAULT NULL,
  PRIMARY KEY (`jizhangid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jizhangsum`
--

LOCK TABLES `jizhangsum` WRITE;
/*!40000 ALTER TABLE `jizhangsum` DISABLE KEYS */;
/*!40000 ALTER TABLE `jizhangsum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optinfo`
--

DROP TABLE IF EXISTS `optinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optinfo` (
  `optid` varchar(37) NOT NULL,
  `optcode` tinyint(4) NOT NULL,
  `opttime` time NOT NULL,
  `optdescription` varchar(37) NOT NULL,
  `optcontent` varchar(256) NOT NULL,
  PRIMARY KEY (`optid`),
  KEY `timeidx` (`opttime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optinfo`
--

LOCK TABLES `optinfo` WRITE;
/*!40000 ALTER TABLE `optinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `optinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(18) NOT NULL,
  `name` varchar(8) NOT NULL,
  `cellphone` varchar(11) NOT NULL,
  `securityCode` varchar(37) NOT NULL,
  `email` varchar(64) NOT NULL,
  `author` tinyint(4) NOT NULL,
  `password` varchar(20) NOT NULL,
  `pid` varchar(37) NOT NULL DEFAULT '',
  PRIMARY KEY (`pid`),
  UNIQUE KEY `idkey` (`id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('','管理员','','','',0,'123456','5684716731084E53921F34D8CCD61A5D');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userreg`
--

DROP TABLE IF EXISTS `userreg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userreg` (
  `id` varchar(18) NOT NULL,
  `name` varchar(8) NOT NULL,
  `cellphone` varchar(11) NOT NULL,
  `securityCode` varchar(37) NOT NULL DEFAULT '',
  `email` varchar(64) NOT NULL,
  `bsendmail` varchar(1) DEFAULT '0',
  PRIMARY KEY (`securityCode`),
  UNIQUE KEY `idkey` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userreg`
--

LOCK TABLES `userreg` WRITE;
/*!40000 ALTER TABLE `userreg` DISABLE KEYS */;
/*!40000 ALTER TABLE `userreg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usershouyi`
--

DROP TABLE IF EXISTS `usershouyi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usershouyi` (
  `id` varchar(255) NOT NULL,
  `shouyie` double(20,0) NOT NULL,
  `shouyiriqi` date NOT NULL,
  `shouyiid` varchar(37) NOT NULL DEFAULT '',
  PRIMARY KEY (`shouyiid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usershouyi`
--

LOCK TABLES `usershouyi` WRITE;
/*!40000 ALTER TABLE `usershouyi` DISABLE KEYS */;
/*!40000 ALTER TABLE `usershouyi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertixian`
--

DROP TABLE IF EXISTS `usertixian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertixian` (
  `btixian` smallint(1) NOT NULL DEFAULT '0',
  `buserread` smallint(1) NOT NULL DEFAULT '0',
  `id` varchar(18) NOT NULL,
  `tixiane` double(20,0) NOT NULL,
  `tixianriqi` date NOT NULL,
  `tixianid` varchar(37) NOT NULL DEFAULT '',
  PRIMARY KEY (`tixianid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertixian`
--

LOCK TABLES `usertixian` WRITE;
/*!40000 ALTER TABLE `usertixian` DISABLE KEYS */;
/*!40000 ALTER TABLE `usertixian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertouzi`
--

DROP TABLE IF EXISTS `usertouzi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertouzi` (
  `id` varchar(18) NOT NULL,
  `touzie` double(20,0) NOT NULL,
  `touziriqi` date NOT NULL,
  `touziid` varchar(37) NOT NULL,
  `touzitianshu` bigint(20) NOT NULL,
  PRIMARY KEY (`touziid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertouzi`
--

LOCK TABLES `usertouzi` WRITE;
/*!40000 ALTER TABLE `usertouzi` DISABLE KEYS */;
/*!40000 ALTER TABLE `usertouzi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-22  0:00:07
