--CREATE DATABASE search_db;

CREATE TABLE product_master (

    product_id VARCHAR(50) PRIMARY KEY,

    product_name VARCHAR(255) NOT NULL,

    category VARCHAR(100),

    brand VARCHAR(100),

    description TEXT,

    price NUMERIC(12,2),

    currency VARCHAR(10),

    available_quantity INT,

    rating NUMERIC(2,1),

    in_stock BOOLEAN,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);