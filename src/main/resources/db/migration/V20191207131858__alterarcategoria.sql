drop index ix_categoria_01.categoria

alter table categoria alter column cod_categoria VARCHAR (10)

alter table categoria alter column nome_categoria VARCHAR (50)

create unique index ix_categoria_01 on categoria (nome_categoria, id_fornecedor asc);