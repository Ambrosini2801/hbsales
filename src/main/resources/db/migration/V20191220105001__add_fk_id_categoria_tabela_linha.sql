ALTER TABLE linha ADD CONSTRAINT fk_id_categoria
FOREIGN KEY (id_categoria) REFERENCES categoria (id);