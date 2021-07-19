CREATE TABLE IF NOT EXISTS hta.currency_code_names
(
    currency_code_names_id BIGSERIAL NOT NULL,
    currency_code VARCHAR(4) NOT NULL,
    currency_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    state BOOLEAN DEFAULT TRUE,
    CONSTRAINT currency_code_names_id_pkey PRIMARY KEY (currency_code_names_id),
    CONSTRAINT currency_name_unique UNIQUE (currency_name),
    CONSTRAINT currency_code_unique UNIQUE (currency_code)
);

CREATE TABLE IF NOT EXISTS hta.currency_exchange
(
    currency_exchange_id BIGSERIAL NOT NULL,
    amount_exchange_rate DECIMAL(11,5),
    currency_exchange_destination VARCHAR(255) NOT NULL,
    currency_exchange_origin VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    state BOOLEAN DEFAULT TRUE,
    CONSTRAINT currency_exchange_pkey PRIMARY KEY (currency_exchange_id)
);

CREATE TABLE IF NOT EXISTS hta.currency_transaction
(
    currency_transaction_id BIGSERIAL NOT NULL,
    account_destination DECIMAL(19,2) NOT NULL,
    account_origin DECIMAL(19,2) NOT NULL,
    amount DECIMAL(11,5) NOT NULL,
    amount_exchange_rate DECIMAL(11,5) NOT NULL,
    amount_rate DECIMAL(11,5) NOT NULL,
    currency_destination VARCHAR(255) NOT NULL,
    currency_origin VARCHAR(255) NOT NULL,
    document_number VARCHAR(255)  NOT NULL,
    operation_number VARCHAR(255)  NOT NULL,
    username VARCHAR(255)  NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    state BOOLEAN DEFAULT TRUE,
    CONSTRAINT currency_transaction_pkey PRIMARY KEY (currency_transaction_id)
);