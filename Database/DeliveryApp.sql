-- MySQL Script generated by MySQL Workbench
-- Fri Nov 20 21:14:43 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema deliveryApp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema deliveryApp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `deliveryApp` DEFAULT CHARACTER SET utf8 ;
USE `deliveryApp` ;

-- -----------------------------------------------------
-- Table `deliveryApp`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`driverStatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`driverStatus` (
  `id` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idStatus` INT NULL,
  `idRole` INT NOT NULL,
  `firstName` VARCHAR(30) NOT NULL,
  `lastName` VARCHAR(30) NOT NULL,
  `age` VARCHAR(3) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `password` VARCHAR(70) NOT NULL,
  `phone` VARCHAR(15) NULL,
  `location` POINT NULL,
  `isDeleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `eMail_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`idRole` ASC) VISIBLE,
  INDEX `fk_user_driverStatus1_idx` (`idStatus` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`idRole`)
    REFERENCES `deliveryApp`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_driverStatus1`
    FOREIGN KEY (`idStatus`)
    REFERENCES `deliveryApp`.`driverStatus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`orderStatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`orderStatus` (
  `id` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idOrderStatus` INT NOT NULL,
  `idCustomer` INT NOT NULL,
  `idDriver` INT NULL,
  `locationDestination` POINT NOT NULL,
  `dateStart` TIMESTAMP NOT NULL,
  `dateEnd` TIMESTAMP NULL,
  `status` VARCHAR(45) NOT NULL,
  `totalAmount` DECIMAL NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user2_idx` (`idCustomer` ASC) VISIBLE,
  INDEX `fk_order_driver_idx` (`idDriver` ASC) VISIBLE,
  INDEX `fk_order_orderStatus1_idx` (`idOrderStatus` ASC) VISIBLE,
  CONSTRAINT `fk_order_customer`
    FOREIGN KEY (`idCustomer`)
    REFERENCES `deliveryApp`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_driver`
    FOREIGN KEY (`idDriver`)
    REFERENCES `deliveryApp`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_orderStatus1`
    FOREIGN KEY (`idOrderStatus`)
    REFERENCES `deliveryApp`.`orderStatus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`category` (
  `id` INT NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idCategory` INT NOT NULL,
  `price` DECIMAL NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `isDeleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_item_category1_idx` (`idCategory` ASC) VISIBLE,
  CONSTRAINT `fk_item_category1`
    FOREIGN KEY (`idCategory`)
    REFERENCES `deliveryApp`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Street` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `Building` VARCHAR(45) NULL,
  `Floor` VARCHAR(45) NULL,
  `location` POINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`routeCheckpoint`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`routeCheckpoint` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idOrder` INT NOT NULL,
  `time` TIMESTAMP NOT NULL,
  `location` POINT NOT NULL,
  PRIMARY KEY (`id`, `idOrder`),
  INDEX `fk_routePointsTaken_order1_idx` (`idOrder` ASC) VISIBLE,
  CONSTRAINT `fk_routePointsTaken_order1`
    FOREIGN KEY (`idOrder`)
    REFERENCES `deliveryApp`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`userSavedAddress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`userSavedAddress` (
  `idUser` INT NOT NULL,
  `idAddress` INT NOT NULL,
  PRIMARY KEY (`idUser`, `idAddress`),
  INDEX `fk_user_has_Address_Address1_idx` (`idAddress` ASC) VISIBLE,
  INDEX `fk_user_has_Address_user1_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_Address_user1`
    FOREIGN KEY (`idUser`)
    REFERENCES `deliveryApp`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_Address_Address1`
    FOREIGN KEY (`idAddress`)
    REFERENCES `deliveryApp`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`feedback` (
  `idUser` INT NOT NULL,
  `idOrder` INT NOT NULL,
  `description` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`idUser`, `idOrder`),
  INDEX `fk_user_has_order_order1_idx` (`idOrder` ASC) VISIBLE,
  INDEX `fk_user_has_order_user1_idx` (`idUser` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_order_user1`
    FOREIGN KEY (`idUser`)
    REFERENCES `deliveryApp`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_order_order1`
    FOREIGN KEY (`idOrder`)
    REFERENCES `deliveryApp`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`warehouse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`warehouse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idAddress` INT NOT NULL,
  `isDeleted` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_warehouse_address1_idx` (`idAddress` ASC) VISIBLE,
  CONSTRAINT `fk_warehouse_address1`
    FOREIGN KEY (`idAddress`)
    REFERENCES `deliveryApp`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`warehouseItem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`warehouseItem` (
  `idWarehouse` INT NOT NULL,
  `idItem` INT NOT NULL,
  `itemQuantity` INT(3) NOT NULL,
  `isDisabled` TINYINT NOT NULL,
  PRIMARY KEY (`idWarehouse`, `idItem`),
  INDEX `fk_warehouse_has_item_item1_idx` (`idItem` ASC) VISIBLE,
  INDEX `fk_warehouse_has_item_warehouse1_idx` (`idWarehouse` ASC) VISIBLE,
  CONSTRAINT `fk_warehouse_has_item_warehouse1`
    FOREIGN KEY (`idWarehouse`)
    REFERENCES `deliveryApp`.`warehouse` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_warehouse_has_item_item1`
    FOREIGN KEY (`idItem`)
    REFERENCES `deliveryApp`.`item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `deliveryApp`.`orderedWarehouseItem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `deliveryApp`.`orderedWarehouseItem` (
  `idOrder` INT NOT NULL,
  `idWarehouse` INT NOT NULL,
  `idItem` INT NOT NULL,
  `orderedQuantity` INT(3) NOT NULL,
  `unitaryItemPrice` DECIMAL NOT NULL,
  PRIMARY KEY (`idOrder`, `idWarehouse`, `idItem`),
  INDEX `fk_order_has_warehouseItem_warehouseItem1_idx` (`idWarehouse` ASC, `idItem` ASC) VISIBLE,
  INDEX `fk_order_has_warehouseItem_order1_idx` (`idOrder` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_warehouseItem_order1`
    FOREIGN KEY (`idOrder`)
    REFERENCES `deliveryApp`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_warehouseItem_warehouseItem1`
    FOREIGN KEY (`idWarehouse` , `idItem`)
    REFERENCES `deliveryApp`.`warehouseItem` (`idWarehouse` , `idItem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `deliveryApp`;

DELIMITER $$
USE `deliveryApp`$$
CREATE DEFINER = CURRENT_USER TRIGGER `deliveryApp`.`user_AFTER_UPDATE` AFTER UPDATE ON `user` FOR EACH ROW
BEGIN
	if New.isDeleted=1 then 
		update userSavedAddress set isDisabled=1 where userSavedAddress.idUser = New.id;
	end if;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
