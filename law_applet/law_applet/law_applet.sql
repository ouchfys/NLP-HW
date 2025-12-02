-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: law_applet
-- ------------------------------------------------------
-- Server version	5.6.51

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL COMMENT '订单id',
  `content` varchar(500) DEFAULT NULL COMMENT '评价的内容',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `star` int(11) DEFAULT NULL COMMENT '打分',
  `status` int(11) DEFAULT NULL COMMENT '审核状态0-待审核 1-通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='评价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_comment`
--

/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_law_information`
--

DROP TABLE IF EXISTS `t_law_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_law_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `information` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `insert_time` datetime DEFAULT NULL,
  `img_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='法律信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_law_information`
--

/*!40000 ALTER TABLE `t_law_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_message`
--

DROP TABLE IF EXISTS `t_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单编号',
  `insert_time` datetime DEFAULT NULL,
  `message` text COLLATE utf8mb4_unicode_ci COMMENT '消息内容',
  `send_user_id` int(11) DEFAULT NULL COMMENT '发送人',
  `receive_user_id` int(11) DEFAULT NULL COMMENT '接受人',
  `status` int(11) DEFAULT '0' COMMENT '消息是否已读0-未读 1-已读',
  `role` int(11) DEFAULT NULL COMMENT '发送消息人的标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_message`
--

LOCK TABLES `t_message` WRITE;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;
INSERT INTO `t_message` VALUES (1,'1','2024-05-04 22:24:02','律师你好',NULL,2,1,1),(2,'1','2024-05-04 22:25:13','我想问一下',NULL,2,0,1),(3,'1','2024-05-04 22:25:22','关于',NULL,2,0,1),(4,'1787739778988036096','2024-05-07 15:19:17','请问是赵律师吗？',4,5,1,3),(5,'1787739778988036096','2024-05-07 15:22:08','想咨询你一个问题',4,5,1,3),(6,'1787739778988036096','2024-05-07 15:41:52','你好，你是赵律师吗',4,5,1,3),(7,'1787739778988036096','2024-05-07 15:42:41','你好，你是赵律师吗',4,5,1,3),(8,'1787739778988036096','2024-05-07 15:43:16','测试',4,5,1,3),(9,'1787739778988036096','2024-05-07 15:45:17','测试',4,5,1,3),(10,'1787739778988036096','2024-05-07 15:45:56','测试',4,5,1,3),(11,'1787739778988036096','2024-05-07 15:56:02','你好',4,4,1,3),(12,'1787739778988036096','2024-05-07 15:57:35','你在干什么',4,5,1,3),(13,'1787739778988036096','2024-05-07 15:58:23','你在干什么',4,4,1,3),(14,'1787739778988036096','2024-05-07 16:01:50','你在干什么',5,4,1,2),(15,'1787739778988036096','2024-05-07 16:03:43','啥都不干',5,4,1,2),(16,'1787739778988036096','2024-05-07 16:04:35','你在说啥子',5,4,1,2),(17,'1787739778988036096','2024-05-07 16:05:37','哈哈哈哈',5,4,1,2),(18,'1787739778988036096','2024-05-07 16:07:10','喂喂喂',5,4,1,2),(19,'1787739778988036096','2024-05-07 16:11:14','喂喂喂',5,4,1,2),(20,'1787739778988036096','2024-05-07 16:11:24','啦啦啦',5,4,1,2),(21,'1787739778988036096','2024-05-07 16:11:33','什么事',4,5,1,3),(22,'1787739778988036096','2024-05-07 16:12:04','你想说什么',4,5,1,3),(23,'1787739778988036096','2024-05-07 16:12:18','你好',4,5,1,3),(24,'1787739778988036096','2024-05-07 16:12:34','瞎搞什么',5,4,1,2),(25,'1787739778988036096','2024-05-07 16:14:16','好好好好好好啊好好啊阿萨达大师实打实大萨达',4,5,1,3),(26,'1787739778988036096','2024-05-07 16:14:50','发发发发发发',4,5,1,3),(27,'1787739778988036096','2024-05-07 16:15:22','大大大',4,5,1,3),(28,'1787739778988036096','2024-05-07 16:15:27','哈哈哈',4,5,1,3),(29,'1787739778988036096','2024-05-07 16:15:33','呃呃问问',5,4,1,2),(30,'1787739778988036096','2024-05-07 16:15:43','呃呃问问',5,4,1,2),(31,'1787739778988036096','2024-05-07 16:15:44','呃呃问问',5,4,1,2),(32,'1787739778988036096','2024-05-07 16:15:44','呃呃问问',5,4,1,2),(33,'1787739778988036096','2024-05-07 16:17:49','呃呃问问',5,4,1,2),(34,'1787739778988036096','2024-05-07 16:24:25','呃呃问问',5,4,1,2),(35,'1787739778988036096','2024-05-07 16:24:26','呃呃问问',5,4,1,2),(36,'1787739778988036096','2024-05-07 16:24:26','呃呃问问',5,4,1,2),(37,'1787739778988036096','2024-05-07 16:26:11','大阿达阿达阿达大萨达撒旦撒阿达是的',5,4,1,2),(38,'1787669198116945920','2024-05-07 16:26:59','哒哒哒哒哒',4,2,1,3),(39,'1787669198116945920','2024-05-07 16:41:24','比比你',4,2,1,3),(40,'1787669198116945920','2024-05-07 16:52:40','巴巴爸爸',4,2,1,3),(41,'1787739778988036096','2024-05-07 16:53:09','而我我惹我惹我我奇尔韦尔',5,4,1,2),(42,'1787739778988036096','2024-05-07 17:00:26','而我我惹我惹我我奇尔韦尔',5,4,1,2),(43,'1787697350973280256','2024-05-07 21:13:37','哈喽，你好',4,5,1,3),(44,'1787697350973280256','2024-05-07 21:14:28','你在干什么',4,5,1,3),(45,'1787697350973280256','2024-05-07 22:17:31','我在写代码',5,4,1,2),(46,'1787697350973280256','2024-05-07 22:18:21','怎么啦',5,4,1,2),(47,'1787697350973280256','2024-05-07 22:18:44','搞笑的类',5,4,1,2),(48,'1787697350973280256','2024-05-07 22:45:37','什么搞笑',4,5,1,3),(49,'1787697350973280256','2024-05-07 22:47:52','你好',4,5,1,3),(50,'1787697350973280256','2024-05-07 22:49:15','测试',4,5,1,3),(51,'1787697350973280256','2024-05-07 22:51:25','哈哈哈哈',4,5,1,3),(52,'1787697350973280256','2024-05-07 22:52:01','顶顶顶顶',4,5,1,3),(53,'1787697350973280256','2024-05-07 22:52:58','祝你没打过',4,5,1,3),(54,'1787697350973280256','2024-05-07 22:53:12','撒',4,5,1,3),/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单编号',
  `price` decimal(10,2) DEFAULT NULL COMMENT '订单价格',
  `lawyer_user_id` int(11) DEFAULT NULL COMMENT '律师用户id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `status` int(11) DEFAULT '1' COMMENT '1-待付款 2-已付款 3-已完成',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--

/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

INSERT INTO law_applet.t_role
(id, role_name)
VALUES(1, '管理员');
INSERT INTO law_applet.t_role
(id, role_name)
VALUES(2, '律师');
INSERT INTO law_applet.t_role
(id, role_name)
VALUES(3, '用户');

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `user_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `consult_price` decimal(10,2) DEFAULT NULL COMMENT '咨询价格',
  `introduction` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `adept` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '擅长',
  `position` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '职位',
  `institution` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构',
  `img_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'admin','12345',1,'admin',NULL,NULL,NULL,NULL,NULL,NULL,'/file/1726989265153.jpg'),(2,'17212001242','123456',2,'王律师',NULL,10.00,'刘伟渊律师，男，汉族，中国籍，专职律师，广东潮州市饶平县人，中南财经政法大学法学本科，中山大学民商法研究生（在职结业）,执业于北京大成（广州）律师事务所， [1]现任北京大成（广州）律师事务所合伙人','魏磊律师长期从事合同原理、合同法律风险的研究及实际应用的操作。为客户起草、审查、修改过大量的合同，做到理论与实践充分的结合。一些合同文本作为范本被多次借鉴使用。','总裁','方正律师事务所','/file/1726989371847.jpg'),(4,'17381568598','123456',3,'王小二','2024-05-06 11:45:14',NULL,NULL,NULL,NULL,NULL,'/file/1726991999456.jpg'),(5,'17381568598','123456',2,'赵律师','2024-05-06 11:47:30',118.00,'精英律师','擅长所有法律问题','大神','方正律师事务所','/file/1726989326423.jpg');
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-10 12:04:26
