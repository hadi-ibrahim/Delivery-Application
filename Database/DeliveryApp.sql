CREATE DATABASE  IF NOT EXISTS `deliveryapp` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `deliveryapp`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: deliveryapp
-- ------------------------------------------------------
-- Server version	8.0.21

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
  `idUser` int NOT NULL,
  `Street` varchar(45) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `Building` varchar(45) DEFAULT NULL,
  `Floor` varchar(45) DEFAULT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkUser_idx` (`idUser`),
  CONSTRAINT `fkUserAddress` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,1,NULL,NULL,'UA',NULL,22.44000000,23.66600000),(2,1,'Elias al Herawi','Furn al chebbek','Residence de la ville','2nd floor',55.12312400,180.24242000),(3,1,NULL,NULL,NULL,NULL,22.44000000,23.66600000),(4,1,'Elias al Herawi','Furn al chebbek','Residence de la ville','2nd floor',55.12312400,180.24242000),(5,1,NULL,NULL,NULL,NULL,22.44000000,23.66600000),(6,1,'Elias al Herawi','Furn al chebbek','Residence de la ville','2nd floor',55.12312400,180.24242000),(7,1,NULL,NULL,NULL,NULL,22.44000000,23.66600000),(8,1,'Elias al Herawi','Furn al chebbek','Residence de la ville','2nd floor',55.12312400,180.24242000);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUser` int NOT NULL,
  `idOrder` int NOT NULL,
  `description` varchar(300) NOT NULL,
  `isDeleted` tinyint NOT NULL,
  PRIMARY KEY (`id`),
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
  `category` varchar(45) NOT NULL,
  `price` decimal(6,2) NOT NULL,
  `description` varchar(100) NOT NULL,
  `isDeleted` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'GROCERY',12.00,'2',0),(2,'WEAPON',23.00,'212',1),(3,'GROCERY',4.00,'3',0),(4,'WEAPON',231.00,'313',1),(5,'FOOD',2.00,'3',0),(6,'FOOD',142.00,'342',1),(7,'GROCERY',231.00,'test',1),(8,'FOOD',12.00,'test',0),(9,'FOOD',231.00,'214',1),(10,'FOOD',12.00,'12',0),(11,'FOOD',15.30,'Heart attack burger',1),(12,'FOOD',15.30,'Heart attack burger',0),(13,'FOOD',15.30,'Heart attack burger',1),(14,'FOOD',15.30,'Heart attack burger',0),(15,'GROCERY',85.00,'15',0);
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
  `idCustomer` int NOT NULL,
  `idDriver` int DEFAULT NULL,
  `DestinationLongitude` decimal(11,8) NOT NULL,
  `DestinationLatitude` decimal(11,8) NOT NULL,
  `dateStart` timestamp NOT NULL,
  `dateEnd` timestamp NULL DEFAULT NULL,
  `orderStatus` varchar(45) NOT NULL,
  `totalAmount` decimal(9,2) NOT NULL,
  `isDeleted` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_user2_idx` (`idCustomer`),
  KEY `fk_order_driver_idx` (`idDriver`),
  CONSTRAINT `fk_order_customer` FOREIGN KEY (`idCustomer`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_order_driver` FOREIGN KEY (`idDriver`) REFERENCES `user` (`id`)
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
  `id` int NOT NULL AUTO_INCREMENT,
  `idWarehouseItem` int NOT NULL,
  `idOrder` int NOT NULL,
  `orderedQuantity` int NOT NULL,
  `pricePerUnit` decimal(6,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_has_warehouseItem_order1_idx` (`idOrder`),
  KEY `fk_orderedWarehouseItem_warehouseItem1_idx` (`idWarehouseItem`),
  CONSTRAINT `fk_order_has_warehouseItem_order1` FOREIGN KEY (`idOrder`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_orderedWarehouseItem_warehouseItem1` FOREIGN KEY (`idWarehouseItem`) REFERENCES `warehouseitem` (`id`)
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
-- Table structure for table `routecheckpoint`
--

DROP TABLE IF EXISTS `routecheckpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routecheckpoint` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idOrder` int NOT NULL,
  `time` timestamp NOT NULL,
  `longitutde` decimal(11,8) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `isDeleted` tinyint NOT NULL,
  PRIMARY KEY (`id`),
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
  `longitude` decimal(10,8) DEFAULT NULL,
  `latitude` decimal(11,8) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `driverStatus` varchar(45) DEFAULT NULL,
  `isDeleted` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `eMail_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'1','1','1','1','$2a$12$rmMNEOL5alPKOm6CNjTslOBJWZmro5k1KR2NZH88X.K.cWtOsZdXu','11',NULL,NULL,'CUSTOMER',NULL,0),(2,'Courier','Swindler','2','2','$2a$12$.V1cpHQ70B3Y5G28uE4N8.MkwaJRvReu4KtGYltG3Np0Uf07zL5yG','2',23.23234240,130.23310000,'DRIVER','AVAILABLE',0),(3,'3','3','3','3','$2a$12$nXs1PpymlGJNdmmx4L2fX.fg69A1RRY7YrvVOL2LvMUZDqP6P9d/i','3',NULL,NULL,'ADMIN',NULL,0),(4,'Fatima','Ibrahim','31','toutydroid@gmail.com','$2a$12$HVbFoqsxx8F8AKhQ7HEpf.4wCVp4SumxPR/9WzVZ5EvBWmyOQcCYi','76805019',NULL,NULL,'CUSTOMER',NULL,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `isDeleted` tinyint NOT NULL DEFAULT '0',
  `longitude` decimal(11,8) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,'Carrefour',0,123.00000000,12.00000000),(2,'Junkies',1,123.23000000,12.00000000),(4,'Home',0,35.52240095,33.87492245),(5,'21',0,35.53292774,33.87255912),(6,'213',0,35.88723682,33.91188747),(7,'132',1,35.49996876,33.81781125),(8,'jounieh',0,35.65309070,33.94208389),(9,'matar beirut',0,35.49267060,33.81902263),(10,'tewifewfe',1,35.53361430,33.85716482),(11,'Beriut Airport',0,35.49450700,33.81924747);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouseitem`
--

DROP TABLE IF EXISTS `warehouseitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouseitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idWarehouse` int NOT NULL,
  `idItem` int NOT NULL,
  `itemQuantity` int NOT NULL,
  `isDeleted` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_warehouse_has_item_item1_idx` (`idItem`),
  KEY `fk_warehouse_has_item_warehouse1_idx` (`idWarehouse`),
  CONSTRAINT `fk_warehouse_has_item_item1` FOREIGN KEY (`idItem`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_warehouse_has_item_warehouse1` FOREIGN KEY (`idWarehouse`) REFERENCES `warehouse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouseitem`
--

LOCK TABLES `warehouseitem` WRITE;
/*!40000 ALTER TABLE `warehouseitem` DISABLE KEYS */;
INSERT INTO `warehouseitem` VALUES (1,1,1,100,0);
/*!40000 ALTER TABLE `warehouseitem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-15 16:12:52
