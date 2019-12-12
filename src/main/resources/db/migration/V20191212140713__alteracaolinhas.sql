drop index linha.ix_linha_01

alter table linha alter column cod_linha varchar (10) NOT NULL

alter table linha alter column nome_linha varchar (50) NOT NULL

create unique index ix_linha_01 on linha (cod_linha, cat_linha asc);
