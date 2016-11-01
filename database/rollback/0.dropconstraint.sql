
-- usuario
ALTER TABLE `yosumo`.usuario
DROP INDEX uc_email;

ALTER TABLE `yosumo`.usuario
DROP INDEX uc_username;

ALTER TABLE `yosumo`.usuario
DROP INDEX uc_documento;


-- comercio
ALTER TABLE `yosumo`.comercio
DROP INDEX uc_nit;


-- denuncia
ALTER TABLE `yosumo`.denuncia
DROP FOREIGN KEY `cnt_denunia_usuario`;

ALTER TABLE `yosumo`.denuncia
DROP INDEX uc_date;

-- impuesto
ALTER TABLE `yosumo`.impuesto
DROP FOREIGN KEY `cnt_factura_impuesto`;


ALTER TABLE `yosumo`.impuesto
ADD CONSTRAINT uc_impuesto UNIQUE (valor_total, fk_factura );


 -- factura
  
ALTER TABLE `yosumo`.factura
DROP FOREIGN KEY  `cnt_factura_usuario`;

ALTER TABLE `yosumo`.factura
DROP FOREIGN KEY  `cnt_factura_comercio`;


-- amigo
ALTER TABLE `yosumo`.amigo
DROP FOREIGN KEY  `cnt_amigo_usuario`;
