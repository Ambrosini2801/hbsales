create table categoria(

    id    BIGINT IDENTITY (1, 1)        NOT NULL primary key,
    cod_categoria VARCHAR(100)            NOT NULL,
    nome_categoria VARCHAR(100)          NOT NULL,
    id_fornecedor BIGINT NOT NULL,
    FOREIGN KEY(id_fornecedor) REFERENCES cad_fornecedor(id)
);


