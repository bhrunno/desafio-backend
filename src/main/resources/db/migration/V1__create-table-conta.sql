-- Table: public.conta
-- DROP TABLE IF EXISTS public.conta;
CREATE TABLE IF NOT EXISTS public.conta
(
    "ID" integer NOT NULL,
    data_vencimento date,
    data_pagamento date,
    valor bigint,
    descricao character(1) COLLATE pg_catalog."default",
    situacaoConta integer,
    CONSTRAINT conta_pkey PRIMARY KEY ("ID")
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.conta
    OWNER to postgres;