/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50743 (5.7.43-log)
 Source Host           : localhost:3306
 Source Schema         : auto

 Target Server Type    : MySQL
 Target Server Version : 50743 (5.7.43-log)
 File Encoding         : 65001

 Date: 26/08/2023 14:15:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for markers
-- ----------------------------
DROP TABLE IF EXISTS `markers`;
CREATE TABLE `markers`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景区id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '景点名称',
  `latitude` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '景点纬度',
  `longitude` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '景点经度',
  `icon_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `width` double NULL DEFAULT NULL COMMENT '图片宽度--默认值，不需要改变',
  `height` double UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '图片长度--默认值，不需要改变',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of markers
-- ----------------------------
INSERT INTO `markers` VALUES (93, '华航图书馆', '39.527271829920004', '116.73952843443097', 'http://192.168.46.19/img/位置.png', 30, 0000000000000000000020);
INSERT INTO `markers` VALUES (94, '华航教10', '39.52667520697303', '116.73822627451322', 'http://192.168.46.19/img/位置.png', 0, 0000000000000000000000);
INSERT INTO `markers` VALUES (95, '教10', '39.525719618471605', '116.73855662300107', 'http://192.168.46.19/img/位置.png', 0, 0000000000000000000000);

-- ----------------------------
-- Table structure for scene
-- ----------------------------
DROP TABLE IF EXISTS `scene`;
CREATE TABLE `scene`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景点id',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '景点地址',
  `audio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '讲解音频',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '景点详情',
  `radius` double NULL DEFAULT NULL COMMENT '景点范围，单位米',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scene
-- ----------------------------
INSERT INTO `scene` VALUES (93, '图书馆', 'http://192.168.46.19/audio/2023082610260901.mp3', '图书馆', 30);
INSERT INTO `scene` VALUES (94, '教10', 'http://192.168.46.19/audio/2023082610272201.mp3', '教10', 30);
INSERT INTO `scene` VALUES (95, '教10', 'http://192.168.46.19/audio/2023082610280301.mp3', '教10', 30);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '后台登录id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录用户',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `is_del` int(11) NULL DEFAULT NULL COMMENT '是否删除，1为删除，0为在使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
