CREATE TABLE registro_auditoria (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    entidade VARCHAR(30) NOT NULL,
    registro_id BIGINT NOT NULL,
    operacao VARCHAR(10) NOT NULL,
    usuario_responsavel VARCHAR(100) NOT NULL,
    data_alteracao TIMESTAMPTZ NOT NULL,
    dados_antes JSONB,
    dados_depois JSONB
);