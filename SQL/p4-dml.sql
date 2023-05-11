/*
DROPPING EXISTING TABLES
*/
DROP TABLE `useraccounts`;
DROP TABLE `brands`;
DROP TABLE `models`;
DROP TABLE `cars`;

/*
CREATING TABLES
*/
CREATE TABLE `useraccounts` (
  `iduserAccounts` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`iduserAccounts`),
  UNIQUE KEY `iduserAccounts_UNIQUE` (`iduserAccounts`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `brands` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id_UNIQUE` (`Id`),
  UNIQUE KEY `Name_UNIQUE` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `models` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id_UNIQUE` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cars` (
  `idCars` int NOT NULL AUTO_INCREMENT,
  `carBrand_Id` int NOT NULL,
  `carModel_Id` int NOT NULL,
  `Price` double NOT NULL,
  PRIMARY KEY (`idCars`),
  UNIQUE KEY `idCars_UNIQUE` (`idCars`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;