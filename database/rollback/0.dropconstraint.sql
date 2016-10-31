
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

-- amigo
ALTER TABLE `yosumo`.amigo
DROP INDEX uc_amigo;


-- denuncia
 ALTER TABLE `yosumo`.denuncia
DROP INDEX uc_date;
