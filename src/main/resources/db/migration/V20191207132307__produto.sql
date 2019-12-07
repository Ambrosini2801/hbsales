create table produto
(
    id    BIGINT IDENTITY (1, 1)        NOT NULL,
    cod_produto VARCHAR (10)            NOT NULL,
    preco DECIMAL                       NOT NULL,
    unidade_cx int (25)                 NOT NULL,
    peso_uni VARCHAR (25)               NOT NULL,
    val_produto DATE                    NOT NULL,
    FOREIGN KEY(id_linhacategoria) REFERENCES produto(id)
);

create unique index ix_produto01 on produto (cod_produto asc);