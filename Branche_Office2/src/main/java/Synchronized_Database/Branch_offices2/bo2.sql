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
                             sent BOOLEAN DEFAULT FALSE
);



INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total) VALUES('2021-1-31','tunisie','Pantalon',2,25,12.2,7.5,60);

INSERT INTO product_sale(date, region, product, qty, cost, amt, tax, total) VALUES('2021-5-16','france','jean',2,25,12.2,7.5,60);

INSERT INTO product_sale(date, region, produc, qty, cost, amt, tax, total) VALUES('2021-6-18','tunisie','chemise',2,25,12.2,7.5,60);