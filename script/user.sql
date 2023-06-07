/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2023-06-05 15:15:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `user_nickname` varchar(50) NOT NULL COMMENT '昵称',
  `user_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `user_realname` varchar(20) DEFAULT NULL COMMENT '姓名',
  `user_gender` tinyint(1) NOT NULL COMMENT '性别',
  `user_birthday` date NOT NULL COMMENT '出生日期',
  `user_address` char(6) NOT NULL COMMENT '所在地地址',
  `user_homeplace` char(6) NOT NULL COMMENT '家乡',
  `user_profile_picture_src` varchar(100) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `un_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'a120', 'MRJIANG', 'cc123.0', '江超', '1', '2013-12-01', '610112', '110100', null);
INSERT INTO `user` VALUES ('3', 'MRJIANG', '如有巧合', 'cc123.0', '流年', '1', '2018-05-11', '110101', '130000', null);
INSERT INTO `user` VALUES ('6', 'a1209577113', '如有巧合丶', 'cc123.0', '', '1', '2018-05-07', '230102', '130000', null);
INSERT INTO `user` VALUES ('7', '李浩', '李', 'cc123.0', '李浩', '1', '1997-06-22', '110109', '130000', null);
INSERT INTO `user` VALUES ('8', '聚散又依依', '聚散又依依', 'a1392432919', '邓云涛', '1', '1997-04-05', '110101', '130000', null);
