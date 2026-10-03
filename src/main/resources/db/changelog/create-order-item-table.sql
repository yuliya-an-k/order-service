--liquibase formatted sql
--changeset yuliya:create-order-item-table
CREATE TABLE order_item
(id UUID NOT NULL,
order_id UUID,
product_id UUID,
product_name VARCHAR(100),
unit_price NUMERIC(19,4) NOT NULL,
quantity INT,
CONSTRAINT pk_order_item PRIMARY KEY (id),
FOREIGN KEY order_id REFERENCES orders(id)
);

