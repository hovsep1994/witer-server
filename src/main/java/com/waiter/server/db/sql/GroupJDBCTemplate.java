package com.waiter.server.db.sql;

import com.waiter.server.commons.entities.Group;
import com.waiter.server.db.GroupDAO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
public class GroupJDBCTemplate extends BaseJDBCTemplate implements GroupDAO{

    public GroupJDBCTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Group group) {
        String sql = new StringBuilder("INSERT INTO groups (name, image, menu_id) )")
                .append("VALUES (:name, :image, :menu_id)")
                .toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, group.getName());
        params.addValue(IMAGE, group.getImage());
        params.addValue(MENU_ID, group.getMenu().getId());
        int groupId = insertAndGetId(sql, params);
        if(groupId != -1 && group.getTags() != null) {
            insertGroupTags(group);
        }
        return groupId;
    }

    private void insertGroupTags(Group group) {
        TagJDBCTemplate tagJDBCTemplate = new TagJDBCTemplate(dataSource);
        List<Integer> tagIds = tagJDBCTemplate.insertAndGetIds(group.getTags());
        insertMappings(MappingTable.GROUP_TAG_MAP, group.getId(), tagIds);
    }
}
