CREATE DATABASE  IF NOT EXISTS `deliveryapp` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `deliveryapp`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: deliveryapp
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Street` varchar(45) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `Building` varchar(45) DEFAULT NULL,
  `Floor` varchar(45) DEFAULT NULL,
  `longitude` decimal(10,8) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,NULL,NULL,NULL,NULL,22.44000000,23.66600000),(2,'Elias al Herawi','Furn al chebbek','Residence de la ville','2nd floor',55.12312400,180.24242000),(3,NULL,NULL,NULL,NULL,22.44000000,23.66600000),(4,NULL,NULL,NULL,NULL,22.44000000,23.66600000);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_UNIQUE` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Food');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driverstatus`
--

DROP TABLE IF EXISTS `driverstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driverstatus` (
  `id` int NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driverstatus`
--

LOCK TABLES `driverstatus` WRITE;
/*!40000 ALTER TABLE `driverstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `driverstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `idUser` int NOT NULL,
  `idOrder` int NOT NULL,
  `description` varchar(300) NOT NULL,
  PRIMARY KEY (`idUser`,`idOrder`),
  KEY `fk_user_has_order_order1_idx` (`idOrder`),
  KEY `fk_user_has_order_user1_idx` (`idUser`),
  CONSTRAINT `fk_user_has_order_order1` FOREIGN KEY (`idOrder`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_user_has_order_user1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) NOT NULL,
  `description` varchar(100) NOT NULL,
  `isDeleted` tinyint NOT NULL DEFAULT '0',
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,15.30,'Heart attack burger',0,'FOOD');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idOrderStatus` int NOT NULL,
  `idCustomer` int NOT NULL,
  `idDriver` int DEFAULT NULL,
  `locationDestination` point NOT NULL,
  `dateStart` timestamp NOT NULL,
  `dateEnd` timestamp NULL DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `totalAmount` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_user2_idx` (`idCustomer`),
  KEY `fk_order_driver_idx` (`idDriver`),
  KEY `fk_order_orderStatus1_idx` (`idOrderStatus`),
  CONSTRAINT `fk_order_customer` FOREIGN KEY (`idCustomer`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_order_driver` FOREIGN KEY (`idDriver`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_order_orderStatus1` FOREIGN KEY (`idOrderStatus`) REFERENCES `orderstatus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderedwarehouseitem`
--

DROP TABLE IF EXISTS `orderedwarehouseitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderedwarehouseitem` (
  `idOrder` int NOT NULL,
  `idWarehouse` int NOT NULL,
  `idItem` int NOT NULL,
  `orderedQuantity` int NOT NULL,
  `unitaryItemPrice` decimal(10,0) NOT NULL,
  PRIMARY KEY (`idOrder`,`idWarehouse`,`idItem`),
  KEY `fk_order_has_warehouseItem_warehouseItem1_idx` (`idWarehouse`,`idItem`),
  KEY `fk_order_has_warehouseItem_order1_idx` (`idOrder`),
  CONSTRAINT `fk_order_has_warehouseItem_order1` FOREIGN KEY (`idOrder`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_order_has_warehouseItem_warehouseItem1` FOREIGN KEY (`idWarehouse`, `idItem`) REFERENCES `warehouseitem` (`idWarehouse`, `idItem`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderedwarehouseitem`
--

LOCK TABLES `orderedwarehouseitem` WRITE;
/*!40000 ALTER TABLE `orderedwarehouseitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderedwarehouseitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderstatus`
--

DROP TABLE IF EXISTS `orderstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderstatus` (
  `id` int NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderstatus`
--

LOCK TABLES `orderstatus` WRITE;
/*!40000 ALTER TABLE `orderstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routecheckpoint`
--

DROP TABLE IF EXISTS `routecheckpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routecheckpoint` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idOrder` int NOT NULL,
  `time` timestamp NOT NULL,
  `location` point NOT NULL,
  PRIMARY KEY (`id`,`idOrder`),
  KEY `fk_routePointsTaken_order1_idx` (`idOrder`),
  CONSTRAINT `fk_routePointsTaken_order1` FOREIGN KEY (`idOrder`) REFERENCES `order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routecheckpoint`
--

LOCK TABLES `routecheckpoint` WRITE;
/*!40000 ALTER TABLE `routecheckpoint` DISABLE KEYS */;
/*!40000 ALTER TABLE `routecheckpoint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `age` varchar(3) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(70) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `isDeleted` tinyint NOT NULL,
  `role` varchar(45) DEFAULT NULL,
  `longitude` decimal(10,8) DEFAULT NULL,
  `latitude` decimal(11,8) DEFAULT NULL,
  `driverStatus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `eMail_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'Hadi','Ibrahim','20','hadi@gmail.com','$2a$12$0luLhfNVHCbWzlhVTBbpA.jnNzUAxgYArtl2081YpdeTPd4Ku78u.','70464255',0,'CUSTOMER',0.00000000,0.00000000,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersavedaddress`
--

DROP TABLE IF EXISTS `usersavedaddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usersavedaddress` (
  `idUser` int NOT NULL,
  `idAddress` int NOT NULL,
  PRIMARY KEY (`idUser`,`idAddress`),
  KEY `fk_user_has_Address_Address1_idx` (`idAddress`),
  KEY `fk_user_has_Address_user1_idx` (`idUser`),
  CONSTRAINT `fk_user_has_Address_Address1` FOREIGN KEY (`idAddress`) REFERENCES `address` (`id`),
  CONSTRAINT `fk_user_has_Address_user1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersavedaddress`
--

LOCK TABLES `usersavedaddress` WRITE;
/*!40000 ALTER TABLE `usersavedaddress` DISABLE KEYS */;
/*!40000 ALTER TABLE `usersavedaddress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idAddress` int NOT NULL,
  `isDeleted` tinyint NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_warehouse_address1_idx` (`idAddress`),
  CONSTRAINT `fk_warehouse_address1` FOREIGN KEY (`idAddress`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,2,0,'Junkies');
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouseitem`
--

DROP TABLE IF EXISTS `warehouseitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouseitem` (
  `idWarehouse` int NOT NULL,
  `idItem` int NOT NULL,
  `itemQuantity` int NOT NULL,
  `isDisabled` tinyint NOT NULL,
  PRIMARY KEY (`idWarehouse`,`idItem`),
  KEY `fk_warehouse_has_item_item1_idx` (`idItem`),
  KEY `fk_warehouse_has_item_warehouse1_idx` (`idWarehouse`),
  CONSTRAINT `fk_warehouse_has_item_item1` FOREIGN KEY (`idItem`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_warehouse_has_item_warehouse1` FOREIGN KEY (`idWarehouse`) REFERENCES `warehouse` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouseitem`
--

LOCK TABLES `warehouseitem` WRITE;
/*!40000 ALTER TABLE `warehouseitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouseitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'deliveryapp'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-21  3:50:20
