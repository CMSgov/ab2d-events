CREATE TABLE IF NOT EXISTS event.event_metrics
(
    id            BIGSERIAL PRIMARY KEY,
    service       varchar(64) not null,
    state_type       varchar(64) not null, -- is the problem starting, continuing, or ending
    time_of_event timestamp   not null,
    awsId         varchar(255),
    environment   varchar(255),
    job_id        varchar(255)
)