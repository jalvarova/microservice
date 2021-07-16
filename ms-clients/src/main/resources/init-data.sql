CREATE TABLE IF NOT EXISTS hta.document_type
(
    document_type_id BIGSERIAL NOT NULL,
    type CHARACTER VARYING(2) NOT NULL,
    description CHARACTER VARYING (60) NOT NULL,
    short_description CHARACTER VARYING (20) NOT NULL,
    state BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT document_type_pkey PRIMARY KEY (document_type_id),
    CONSTRAINT short_description_unique UNIQUE (short_description),
    CONSTRAINT description_unique UNIQUE (description),
    CONSTRAINT type_unique UNIQUE (type)
);


CREATE TABLE IF NOT EXISTS hta.contact
(
    contact_id BIGSERIAL NOT NULL,
    contact_values jsonb,
    document CHARACTER VARYING(255),
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    state BOOLEAN DEFAULT TRUE,
    CONSTRAINT contact_pkey PRIMARY KEY (contact_id)
);


CREATE TABLE IF NOT EXISTS hta.client
(
    client_id BIGSERIAL NOT NULL,
    address CHARACTER VARYING(100),
    document CHARACTER VARYING(20),
    name CHARACTER VARYING(50),
    last_name CHARACTER VARYING(50),
    bird_date date,
    business_name CHARACTER VARYING(255),
    contact_id bigint,
    document_type_id bigint,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    state BOOLEAN DEFAULT TRUE,
    CONSTRAINT client_pkey PRIMARY KEY (client_id),
    CONSTRAINT document_unique UNIQUE (document),
    CONSTRAINT business_name_unique UNIQUE (business_name),
    CONSTRAINT fk_document_type_id FOREIGN KEY (document_type_id)
        REFERENCES hta.document_type (document_type_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_contact_id FOREIGN KEY (contact_id)
        REFERENCES hta.contact (contact_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

insert into hta.document_type(type, description,short_description)

values	('01', 'LIBRETA ELECTORAL O DNI','DNI'),
		('04', 'CARNET DE EXTRANJERIA','CARNET EXT.'),
		('06', 'REG. UNICO DE CONTRIBUYENTES','RUC'),
		('07', 'PASAPORTE','PASAPORTE');

select * from hta.document_type;
