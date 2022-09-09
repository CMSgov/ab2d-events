package gov.cms.ab2d.eventlogger.eventloggers.sql;

import gov.cms.ab2d.eventclient.events.LoggableEvent;
import gov.cms.ab2d.eventclient.events.MetricsEvent;
import gov.cms.ab2d.eventlogger.EventLoggingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.stream.Stream;

public class MetricsEventMapper extends SqlEventMapper {

    private final NamedParameterJdbcTemplate template;

    MetricsEventMapper(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    void log(LoggableEvent event) {
        if (event.getClass() != MetricsEvent.class) {
            throw new EventLoggingException("Used " + event.getClass()
                    .toString() + " instead of " + MetricsEvent.class);
        }
        MetricsEvent metricsEvent = (MetricsEvent) event;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "insert into event.event_metrics " +
                " (time_of_event, service) " +
                " values (:time, :service)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("time", metricsEvent.getTimeOfEvent())
                .addValue("service", metricsEvent.getService()
                        .toString());

        template.update(query, parameters, keyHolder);
        event.setId(Stream.of(keyHolder.getKeys())
                .filter(Objects::nonNull)
                .filter(f -> f.containsKey("id"))
                .map(f -> f.get("id"))
                .map(String::valueOf)
                .map(Long::valueOf)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Database did not generate key")));
    }

    @Override
    public Object mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
        return MetricsEvent.builder()
                .service(rs.getString("service"))
                .timeOfEvent(rs.getObject("time_of_event", OffsetDateTime.class))
                .build();
    }
}
