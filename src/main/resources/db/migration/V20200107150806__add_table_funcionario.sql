create table funcionarios(

id    BIGINT           IDENTITY  (1, 1)       NOT NULL PRIMARY KEY,
nome_funcionarios      VARCHAR(50)                 NOT NULL,
email                  VARCHAR(50)                 NOT NULL,
uuid                   VARCHAR(36)                 NOT NULL,

);