package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Name;
import com.waiter.server.db.NameDAO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;

/**
 * @author shahenpoghosyan
 */
public class NameJDBCTemplate extends BaseJDBCTemplate implements NameDAO {

    public NameJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Name name) {
        String sql = new StringBuilder()
                .append(" INSERT INTO names (name, language, entity_id, entity_type)")
                .append(" VALUES (:name, :language, :entity_id, :entity_type)")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, name.getName());
        params.addValue("language", name.getLanguage().getCode());
        params.addValue("entity_id", name.getEntityId());
        params.addValue("entity_type", name.getEntityType().toString());
        params.addValue("translation_type", name.getTranslationType());

        return insertAndGetId(sql, params);
    }
}
