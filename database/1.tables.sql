SET sql_notes = 0;      -- Temporarily disable the "Table already exists" warning

CREATE TABLE IF NOT EXISTS `yosumo`.`usuario` (
--  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `nombre` varchar(50) NOT NULL COMMENT 'Nombre completo del usuario',
  `username` varchar(16) NOT NULL COMMENT 'Username de un usuario',
  `email` varchar(255) NOT NULL COMMENT 'Email de un usuario',
  `password` varchar(32) NOT NULL COMMENT 'Password de un usuario',
  `estado` varchar(20) NOT NULL DEFAULT 'activo' COMMENT 'Estado de un usuario',
  `documento` BIGINT(14) NOT NULL COMMENT 'Documento de identificación de un usuario',
  -- `ciudad` varchar(20) NOT NULL,
  -- `direccion` varchar(50) 	NOT NULL,
  `dt_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `yosumo`.`amigo` (
  `username` varchar(16) NOT NULL COMMENT 'Id del usuario',
  `fk_usuario` varchar(16) NOT NULL COMMENT 'Es el id de un amigo del usuario de un usuario',
  PRIMARY KEY (`username`, `fk_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `yosumo`.`comercio` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `nit` BIGINT(12) NOT NULL COMMENT 'Documento nit de identificación del comercio',
  `nombre` varchar(150) NOT NULL COMMENT 'Nombre del comercio',
  `nombre_legal` varchar(50) NOT NULL COMMENT 'Nombre legal del comercio',
  `regimen` varchar(50) NOT NULL COMMENT 'Regimen al que pertenece el comercio',
  `direccion` varchar(50) 	NOT NULL COMMENT 'Es punto dueno de la bicicleta',
  `ciudad` varchar(20) NOT NULL COMMENT 'Ciudad a la que pertenece el comercio',
  `estado` varchar(20) NOT NULL DEFAULT 'activo' COMMENT 'Estado',
  `dt_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `yosumo`.`factura` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `nk_consecutivo` varchar(20) NULL COMMENT 'Llave natural de la tabla',
  `dt_compra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de la compra',
  `dt_captura` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de la captura',
  `pathfactura` varchar(150) NULL COMMENT 'Path de la factura',
  `fk_comercio` int(9) NULL COMMENT 'Nombre del comercio',
  `fk_usuario` varchar(40) NOT NULL COMMENT 'FK usuario username',
  `valor_total` double NULL COMMENT 'VALOR TOTAL',
  `tag` VARCHAR(150) NULL COMMENT 'tag de las facturas',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS `yosumo`.`impuesto` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `porcentaje_iva` float(3) NOT NULL COMMENT 'Porcentaje iva de la factura',
  `valor_iva` double NULL COMMENT 'Valor iva de la factura',
  `porcentaje_ico` float(3) NOT NULL COMMENT 'Porcentaje ico de la factura',
  `valor_ico` double NULL COMMENT 'Valor ico de la factura',
  `valor_total` double NULL COMMENT 'Valor total de la factura',
  `fk_factura` int (9) NULL COMMENT 'id de la fatura',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `yosumo`.`denuncia` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
--  `fk_usuario` float(8) NULL COMMENT 'id del usuario que denuncia',
  `username` varchar(16) NOT NULL COMMENT 'Username de un usuario',
  `nombre_comercio` varchar(50) NULL COMMENT 'Nombre del comercio',
  `direccion_comercio` varchar(50) NULL COMMENT 'Direccion del comercio',
  `comentario` varchar(250) NOT NULL COMMENT 'Comentario de la denuncia',
  `latitud` double(10,8) NULL COMMENT 'Coordenada latitud de la denuncia',
  `longitud` double(10,8) NULL COMMENT 'Coordenada longitud de la denuncia',
  `estado` VARCHAR(9) NULL COMMENT 'Estado de la denuncia',
  `dt_denuncia` timestamp NOT NULL COMMENT 'Fecha de la denuncia',
  `dt_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de ceracion de la denuncia',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET sql_notes = 1;


