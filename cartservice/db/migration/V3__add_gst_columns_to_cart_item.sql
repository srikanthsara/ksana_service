ALTER TABLE cart_item
ADD COLUMN gst_percentage NUMERIC(5,2);

ALTER TABLE cart_item
ADD COLUMN gst_amount NUMERIC(12,2);


ALTER TABLE cart_item
ALTER COLUMN unit_price TYPE NUMERIC(12,2);

ALTER TABLE cart_item
ALTER COLUMN total_price TYPE NUMERIC(12,2);
