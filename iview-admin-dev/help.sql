/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50522
Source Host           : localhost:3306
Source Database       : help

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2018-05-16 01:45:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `back`
-- ----------------------------
DROP TABLE IF EXISTS `back`;
CREATE TABLE `back` (
  `backid` varchar(37) NOT NULL,
  `backfile` varchar(128) NOT NULL,
  PRIMARY KEY (`backid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of back
-- ----------------------------

-- ----------------------------
-- Table structure for `jizhangday`
-- ----------------------------
DROP TABLE IF EXISTS `jizhangday`;
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

-- ----------------------------
-- Records of jizhangday
-- ----------------------------

-- ----------------------------
-- Table structure for `jizhangsum`
-- ----------------------------
DROP TABLE IF EXISTS `jizhangsum`;
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

-- ----------------------------
-- Records of jizhangsum
-- ----------------------------

-- ----------------------------
-- Table structure for `optinfo`
-- ----------------------------
DROP TABLE IF EXISTS `optinfo`;
CREATE TABLE `optinfo` (
  `optid` varchar(37) NOT NULL,
  `optcode` tinyint(4) NOT NULL,
  `opttime` time NOT NULL,
  `optdescription` varchar(37) NOT NULL,
  `optcontent` varchar(64) NOT NULL,
  PRIMARY KEY (`optid`),
  KEY `timeidx` (`opttime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of optinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
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

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('', '管理员', '', '', '', '0', '123456', '5684716731084E53921F34D8CCD61A5D');

-- ----------------------------
-- Table structure for `userreg`
-- ----------------------------
DROP TABLE IF EXISTS `userreg`;
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

-- ----------------------------
-- Records of userreg
-- ----------------------------

-- ----------------------------
-- Table structure for `usershouyi`
-- ----------------------------
DROP TABLE IF EXISTS `usershouyi`;
CREATE TABLE `usershouyi` (
  `id` varchar(255) NOT NULL,
  `shouyie` double(20,0) NOT NULL,
  `shouyiriqi` date NOT NULL,
  `shouyiid` varchar(37) NOT NULL DEFAULT '',
  PRIMARY KEY (`shouyiid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usershouyi
-- ----------------------------

-- ----------------------------
-- Table structure for `usertixian`
-- ----------------------------
DROP TABLE IF EXISTS `usertixian`;
CREATE TABLE `usertixian` (
  `btixian` smallint(1) NOT NULL DEFAULT '0',
  `buserread` smallint(1) NOT NULL DEFAULT '0',
  `id` varchar(18) NOT NULL,
  `tixiane` double(20,0) NOT NULL,
  `tixianriqi` date NOT NULL,
  `tixianid` varchar(37) NOT NULL DEFAULT '',
  PRIMARY KEY (`tixianid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertixian
-- ----------------------------

-- ----------------------------
-- Table structure for `usertouzi`
-- ----------------------------
DROP TABLE IF EXISTS `usertouzi`;
CREATE TABLE `usertouzi` (
  `id` varchar(18) NOT NULL,
  `touzie` double(20,0) NOT NULL,
  `touziriqi` date NOT NULL,
  `touziid` varchar(37) NOT NULL,
  `touzitianshu` bigint(20) NOT NULL,
  PRIMARY KEY (`touziid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertouzi
-- ----------------------------
