-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema chatroom
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema chatroom
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `chatroom` DEFAULT CHARACTER SET utf8 ;
USE `chatroom` ;

-- -----------------------------------------------------
-- Table `chatroom`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatroom`.`user` (
  `id` INT NOT NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatroom`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatroom`.`friends` (
  `id` INT NOT NULL,
  `userid_1` INT NULL,
  `userid_2` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `user1_idx` (`userid_1` ASC) VISIBLE,
  INDEX `user2_idx` (`userid_2` ASC) VISIBLE,
  CONSTRAINT `user1`
    FOREIGN KEY (`userid_1`)
    REFERENCES `chatroom`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user2`
    FOREIGN KEY (`userid_2`)
    REFERENCES `chatroom`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatroom`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatroom`.`room` (
  `id` INT NOT NULL,
  `type` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatroom`.`room_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatroom`.`room_member` (
  `id` INT NOT NULL,
  `user_id` INT NULL,
  `room_id` INT NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `user_idx` (`user_id` ASC) VISIBLE,
  INDEX `room_user_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `user`
    FOREIGN KEY (`user_id`)
    REFERENCES `chatroom`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `room_user`
    FOREIGN KEY (`room_id`)
    REFERENCES `chatroom`.`room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatroom`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatroom`.`message` (
  `id` INT NOT NULL,
  `date` VARCHAR(40) NULL,
  `data` BLOB NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatroom`.`room_msg`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatroom`.`room_msg` (
  `id` INT NOT NULL,
  `room_id` INT NULL,
  `msg_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `room_idx` (`room_id` ASC) VISIBLE,
  INDEX `message_idx` (`msg_id` ASC) VISIBLE,
  CONSTRAINT `room_idx0`
    FOREIGN KEY (`room_id`)
    REFERENCES `chatroom`.`room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `message_room`
    FOREIGN KEY (`msg_id`)
    REFERENCES `chatroom`.`message` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
