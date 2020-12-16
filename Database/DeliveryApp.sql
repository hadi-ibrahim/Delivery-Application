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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (9,1,'Main Rd','Nabay','Emile Ghoul Bldg','Floor 1',35.61736390,33.89799255),(10,1,'Hadath','Hadath','','',35.53621070,33.83463376);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (18,'FOOD',7.50,'Labneh Box 500G',0),(19,'FOOD',20.00,'Chicken Breasts 1KG',0),(20,'GROCERY',25.00,'Persil 20KG',0),(21,'GROCERY',5.00,'Toothbrush Colgate',0),(22,'WEAPON',30.00,'Shovel',0),(23,'WEAPON',25.00,'Razor',0);
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
  `isDeleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_order_user2_idx` (`idCustomer`),
  KEY `fk_order_driver_idx` (`idDriver`),
  CONSTRAINT `fk_order_customer` FOREIGN KEY (`idCustomer`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_order_driver` FOREIGN KEY (`idDriver`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (3,1,2,35.61736390,33.89799255,'2020-12-16 00:36:08',NULL,'COMPLETED',37.50,0),(4,1,2,35.53621070,33.83463376,'2020-12-16 00:38:57','2020-12-16 00:58:36','COMPLETED',140.00,0),(5,1,2,35.61736390,33.89799255,'2020-12-16 01:05:44','2020-12-16 01:08:15','COMPLETED',37.50,0),(6,1,2,35.53621070,33.83463376,'2020-12-16 01:08:08',NULL,'ACCEPTED',7.50,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderedwarehouseitem`
--

LOCK TABLES `orderedwarehouseitem` WRITE;
/*!40000 ALTER TABLE `orderedwarehouseitem` DISABLE KEYS */;
INSERT INTO `orderedwarehouseitem` VALUES (6,8,3,5,7.50),(7,15,4,2,25.00),(8,16,4,3,30.00),(9,17,5,5,7.50),(10,8,6,1,7.50);
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
  `longitude` decimal(11,8) NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `isDeleted` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_routePointsTaken_order1_idx` (`idOrder`),
  CONSTRAINT `fk_routePointsTaken_order1` FOREIGN KEY (`idOrder`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routecheckpoint`
--

LOCK TABLES `routecheckpoint` WRITE;
/*!40000 ALTER TABLE `routecheckpoint` DISABLE KEYS */;
INSERT INTO `routecheckpoint` VALUES (9,3,'2020-12-16 00:47:20',35.25450830,33.32428194,0),(10,3,'2020-12-16 00:47:42',35.27899190,33.39534137,0),(11,3,'2020-12-16 00:47:59',35.31822900,33.46160672,0),(12,3,'2020-12-16 00:48:20',35.44825260,33.70254359,0),(13,3,'2020-12-16 00:48:22',35.53976250,33.88893967,0),(14,3,'2020-12-16 00:48:42',35.54843930,33.88555131,0),(15,3,'2020-12-16 00:48:57',35.56663920,33.89663320,0),(16,3,'2020-12-16 00:48:58',35.61736390,33.89799255,0),(18,4,'2020-12-16 00:58:35',35.68166740,33.92058059,0),(19,4,'2020-12-16 00:58:36',35.53621070,33.83463376,0),(20,5,'2020-12-16 01:05:56',35.82286810,34.44422900,0),(21,5,'2020-12-16 01:08:15',35.61736390,33.89799255,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Customer','Ghoul','21','1','$2a$12$rmMNEOL5alPKOm6CNjTslOBJWZmro5k1KR2NZH88X.K.cWtOsZdXu','11',NULL,NULL,'CUSTOMER',NULL,0),(2,'Courier','Swindler','2','2','$2a$12$.V1cpHQ70B3Y5G28uE4N8.MkwaJRvReu4KtGYltG3Np0Uf07zL5yG','2',35.61736390,33.89799255,'DRIVER','BUSY',0),(3,'ADMIN','ADMIN','3','3','$2a$12$nXs1PpymlGJNdmmx4L2fX.fg69A1RRY7YrvVOL2LvMUZDqP6P9d/i','3',NULL,NULL,'ADMIN',NULL,0),(4,'Fatima','Ibrahim','31','toutydroid@gmail.com','$2a$12$HVbFoqsxx8F8AKhQ7HEpf.4wCVp4SumxPR/9WzVZ5EvBWmyOQcCYi','70123768',NULL,NULL,'CUSTOMER',NULL,0),(6,'david','ghoul','21','david_ghoul@hotmail.com','$2a$12$kVARRY68sedlHRpMPWFzg.JPyChWD2HdxYbA1uoPqH6PtdwIRFj1O','12345678',NULL,NULL,'CUSTOMER',NULL,0),(7,'David','Test321','21','test123@gmail.com','$2a$12$Q2fj82SH9vkRfAm9.LT/.ulFLU/CKl.nBsNfAmyrOT4hQ1rjRSgSC','70123456',NULL,NULL,'CUSTOMER',NULL,0),(9,'Courier','Driver2','25','driver2@gmail.com','$2a$12$hY4Vi3LUSWEfzhEq/uMQjuE0Zb34ZN.bW2VDho11q0TAB..Ob4Ory','12345679',NULL,NULL,'DRIVER',NULL,0),(10,'Driver 3 ','Courrier','35','driver3@gmail.com','$2a$12$6STKAAb9hulyLh.P7y3daO2mntNu7.FL4TEQJa5oZccqBveTrGRKW','14235768',NULL,NULL,'DRIVER',NULL,0),(11,'Driver 4','Courrier','21','Driver4@gmail.com','$2a$12$glCj42ISUcRx4M4KcKJzKuzR6h7Syum1wIP7gIxMD.Z3KKaiGTk72','15643728',NULL,NULL,'DRIVER',NULL,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (13,'Burj Hammoud',0,35.53976250,33.88893967),(14,'Bikfaya',0,35.68166740,33.92058059),(15,'Tripolo',0,35.82286810,34.44422900);
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouseitem`
--

LOCK TABLES `warehouseitem` WRITE;
/*!40000 ALTER TABLE `warehouseitem` DISABLE KEYS */;
INSERT INTO `warehouseitem` VALUES (8,13,18,99,0),(9,13,19,50,0),(10,13,20,20,0),(11,13,21,250,0),(12,13,22,10,0),(13,13,23,35,0),(14,14,18,10,0),(15,14,20,10,0),(16,14,22,5,0),(17,15,18,350,0);
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

-- Dump completed on 2020-12-16  3:09:23
