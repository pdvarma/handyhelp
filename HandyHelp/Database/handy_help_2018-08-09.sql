# ************************************************************
# Sequel Pro SQL dump
# Version 4499
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 10.1.13-MariaDB)
# Database: handy_help
# Generation Time: 2018-08-09 07:01:05 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table appointments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `appointments`;

CREATE TABLE `appointments` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `helper_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `payment_status` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;

INSERT INTO `appointments` (`id`, `user_id`, `helper_id`, `service_id`, `date`, `payment_status`)
VALUES
	(1,1,1,1,'28-01-2018',0),
	(2,1,2,1,'Thu Aug 09 11:26:28 IST 2018',1),
	(3,1,1,1,'Thu Aug 09 12:26:52 IST 2018',1);

/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table helpers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `helpers`;

CREATE TABLE `helpers` (
  `helper_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `full_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `contact` varchar(15) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT '0',
  `longitude` varchar(50) DEFAULT '0',
  `service_id` int(11) DEFAULT NULL,
  `rating` varchar(10) DEFAULT '0.00',
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`helper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

LOCK TABLES `helpers` WRITE;
/*!40000 ALTER TABLE `helpers` DISABLE KEYS */;

INSERT INTO `helpers` (`helper_id`, `full_name`, `email`, `contact`, `latitude`, `longitude`, `service_id`, `rating`, `password`)
VALUES
	(1,'Tanmay','tanmay@gmail.com','9673235486','19.1219858','72.890361',1,'0.00','test'),
	(2,'Dheeraj Verma','dheeraj@gmail.com','8989898989','22.1219961','75.8903399',2,'0.00','test');

/*!40000 ALTER TABLE `helpers` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table services
# ------------------------------------------------------------

DROP TABLE IF EXISTS `services`;

CREATE TABLE `services` (
  `service_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;

INSERT INTO `services` (`service_id`, `name`)
VALUES
	(1,'AC Repair'),
	(2,'Birthday Party Planners'),
	(3,'Household Repairs'),
	(4,'Moving and Packing'),
	(5,'Furniture Assembly');

/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `full_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `contact` varchar(15) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT '0.00',
  `longitude` varchar(50) DEFAULT '0.00',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`user_id`, `full_name`, `email`, `password`, `contact`, `latitude`, `longitude`)
VALUES
	(1,'Tanmay','tanmay@gmail.com','test','9673235486','0.00','0.00'),
	(2,'Dheeraj Verma','verma@gmail.com','test','8754875487','0.00','0.00');

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
