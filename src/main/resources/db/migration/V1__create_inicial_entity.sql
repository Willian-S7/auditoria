CREATE TABLE usuario (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome varchar(100) NOT NULL,
    login VARCHAR(100) NOT NULL UNIQUE,
    ativo boolean not NULL
);

CREATE TABLE categoria (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome varchar(100) NOT NULL UNIQUE,
    ativo boolean not null
);

CREATE TABLE produto (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome varchar(100) NOT NULL,
    descricao varchar(300) NOT NULL,
    preco decimal(10,2) NOT NULL CHECK (preco > 0),
    qtd_Estoque INT not null CHECK (qtd_estoque >= 0),
    ativo boolean not null,
    categoria_id BIGINT NOT NULL,

    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);