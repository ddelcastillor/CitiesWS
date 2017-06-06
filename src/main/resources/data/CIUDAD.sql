USE `mysql`;

DROP TABLE IF EXISTS `CIUDAD`;

CREATE TABLE `CIUDAD` (
    `NAME` varchar(100) not null,
    `COUNTRY` varchar(100) not null,
    `CODE` int not null,
    `POPULATION` int not null,
    `FOUNDED` int not null,
    PRIMARY KEY(`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `CIUDAD` WRITE;

INSERT INTO `CIUDAD` (`name`, `country`, `code`,`population`,`founded`)  VALUES ('Torrejon de Ardoz','Spain',28850,10000,1900),('Neuruppin','Germany',16816,30000,1256),('Paris','France',75001,2250000,1256);

UNLOCK TABLES;

