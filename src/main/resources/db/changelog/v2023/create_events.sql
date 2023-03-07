CREATE TABLE IF NOT EXISTS event.event_api_request (
    id integer NOT NULL DEFAULT nextval('event_api_request_id_seq'::regclass),
    time_of_event timestamp with time zone,
                                job_id character varying(255) COLLATE pg_catalog."default",
    url character varying(2048) COLLATE pg_catalog."default",
    ip_address character varying(32) COLLATE pg_catalog."default",
    token_hash character varying(255) COLLATE pg_catalog."default",
    request_id character varying(255) COLLATE pg_catalog."default",
    aws_id character varying(255) COLLATE pg_catalog."default",
    environment character varying(255) COLLATE pg_catalog."default",
    organization character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT event_api_request_pkey PRIMARY KEY (id)
);

GRANT SELECT ON TABLE event.event_api_request TO ab2d_analyst;

CREATE TABLE IF NOT EXISTS event.event_api_response (
    id integer NOT NULL DEFAULT nextval('event_api_response_id_seq'::regclass),
    time_of_event timestamp with time zone,
                                job_id character varying(255) COLLATE pg_catalog."default",
    response_code integer,
    response_string text COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    request_id character varying(255) COLLATE pg_catalog."default",
    aws_id character varying(255) COLLATE pg_catalog."default",
    environment character varying(255) COLLATE pg_catalog."default",
    organization character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT event_api_response_pkey PRIMARY KEY (id)
);

GRANT SELECT ON TABLE event.event_api_response TO ab2d_analyst;

CREATE TABLE IF NOT EXISTS event.event_bene_reload (
    id integer NOT NULL DEFAULT nextval('event_bene_reload_id_seq'::regclass),
    time_of_event timestamp with time zone,
                                job_id character varying(255) COLLATE pg_catalog."default",
    file_type character varying(255) COLLATE pg_catalog."default",
    file_name character varying(500) COLLATE pg_catalog."default",
    number_loaded integer,
    aws_id character varying(255) COLLATE pg_catalog."default",
    environment character varying(255) COLLATE pg_catalog."default",
    organization character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT event_bene_reload_pkey PRIMARY KEY (id)
);

GRANT SELECT ON TABLE event.event_bene_reload TO ab2d_analyst;

CREATE TABLE IF NOT EXISTS event.event_bene_search (
    id integer NOT NULL DEFAULT nextval('event_bene_search_id_seq'::regclass),
    time_of_event timestamp with time zone,
                                job_id character varying(255) COLLATE pg_catalog."default",
    contract_number character varying(255) COLLATE pg_catalog."default",
    benes_expected integer,
    benes_searched integer,
    num_opted_out integer,
    benes_errored integer,
    aws_id character varying(255) COLLATE pg_catalog."default",
    environment character varying(255) COLLATE pg_catalog."default",
    organization character varying(255) COLLATE pg_catalog."default",
    benes_queued integer DEFAULT 0,
    eobs_fetched integer DEFAULT 0,
    eobs_written integer DEFAULT 0,
    eob_files integer DEFAULT 0,
    benes_with_eobs integer DEFAULT 0,
    CONSTRAINT event_bene_search_pkey PRIMARY KEY (id)
);

GRANT SELECT ON TABLE event.event_bene_search TO ab2d_analyst;

CREATE INDEX IF NOT EXISTS event_bene_search_job_id_idx
    ON event.event_bene_search USING btree
    (job_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_bene_search_job_id
    ON event.event_bene_search USING btree
    (job_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS event.event_error (
    id integer NOT NULL DEFAULT nextval('event_error_id_seq'::regclass),
    time_of_event timestamp with time zone,
                                job_id character varying(255) COLLATE pg_catalog."default",
    error_type character varying(255) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    aws_id character varying(255) COLLATE pg_catalog."default",
    environment character varying(255) COLLATE pg_catalog."default",
    organization character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT event_error_pkey PRIMARY KEY (id)
);

GRANT SELECT ON TABLE event.event_error TO ab2d_analyst;

CREATE TABLE IF NOT EXISTS event.event_file (
    id integer NOT NULL DEFAULT nextval('event_file_id_seq'::regclass),
    time_of_event timestamp with time zone,
                                job_id character varying(255) COLLATE pg_catalog."default",
    file_name character varying(500) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    file_size bigint,
    file_hash character varying(255) COLLATE pg_catalog."default",
    aws_id character varying(255) COLLATE pg_catalog."default",
    environment character varying(255) COLLATE pg_catalog."default",
    organization character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT event_file_pkey PRIMARY KEY (id)
);

GRANT SELECT ON TABLE event.event_file TO ab2d_analyst;

GRANT ALL ON TABLE event.event_file TO postgres;
-- Index: event_file_job_id_idx

CREATE INDEX IF NOT EXISTS event_file_job_id_idx
    ON event.event_file USING btree
    (job_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_file_event_job_id
    ON event.event_file USING btree
    (job_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS event.event_job_status_change (
    id integer NOT NULL DEFAULT nextval('event_job_status_change_id_seq'::regclass),
    time_of_event timestamp with time zone,
                                job_id character varying(255) COLLATE pg_catalog."default",
    old_status character varying(255) COLLATE pg_catalog."default",
    new_status character varying(255) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    aws_id character varying(255) COLLATE pg_catalog."default",
    environment character varying(255) COLLATE pg_catalog."default",
    organization character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT event_job_status_change_pkey PRIMARY KEY (id)
);

GRANT SELECT ON TABLE event.event_job_status_change TO ab2d_analyst;

CREATE INDEX IF NOT EXISTS event_job_status_change_job_id_idx
    ON event.event_job_status_change USING btree
    (job_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS ix_job_status_job_id
    ON event.event_job_status_change USING btree
    (job_id COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;