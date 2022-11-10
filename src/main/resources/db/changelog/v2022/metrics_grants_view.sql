GRANT USAGE ON SCHEMA event TO ab2d_analyst;

GRANT SELECT ON event.event_metrics TO ab2d_analyst;

-- used by quicksight queries
CREATE SEQUENCE IF NOT EXISTS metrics_sequence START 1;

GRANT USAGE ON metrics_sequence TO ab2d_analyst;
