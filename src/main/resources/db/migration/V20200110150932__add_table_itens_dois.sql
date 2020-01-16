create table itens
(
id    BIGINT    IDENTITY    (1, 1)        NOT NULL PRIMARY KEY,
preco           DECIMAL     (6,2)         NOT NULL,
fk_pedido  BIGINT   FOREIGN KEY REFERENCES  pedidos(id_pedido)
);