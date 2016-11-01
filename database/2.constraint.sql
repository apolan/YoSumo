
-- usuario
ALTER TABLE `yosumo`.usuario
ADD CONSTRAINT uc_email UNIQUE (email);

ALTER TABLE `yosumo`.usuario
ADD CONSTRAINT uc_username UNIQUE (username);

ALTER TABLE `yosumo`.usuario
ADD CONSTRAINT uc_documento UNIQUE (documento);


-- comercio
ALTER TABLE `yosumo`.comercio
ADD CONSTRAINT uc_nit UNIQUE (nit, nombre, ciudad, direccion );


-- denuncia

ALTER TABLE `yosumo`.denuncia
ADD CONSTRAINT `cnt_denunia_usuario`
FOREIGN KEY (`username`) REFERENCES `usuario`(`username`)
ON UPDATE CASCADE ON DELETE CASCADE;

 ALTER TABLE `yosumo`.denuncia
 ADD CONSTRAINT uc_date UNIQUE (username, dt_denuncia, nombre_comercio, direccion_comercio, latitud , longitud);
-- impuesto
ALTER TABLE `yosumo`.impuesto
ADD CONSTRAINT `cnt_factura_impuesto`
FOREIGN KEY (`fk_factura`) REFERENCES `factura`(`id`)
ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE `yosumo`.impuesto
ADD CONSTRAINT uc_impuesto UNIQUE (valor_total, fk_factura );

 
 -- factura

ALTER TABLE `yosumo`.factura
ADD CONSTRAINT `cnt_factura_usuario`
FOREIGN KEY (`fk_usuario`) REFERENCES `usuario`(`username`)
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE `yosumo`.factura
ADD CONSTRAINT `cnt_factura_comercio`
FOREIGN KEY (`fk_comercio`) REFERENCES `comercio`(`id`)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `yosumo`.factura
ADD CONSTRAINT uc_factura UNIQUE (fk_usuario, dt_compra, fk_comercio , valor_total);


-- amigo
ALTER TABLE `yosumo`.amigo
ADD CONSTRAINT `cnt_amigo_usuario`
FOREIGN KEY (`fk_usuario`) REFERENCES `usuario`(`username`)
ON UPDATE CASCADE ON DELETE CASCADE;


