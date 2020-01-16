create table itens
(
id    BIGINT    IDENTITY    (1, 1)        NOT NULL PRIMARY KEY,
quantidade       INT                      NOT NULL,
preco           DECIMAL     (6,2)         NOT NULL,
fk_pedido_produto BIGINT   FOREIGN KEY REFERENCES produto(id)
);