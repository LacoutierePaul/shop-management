
DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    quantity INT NOT NULL,
    purchasePrice DECIMAL(6,2) NOT NULL,
    sellingPrice DECIMAL(6,2) NOT NULL,
    category VARCHAR(45) NOT NULL,
    size INT,
    PRIMARY KEY (id));

DROP TABLE IF EXISTS money;
CREATE TABLE money(
    id INT NOT NULL,
    capital DECIMAL(6,2) NOT NULL,
    income DECIMAL(6,2) NOT NULL,
    cost DECIMAL(6,2) NOT NULL,
    PRIMARY KEY (id));


INSERT INTO product VALUES (NULL, "Elegant Necklace", 10, 25, 49, "Accessories", NULL);
INSERT INTO product VALUES (NULL, "Summer Dress", 15, 39, 79, "Clothes", 38);
INSERT INTO product VALUES (NULL, "Running Shoes", 20, 49, 99, "Shoes", 42);
INSERT INTO product VALUES (NULL, "Leather Wallet", 12, 34, 69, "Accessories", NULL);
INSERT INTO product VALUES (NULL, "Winter Coat", 8, 89, 179, "Clothes", 40);
INSERT INTO product VALUES (NULL, "High Heel Boots", 18, 59, 119, "Shoes", 38);
INSERT INTO product VALUES (NULL, "Statement Earrings", 14, 19, 39, "Accessories", NULL);
INSERT INTO product VALUES (NULL, "Casual Jeans", 22, 29, 59, "Clothes", 36);
INSERT INTO product VALUES (NULL, "Sneakers", 25, 39, 79, "Shoes", 44);
INSERT INTO product VALUES (NULL, "Sun Hat", 7, 15, 29, "Accessories", NULL);
INSERT INTO product VALUES (NULL, "Diamond Stud Earrings", 8, 149, 299, "Accessories", NULL);
INSERT INTO product VALUES (NULL, "Floral Maxi Dress", 12, 59, 119, "Clothes", 42);
INSERT INTO product VALUES (NULL, "Athletic Sneakers", 30, 44, 89, "Shoes", 39);
INSERT INTO product VALUES (NULL, "Leather Tote Bag", 15, 79, 159, "Accessories", NULL);
INSERT INTO product VALUES (NULL, "Knit Sweater", 18, 34, 69, "Clothes", 38);
INSERT INTO product VALUES (NULL, "Classic Ballet Flats", 25, 29, 59, "Shoes", 37);
INSERT INTO product VALUES (NULL, "Pearl Bracelet", 10, 39, 79, "Accessories", NULL);
INSERT INTO product VALUES (NULL, "Skinny Jeans", 20, 24, 49, "Clothes", 34);
INSERT INTO product VALUES (NULL, "Hiking Boots", 15, 69, 139, "Shoes", 41);
INSERT INTO product VALUES (NULL, "Straw Beach Bag", 5, 19, 39, "Accessories", NULL);

insert into money values(1, 1000, 0, 0);

select * from product;