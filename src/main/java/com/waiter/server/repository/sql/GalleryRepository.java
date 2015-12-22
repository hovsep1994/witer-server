package com.waiter.server.repository.sql;

import com.waiter.server.commons.entities.Gallery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * @author shahenpoghosyan
 */
@Repository
public class GalleryRepository extends BaseRepository {

    private static final Logger logger = Logger.getLogger(GalleryRepository.class);

    public Gallery save(Gallery gallery) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("path", gallery.getPath());
        params.addValue("type", gallery.getImageType().toString());

        String sql = "INSERT INTO gallery VALUES (path, image_type) (:path, :type)";
        gallery.setId(insertAndGetId(sql, params));
        return gallery;
    }
}
