--liquibase formatted sql
--changeset yuliya:create-order-table
CREATE TYPE order_status AS ENUM ('CREATED');

CREATE TABLE orders
(id UUID NOT NULL,
customer_name VARCHAR(100),
status order_status NOT NULL DEFAULT 'CREATED',
created_at TIMESTAMP NOT NULL,
total_amount NUMERIC(19,4) NOT NULL,
CONSTRAINT pk_order PRIMARY KEY (id)
);
