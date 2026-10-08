--liquibase formatted sql

--changeset dev:001
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    customer_name VARCHAR(255),
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE,
    total_amount NUMERIC(19, 2)
);

CREATE TABLE order_item (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL REFERENCES orders(id),
    product_id UUID,
    product_name VARCHAR(255) NOT NULL,
    unit_price NUMERIC(19, 2) NOT NULL,
    quantity INTEGER NOT NULL
);