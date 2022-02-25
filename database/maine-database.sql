-- MySQL Script generated by MySQL Workbench
-- Fri Feb 25 14:43:17 2022
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
  `id` BIGINT UNSIGNED NOT NULL COMMENT '业务ID',
  `name` VARCHAR(16) NOT NULL COMMENT '用户名',
  `password` CHAR(128) NOT NULL COMMENT '密码',
  `password_status` TINYINT UNSIGNED NOT NULL COMMENT '密码状态\n\n0: 未设置密码\n1: 已设置密码\n',
  `phone` VARCHAR(16) NOT NULL COMMENT '手机号',
  `phone_status` TINYINT UNSIGNED NOT NULL COMMENT '手机号状态\n\n0: 未验证\n1: 已验证',
  `email` VARCHAR(32) NOT NULL COMMENT '邮箱',
  `email_status` TINYINT UNSIGNED NOT NULL COMMENT '邮箱状态\n\n0: 未验证\n1: 已验证',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `create_Time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maine`.`maine_attendance_record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maine`.`maine_attendance_record` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `create_Time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maine`.`maine_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maine`.`maine_group` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `owner_id` BIGINT UNSIGNED NOT NULL,
  `descript` TEXT NULL,
  `status` TINYINT NOT NULL,
  `create_Time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10000;


-- -----------------------------------------------------
-- Table `maine`.`maine_group_number`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maine`.`maine_group_number` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `group_id` BIGINT UNSIGNED NOT NULL,
  `create_Time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maine`.`maine_attendance_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maine`.`maine_attendance_task` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(64) NOT NULL,
  `description` TEXT NULL,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `create_Time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maine`.`maine_attendance_setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maine`.`maine_attendance_setting` (
  `attendance_id` BIGINT NOT NULL,
  PRIMARY KEY (`attendance_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
