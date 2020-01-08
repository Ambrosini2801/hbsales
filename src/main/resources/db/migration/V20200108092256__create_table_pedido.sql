create table pedidos(

id_pedido    BIGINT   IDENTITY  (1, 1)  NOT NULL PRIMARY KEY,
codigo       VARCHAR            (10)    NOT NULL,
status       VARCHAR            (10)    NOT NULL,
quantidade   INT                        NOT NULL,
uuid         VARCHAR            (36)    NOT NULL,
data         DATE                       NOT NULL,
fk_pedido_fornecedor   BIGINT  FOREIGN  KEY  REFERENCES  cad_fornecedor(id),
fk_pedido_produto      BIGINT  FOREIGN  KEY  REFERENCES  produto(id),
fk_pedido_vendas       BIGINT  FOREIGN  KEY  REFERENCES  vendas(id)
);

create unique index ix_pedidos_01 on pedidos (codigo asc);


