
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

