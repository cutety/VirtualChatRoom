/*
Navicat MySQL Data Transfer

Source Server         : alex
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : vchatroom

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2019-06-17 09:40:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for 1138_friend
-- ----------------------------
DROP TABLE IF EXISTS `1138_friend`;
CREATE TABLE `1138_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1138_friend
-- ----------------------------

-- ----------------------------
-- Table structure for 11_friend
-- ----------------------------
DROP TABLE IF EXISTS `11_friend`;
CREATE TABLE `11_friend` (
  `id` int(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 11_friend
-- ----------------------------
INSERT INTO `11_friend` VALUES ('1', '1', '往事随风');

-- ----------------------------
-- Table structure for 1231_friend
-- ----------------------------
DROP TABLE IF EXISTS `1231_friend`;
CREATE TABLE `1231_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1231_friend
-- ----------------------------
INSERT INTO `1231_friend` VALUES ('1', '1231', 'asdad');

-- ----------------------------
-- Table structure for 1446_friend
-- ----------------------------
DROP TABLE IF EXISTS `1446_friend`;
CREATE TABLE `1446_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1446_friend
-- ----------------------------
INSERT INTO `1446_friend` VALUES ('1', '1446', '无名氏');
INSERT INTO `1446_friend` VALUES ('8', '1', '往事随风');

-- ----------------------------
-- Table structure for 1526_friend
-- ----------------------------
DROP TABLE IF EXISTS `1526_friend`;
CREATE TABLE `1526_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1526_friend
-- ----------------------------

-- ----------------------------
-- Table structure for 1527_friend
-- ----------------------------
DROP TABLE IF EXISTS `1527_friend`;
CREATE TABLE `1527_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1527_friend
-- ----------------------------
INSERT INTO `1527_friend` VALUES ('1', '1527', 'wode');

-- ----------------------------
-- Table structure for 1677_friend
-- ----------------------------
DROP TABLE IF EXISTS `1677_friend`;
CREATE TABLE `1677_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1677_friend
-- ----------------------------
INSERT INTO `1677_friend` VALUES ('1', '1677', '1333');

-- ----------------------------
-- Table structure for 1700_friend
-- ----------------------------
DROP TABLE IF EXISTS `1700_friend`;
CREATE TABLE `1700_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1700_friend
-- ----------------------------
INSERT INTO `1700_friend` VALUES ('1', '1700', 'xiaoming');

-- ----------------------------
-- Table structure for 1738_friend
-- ----------------------------
DROP TABLE IF EXISTS `1738_friend`;
CREATE TABLE `1738_friend` (
  `id` int(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1738_friend
-- ----------------------------

-- ----------------------------
-- Table structure for 1_friend
-- ----------------------------
DROP TABLE IF EXISTS `1_friend`;
CREATE TABLE `1_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 1_friend
-- ----------------------------
INSERT INTO `1_friend` VALUES ('1', '1', '往事随风');
INSERT INTO `1_friend` VALUES ('2', '4', '社会人');
INSERT INTO `1_friend` VALUES ('3', '11', '飞翔的忍者');
INSERT INTO `1_friend` VALUES ('4', '3', 'IAMS0RRY');
INSERT INTO `1_friend` VALUES ('6', '2', '茉莉花茶');
INSERT INTO `1_friend` VALUES ('10', '2001', '群聊');
INSERT INTO `1_friend` VALUES ('20', '5', '小猪佩奇');
INSERT INTO `1_friend` VALUES ('28', '1446', '无名氏');

-- ----------------------------
-- Table structure for 2001_friend
-- ----------------------------
DROP TABLE IF EXISTS `2001_friend`;
CREATE TABLE `2001_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 2001_friend
-- ----------------------------
INSERT INTO `2001_friend` VALUES ('1', '1', '往事随风');
INSERT INTO `2001_friend` VALUES ('2', '4', '社会人');
INSERT INTO `2001_friend` VALUES ('3', '11', '飞翔的忍者');
INSERT INTO `2001_friend` VALUES ('4', '3', 'IAMS0RRY');
INSERT INTO `2001_friend` VALUES ('5', '5', '小猪佩奇');
INSERT INTO `2001_friend` VALUES ('6', '2', '茉莉花茶');

-- ----------------------------
-- Table structure for 2_friend
-- ----------------------------
DROP TABLE IF EXISTS `2_friend`;
CREATE TABLE `2_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 2_friend
-- ----------------------------
INSERT INTO `2_friend` VALUES ('1', '2', '茉莉花茶');
INSERT INTO `2_friend` VALUES ('2', '3', 'IAMS0RRY');
INSERT INTO `2_friend` VALUES ('3', '1', '往事随风');
INSERT INTO `2_friend` VALUES ('5', '2001', '群聊');

-- ----------------------------
-- Table structure for 3_friend
-- ----------------------------
DROP TABLE IF EXISTS `3_friend`;
CREATE TABLE `3_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 3_friend
-- ----------------------------
INSERT INTO `3_friend` VALUES ('1', '3', 'IAMS0RRY');
INSERT INTO `3_friend` VALUES ('2', '2', '茉莉花茶');
INSERT INTO `3_friend` VALUES ('5', '5', '小猪佩奇');
INSERT INTO `3_friend` VALUES ('6', '1', '往事随风');
INSERT INTO `3_friend` VALUES ('7', '2001', '群聊');

-- ----------------------------
-- Table structure for 4_friend
-- ----------------------------
DROP TABLE IF EXISTS `4_friend`;
CREATE TABLE `4_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 4_friend
-- ----------------------------
INSERT INTO `4_friend` VALUES ('1', '4', '社会人');
INSERT INTO `4_friend` VALUES ('6', '1', '往事随风');
INSERT INTO `4_friend` VALUES ('7', '2001', '群聊');

-- ----------------------------
-- Table structure for 5_friend
-- ----------------------------
DROP TABLE IF EXISTS `5_friend`;
CREATE TABLE `5_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 5_friend
-- ----------------------------
INSERT INTO `5_friend` VALUES ('1', '5', '小猪佩奇');
INSERT INTO `5_friend` VALUES ('15', '1', '往事随风');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_email` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_sex` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT 'M',
  `user_birthday` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_avatar` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `user_trades` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `user_registertime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '往事随风', '123', null, 'M', null, null, '想起那天在夕阳下的奔跑，那是我逝去的青春', '2019-06-09 19:27:12');
INSERT INTO `user` VALUES ('2', '茉莉花茶', '456', null, 'F', null, null, '康师傅茉莉花茶', '2019-06-09 19:27:25');
INSERT INTO `user` VALUES ('3', 'IAMS0RRY', '123', null, 'M', null, null, '我的橘子账号', '2019-06-11 00:37:43');
INSERT INTO `user` VALUES ('4', '社会人', '123', null, 'M', null, null, 'asdasdasdasdasd', '2019-06-11 01:24:15');
INSERT INTO `user` VALUES ('5', '小猪佩奇', '123', null, 'M', null, null, null, '2019-06-11 01:26:04');
INSERT INTO `user` VALUES ('7', null, '1233', null, 'M', null, null, null, '2019-06-11 01:28:56');
INSERT INTO `user` VALUES ('11', '飞翔的忍者', '123', null, 'M', null, null, null, '2019-06-11 22:36:20');
INSERT INTO `user` VALUES ('1138', '4444', '123', null, 'M', null, null, null, '2019-06-15 12:37:43');
INSERT INTO `user` VALUES ('1231', 'asdad', '123', null, 'M', null, null, null, '2019-06-15 12:33:13');
INSERT INTO `user` VALUES ('1394', '11111111', '123', null, 'M', null, null, null, '2019-06-15 12:25:51');
INSERT INTO `user` VALUES ('1446', '无名氏', '', null, 'M', null, null, null, '2019-06-16 14:17:45');
INSERT INTO `user` VALUES ('1526', 'asddd', '123', null, 'M', null, null, null, '2019-06-15 12:36:02');
INSERT INTO `user` VALUES ('1527', 'wode', '123', null, 'M', null, null, null, '2019-06-15 23:46:00');
INSERT INTO `user` VALUES ('1594', 'qwewqe', '123', null, 'M', null, null, null, '2019-06-15 12:24:29');
INSERT INTO `user` VALUES ('1677', '1333', '123', null, 'M', null, null, null, '2019-06-15 12:38:25');
INSERT INTO `user` VALUES ('1700', 'xiaoming', '123', null, 'M', null, null, null, '2019-06-15 23:10:27');
INSERT INTO `user` VALUES ('2001', '群聊', '123', null, 'M', null, null, null, '2019-06-15 09:38:48');
