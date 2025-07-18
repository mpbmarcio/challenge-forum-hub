create table respostas(
    id bigint not null auto_increment,
    topico_id bigint not null,
    data_inc datetime not null,
    autor_id bigint not null,
    mensagem varchar(255) not null,

    primary key(id),

    constraint fk_respostas_topico_id foreign key (topico_id) references topicos(id),
    constraint fk_respostas_autor_id foreign key (autor_id) references usuarios(id)
);