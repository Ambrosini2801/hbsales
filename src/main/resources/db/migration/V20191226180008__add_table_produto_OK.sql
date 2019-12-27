create table produto
(
id    BIGINT    IDENTITY    (1, 1)        NOT NULL PRIMARY KEY,
cod_produto     VARCHAR     (10)          NOT NULL,
nome_produto    VARCHAR     (200)         NOT NULL,
preco           DECIMAL     (9,2)         NOT NULL,
peso_uni        DECIMAL     (9,3)         NOT NULL,
unidade_peso    VARCHAR     (5)           NOT NULL,
val_produto     DATE                      NOT NULL,
unidade_cx      INT                       NOT NULL,
fk_id_linha BIGINT   FOREIGN KEY REFERENCES linha(id)
);

create unique index ix_produto01 on produto (cod_produto asc);




