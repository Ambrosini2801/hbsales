create table vendas(

id    BIGINT       IDENTITY  (1, 1)     NOT NULL PRIMARY KEY,
inicio_vendas      DATE                 NOT NULL,
fim_vendas         DATE                 NOT NULL,
retirada_pedido    DATE                 NOT NULL,
descricao          VARCHAR   (50)       NOT NULL,

fk_id_fornecedor BIGINT   FOREIGN KEY REFERENCES cad_fornecedor(id)
);