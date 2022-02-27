-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: baza_podsistem3
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `filijala`
--

DROP TABLE IF EXISTS `filijala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filijala` (
  `IdF` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Adresa` varchar(45) NOT NULL,
  `IdM` int NOT NULL,
  PRIMARY KEY (`IdF`),
  KEY `FK_IdM_Filijala_idx` (`IdM`),
  CONSTRAINT `FK_IdM_Filijala` FOREIGN KEY (`IdM`) REFERENCES `mesto` (`IdM`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filijala`
--

LOCK TABLES `filijala` WRITE;
/*!40000 ALTER TABLE `filijala` DISABLE KEYS */;
INSERT INTO `filijala` VALUES (1,'FilijalaBeograd1','Venizelosova 13',1),(2,'FilijalaNoviSad1','Bulevar cara Lazara 33',2),(3,'FilijalaBeograd2','Kneza Mihaila 33',1),(4,'FilijalaNis1','Niska 44',5);
/*!40000 ALTER TABLE `filijala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isplata`
--

DROP TABLE IF EXISTS `isplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `isplata` (
  `IdT` int NOT NULL,
  `IdFil` int NOT NULL,
  PRIMARY KEY (`IdT`),
  CONSTRAINT `FK_IdT_isplata` FOREIGN KEY (`IdT`) REFERENCES `transakcija` (`IdT`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isplata`
--

LOCK TABLES `isplata` WRITE;
/*!40000 ALTER TABLE `isplata` DISABLE KEYS */;
INSERT INTO `isplata` VALUES (1,2);
/*!40000 ALTER TABLE `isplata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `komitent`
--

DROP TABLE IF EXISTS `komitent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `komitent` (
  `IdK` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Adresa` varchar(45) NOT NULL,
  `IdM` int NOT NULL,
  PRIMARY KEY (`IdK`),
  KEY `FK_IdM_komitent_idx` (`IdM`),
  CONSTRAINT `FK_IdM_komitent` FOREIGN KEY (`IdM`) REFERENCES `mesto` (`IdM`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `komitent`
--

LOCK TABLES `komitent` WRITE;
/*!40000 ALTER TABLE `komitent` DISABLE KEYS */;
INSERT INTO `komitent` VALUES (1,'Jana Milovanovic','Strahinjica Bana 24',1),(2,'Sara Milovanovic','Jovana Cvijica 6',2),(3,'Andjelija Pavlovic','Balkanska 5',1),(4,'Marko Markovic','Kralja petra 44',3);
/*!40000 ALTER TABLE `komitent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `IdM` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `PosBr` int NOT NULL,
  PRIMARY KEY (`IdM`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Beograd',11000),(2,'Novi Sad',21000),(3,'Ub',14210),(4,'Kraljevo',36000),(5,'Nis',15000);
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenos`
--

DROP TABLE IF EXISTS `prenos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenos` (
  `IdT` int NOT NULL,
  `IdRacSa` int NOT NULL,
  `IdRacKa` int NOT NULL,
  PRIMARY KEY (`IdT`),
  KEY `FK_IdRacKa_prenos_idx` (`IdRacKa`),
  KEY `FK_IdRacSa_prenos_idx` (`IdRacSa`),
  CONSTRAINT `FK_IdRacKa_prenos` FOREIGN KEY (`IdRacKa`) REFERENCES `racun` (`IdR`) ON UPDATE CASCADE,
  CONSTRAINT `FK_IdRacSa_prenos` FOREIGN KEY (`IdRacSa`) REFERENCES `racun` (`IdR`) ON UPDATE CASCADE,
  CONSTRAINT `FK_IdT_prenos` FOREIGN KEY (`IdT`) REFERENCES `transakcija` (`IdT`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenos`
--

LOCK TABLES `prenos` WRITE;
/*!40000 ALTER TABLE `prenos` DISABLE KEYS */;
INSERT INTO `prenos` VALUES (4,3,1),(5,3,1);
/*!40000 ALTER TABLE `prenos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `racun`
--

DROP TABLE IF EXISTS `racun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `racun` (
  `IdR` int NOT NULL AUTO_INCREMENT,
  `IdM` int NOT NULL,
  `Stanje` int NOT NULL,
  `IdK` int NOT NULL,
  `DozvoljeniMinus` int NOT NULL,
  `Status` varchar(45) NOT NULL,
  `DatumIVremeOtvaranja` datetime NOT NULL,
  `BrTransakcija` int NOT NULL,
  PRIMARY KEY (`IdR`),
  KEY `FK_IdK_Racun_idx` (`IdK`),
  CONSTRAINT `FK_IdK_Racun` FOREIGN KEY (`IdK`) REFERENCES `komitent` (`IdK`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `racun`
--

LOCK TABLES `racun` WRITE;
/*!40000 ALTER TABLE `racun` DISABLE KEYS */;
INSERT INTO `racun` VALUES (1,2,1000,1,1000,"A","12.12.2021",1),(2,3,-1000,2,200,"B","12.12.2021",1),(3,1,49000,1,2000,"A","12.12.2021",3);
/*!40000 ALTER TABLE `racun` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `IdT` int NOT NULL AUTO_INCREMENT,
  `Iznos` int NOT NULL,
  `DatumIVreme` datetime NOT NULL,
  `Svrha` varchar(45) NOT NULL,
  `RedniBr` int NOT NULL,
  `IdRac` int NOT NULL,
  PRIMARY KEY (`IdT`),
  KEY `FK_IdRac_transakcija_idx` (`IdRac`),
  CONSTRAINT `FK_IdRac_transakcija` FOREIGN KEY (`IdRac`) REFERENCES `racun` (`IdR`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcija`
--

LOCK TABLES `transakcija` WRITE;
/*!40000 ALTER TABLE `transakcija` DISABLE KEYS */;
INSERT INTO `transakcija` VALUES (1,1000,"12.12.2021","isplata",1,2),(2,25000,"23.12.2020","uplata",1,3),(3,25000,"1.12.2020","uplata",2,3),(4,1000,"12.4.2021","prenos",3,3),(5,1000,"12.4.2021","prenos",1,1);
/*!40000 ALTER TABLE `transakcija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uplata`
--

DROP TABLE IF EXISTS `uplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uplata` (
  `IdT` int NOT NULL,
  `IdFil` int NOT NULL,
  PRIMARY KEY (`IdT`),
  CONSTRAINT `FK_IdT_uplata` FOREIGN KEY (`IdT`) REFERENCES `transakcija` (`IdT`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uplata`
--

LOCK TABLES `uplata` WRITE;
/*!40000 ALTER TABLE `uplata` DISABLE KEYS */;
INSERT INTO `uplata` VALUES (2,1),(3,2);
/*!40000 ALTER TABLE `uplata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-16 12:34:28
