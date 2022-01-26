-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-12-2021 a las 22:48:58
-- Versión del servidor: 10.4.18-MariaDB
-- Versión de PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `telecosta`
--
CREATE DATABASE IF NOT EXISTS `telecosta` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish2_ci;
USE `telecosta`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `idcliente` int(11) NOT NULL,
  `idconfiguracionpago` int(11) NOT NULL,
  `cui` varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `nombres` varchar(250) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidos` varchar(250) COLLATE utf8mb4_spanish_ci NOT NULL,
  `direccion` varchar(500) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `idmunicipio` int(11) NOT NULL,
  `usuariocreacion` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fechacreacion` datetime NOT NULL,
  `usuariomodificacion` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `fechamodificacion` datetime DEFAULT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idcliente`, `idconfiguracionpago`, `cui`, `nombres`, `apellidos`, `direccion`, `telefono`, `idmunicipio`, `usuariocreacion`, `fechacreacion`, `usuariomodificacion`, `fechamodificacion`, `activo`) VALUES(4, 1, '2521052300401', 'prueba', 'prueba', '22222', 333222, 1, 'admin', '2021-11-30 13:24:43', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuracionpago`
--

DROP TABLE IF EXISTS `configuracionpago`;
CREATE TABLE `configuracionpago` (
  `idconfiguracionpago` int(11) NOT NULL,
  `valor` int(11) NOT NULL,
  `descripcion` varchar(1000) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `usuariocreacion` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `fechacreacion` datetime NOT NULL,
  `usuarioeliminacion` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fechaeliminacion` datetime DEFAULT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `configuracionpago`
--

INSERT INTO `configuracionpago` (`idconfiguracionpago`, `valor`, `descripcion`, `usuariocreacion`, `fechacreacion`, `usuarioeliminacion`, `fechaeliminacion`, `activo`) VALUES(1, 75, 'cable', 'admin', '2021-11-29 00:00:00', NULL, NULL, 1);
INSERT INTO `configuracionpago` (`idconfiguracionpago`, `valor`, `descripcion`, `usuariocreacion`, `fechacreacion`, `usuarioeliminacion`, `fechaeliminacion`, `activo`) VALUES(2, 200, 'Internet', 'admin', '2021-11-29 00:00:00', NULL, NULL, 1);
INSERT INTO `configuracionpago` (`idconfiguracionpago`, `valor`, `descripcion`, `usuariocreacion`, `fechacreacion`, `usuarioeliminacion`, `fechaeliminacion`, `activo`) VALUES(3, 300, 'cable + internet', 'admin', '2021-11-29 00:00:00', NULL, NULL, 1);
INSERT INTO `configuracionpago` (`idconfiguracionpago`, `valor`, `descripcion`, `usuariocreacion`, `fechacreacion`, `usuarioeliminacion`, `fechaeliminacion`, `activo`) VALUES(4, 60, 'Cable', 'admin', '2021-12-01 00:00:00', NULL, NULL, 1);
INSERT INTO `configuracionpago` (`idconfiguracionpago`, `valor`, `descripcion`, `usuariocreacion`, `fechacreacion`, `usuarioeliminacion`, `fechaeliminacion`, `activo`) VALUES(5, 50, 'Cable', 'admin', '2021-12-01 00:00:00', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

DROP TABLE IF EXISTS `departamento`;
CREATE TABLE `departamento` (
  `iddepartamento` int(11) NOT NULL,
  `nombre` varchar(500) COLLATE utf8mb4_spanish_ci NOT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`iddepartamento`, `nombre`, `activo`) VALUES(1, 'San Marcos', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipio`
--

DROP TABLE IF EXISTS `municipio`;
CREATE TABLE `municipio` (
  `idmunicipio` int(11) NOT NULL,
  `iddepartamento` int(11) NOT NULL,
  `municipio` varchar(500) COLLATE utf8mb4_spanish_ci NOT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `municipio`
--

INSERT INTO `municipio` (`idmunicipio`, `iddepartamento`, `municipio`, `activo`) VALUES(1, 1, 'Catarina', 1);
INSERT INTO `municipio` (`idmunicipio`, `iddepartamento`, `municipio`, `activo`) VALUES(2, 1, 'Pajapita', 1);
INSERT INTO `municipio` (`idmunicipio`, `iddepartamento`, `municipio`, `activo`) VALUES(3, 1, 'San José El Rodeo', 1);
INSERT INTO `municipio` (`idmunicipio`, `iddepartamento`, `municipio`, `activo`) VALUES(4, 1, 'Tecún Umán', 1);
INSERT INTO `municipio` (`idmunicipio`, `iddepartamento`, `municipio`, `activo`) VALUES(5, 1, 'Malacatán', 1);
INSERT INTO `municipio` (`idmunicipio`, `iddepartamento`, `municipio`, `activo`) VALUES(6, 1, 'San Pablo', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

DROP TABLE IF EXISTS `pago`;
CREATE TABLE `pago` (
  `idpago` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL,
  `idtipopago` int(11) NOT NULL,
  `idconfiguracionpago` int(11) NOT NULL,
  `mes` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `anio` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fechacreacion` datetime NOT NULL,
  `fechapago` datetime DEFAULT NULL,
  `usuariocreacion` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fechamodificacion` datetime DEFAULT NULL,
  `usuariomodificacion` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`idpago`, `idcliente`, `idtipopago`, `idconfiguracionpago`, `mes`, `anio`, `cantidad`, `fechacreacion`, `fechapago`, `usuariocreacion`, `fechamodificacion`, `usuariomodificacion`, `activo`) VALUES(1, 4, 2, 1, 'Enero', 2021, 250, '2021-11-30 14:03:11', '2021-11-30 14:08:47', 'admin', NULL, NULL, 1);
INSERT INTO `pago` (`idpago`, `idcliente`, `idtipopago`, `idconfiguracionpago`, `mes`, `anio`, `cantidad`, `fechacreacion`, `fechapago`, `usuariocreacion`, `fechamodificacion`, `usuariomodificacion`, `activo`) VALUES(2, 4, 1, 1, 'diciembre', 2021, 2021, '2021-12-02 11:05:21', NULL, 'Cobro automatico', NULL, NULL, 1);
INSERT INTO `pago` (`idpago`, `idcliente`, `idtipopago`, `idconfiguracionpago`, `mes`, `anio`, `cantidad`, `fechacreacion`, `fechapago`, `usuariocreacion`, `fechamodificacion`, `usuariomodificacion`, `activo`) VALUES(3, 4, 1, 1, 'diciembre', 2021, 2021, '2021-12-02 11:10:00', NULL, 'Cobro automatico', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipopago`
--

DROP TABLE IF EXISTS `tipopago`;
CREATE TABLE `tipopago` (
  `idtipopago` int(11) NOT NULL,
  `pago` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fechacreacion` datetime NOT NULL,
  `usuariocreacion` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `tipopago`
--

INSERT INTO `tipopago` (`idtipopago`, `pago`, `fechacreacion`, `usuariocreacion`, `activo`) VALUES(1, 'Cobro', '2021-11-19 00:00:00', 'admin', 1);
INSERT INTO `tipopago` (`idtipopago`, `pago`, `fechacreacion`, `usuariocreacion`, `activo`) VALUES(2, 'Pago', '2021-11-19 00:00:00', 'admin', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL,
  `nombres` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidos` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `usuario` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `root` int(11) NOT NULL,
  `fechacreacion` datetime DEFAULT NULL,
  `usuariocreacion` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `fechamodificacion` datetime DEFAULT NULL,
  `usuariomodificacion` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idusuario`, `nombres`, `apellidos`, `usuario`, `password`, `root`, `fechacreacion`, `usuariocreacion`, `fechamodificacion`, `usuariomodificacion`, `activo`) VALUES(1, 'admin', 'admin', 'admin', 'admin', 1, NULL, NULL, NULL, NULL, 1);
INSERT INTO `usuario` (`idusuario`, `nombres`, `apellidos`, `usuario`, `password`, `root`, `fechacreacion`, `usuariocreacion`, `fechamodificacion`, `usuariomodificacion`, `activo`) VALUES(2, 'admin', 'admin', 'admin', 'admin', 1, NULL, NULL, NULL, NULL, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idcliente`),
  ADD KEY `idmunicipio` (`idmunicipio`),
  ADD KEY `FK_Cobro` (`idconfiguracionpago`);

--
-- Indices de la tabla `configuracionpago`
--
ALTER TABLE `configuracionpago`
  ADD PRIMARY KEY (`idconfiguracionpago`);

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`iddepartamento`);

--
-- Indices de la tabla `municipio`
--
ALTER TABLE `municipio`
  ADD PRIMARY KEY (`idmunicipio`),
  ADD KEY `FK_departamento` (`iddepartamento`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idpago`),
  ADD KEY `fk_submittedForecast` (`idtipopago`),
  ADD KEY `fk_cliente` (`idcliente`),
  ADD KEY `FK_Configuracion` (`idconfiguracionpago`);

--
-- Indices de la tabla `tipopago`
--
ALTER TABLE `tipopago`
  ADD PRIMARY KEY (`idtipopago`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idusuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idcliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `configuracionpago`
--
ALTER TABLE `configuracionpago`
  MODIFY `idconfiguracionpago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `iddepartamento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `municipio`
--
ALTER TABLE `municipio`
  MODIFY `idmunicipio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idpago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tipopago`
--
ALTER TABLE `tipopago`
  MODIFY `idtipopago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idusuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FK_Cobro` FOREIGN KEY (`idconfiguracionpago`) REFERENCES `configuracionpago` (`idconfiguracionpago`),
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`idmunicipio`) REFERENCES `municipio` (`idmunicipio`);

--
-- Filtros para la tabla `municipio`
--
ALTER TABLE `municipio`
  ADD CONSTRAINT `FK_departamento` FOREIGN KEY (`iddepartamento`) REFERENCES `departamento` (`iddepartamento`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `FK_Configuracion` FOREIGN KEY (`idconfiguracionpago`) REFERENCES `configuracionpago` (`idconfiguracionpago`),
  ADD CONSTRAINT `fk_cliente` FOREIGN KEY (`idcliente`) REFERENCES `cliente` (`idcliente`),
  ADD CONSTRAINT `fk_submittedForecast` FOREIGN KEY (`idtipopago`) REFERENCES `tipopago` (`idtipopago`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
