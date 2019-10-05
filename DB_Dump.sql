

# Dump of table person
# ------------------------------------------------------------

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `personid` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;

INSERT INTO `person` (`personid`, `active`, `email`, `lastname`, `name`, `password`)
VALUES
	(1,1,'drausioteste@gmail.com','Smith','Drausio','123456'),
	(2,1,'lucas_teste@gmail.com','Jhonson','Lucas','asd123');

/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;



