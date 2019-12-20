create table linha(
    id BIGINT IDENTITY (1, 1)    NOT NULL PRIMARY KEY,
    cod_linha VARCHAR  (10)      NOT NULL,
    nome_linha VARCHAR (50)      NOT NULL,
    id_categoria BIGINT          NOT NULL,
    FOREIGN KEY (id_categoria)   REFERENCES linha(id)
);

create unique index ix_linha_01 on linha (cod_linha asc);