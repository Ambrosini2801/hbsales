create table itens
(
id    BIGINT    IDENTITY    (1, 1)        NOT NULL PRIMARY KEY,
quantidade       INT                      NOT NULL,
preco           DECIMAL     (6,2)         NOT NULL,
fk_produto BIGINT   FOREIGN KEY REFERENCES produto(id),
fk_pedido  BIGINT   FOREIGN KEY REFERENCES  pedidos(id_pedido)
);