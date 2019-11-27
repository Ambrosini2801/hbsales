create table cad_fornecedor(
    id    BIGINT IDENTITY (1, 1)        NOT NULL primary key,
    razao_social VARCHAR(100)           NOT NULL,
    cnpj VARCHAR(14)                    NOT NULL,
    nome_fantasia VARCHAR(100)          NOT NULL,
    endereco VARCHAR(100)               NOT NULL,
    telefone VARCHAR(50)                NOT NULL,
    email VARCHAR (100)                 NOT NULL
);
create unique index ix_cad_fornecedor_01 on cad_fornecedor (razao_social asc);
create unique index ix_cad_fornecedor_02 on cad_fornecedor (cnpj asc);