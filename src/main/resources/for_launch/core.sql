-- MySQL Script generated by MySQL Workbench
-- Fri May 11 20:38:14 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema core
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `core` DEFAULT CHARACTER SET latin1 ;
USE `core` ;

-- -----------------------------------------------------
-- Table `core`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`roles` (
  `id` INT(11) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `core`.`operations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`operations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `url_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `core`.`access_rules`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`access_rules` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_id` INT(11) NOT NULL,
  `op_id` INT(11) NOT NULL,
  `post` TINYINT(4) NOT NULL,
  `get` TINYINT(4) NOT NULL,
  `put` TINYINT(4) NOT NULL,
  `delete` TINYINT(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `op_id_idx` (`op_id` ASC),
  INDEX `fk_role_id_idx` (`role_id` ASC),
  CONSTRAINT `fk_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `core`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `op_id`
    FOREIGN KEY (`op_id`)
    REFERENCES `core`.`operations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `core`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`accounts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  INDEX `role_id_idx` (`role_id` ASC),
  CONSTRAINT `role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `core`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`disciplines`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`disciplines` (
  id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL);


-- -----------------------------------------------------
-- Table `core`.`curriculums`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`curriculums` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`semesters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`semesters` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`groups` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NOT NULL,
  `curriculums_id` INT(11) NOT NULL,
  `current_semester_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `curriculums_id`, `current_semester_id`),
  INDEX `fk_groups_curriculums1_idx` (`curriculums_id` ASC),
  INDEX `fk_groups_semesters1_idx` (`current_semester_id` ASC),
  CONSTRAINT `fk_groups_curriculums1`
    FOREIGN KEY (`curriculums_id`)
    REFERENCES `core`.`curriculums` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_groups_semesters1`
    FOREIGN KEY (`current_semester_id`)
    REFERENCES `core`.`semesters` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`teachers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`teachers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `accounts_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `accounts_id`),
  INDEX `fk_teachers_accounts1_idx` (`accounts_id` ASC),
  CONSTRAINT `fk_teachers_accounts1`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `core`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`currentdeals`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`currentdeals` (
  `report_id` INT(11) NOT NULL AUTO_INCREMENT,
  `groups_id` INT(11) NOT NULL,
  `disciplines_id` INT(11) NOT NULL,
  `teachers_id` INT(11) NOT NULL,
  PRIMARY KEY (`report_id`, `groups_id`, `disciplines_id`, `teachers_id`),
  INDEX `fk_current_deals_groups1_idx` (`groups_id` ASC),
  INDEX `fk_current_deals_disciplines1_idx` (`disciplines_id` ASC),
  INDEX `fk_current_deals_teachers1_idx` (`teachers_id` ASC),
  CONSTRAINT `fk_current_deals_disciplines1`
    FOREIGN KEY (`disciplines_id`)
    REFERENCES `core`.`disciplines` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_current_deals_groups1`
    FOREIGN KEY (`groups_id`)
    REFERENCES `core`.`groups` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_current_deals_teachers1`
    FOREIGN KEY (`teachers_id`)
    REFERENCES `core`.`teachers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`students` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `groups_id` INT(11) NOT NULL,
  `accounts_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `groups_id`, `accounts_id`),
  INDEX `fk_students_groups_idx` (`groups_id` ASC),
  INDEX `fk_students_accounts1_idx` (`accounts_id` ASC),
  CONSTRAINT `fk_students_accounts1`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `core`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_students_groups`
    FOREIGN KEY (`groups_id`)
    REFERENCES `core`.`groups` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`drepnotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`drepnotes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `mark` INT(11) NOT NULL,
  `students_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`, `students_id`),
  INDEX `fk_drepnotes_students1_idx` (`students_id` ASC),
  CONSTRAINT `fk_drepnotes_students1`
    FOREIGN KEY (`students_id`)
    REFERENCES `core`.`students` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`currentdeals_drepnotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`currentdeals_drepnotes` (
  `currentdeals_report_id` INT(11) NOT NULL,
  `drepnotes_id` INT(11) NOT NULL,
  PRIMARY KEY (`currentdeals_report_id`, `drepnotes_id`),
  INDEX `fk_currentdeals_has_drepnotes_drepnotes1_idx` (`drepnotes_id` ASC),
  INDEX `fk_currentdeals_has_drepnotes_currentdeals1_idx` (`currentdeals_report_id` ASC),
  CONSTRAINT `fk_currentdeals_has_drepnotes_currentdeals1`
    FOREIGN KEY (`currentdeals_report_id`)
    REFERENCES `core`.`currentdeals` (`report_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_currentdeals_has_drepnotes_drepnotes1`
    FOREIGN KEY (`drepnotes_id`)
    REFERENCES `core`.`drepnotes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`curriculums_semesters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`curriculums_semesters` (
  `curriculums_id` INT(11) NOT NULL,
  `semesters_id` INT(11) NOT NULL,
  PRIMARY KEY (`curriculums_id`, `semesters_id`),
  INDEX `fk_curriculums_has_semesters_semesters1_idx` (`semesters_id` ASC),
  INDEX `fk_curriculums_has_semesters_curriculums1_idx` (`curriculums_id` ASC),
  CONSTRAINT `fk_curriculums_has_semesters_curriculums1`
    FOREIGN KEY (`curriculums_id`)
    REFERENCES `core`.`curriculums` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_curriculums_has_semesters_semesters1`
    FOREIGN KEY (`semesters_id`)
    REFERENCES `core`.`semesters` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`disciplines_teachers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`disciplines_teachers` (
  `disciplines_id` INT(11) NOT NULL,
  `teachers_id` INT(11) NOT NULL,
  PRIMARY KEY (`disciplines_id`, `teachers_id`),
  INDEX `fk_disciplines_has_teachers_teachers1_idx` (`teachers_id` ASC),
  INDEX `fk_disciplines_has_teachers_disciplines1_idx` (`disciplines_id` ASC),
  CONSTRAINT `fk_disciplines_has_teachers_disciplines1`
    FOREIGN KEY (`disciplines_id`)
    REFERENCES `core`.`disciplines` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_disciplines_has_teachers_teachers1`
    FOREIGN KEY (`teachers_id`)
    REFERENCES `core`.`teachers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`semesters_disciplines`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`semesters_disciplines` (
  `semesters_id` INT(11) NOT NULL,
  `disciplines_id` INT(11) NOT NULL,
  PRIMARY KEY (`semesters_id`, `disciplines_id`),
  INDEX `fk_semesters_has_disciplines_disciplines1_idx` (`disciplines_id` ASC),
  INDEX `fk_semesters_has_disciplines_semesters1_idx` (`semesters_id` ASC),
  CONSTRAINT `fk_semesters_has_disciplines_disciplines1`
    FOREIGN KEY (`disciplines_id`)
    REFERENCES `core`.`disciplines` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_semesters_has_disciplines_semesters1`
    FOREIGN KEY (`semesters_id`)
    REFERENCES `core`.`semesters` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`sessions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`sessions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(60) NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `token_UNIQUE` (`token` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `core`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `core`.`subgroups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`subgroups` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`subgroup_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`subgroup_student` (
  `subgroup_id` BIGINT(20) NOT NULL,
  `student_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`subgroup_id`, `student_id`),
  INDEX `fk_subgroup_student_2_idx` (`student_id` ASC),
  CONSTRAINT `fk_subgroup_student_1`
    FOREIGN KEY (`subgroup_id`)
    REFERENCES `core`.`subgroups` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_subgroup_student_2`
    FOREIGN KEY (`student_id`)
    REFERENCES `core`.`students` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `core`.`tests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`tests` (
  test_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  discipline_id INT NOT NULL,
  CONSTRAINT discipline_id_tests
    FOREIGN KEY (discipline_id)
    REFERENCES core.disciplines (id));


-- -----------------------------------------------------
-- Table `core`.`test_conditions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`test_conditions` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `subgroup_id` BIGINT(20) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `finish_time` DATETIME NOT NULL,
  `test_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_idx` (`subgroup_id` ASC),
  INDEX `fk_test_idx` (`test_id` ASC),
  CONSTRAINT `fk_subgroup`
    FOREIGN KEY (`subgroup_id`)
    REFERENCES `core`.`subgroups` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_test`
    FOREIGN KEY (`test_id`)
    REFERENCES `core`.`tests` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`test_solutions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`test_solutions` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `test_id` BIGINT(20) NOT NULL,
  `student_id` BIGINT(20) NOT NULL,
  `problem_solution` TEXT NOT NULL,
  `load_time` DATETIME NOT NULL,
  `score` INT(11) NULL DEFAULT NULL,
  `autochecking_score` INT(11) NULL DEFAULT NULL,
  `note` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_sol_idx` (`student_id` ASC),
  INDEX `fk_test_sol_idx` (`test_id` ASC),
  CONSTRAINT `fk_student_sol`
    FOREIGN KEY (`student_id`)
    REFERENCES `core`.`students` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_test_sol`
    FOREIGN KEY (`test_id`)
    REFERENCES `core`.`tests` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `core`.`topics`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`topics` (
  topic_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  discipline_id INT NOT NULL,
  CONSTRAINT discipline_id_topics
  FOREIGN KEY (discipline_id)
    REFERENCES core.disciplines (id));


-- -----------------------------------------------------
-- Table `core`.`problems`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`problems` (
  problem_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  statement TEXT NOT NULL,
  difficulty INT NOT NULL,
  topic_id INT NOT NULL,
  discipline_id INT NOT NULL,
  start_expression TEXT NOT NULL,
  final_expression TEXT NOT NULL,
  CONSTRAINT topic_id_problems
    FOREIGN KEY (topic_id)
    REFERENCES core.topics (topic_id),
  CONSTRAINT discipline_id_problems
    FOREIGN KEY (discipline_id)
    REFERENCES core.disciplines (id));

-- -----------------------------------------------------
-- Table `core`.`tests_problems`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `core`.`tests_problems` (
  test_id INT NOT NULL,
  problem_id INT NOT NULL,
  score INT NOT NULL,
  problem_local_id INT NOT NULL,
  CONSTRAINT test_id_tests_problems
    FOREIGN KEY (test_id)
    REFERENCES core.tests (test_id),
  CONSTRAINT problem_id_tests_problems
    FOREIGN KEY (problem_id)
    REFERENCES core.problems (problem_id));


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
