-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema pathological
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pathological
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pathological` DEFAULT CHARACTER SET utf8mb3 ;
USE `pathological` ;

-- -----------------------------------------------------
-- Table `pathological`.`consumable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pathological`.`consumable` (
  `consumable_id` INT NOT NULL,
  `consumable_name` VARCHAR(45) NOT NULL,
  `consumable_description` LONGTEXT NOT NULL,
  `consumable_hp_mod` INT NOT NULL,
  `consumable_mp_mod` INT NOT NULL,
  PRIMARY KEY (`consumable_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

INSERT INTO consumable VALUES
(1, "Small HP Potion", "A potioncrafter's weakest potion, healing only the smallest of cuts (Heal +5HP)", 5, 0),
(2, "Medium HP Potion", "A potioncrafter's medium sized potion. Drinking it makes you feel like you just woken up from a good nap (Heal +10HP)", 10, 0),
(3, "Large HP Potion", "An abnormally large sized potion, No idea how you can fit multiple of these in your pocket (Heal +20HP)", 20, 0),
(4, "Mana Potion", "A potion with a refreshing taste, leaving you feeling re-energized (Restore +5MP)", 0, 5);
-- -----------------------------------------------------
-- Table `pathological`.`equipment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pathological`.`equipment` (
  `equipment_id` INT NOT NULL,
  `equipment_name` VARCHAR(45) NOT NULL,
  `equipment_description` LONGTEXT NOT NULL,
  `equipment_hp` INT NOT NULL,
  `equipment_atk` INT NOT NULL,
  `equipment_def` INT NOT NULL,
  PRIMARY KEY (`equipment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

INSERT INTO equipment VALUES
(1, "Copper Helmet", "A helmet made of copper built to help protect fresh adventurers", 5, 0, 2),
(2, "Copper Chestpiece", "A chestpiece made of copper capable of blocking attacks from moderately built weapons", 5, 0, 5),
(3, "Copper Sword", "A cheaply made sword sturdy enough to deal damage to weak foes", 0, 3, 1);
-- -----------------------------------------------------
-- Table `pathological`.`player_run`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pathological`.`player_run` (
  `run_id` INT NOT NULL,
  `atk` INT NOT NULL,
  `def` INT NOT NULL,
  `hp` INT NOT NULL,
  `mp` INT NOT NULL,
  `pathFloor` INT NOT NULL,
  `activeStatus` VARCHAR(45) NOT NULL,
  `remainingHP` INT NOT NULL,
  `remainingMP` INT NOT NULL,
  PRIMARY KEY (`run_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pathological`.`current_equipment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pathological`.`current_equipment` (
  `run_id` INT NOT NULL,
  `index` INT NOT NULL,
  `equipment_equipment_id` INT NOT NULL,
  PRIMARY KEY (`run_id`, `index`),
  INDEX `fk_player_run_has_equipment_equipment1_idx` (`equipment_equipment_id` ASC) VISIBLE,
  INDEX `fk_player_run_has_equipment_player_run1_idx` (`run_id` ASC) VISIBLE,
  CONSTRAINT `fk_player_run_has_equipment_equipment1`
    FOREIGN KEY (`equipment_equipment_id`)
    REFERENCES `pathological`.`equipment` (`equipment_id`),
  CONSTRAINT `fk_player_run_has_equipment_player_run1`
    FOREIGN KEY (`run_id`)
    REFERENCES `pathological`.`player_run` (`run_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pathological`.`inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pathological`.`inventory` (
  `run_id` INT NOT NULL,
  `index` VARCHAR(45) NOT NULL,
  `items_item_id` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'INACTIVE',
  PRIMARY KEY (`run_id`, `index`),
  INDEX `fk_player_run_has_items_items1_idx` (`items_item_id` ASC) VISIBLE,
  INDEX `fk_player_run_has_items_player_run_idx` (`run_id` ASC) VISIBLE,
  CONSTRAINT `fk_player_run_has_items_items1`
    FOREIGN KEY (`items_item_id`)
    REFERENCES `pathological`.`consumable` (`consumable_id`),
  CONSTRAINT `fk_player_run_has_items_player_run`
    FOREIGN KEY (`run_id`)
    REFERENCES `pathological`.`player_run` (`run_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `pathological`.`skills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pathological`.`skills` (
  `skill_id` INT NOT NULL,
  `skill_name` VARCHAR(45) NOT NULL,
  `skill_description` LONGTEXT NOT NULL,
  `skill_modifier` DECIMAL(4,2) NOT NULL,
  `mp_cost` INT NOT NULL,
  PRIMARY KEY (`skill_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

INSERT INTO skills VALUES
(1, "Power Slash", "A powerful slash dealing extra damage to a single foe", 1.3, 5),
(2, "Heavy Strike", "A strong winded attck that does hefty damage", 2.5, 15),
(3, "Reverse Strike", "Surprise your foe with a reverse slash", 1.5, 2),
(4, "Rock Debris Slash", "There are plenty of rocks around you, why not?", 2, 10);
-- -----------------------------------------------------
-- Table `pathological`.`skills_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pathological`.`skills_list` (
  `run_id` INT NOT NULL,
  `index` VARCHAR(45) NOT NULL,
  `skills_skill_id` INT NOT NULL,
  PRIMARY KEY (`run_id`, `skills_skill_id`),
  INDEX `fk_player_run_has_skills_skills1_idx` (`skills_skill_id` ASC) VISIBLE,
  INDEX `fk_player_run_has_skills_player_run1_idx` (`run_id` ASC) VISIBLE,
  CONSTRAINT `fk_player_run_has_skills_player_run1`
    FOREIGN KEY (`run_id`)
    REFERENCES `pathological`.`player_run` (`run_id`),
  CONSTRAINT `fk_player_run_has_skills_skills1`
    FOREIGN KEY (`skills_skill_id`)
    REFERENCES `pathological`.`skills` (`skill_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
