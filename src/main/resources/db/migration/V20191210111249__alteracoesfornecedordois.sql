drop index cad_fornecedor.ix_cad_fornecedor_01

alter table cad_fornecedor alter column razao_social varchar(100) not null

alter table cad_fornecedor alter column cnpj varchar(14) not null

alter table cad_fornecedor alter column nome_fantasia varchar (100) not null

alter table cad_fornecedor alter column endereco varchar(100) not null

alter table cad_fornecedor alter column telefone varchar(13) not null

alter table cad_fornecedor alter column email varchar(50) not null

create unique index ix_cad_fornecedor_01 on cad_fornecedor (cnpj asc);