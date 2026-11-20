CREATE OR REPLACE FUNCTION  registrar_auditoria() RETURNS TRIGGER AS $$
DECLARE
    v_registro_id BIGINT;
    v_dados_antes JSONB;
    v_dados_depois JSONB;
BEGIN

    IF TG_OP = 'INSERT' THEN
        v_registro_id := NEW.id;
        v_dados_antes := NULL;
        v_dados_depois := to_jsonb(NEW);

    ELSIF TG_OP = 'UPDATE' THEN
        IF to_jsonb(OLD) = to_jsonb(NEW) THEN
            RETURN NULL;
        END IF;

        v_registro_id := NEW.id;
        v_dados_antes := to_jsonb(OLD);
        v_dados_depois := to_jsonb(NEW);

    ELSIF TG_OP = 'DELETE' THEN

        v_registro_id := OLD.id;
        v_dados_antes := to_jsonb(OLD);
        v_dados_depois := NULL;
    END IF;

    INSERT INTO registro_auditoria (
        entidade,
        registro_id,
        operacao,
        usuario_responsavel,
        data_alteracao,
        dados_antes,
        dados_depois
    )
    VALUES(UPPER(TG_TABLE_NAME),
        v_registro_id,
        TG_OP,
        COALESCE(
        NULLIF(current_setting('app.usuario', true), ''), 'NAO_IDENTIFICADO'
        ),
        clock_timestamp(),
        v_dados_antes,
        v_dados_depois
        );
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auditoria_usuario
AFTER INSERT OR UPDATE OR DELETE ON usuario
FOR EACH ROW
EXECUTE FUNCTION registrar_auditoria();

CREATE TRIGGER trg_auditoria_produto
AFTER INSERT OR UPDATE OR DELETE ON produto
FOR EACH ROW
EXECUTE FUNCTION registrar_auditoria();

CREATE TRIGGER trg_auditoria_categoria
AFTER INSERT OR UPDATE OR DELETE ON categoria
FOR EACH ROW
EXECUTE FUNCTION registrar_auditoria();