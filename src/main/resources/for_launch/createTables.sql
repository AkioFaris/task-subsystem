SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

-- -----------------------------------------------------
-- Schema apprz_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS apprz_db ;
USE apprz_db ;

-- -----------------------------------------------------
-- Table apprz_db.disciplines
-- -----------------------------------------------------
DROP TABLE IF EXISTS apprz_db.disciplines ;

CREATE TABLE IF NOT EXISTS apprz_db.disciplines (
  id_discipline INT NOT NULL PRIMARY KEY, 
  name_discipline VARCHAR(45) NOT NULL);

-- -----------------------------------------------------
-- Table apprz_db.topics
-- -----------------------------------------------------
DROP TABLE IF EXISTS apprz_db.topics ;

CREATE TABLE IF NOT EXISTS apprz_db.topics (
  id_topics INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name_topic VARCHAR(45) NOT NULL,
  id_discipline INT NOT NULL,
  CONSTRAINT id_disciplines
  FOREIGN KEY (id_discipline)
    REFERENCES apprz_db.disciplines (id_discipline));

-- -----------------------------------------------------
-- Table apprz_db.problems
-- -----------------------------------------------------
DROP TABLE IF EXISTS apprz_db.problems ;

CREATE TABLE IF NOT EXISTS apprz_db.problems (
  id_problem INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  statement TEXT NOT NULL,
  difficulty INT NOT NULL,
  id_topic INT NOT NULL,
  id_discipline INT NOT NULL,
  start_expression TEXT NOT NULL,
  final_expression TEXT NOT NULL,
  CONSTRAINT id_topic
    FOREIGN KEY (id_topic)
    REFERENCES apprz_db.topics (id_topics),
  CONSTRAINT id_discipline_problems
    FOREIGN KEY (id_discipline)
    REFERENCES apprz_db.disciplines (id_discipline));

-- -----------------------------------------------------
-- Table apprz_db.tests
-- -----------------------------------------------------
DROP TABLE IF EXISTS apprz_db.tests ;

CREATE TABLE IF NOT EXISTS apprz_db.tests (
  id_test INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  num_problems INT NOT NULL,
  id_discipline INT NOT NULL,
  sum_scores INT NOT NULL,
  CONSTRAINT id_discipline_tests
    FOREIGN KEY (id_discipline)
    REFERENCES apprz_db.disciplines (id_discipline));

-- -----------------------------------------------------
-- Table apprz_db.test_problems
-- -----------------------------------------------------
DROP TABLE IF EXISTS apprz_db.test_problems ;

CREATE TABLE IF NOT EXISTS apprz_db.test_problems (
  id_test INT NOT NULL,
  id_problem INT NOT NULL,
  score INT NOT NULL,
  CONSTRAINT id_test
    FOREIGN KEY (id_test)
    REFERENCES apprz_db.tests (id_test),
  CONSTRAINT id_apprz_db
    FOREIGN KEY (id_problem)
    REFERENCES apprz_db.problems (id_problem));

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
