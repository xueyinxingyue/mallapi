/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2023-06-05 15:14:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_product_id` int(11) DEFAULT NULL,
  `cart_count` int(11) DEFAULT NULL,
  `cart_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('1', '1', '3', '1');
INSERT INTO `cart` VALUES ('2', '2', '1', '1');
INSERT INTO `cart` VALUES ('3', '1', '2', '3');
INSERT INTO `cart` VALUES ('4', '3', '3', '1');
INSERT INTO `cart` VALUES ('5', '4', '2', '1');
