ALTER TABLE produto ADD CONSTRAINT fk_cat_linha
FOREIGN KEY (id) REFERENCES linha (id);