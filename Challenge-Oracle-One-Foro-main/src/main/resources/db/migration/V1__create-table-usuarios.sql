CREATE TABLE usuarios(
  id bigint NOT NULL AUTO_INCREMENT,
  nombre varchar(100) NOT NULL,
  email varchar(100) NOT NULL unique,
  contraseña varchar(300) NOT NULL,
  primary key(id)
);