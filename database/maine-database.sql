-- MySQL Script generated by MySQL Workbench
-- Fri Dec 24 14:08:07 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema maine
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema maine
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `maine` DEFAULT CHARACTER SET utf8mb4 ;
USE `maine` ;

-- -----------------------------------------------------
-- Table `maine`.`maine_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maine`.`maine_user` (
  `trace_id` BIGINT NOT NULL AUTO_INCREMENT,
  `id` BIGINT NOT NULL,
  `name` VARCHAR(16) NOT NULL,
  `password` CHAR(128) NOT NULL,
  `phone` VARCHAR(16) NOT NULL,
  `email` VARCHAR(32) NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0,
  `create_Time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`trace_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;