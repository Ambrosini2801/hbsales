drop index produto.ix_produto01

alter table produto alter column  preco           DECIMAL     (9,2)                NOT NULL
alter table produto alter column  peso_uni        DECIMAL     (9,3)                NOT NULL
alter table produto add           unidade_peso    VARCHAR     (5)                  NOT NULL
alter table produto alter column  val_produto     DATE                             NOT NULL

