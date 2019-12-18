drop index linha.ix_linha_01

alter table linha add id_categoria BIGINT NOT NULL FOREIGN KEY(id_categoria) REFERENCES linha(id);

alter table linha add constraint ix_linha_0 unique (cat_linha, id_categoria);
