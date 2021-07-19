
CREATE TABLE hta.role (
  role_id BIGSERIAL,
  role_name VARCHAR(255) NOT NULL,
  role_description TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  state BOOLEAN DEFAULT TRUE,
  PRIMARY KEY(role_id)
);


CREATE TABLE hta.user(
  user_id BIGSERIAL,
  name VARCHAR(200) NOT NULL,
  last_name VARCHAR(200) NOT NULL,
  username VARCHAR(255) NOT NULL,
  document_number VARCHAR(20) NOT NULL,
  password TEXT NOT NULL,
  enabled BOOLEAN DEFAULT TRUE,
  is_blocked BOOLEAN DEFAULT FALSE,
  login_failures INT DEFAULT 0,
  password_expires_at TIMESTAMP,
  block_reason TEXT DEFAULT 'PASSWORD_CHANGE_REQUIRED',
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  state BOOLEAN DEFAULT TRUE,
  UNIQUE(username),
  PRIMARY KEY(user_id)
  );

CREATE TABLE hta.user_role(
  role_id INT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY(role_id,user_id),
  FOREIGN KEY(user_id) REFERENCES hta.user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(role_id) REFERENCES hta.role(role_id)  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE hta.permission (
  permission_id BIGSERIAL,
  permission_name VARCHAR(255),
  permission_description TEXT,
  permission_type TEXT DEFAULT 'MODULE',
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW(),
  state BOOLEAN DEFAULT TRUE,
  PRIMARY KEY(permission_id)
);


CREATE TABLE hta.role_permission(
  permission_id INT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY(permission_id,role_id),
  FOREIGN KEY(permission_id) REFERENCES hta.permission(permission_id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(role_id) REFERENCES hta.role(role_id) ON UPDATE CASCADE ON DELETE CASCADE
);


insert into hta.role(role_name,role_description)

values	('ADMIN', 'Administator'),
		('USER', 'User common'),
		('DEV', 'User Developers');

insert into hta.permission(permission_name,permission_description)
values
('Nivel 1','Nivel 1 de aprobación'),
('Nivel 2','Nivel 2 de aprobación'),
('Nivel 3','Nivel 3 de aprobación'),
('Cambio Precio','Cambio de Precio de productos'),
('Modificación Stock','Modificación de stock para la venta'),
('Modificacion Flete','Modificación de flete para el envió del pedido'),
('Credito','Venta con crédito'),
('UEMPRESA','Usuario de venta empresa'),
('UCLIENTE','Usuario de venta cliente');

insert into hta.role_permission
values
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(6,1),
(7,1);

select * from hta.role;
select * from hta.permission;
