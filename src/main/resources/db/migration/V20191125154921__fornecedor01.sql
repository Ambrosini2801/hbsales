create table cad_fornecedor(

    id    BIGINT IDENTITY (1, 1) primary key NOT NULL,
    razao_social VARCHAR(100)           NOT NULL,
    cnpj VARCHAR(14)                    NOT NULL,
    nome_fantasia VARCHAR(100)          NOT NULL,
    endereco VARCHAR(100)               NOT NULL,
    telefone VARCHAR(13)                NOT NULL,
    email VARCHAR (50)                 NOT NULL
);
create unique index ix_cad_fornecedor_01 on cad_fornecedor (cnpj asc);
