/*
Navicat MySQL Data Transfer

Source Server         : 1111
Source Server Version : 50636
Source Host           : 127.0.0.1:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2023-06-03 07:57:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `admin_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '账户名',
  `admin_nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `admin_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `admin_profile_picture_src` varchar(255) DEFAULT NULL COMMENT '头像地址',
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE KEY `un_admin_name` (`admin_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', '超管', '123456', null);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int(10) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='类别表';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '女装 /大衣');
INSERT INTO `category` VALUES ('2', '男装 /运动户外');
INSERT INTO `category` VALUES ('3', '女鞋 /男鞋 /箱包');
INSERT INTO `category` VALUES ('4', '美妆 /个人护理');
INSERT INTO `category` VALUES ('5', '腕表 /眼镜 /珠宝饰品');
INSERT INTO `category` VALUES ('6', '手机 /数码 /电脑办公');
INSERT INTO `category` VALUES ('7', '1');
INSERT INTO `category` VALUES ('8', '平板');
INSERT INTO `category` VALUES ('9', '平板1');
INSERT INTO `category` VALUES ('10', '平板3');
INSERT INTO `category` VALUES ('11', '');
INSERT INTO `category` VALUES ('12', '');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int(10) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `product_title` varchar(100) DEFAULT NULL COMMENT '产品标题',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
  `product_sale_price` decimal(10,2) NOT NULL COMMENT '促销价',
  `product_create_date` datetime NOT NULL COMMENT '创建日期',
  `product_category_id` int(10) NOT NULL COMMENT '类别id',
  `product_is_up` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否上架  1上架 0 下架',
  `product_count` int(11) NOT NULL DEFAULT '0' COMMENT '产品数量',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='产品表';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '2018春装新款韩版潮学生宽松薄款卫衣女长袖ins超火的上衣服外套', '春装新款百搭~~', '119.00', '79.00', '2023-04-24 10:36:23', '1', '0', '100');
INSERT INTO `product` VALUES ('2', '乔琪诺牛仔卫衣女中长款连帽套头中袖韩版宽松2018新款连衣裙夏', '乔琪诺原创设计 送运费险', '270.00', '199.00', '2023-04-24 10:54:59', '1', '0', '150');
INSERT INTO `product` VALUES ('3', '花花公子春季男士长袖衬衫免烫中青年休闲衬衣潮流男装印花衬衣', '送运费险 放心购物', '288.00', '139.00', '2023-04-24 10:58:08', '2', '0', '100');
INSERT INTO `product` VALUES ('4', '人本高帮帆布鞋内增高布鞋高邦高腰单鞋牛仔布系带学院风学生款女', '帆布鞋', '150.00', '75.00', '2023-04-24 11:00:25', '3', '0', '25');
INSERT INTO `product` VALUES ('5', '2018新款ins超火短款连帽卫衣女夏春季bf风薄款帽衫潮学生宽松', '潮流卫衣', '108.00', '69.00', '2023-04-24 11:06:45', '1', '0', '55');
INSERT INTO `product` VALUES ('6', '花花公子牛仔裤男修身款 秋冬季直筒商务休闲弹力男士牛仔长裤子', '花花公子正品 默认圆通 送运费险', '259.00', '159.00', '2023-04-24 11:08:18', '2', '1', '30');
INSERT INTO `product` VALUES ('91', 'name', 'title', '100.00', '1.00', '2023-06-03 07:19:55', '1', '1', '100');

-- ----------------------------
-- Table structure for productimage
-- ----------------------------
DROP TABLE IF EXISTS `productimage`;
CREATE TABLE `productimage` (
  `productimage_id` int(10) NOT NULL AUTO_INCREMENT,
  `productimage_type` tinyint(1) unsigned NOT NULL COMMENT '类型(0:概述图片 1:详情图片)',
  `productimage_src` varchar(255) NOT NULL COMMENT '图片地址',
  `productimage_product_id` int(10) NOT NULL COMMENT '产品id',
  PRIMARY KEY (`productimage_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='产品图片表';

-- ----------------------------
-- Records of productimage
-- ----------------------------
INSERT INTO `productimage` VALUES ('1', '0', '/upload/single/c58a20df-e27d-4c9b-ab62-f3f3e6dff009.jpeg', '91');
INSERT INTO `productimage` VALUES ('2', '0', '/upload/single/72e6f843-7886-4c4c-8c0f-d691a45d2daf.png', '91');
INSERT INTO `productimage` VALUES ('3', '1', '/upload/detail/7853a4f9-d367-4c17-a58a-0abe80d5acd8.jpg', '91');

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `property_id` int(10) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(25) NOT NULL COMMENT '属性名称',
  `property_category_id` int(10) NOT NULL COMMENT '关联类别id',
  PRIMARY KEY (`property_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='类别属性表';

-- ----------------------------
-- Records of property
-- ----------------------------
INSERT INTO `property` VALUES ('1', '材质成分', '1');
INSERT INTO `property` VALUES ('2', '服装版型', '1');
INSERT INTO `property` VALUES ('3', '风格', '1');
INSERT INTO `property` VALUES ('4', '衣长', '1');
INSERT INTO `property` VALUES ('5', '袖长', '1');
INSERT INTO `property` VALUES ('6', '领型', '1');
INSERT INTO `property` VALUES ('7', '图案', '1');
INSERT INTO `property` VALUES ('8', '品牌', '1');
INSERT INTO `property` VALUES ('9', '适用年龄', '1');
INSERT INTO `property` VALUES ('10', '年份季节', '1');
INSERT INTO `property` VALUES ('11', '颜色分类', '1');
INSERT INTO `property` VALUES ('12', '1', '7');
INSERT INTO `property` VALUES ('13', '2', '7');
INSERT INTO `property` VALUES ('14', '3', '7');
INSERT INTO `property` VALUES ('15', '4', '7');
INSERT INTO `property` VALUES ('16', '屏幕尺寸', '8');
INSERT INTO `property` VALUES ('17', '品牌', '8');
INSERT INTO `property` VALUES ('18', '颜色', '8');
INSERT INTO `property` VALUES ('27', '11', '10');

-- ----------------------------
-- Table structure for propertyvalue
-- ----------------------------
DROP TABLE IF EXISTS `propertyvalue`;
CREATE TABLE `propertyvalue` (
  `propertyvalue_id` int(10) NOT NULL AUTO_INCREMENT,
  `propertyvalue_value` varchar(100) NOT NULL COMMENT '属性值',
  `propertyvalue_property_id` int(10) NOT NULL COMMENT '关联属性id',
  `propertyvalue_product_id` int(10) NOT NULL COMMENT '关联产品id',
  PRIMARY KEY (`propertyvalue_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='产品属性管理表';

-- ----------------------------
-- Records of propertyvalue
-- ----------------------------
INSERT INTO `propertyvalue` VALUES ('1', '通勤', '3', '1');
INSERT INTO `propertyvalue` VALUES ('2', '宽松', '2', '1');
INSERT INTO `propertyvalue` VALUES ('3', '2018年春季', '10', '1');
INSERT INTO `propertyvalue` VALUES ('4', '棉95.1% 聚氨酯弹性纤维(氨纶)4.9%', '1', '1');
INSERT INTO `propertyvalue` VALUES ('5', '拼色', '7', '1');
INSERT INTO `propertyvalue` VALUES ('6', '连帽', '6', '1');
INSERT INTO `propertyvalue` VALUES ('7', '长袖', '5', '1');
INSERT INTO `propertyvalue` VALUES ('8', '常规', '4', '1');
INSERT INTO `propertyvalue` VALUES ('9', '18-24周岁', '9', '1');
INSERT INTO `propertyvalue` VALUES ('10', '戚米', '8', '1');
INSERT INTO `propertyvalue` VALUES ('11', '白色 红色 黄色', '11', '1');
INSERT INTO `propertyvalue` VALUES ('12', '均码', '12', '1');
INSERT INTO `propertyvalue` VALUES ('13', '通勤', '3', '2');
INSERT INTO `propertyvalue` VALUES ('14', '宽松', '2', '2');
INSERT INTO `propertyvalue` VALUES ('15', '2018年春季', '10', '2');
INSERT INTO `propertyvalue` VALUES ('16', '成分', '1', '91');
INSERT INTO `propertyvalue` VALUES ('17', '服装版型', '2', '91');
INSERT INTO `propertyvalue` VALUES ('18', '风格', '3', '91');
INSERT INTO `propertyvalue` VALUES ('19', '衣长', '4', '91');
INSERT INTO `propertyvalue` VALUES ('20', '袖长', '5', '91');
INSERT INTO `propertyvalue` VALUES ('21', '领型', '6', '91');
INSERT INTO `propertyvalue` VALUES ('22', '', '7', '91');
INSERT INTO `propertyvalue` VALUES ('23', '', '8', '91');
INSERT INTO `propertyvalue` VALUES ('24', '', '9', '91');
INSERT INTO `propertyvalue` VALUES ('25', '', '10', '91');
INSERT INTO `propertyvalue` VALUES ('26', '', '11', '91');
