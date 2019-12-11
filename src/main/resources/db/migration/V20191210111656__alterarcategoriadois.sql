drop index categoria.ix_categoria_01

create unique index ix_categoria_01 on categoria (cod_categoria asc);
