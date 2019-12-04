create table linha(
    id  BIGINT IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    cod_linha   VARCHAR (100)    NOT NULL,
    cat_linha   VARCHAR (100)   NOT NULL,
    nome_linha VARCHAR  (100)     NOT NULL,

);
--create unique index ix_linha_01 on linha (cod_linha, cat_linha asc);

