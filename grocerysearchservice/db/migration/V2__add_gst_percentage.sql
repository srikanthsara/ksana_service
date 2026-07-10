ALTER TABLE product_master
ADD COLUMN gst_percentage NUMERIC(5,2);

UPDATE product_master
SET gst_percentage = 0.00
WHERE gst_percentage IS NULL;

ALTER TABLE product_master
ADD COLUMN image_url VARCHAR(1000);