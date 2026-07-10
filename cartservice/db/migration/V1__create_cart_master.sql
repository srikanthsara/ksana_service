CREATE TABLE cart_master (

    cart_id BIGSERIAL PRIMARY KEY,

    customer_id VARCHAR(50),

    customer_name VARCHAR(255),

    total_amount NUMERIC(12,2),

    total_items INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);