create table categorialinha(

    id    BIGINT IDENTITY (1, 1)        NOT NULL primary key,
    cod_linha VARCHAR(100)          NOT NULL,
    nome_linha VARCHAR(100)          NOT NULL,
    cat_linha VARCHAR (100)            NOT NULL,
    categoria BIGINT NOT NULL,
    FOREIGN KEY(cad_fornecedor) REFERENCES linhas(id)
);
create unique index ix_linhas_01 on linhas (cod_linha, cat_linha asc);
