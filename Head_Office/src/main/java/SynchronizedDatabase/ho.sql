CREATE TABLE product_sale(
                             id SERIAL PRIMARY KEY NOT NULL,
                             date DATE,
                             region VARCHAR(255),
                             product VARCHAR(255),
                             qty INTEGER,
                             cost FLOAT,
                             amt REAL,
                             tax FLOAT,
                             total NUMERIC,
                             bo_num INTEGER
);
SELECT * FROM product_sale;