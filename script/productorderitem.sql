/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2023-06-07 15:13:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for productorderitem
-- ----------------------------
DROP TABLE IF EXISTS `productorderitem`;
CREATE TABLE `productorderitem` (
  `productorderitem_id` int(10) NOT NULL AUTO_INCREMENT,
  `productorderitem_number` smallint(5) unsigned NOT NULL COMMENT '数量',
  `productorderitem_price` decimal(10,2) NOT NULL COMMENT '单价',
  `productorderitem_product_id` int(10) NOT NULL COMMENT '关联产品id',
  `productorderitem_order_id` int(10) DEFAULT NULL COMMENT '关联订单id',
  `productorderitem_user_id` int(10) NOT NULL COMMENT '关联用户id',
  `productorderitem_userMessage` varchar(255) DEFAULT NULL COMMENT '用户备注',
  PRIMARY KEY (`productorderitem_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='产品订单详细表';

-- ----------------------------
-- Records of productorderitem
-- ----------------------------
INSERT INTO `productorderitem` VALUES ('2', '1', '49.00', '1', '1', '1', '');
INSERT INTO `productorderitem` VALUES ('3', '1', '118.00', '2', '1', '1', '回头客');
INSERT INTO `productorderitem` VALUES ('4', '1', '35.00', '3', '1', '1', '');
INSERT INTO `productorderitem` VALUES ('5', '1', '2599.00', '4', '1', '1', '');
INSERT INTO `productorderitem` VALUES ('6', '5', '12995.00', '5', '1', '1', '');
