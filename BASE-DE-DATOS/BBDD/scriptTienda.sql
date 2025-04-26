SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS `scriptTienda`;

CREATE SCHEMA IF NOT EXISTS `scriptTienda` DEFAULT CHARACTER SET utf8 ;
USE `scriptTienda`;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`empresas` (
  `id_empresa` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `direccion` VARCHAR(255) NULL,
  `telefono` VARCHAR(20) NULL,
  `correo_electronico` VARCHAR(255) NOT NULL UNIQUE,
  `descripcion` TEXT NULL,
  `sitio_web` VARCHAR(255) NULL,
  PRIMARY KEY (`id_empresa`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `apellido` VARCHAR(255) NOT NULL,
  `correo` VARCHAR(255) NOT NULL UNIQUE,
  `contrasena` VARCHAR(255) NOT NULL,
  `rol` VARCHAR(50) NOT NULL,
  `empresas_id_empresa` INT NOT NULL,
  PRIMARY KEY (`id_usuario`),
  INDEX `fk_usuarios_empresas1_idx` (`empresas_id_empresa` ASC),
  CONSTRAINT `fk_usuarios_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`clientes` (
  `dni` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  `apellidos` VARCHAR(255) NOT NULL,
  `numero_telefono` VARCHAR(20) NULL,
  `correo` VARCHAR(255) NULL,
  `direccion` VARCHAR(255) NULL,
  `ciudad` VARCHAR(255) NULL,
  `codigo_postal` VARCHAR(10) NULL,
  `provincia` VARCHAR(255) NULL,
  `empresas_id_empresa` INT NOT NULL,
  PRIMARY KEY (`dni`),
  INDEX `fk_clientes_empresas1_idx` (`empresas_id_empresa` ASC),
  CONSTRAINT `fk_clientes_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`proveedores` (
  `id_proveedores` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `contacto` VARCHAR(255) NULL,
  `telefono` VARCHAR(20) NULL,
  `direccion` VARCHAR(255) NULL,
  `ciudad` VARCHAR(255) NULL,
  `provincia` VARCHAR(255) NULL,
  `codigo_postal` VARCHAR(10) NULL,
  `pais` VARCHAR(255) NULL,
  `rfc_nit` VARCHAR(20) NULL,
  `tipo` VARCHAR(255) NULL,
  `sitio_web` VARCHAR(255) NULL,
  `fecha_registro` DATE NULL,
  `empresas_id_empresa` INT NOT NULL,
  PRIMARY KEY (`id_proveedores`),
  INDEX `fk_proveedores_empresas1_idx` (`empresas_id_empresa` ASC),
  CONSTRAINT `fk_proveedores_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`compras` (
  `id_compra` INT NOT NULL AUTO_INCREMENT,
  `fecha_compra` DATE NOT NULL,
  `fecha_devolucion` DATE NULL,
  `monto_total` DECIMAL(10, 2) NULL,
  `empresas_id_empresa` INT NOT NULL,
  `proveedores_id_proveedores` INT NOT NULL,
  PRIMARY KEY (`id_compra`),
  INDEX `fk_compras_empresas1_idx` (`empresas_id_empresa` ASC),
  INDEX `fk_compras_proveedores1_idx` (`proveedores_id_proveedores` ASC),
  CONSTRAINT `fk_compras_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`productos` (
  `id_producto` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `descripcion` TEXT NULL,
  `precio` DECIMAL(10, 2) NOT NULL,
  `anyos_garantia` INT NULL,
  `unidades` INT NOT NULL,
  `proveedores_id_proveedores` INT NOT NULL,
  `empresas_id_empresa` INT NOT NULL,
  PRIMARY KEY (`id_producto`),
  INDEX `fk_productos_proveedores1_idx` (`proveedores_id_proveedores` ASC),
  INDEX `fk_productos_empresas1_idx` (`empresas_id_empresa` ASC),
  CONSTRAINT `fk_productos_proveedores1`
    FOREIGN KEY (`proveedores_id_proveedores`)
    REFERENCES `scriptTienda`.`proveedores` (`id_proveedores`),
  CONSTRAINT `fk_productos_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`detalles_compra` (
  `id_detalles_compra` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL,
  `precio_unidad` DECIMAL(10, 2) NOT NULL,
  `compras_id_compra` INT NULL,
  `productos_id_producto` INT NULL,
  `empresas_id_empresa` INT NOT NULL,
  PRIMARY KEY (`id_detalles_compra`),
  INDEX `fk_detalles_compra_compras1_idx` (`compras_id_compra` ASC),
  INDEX `fk_detalles_compra_productos1_idx` (`productos_id_producto` ASC),
  INDEX `fk_detalles_compra_empresas1_idx` (`empresas_id_empresa` ASC),
  CONSTRAINT `fk_detalles_compra_compras1`
    FOREIGN KEY (`compras_id_compra`)
    REFERENCES `scriptTienda`.`compras` (`id_compra`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_detalles_compra_productos1`
    FOREIGN KEY (`productos_id_producto`)
    REFERENCES `scriptTienda`.`productos` (`id_producto`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_detalles_compra_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`envios` (
  `id_envio` INT NOT NULL AUTO_INCREMENT,
  `fecha_envio` DATE NOT NULL,
  `fecha_entrega` DATE NULL,
  `direccion_envio` VARCHAR(255) NOT NULL,
  `ciudad_envio` VARCHAR(255) NULL,
  `codigo_postal_envio` VARCHAR(10) NULL,
  `estado_envio` VARCHAR(255) NULL,
  `detalles_venta_id_detalles_venta` INT NULL,
  `empresas_id_empresa` INT NOT NULL,
  PRIMARY KEY (`id_envio`),
  INDEX `fk_envios_detalles_venta1_idx` (`detalles_venta_id_detalles_venta` ASC),
  INDEX `fk_envios_empresas1_idx` (`empresas_id_empresa` ASC),
  CONSTRAINT `fk_envios_detalles_venta1`
    FOREIGN KEY (`detalles_venta_id_detalles_venta`)
    REFERENCES `scriptTienda`.`detalles_venta` (`id_detalles_venta`)
    ON DELETE SET NULL,
  CONSTRAINT `fk_envios_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`ventas` (
  `id_venta` INT NOT NULL AUTO_INCREMENT,
  `fecha_venta` DATE NOT NULL,
  `monto_total` DECIMAL(10, 2) NOT NULL,
  `empresas_id_empresa` INT NOT NULL,
  `clientes_dni` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_venta`),
  INDEX `fk_ventas_empresas1_idx` (`empresas_id_empresa` ASC),
  INDEX `fk_ventas_clientes1_idx` (`clientes_dni` ASC),
  CONSTRAINT `fk_ventas_empresas1`
    FOREIGN KEY (`empresas_id_empresa`)
    REFERENCES `scriptTienda`.`empresas` (`id_empresa`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_ventas_clientes1`
    FOREIGN KEY (`clientes_dni`)
    REFERENCES `scriptTienda`.`clientes` (`dni`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `scriptTienda`.`detalles_venta` (
  `id_detalles_venta` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL,
  `precio_unidad` DECIMAL(10, 2) NOT NULL,
  `ventas_id_venta` INT NOT NULL,
  `productos_id_producto` INT NOT NULL,
  `is_enviado` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_detalles_venta`),
  INDEX `fk_detalles_venta_ventas1_idx` (`ventas_id_venta` ASC),
  INDEX `fk_detalles_venta_productos1_idx` (`productos_id_producto` ASC),
  CONSTRAINT `fk_detalles_venta_ventas1`
    FOREIGN KEY (`ventas_id_venta`)
    REFERENCES `scriptTienda`.`ventas` (`id_venta`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_detalles_venta_productos1`
    FOREIGN KEY (`productos_id_producto`)
    REFERENCES `scriptTienda`.`productos` (`id_producto`)
    ON DELETE CASCADE)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
