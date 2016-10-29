CREATE TABLE `yosumo`.`usuario` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `nombre` varchar(50) NOT NULL COMMENT 'Nombre completo del usuario',
  `username` varchar(16) NOT NULL COMMENT 'Username de un usuario',
  `email` varchar(255) NOT NULL COMMENT 'Email de un usuario',
  `password` varchar(32) NOT NULL COMMENT 'Password de un usuario',
  `estado` varchar(20) NOT NULL DEFAULT 'activo' COMMENT 'Estado de un usuario',
  `documento` BIGINT(14) NOT NULL COMMENT 'Documento de identificación de un usuario',
  -- `ciudad` varchar(20) NOT NULL,
  -- `direccion` varchar(50) 	NOT NULL,
  `dt_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `yosumo`.`amigo` (
  `id` int(9) NOT NULL COMMENT 'Id del amigo',
  `fk_usuairo` int(9) NOT NULL COMMENT 'Es el id de un amigo del usuario de un usuario',
  PRIMARY KEY (`id`, `fk_usuairo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `yosumo`.`comercio` (
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

CREATE TABLE `yosumo`.`factura` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `nk_consecutivo` BIGINT(12) NOT NULL COMMENT 'Consecutivo de la factura',
  `dt_compra` timestamp NULL COMMENT 'Fecha de compra de la factura',
  `dt_captura` timestamp NULL COMMENT 'Fecha de captura de la factura',
  `fk_usuario` int(9) NOT NULL COMMENT 'id del usuario',
  `fk_comercio` int(9) NOT NULL COMMENT 'id del comercio',
  `fk_impuesto` int(9) NOT NULL COMMENT 'id del impuesto',
  `valor_total` BIGINT(15) NOT NULL COMMENT 'Valo de la factura',
  `tag` varchar(50) default null COMMENT 'Los tags de la factura',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `yosumo`.`impuesto` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `porcentaje_iva` float(8) NOT NULL COMMENT 'Porcentaje iva de la factura',
  `valor_iva` double NULL COMMENT 'Valor iva de la factura',
  `porcentaje_ico` float(8) NOT NULL COMMENT 'Porcentaje ico de la factura',
  `valor_ico` double NULL COMMENT 'Valor ico de la factura',
  `fk_factura` int(9) COMMENT 'Es el id  de la factura',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `yosumo`.`denuncia` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'Id de la tabla',
  `fk_usuario` float(8) NULL COMMENT 'id del usuario que denuncia',
  `nombre_comercio` varchar(50) NULL COMMENT 'Nombre del comercio',
  `direccion_comercio` varchar(50) NULL COMMENT 'Direccion del comercio',
  `comentario` varchar(250) NOT NULL COMMENT 'Comentario de la denuncia',
  `dt_denuncia` timestamp NULL COMMENT 'Fecha de la denuncia',
  `dt_creacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de ceracion de la denuncia',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

