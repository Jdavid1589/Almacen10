CREATE DATABASE  IF NOT EXISTS `almacen13` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `almacen13`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: almacen13
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.28-MariaDB

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
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `idCategorias` int(11) NOT NULL AUTO_INCREMENT,
  `tipos` varchar(45) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCategorias`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Abarrotes',''),(2,'Carnes y embutidos',''),(3,'Frutas y verduras',''),(4,'Panadería y repostería',''),(5,'Congelados',NULL),(6,'Bebidas',NULL),(7,'Cereales y productos de desayuno',NULL),(8,'Productos de limpieza y cuidado del hogar',NULL),(9,' Higiene y cuidado personal',NULL),(10,'Bebidas alcohólicas',NULL),(11,'Snacks y golosinas',NULL),(12,'Productos de bebé',NULL),(13,'Mascotas',NULL),(14,'Farmacia y cuidado de la salud',NULL),(15,'Electrónica y accesorios para el hogar','');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias2`
--

DROP TABLE IF EXISTS `categorias2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `categoria_padre_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoria_padre_id` (`categoria_padre_id`),
  CONSTRAINT `categorias2_ibfk_1` FOREIGN KEY (`categoria_padre_id`) REFERENCES `categorias2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias2`
--

LOCK TABLES `categorias2` WRITE;
/*!40000 ALTER TABLE `categorias2` DISABLE KEYS */;
INSERT INTO `categorias2` VALUES (1,'Frutas','Productos de tipo fruta',NULL),(2,'Verduras','Productos de tipo vegetal',NULL),(3,'Cereales','Granos y productos derivados de cereales',NULL),(4,'Lácteos','Productos derivados de la leche',NULL),(5,'Carnes','Productos de carne y derivados',NULL),(6,'Pescados y Mariscos','Productos de pescado y mariscos',NULL),(7,'Bebidas','Bebidas alcohólicas y no alcohólicas',NULL),(8,'Panadería','Productos de panadería y repostería',NULL),(9,'Snacks','Aperitivos y bocadillos',NULL),(10,'Condimentos','Especias, hierbas y condimentos',NULL),(11,'Congelados','Productos congelados',NULL),(12,'Salsas y Aderezos','Salsas y aderezos para comidas',NULL),(13,'Abarrotes','Productos enlatados y no perecederos',NULL),(14,'Productos Internacionales','Productos de diversas partes del mundo',NULL),(15,'Productos Orgánicos','Productos orgánicos y naturales',NULL),(16,'Alimentos para Mascotas','Comida y productos para animales',NULL),(17,'Cítricos','Frutas cítricas como limones y naranjas',1),(18,'Berries','Frutas pequeñas como fresas y moras',1),(19,'Tropicales','Frutas tropicales como mangos y piñas',1),(20,'Frutas Secas','Frutas deshidratadas como pasas y dátiles',1),(21,'Hortalizas','Verduras que se cultivan bajo tierra',2),(22,'Hojas Verdes','Verduras de hojas verdes como espinacas y lechugas',2),(23,'Vegetales Crucíferos','Verduras como brócoli y coliflor',2),(24,'Vegetales Raíces','Verduras de raíz como zanahorias y remolachas',2),(25,'Arroz','Diferentes tipos de arroz',3),(26,'Trigo','Productos derivados del trigo',3),(27,'Avena','Avena y productos a base de avena',3),(28,'Maíz','Productos derivados del maíz',3),(29,'Leche','Leche fresca y en polvo',4),(30,'Quesos','Diferentes tipos de quesos',4),(31,'Yogur','Yogur natural y con sabores',4),(32,'Mantequilla','Mantequilla y margarina',4),(33,'Carne de Res','Carne de res fresca y procesada',5),(34,'Carne de Cerdo','Carne de cerdo fresca y procesada',5),(35,'Carne de Pollo','Carne de pollo fresca y procesada',5),(36,'Carnes Curadas','Carnes curadas y embutidos',5),(37,'Pescados Frescos','Pescados frescos como salmón y atún',6),(38,'Mariscos','Mariscos como camarones y mejillones',6),(39,'Pescados Congelados','Pescados congelados',6),(40,'Mariscos Congelados','Mariscos congelados',6),(41,'Bebidas Alcohólicas','Cerveza, vino, licores',7),(42,'Bebidas No Alcohólicas','Jugos, refrescos, agua',7),(43,'Café y Té','Café, té y productos relacionados',7),(44,'Bebidas Energéticas','Bebidas energéticas y suplementos',7),(45,'Pan','Pan fresco y envasado',8),(46,'Pasteles','Pasteles y dulces',8),(47,'Galletas','Galletas y bocadillos de panadería',8),(48,'Masa','Masa para pan y repostería',8),(49,'Chocolates','Chocolates y dulces',9),(50,'Chips','Chips y papas fritas',9),(51,'Frutos Secos','Nuez, almendras, etc.',9),(52,'Galletas Saladas','Galletas saladas y crackers',9),(53,'Sal','Sal y mezclas de sal',10),(54,'Pimienta','Pimienta y mezclas de pimienta',10),(55,'Especias','Especias diversas como comino y cúrcuma',10),(56,'Hierbas','Hierbas secas y frescas',10),(57,'Vegetales Congelados','Vegetales congelados',11),(58,'Frutas Congeladas','Frutas congeladas',11),(59,'Platos Preparados','Comidas preparadas y congeladas',11),(60,'Helados','Helados y postres congelados',11),(61,'Salsas para Pasta','Salsas para pasta y pizza',12),(62,'Aderezos para Ensaladas','Aderezos y vinagretas',12),(63,'Salsas Picantes','Salsas picantes y tabasco',12),(64,'Salsas para Carnes','Salsas para acompañar carnes',12),(65,'Conservas','Alimentos enlatados',13),(66,'Legumbres','Legumbres secas como frijoles y lentejas',13),(67,'Harinas','Harinas diversas para cocinar',13),(68,'Aceites','Aceites para cocinar',13),(69,'Italianos','Productos alimenticios italianos',14),(70,'Asiáticos','Productos alimenticios asiáticos',14),(71,'Latinoamericanos','Productos alimenticios latinoamericanos',14),(72,'Europeos','Productos alimenticios europeos',14),(73,'Frutas Orgánicas','Frutas cultivadas orgánicamente',15),(74,'Verduras Orgánicas','Verduras cultivadas orgánicamente',15),(75,'Cereales Orgánicos','Cereales cultivados orgánicamente',15),(76,'Lácteos Orgánicos','Productos lácteos orgánicos',15),(77,'Alimentos para Perros','Comida para perros',16),(78,'Alimentos para Gatos','Comida para gatos',16),(79,'Accesorios para Mascotas','Accesorios y juguetes para mascotas',16),(80,'Suplementos para Mascotas','Suplementos nutricionales para mascotas',16),(81,'Frutas','Productos de tipo fruta',NULL),(82,'Verduras','Productos de tipo vegetal',NULL),(83,'Cereales','Granos y productos derivados de cereales',NULL),(84,'Lácteos','Productos derivados de la leche',NULL),(85,'Carnes','Productos de carne y derivados',NULL),(86,'Pescados y Mariscos','Productos de pescado y mariscos',NULL),(87,'Bebidas','Bebidas alcohólicas y no alcohólicas',NULL),(88,'Panadería','Productos de panadería y repostería',NULL),(89,'Snacks','Aperitivos y bocadillos',NULL),(90,'Condimentos','Especias, hierbas y condimentos',NULL),(91,'Congelados','Productos congelados',NULL),(92,'Salsas y Aderezos','Salsas y aderezos para comidas',NULL),(93,'Abarrotes','Productos enlatados y no perecederos',NULL),(94,'Productos Internacionales','Productos de diversas partes del mundo',NULL),(95,'Productos Orgánicos','Productos orgánicos y naturales',NULL),(96,'Alimentos para Mascotas','Comida y productos para animales',NULL),(97,'Cítricos','Frutas cítricas como limones y naranjas',1),(98,'Berries','Frutas pequeñas como fresas y moras',1),(99,'Tropicales','Frutas tropicales como mangos y piñas',1),(100,'Frutas Secas','Frutas deshidratadas como pasas y dátiles',1),(101,'Hortalizas','Verduras que se cultivan bajo tierra',2),(102,'Hojas Verdes','Verduras de hojas verdes como espinacas y lechugas',2),(103,'Vegetales Crucíferos','Verduras como brócoli y coliflor',2),(104,'Vegetales Raíces','Verduras de raíz como zanahorias y remolachas',2),(105,'Arroz','Diferentes tipos de arroz',3),(106,'Trigo','Productos derivados del trigo',3),(107,'Avena','Avena y productos a base de avena',3),(108,'Maíz','Productos derivados del maíz',3),(109,'Leche','Leche fresca y en polvo',4),(110,'Quesos','Diferentes tipos de quesos',4),(111,'Yogur','Yogur natural y con sabores',4),(112,'Mantequilla','Mantequilla y margarina',4),(113,'Carne de Res','Carne de res fresca y procesada',5),(114,'Carne de Cerdo','Carne de cerdo fresca y procesada',5),(115,'Carne de Pollo','Carne de pollo fresca y procesada',5),(116,'Carnes Curadas','Carnes curadas y embutidos',5),(117,'Pescados Frescos','Pescados frescos como salmón y atún',6),(118,'Mariscos','Mariscos como camarones y mejillones',6),(119,'Pescados Congelados','Pescados congelados',6),(120,'Mariscos Congelados','Mariscos congelados',6),(121,'Bebidas Alcohólicas','Cerveza, vino, licores',7),(122,'Bebidas No Alcohólicas','Jugos, refrescos, agua',7),(123,'Café y Té','Café, té y productos relacionados',7),(124,'Bebidas Energéticas','Bebidas energéticas y suplementos',7),(125,'Pan','Pan fresco y envasado',8),(126,'Pasteles','Pasteles y dulces',8),(127,'Galletas','Galletas y bocadillos de panadería',8),(128,'Masa','Masa para pan y repostería',8),(129,'Chocolates','Chocolates y dulces',9),(130,'Chips','Chips y papas fritas',9),(131,'Frutos Secos','Nuez, almendras, etc.',9),(132,'Galletas Saladas','Galletas saladas y crackers',9),(133,'Sal','Sal y mezclas de sal',10),(134,'Pimienta','Pimienta y mezclas de pimienta',10),(135,'Especias','Especias diversas como comino y cúrcuma',10),(136,'Hierbas','Hierbas secas y frescas',10),(137,'Vegetales Congelados','Vegetales congelados',11),(138,'Frutas Congeladas','Frutas congeladas',11),(139,'Platos Preparados','Comidas preparadas y congeladas',11),(140,'Helados','Helados y postres congelados',11),(141,'Salsas para Pasta','Salsas para pasta y pizza',12),(142,'Aderezos para Ensaladas','Aderezos y vinagretas',12),(143,'Salsas Picantes','Salsas picantes y tabasco',12),(144,'Salsas para Carnes','Salsas para acompañar carnes',12),(145,'Conservas','Alimentos enlatados',13),(146,'Legumbres','Legumbres secas como frijoles y lentejas',13),(147,'Harinas','Harinas diversas para cocinar',13),(148,'Aceites','Aceites para cocinar',13),(149,'Italianos','Productos alimenticios italianos',14),(150,'Asiáticos','Productos alimenticios asiáticos',14),(151,'Latinoamericanos','Productos alimenticios latinoamericanos',14),(152,'Europeos','Productos alimenticios europeos',14),(153,'Frutas Orgánicas','Frutas cultivadas orgánicamente',15),(154,'Verduras Orgánicas','Verduras cultivadas orgánicamente',15),(155,'Cereales Orgánicos','Cereales cultivados orgánicamente',15),(156,'Lácteos Orgánicos','Productos lácteos orgánicos',15),(157,'Alimentos para Perros','Comida para perros',16),(158,'Alimentos para Gatos','Comida para gatos',16),(159,'Accesorios para Mascotas','Accesorios y juguetes para mascotas',16),(160,'Suplementos para Mascotas','Suplementos nutricionales para mascotas',16);
/*!40000 ALTER TABLE `categorias2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Consumidor Final','000'),(2,'Juan ','325625');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `idCompra` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `proveedorId` int(11) NOT NULL,
  `totalCompra` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idCompra`),
  KEY `fk_tblCompras_proveedores1_idx` (`proveedorId`),
  CONSTRAINT `fk_tblCompras_proveedores1` FOREIGN KEY (`proveedorId`) REFERENCES `proveedores` (`idProveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (31,'2024-09-13',1,500.00),(32,'2024-09-13',1,500.00),(33,'2024-09-13',1,500.00),(34,'2024-09-14',2,253.75),(35,'2024-09-14',2,15000.00),(36,'2024-09-14',2,20000.00),(37,'2024-09-14',2,58500.00),(38,'2024-09-14',2,86500.00),(39,'2024-09-16',1,188500.00),(40,'2024-09-16',1,16680.00),(41,'2024-09-17',1,22680.00),(42,'2024-09-17',1,3000.00),(43,'2024-09-18',1,5120.00),(44,'2024-09-18',1,7680.00),(45,'2024-09-18',1,5120.00),(46,'2024-09-18',1,5120.00),(47,'2024-09-18',1,63960.00),(48,'2024-09-18',1,73680.00),(49,'2024-09-18',1,7680.00),(50,'2024-09-18',1,28680.00),(51,'2024-09-18',1,7680.00),(52,'2024-09-19',1,10240.00),(53,'2024-09-20',1,7680.00),(54,'2024-09-24',1,10240.00),(55,'2024-09-24',1,5250.00),(56,'2024-09-24',1,6800.00),(57,'2024-09-25',1,12000.00),(58,'2024-09-25',1,17200.00),(59,'2024-09-25',1,37500.00),(60,'2024-09-25',1,3600.00),(61,'2024-09-25',1,3500.00),(62,'2024-09-25',1,5000.00),(64,'2024-09-25',1,9000.00),(65,'2024-09-25',1,4800.00),(66,'2024-09-25',1,28500.00),(67,'2024-09-25',1,42750.00),(68,'2024-09-25',1,42750.00),(69,'2024-09-26',1,23310.00),(70,'2024-09-26',1,14250.00),(71,'2024-09-27',2,97200.00);
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comprasproductos`
--

DROP TABLE IF EXISTS `comprasproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comprasproductos` (
  `idCompraProducto` int(11) NOT NULL AUTO_INCREMENT,
  `productosId` int(11) NOT NULL,
  `comprasId` int(11) NOT NULL,
  `cantidad` decimal(8,2) NOT NULL,
  `costoArticulo` decimal(8,2) NOT NULL,
  `porcIva` int(11) NOT NULL,
  `precioVenta` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idCompraProducto`),
  KEY `fk_tblcomprasarticulo_productos1_idx` (`productosId`),
  KEY `fk_tblcomprasarticulo_tblCompras1_idx` (`comprasId`),
  CONSTRAINT `fk_tblcomprasarticulo_productos1` FOREIGN KEY (`productosId`) REFERENCES `productos` (`idProductos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tblcomprasarticulo_tblCompras1` FOREIGN KEY (`comprasId`) REFERENCES `compras` (`idCompra`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comprasproductos`
--

LOCK TABLES `comprasproductos` WRITE;
/*!40000 ALTER TABLE `comprasproductos` DISABLE KEYS */;
INSERT INTO `comprasproductos` VALUES (24,1,31,10.00,50.00,19,0.00),(25,2,31,5.00,20.00,0,0.00),(26,2,32,50.00,50.00,19,0.00),(27,2,32,55.00,20.00,0,0.00),(28,2,33,50.00,50.00,19,0.00),(29,2,33,55.00,20.00,19,0.00),(30,1,34,5.00,50.75,0,0.00),(31,2,35,6.00,2500.00,0,0.00),(32,2,36,8.00,2500.00,19,0.00),(33,2,37,3.00,2500.00,19,0.00),(34,3,37,2.00,25500.00,19,0.00),(35,2,38,4.00,2500.00,19,0.00),(36,3,38,3.00,25500.00,19,0.00),(37,3,39,7.00,25500.00,19,0.00),(38,2,39,4.00,2500.00,19,0.00),(39,1,40,3.00,5560.00,19,0.00),(40,1,41,3.00,2560.00,19,0.00),(41,5,41,4.00,3000.00,19,0.00),(42,10,41,3.00,1000.00,19,0.00),(43,10,42,3.00,1000.00,19,0.00),(44,1,43,2.00,2560.00,19,0.00),(45,1,44,3.00,2560.00,19,0.00),(46,1,45,2.00,2560.00,19,0.00),(47,1,46,2.00,2560.00,19,0.00),(48,1,47,16.00,2560.00,19,0.00),(49,12,47,6.00,3500.00,19,0.00),(50,15,47,2.00,1000.00,19,0.00),(51,14,48,8.00,1200.00,19,0.00),(52,1,48,15.00,2560.00,19,0.00),(53,2,48,3.00,2560.00,19,0.00),(54,11,48,3.00,6000.00,19,0.00),(55,1,49,3.00,2560.00,19,0.00),(56,1,50,3.00,2560.00,19,0.00),(57,6,50,3.00,7000.00,19,0.00),(58,1,51,3.00,2560.00,19,0.00),(59,1,52,4.00,2560.00,19,0.00),(60,1,53,3.00,2560.00,19,0.00),(61,1,54,2.00,2560.00,19,0.00),(62,2,54,2.00,2560.00,19,0.00),(63,4,55,1.00,5250.00,19,0.00),(64,4,56,1.00,6800.00,19,0.00),(65,10,57,8.00,1500.00,19,0.00),(66,14,58,6.00,1200.00,19,0.00),(67,4,58,2.00,5000.00,19,0.00),(68,10,59,25.00,1500.00,19,0.00),(69,1,60,1.00,3600.00,19,0.00),(70,1,61,1.00,3500.00,19,0.00),(71,1,62,1.00,5000.00,19,0.00),(72,1,64,1.00,9000.00,19,11000.00),(73,1,65,3.00,1600.00,19,2500.00),(74,1,66,2.00,14250.00,16,23650.00),(75,1,67,3.00,14250.00,16,23650.00),(76,1,68,3.00,14250.00,16,23650.00),(77,2,69,9.00,2590.00,19,3600.00),(78,1,70,1.00,14250.00,16,23650.00),(79,3,71,54.00,1800.00,19,2500.00);
/*!40000 ALTER TABLE `comprasproductos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalles_facturas`
--

DROP TABLE IF EXISTS `detalles_facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalles_facturas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `facturasId` int(11) NOT NULL,
  `productosId` int(11) NOT NULL,
  `cantidad` decimal(8,2) NOT NULL,
  `precioCompra` decimal(8,2) NOT NULL,
  `precioVenta` decimal(8,2) NOT NULL,
  `porcIva` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_factura_articulos_facturas1_idx` (`facturasId`),
  KEY `fk_factura_articulos_productos1_idx` (`productosId`),
  CONSTRAINT `fk_factura_articulos_facturas1` FOREIGN KEY (`facturasId`) REFERENCES `facturas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_factura_articulos_productos1` FOREIGN KEY (`productosId`) REFERENCES `productos` (`idProductos`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_facturas`
--

LOCK TABLES `detalles_facturas` WRITE;
/*!40000 ALTER TABLE `detalles_facturas` DISABLE KEYS */;
INSERT INTO `detalles_facturas` VALUES (1,1,3,2.00,1800.00,2500.00,19),(2,2,16,3.00,3500.00,4800.00,19),(3,2,12,7.00,3500.00,5000.00,19),(4,3,1,2.00,2560.00,3500.00,19),(5,4,1,2.00,2560.00,3500.00,19),(6,5,10,4.00,1000.00,1500.00,19),(7,6,10,3.00,1000.00,1500.00,19),(8,7,10,3.00,1000.00,1500.00,19),(9,8,6,3.00,7000.00,8500.00,19),(10,9,10,2.00,1000.00,1500.00,19),(11,10,1,3.00,14250.00,23650.00,16),(12,11,1,1.00,14250.00,23650.00,16),(13,11,11,1.00,6000.00,7500.00,19),(14,12,1,1.00,14250.00,23650.00,16),(15,12,12,2.00,3500.00,5000.00,19),(16,13,1,1.00,14250.00,23650.00,16),(17,14,1,1.00,14250.00,23650.00,16),(18,15,1,1.00,14250.00,23650.00,16),(19,15,12,4.00,3500.00,5000.00,19),(20,16,1,1.00,14250.00,23650.00,16),(21,16,4,1.00,5000.00,6500.00,19),(22,17,1,1.00,14250.00,23650.00,16),(23,17,14,4.00,1200.00,2000.00,19),(24,18,4,2.00,5000.00,6500.00,19),(25,18,10,2.00,1500.00,1800.00,19),(26,19,1,2.00,14250.00,23650.00,16),(27,20,1,3.00,14250.00,23650.00,16),(28,21,12,1.00,14250.00,23650.00,16),(29,21,5,2.00,3000.00,4000.00,19),(30,22,10,3.00,1500.00,1800.00,19),(31,23,10,3.00,1500.00,1800.00,19),(32,24,10,2.00,1500.00,1800.00,19),(33,25,10,2.00,1500.00,1800.00,19),(34,25,4,2.00,5000.00,6500.00,19),(35,26,12,1.00,3500.00,5000.00,19),(36,27,1,14.00,14250.00,23650.00,16);
/*!40000 ALTER TABLE `detalles_facturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento` (
  `idDocumento` int(11) NOT NULL AUTO_INCREMENT,
  `tipoDocumento` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idDocumento`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento`
--

LOCK TABLES `documento` WRITE;
/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturas`
--

DROP TABLE IF EXISTS `facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `clienteId` int(11) NOT NULL,
  `totalCosto` decimal(8,2) NOT NULL,
  `totalIva` decimal(8,2) NOT NULL,
  `totalVenta` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_facturas_clientes1_idx` (`clienteId`),
  CONSTRAINT `fk_facturas_clientes1` FOREIGN KEY (`clienteId`) REFERENCES `clientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturas`
--

LOCK TABLES `facturas` WRITE;
/*!40000 ALTER TABLE `facturas` DISABLE KEYS */;
INSERT INTO `facturas` VALUES (1,'2024-09-23',1,3600.00,684.00,5000.00),(2,'2024-09-23',1,35000.00,6650.00,49400.00),(3,'2024-09-23',1,5120.00,972.80,7000.00),(4,'2024-09-23',1,5120.00,972.80,7000.00),(5,'2024-09-23',1,4000.00,760.00,6000.00),(6,'2024-09-23',1,3000.00,570.00,4500.00),(7,'2024-09-23',1,3000.00,570.00,4500.00),(8,'2024-09-24',1,21000.00,3990.00,25500.00),(9,'2024-09-24',1,2000.00,380.00,3000.00),(10,'2024-09-25',1,42750.00,6840.00,70950.00),(11,'2024-09-25',1,20250.00,3420.00,31150.00),(12,'2024-09-25',1,21250.00,3610.00,33650.00),(13,'2024-09-25',1,23650.00,3784.00,23650.00),(14,'2024-09-25',1,23650.00,3784.00,23650.00),(15,'2024-09-25',1,28650.00,7584.00,43650.00),(16,'2024-09-25',1,30150.00,5019.00,30150.00),(17,'2024-09-25',1,31650.00,5304.00,36954.00),(18,'2024-09-25',1,16600.00,3154.00,19754.00),(19,'2024-09-25',1,47300.00,7568.00,54868.00),(20,'2024-09-26',1,70950.00,11352.00,82302.00),(21,'2024-09-26',1,31650.00,5304.00,36954.00),(22,'2024-09-26',1,5400.00,1026.00,6426.00),(23,'2024-09-26',1,5400.00,1026.00,6426.00),(24,'2024-09-26',1,3600.00,684.00,4284.00),(25,'2024-09-26',1,16600.00,3154.00,19754.00),(26,'2024-09-26',1,5000.00,950.00,5950.00),(27,'2024-09-27',1,331100.00,52976.00,384076.00);
/*!40000 ALTER TABLE `facturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `idPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `tipoperfil` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPerfil`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'Administrador'),(2,'Empleado');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `idProductos` int(11) NOT NULL AUTO_INCREMENT,
  `fechaActualizacion` date NOT NULL,
  `productos` varchar(75) NOT NULL,
  `plu` varchar(75) NOT NULL,
  `categoriasId` int(11) NOT NULL,
  `proveedoresId` int(11) NOT NULL,
  `unidadMedidaId` int(11) NOT NULL,
  `precioCompra` decimal(8,2) NOT NULL,
  `precioVenta` decimal(8,2) NOT NULL,
  `cantidadDisponible` double NOT NULL,
  `porcIva` int(11) NOT NULL,
  PRIMARY KEY (`idProductos`),
  KEY `fk_productos_categorias1_idx` (`categoriasId`),
  KEY `fk_productos_proveedores1_idx` (`proveedoresId`),
  KEY `fk_productos_unidadMedida1_idx` (`unidadMedidaId`),
  CONSTRAINT `fk_productos_categorias1` FOREIGN KEY (`categoriasId`) REFERENCES `categorias` (`idCategorias`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_proveedores1` FOREIGN KEY (`proveedoresId`) REFERENCES `proveedores` (`idProveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_unidadMedida1` FOREIGN KEY (`unidadMedidaId`) REFERENCES `unidadmedida` (`idUnidadMedida`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'2024-09-26','Leche','L-005',1,1,1,14250.00,23650.00,92,16),(2,'2024-09-27','Lecherita','L-007',1,1,1,2590.00,3600.00,64,19),(3,'2024-09-27','Yogur','Y-006',1,1,1,1800.00,2500.00,92,19),(4,'2024-05-06','Queso Panela','Q-007',1,1,1,5000.00,6500.00,29,19),(5,'2024-05-06','Mantequilla','M-008',1,1,1,3000.00,4000.00,27,19),(6,'2024-05-06','Jamón','J-009',1,1,1,7000.00,8500.00,35,19),(7,'2024-05-06','Salchichas','S-010',1,1,1,4500.00,6000.00,50,19),(8,'2024-05-06','Arroz','A-011',1,1,1,1500.00,2200.00,100,19),(9,'2024-05-06','Frijoles','F-012',1,1,1,2000.00,2800.00,80,19),(10,'2024-05-06','Harina de trigo','H-013',1,1,1,1500.00,1800.00,75,19),(11,'2024-10-30','Aceite Vegetal','AV-014',1,1,1,6000.00,7500.00,47,19),(12,'2024-05-06','Mayonesa','MY-015',1,1,1,3500.00,5000.00,11,19),(13,'2024-05-06','Salsa de tomate','ST-016',1,1,1,1800.00,2500.00,60,19),(14,'2024-05-06','Refresco','R-017',1,1,1,1200.00,2000.00,130,19),(15,'2024-10-22','Galletas','G-018',1,1,1,1000.00,1800.00,77,19),(16,'2024-09-28','Chocolate','CH-019',1,1,1,3500.00,4800.00,37,19);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedores` (
  `idProveedor` int(11) NOT NULL AUTO_INCREMENT,
  `proveedor` varchar(100) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `asesor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,'Colanta','31425652','Wilmer'),(2,'Arroz Diana','32525','pedro'),(3,'Harina el trigo','1321312','David'),(4,'Pan panda','314255','Julio ');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidadmedida`
--

DROP TABLE IF EXISTS `unidadmedida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidadmedida` (
  `idUnidadMedida` int(11) NOT NULL AUTO_INCREMENT,
  `unidadMedida` varchar(70) NOT NULL,
  PRIMARY KEY (`idUnidadMedida`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadmedida`
--

LOCK TABLES `unidadmedida` WRITE;
/*!40000 ALTER TABLE `unidadmedida` DISABLE KEYS */;
INSERT INTO `unidadmedida` VALUES (1,'LITRO'),(2,'LIBRA'),(3,'KILO'),(4,'BARRA'),(5,'GRAMOS'),(6,'GALON'),(7,'UNIDAD'),(8,'BOLSA'),(9,'PAQUETE');
/*!40000 ALTER TABLE `unidadmedida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idUsuarios` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(45) DEFAULT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `clave` varchar(45) DEFAULT NULL,
  `perfilId` int(11) NOT NULL,
  PRIMARY KEY (`idUsuarios`),
  KEY `fk_usuarios_perfil_idx` (`perfilId`),
  CONSTRAINT `fk_usuarios_perfil` FOREIGN KEY (`perfilId`) REFERENCES `perfil` (`idPerfil`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Juan David P','jd','cc3',2),(2,'Jorge','jb','cc3',1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-28  9:04:02
