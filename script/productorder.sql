/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2023-06-07 15:13:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for productorder
-- ----------------------------
DROP TABLE IF EXISTS `productorder`;
CREATE TABLE `productorder` (
  `productorder_id` int(10) NOT NULL AUTO_INCREMENT,
  `productorder_code` varchar(30) NOT NULL COMMENT '订单号',
  `productorder_address` char(6) NOT NULL COMMENT '收货地址',
  `productorder_detail_address` varchar(255) NOT NULL COMMENT '收货详细地址',
  `productorder_post` char(6) DEFAULT NULL COMMENT '邮政编码',
  `productorder_receiver` varchar(20) NOT NULL COMMENT '收货人',
  `productorder_mobile` char(11) NOT NULL COMMENT '联系方式',
  `productorder_pay_date` datetime DEFAULT NULL COMMENT '支付日期',
  `productorder_delivery_date` datetime DEFAULT NULL COMMENT '发货日期',
  `productorder_confirm_date` datetime DEFAULT NULL COMMENT '确认日期',
  `productorder_status` tinyint(1) NOT NULL COMMENT '订单状态(0:待付款 1:待发货 2:待确认 3:交易成功 4:交易关闭)',
  `productorder_user_id` int(10) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`productorder_id`) USING BTREE,
  UNIQUE KEY `un_productorder_code` (`productorder_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='产品订单表';

-- ----------------------------
-- Records of productorder
-- ----------------------------
INSERT INTO `productorder` VALUES ('1', '2023050123494601', '440106', '叩丁狼教育', '510630', '张三', '18700000000', '2023-05-01 23:49:46', '2023-06-07 13:02:49', '2023-05-13 22:08:08', '2', '1');
INSERT INTO `productorder` VALUES ('2', '2023050222445401', '440106', '叩丁狼教育', '510630', '张三', '18700000000', '2023-05-03 16:13:14', '2023-05-04 18:57:51', '2023-05-13 22:48:35', '3', '1');
INSERT INTO `productorder` VALUES ('3', '2023050222475401', '440106', '叩丁狼教育', '510630', '张三', '18700000000', '2023-05-03 16:13:14', '2023-05-07 12:07:47', null, '2', '1');
