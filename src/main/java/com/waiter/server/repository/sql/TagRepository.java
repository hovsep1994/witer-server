package com.waiter.server.repository.sql;

import com.waiter.server.commons.entities.Tag;
import com.waiter.server.repository.TagDAO;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class TagRepository extends BaseRepository implements TagDAO {

    public TagRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void batchInsert(final List<Tag> tags) {
        String sql = new StringBuilder()
                .append("INSERT IGNORE INTO tags ")
                .append("(name) VALUES (?)")
                .toString();
        jdbcTemplateObject.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Tag tag = tags.get(i);
                ps.setString(1, tag.getName());
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
    }

    @Override
    public List<Integer> insertAndGetIds(List<Tag> tags) {
        batchInsert(tags);
        List<Integer> ids = findTagIds(Tag.toStrings(tags));
        return ids;
    }

    @Override
    public List<Integer> findTagIds(List<String> tags) {
        String sql = new StringBuilder()
                .append("SELECT id FROM tags ")
                .append("WHERE name IN (:tags)")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(TAGS, tags);
        return jdbcTemplateObject.queryForList(sql, params, Integer.class);
    }

}
