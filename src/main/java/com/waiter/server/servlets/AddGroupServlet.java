package com.waiter.server.servlets;

import com.waiter.server.commons.entities.Group;
import com.waiter.server.commons.entities.Menu;
import com.waiter.server.commons.entities.Tag;
import com.waiter.server.repository.sql.GroupRepository;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.PhotoSaver;
import com.waiter.server.utils.paramparser.IParamParser;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class AddGroupServlet extends BaseServlet {

    private static final Logger logger = Logger.getLogger(AddGroupServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        GroupRepository groupRepository = (GroupRepository) context.getBean("groupJDBCTemplate");
        IResponseWriter<Group> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = parserFactory.newParser(req);

        try {
            String name = paramParser.get("name");
            List<String> tags = paramParser.getList("tags");
            int menuId = paramParser.getInt("menu_id");
            String imagePath = "";

            if (paramParser.isFileExists()) {
                PhotoSaver photoSaver = new PhotoSaver(paramParser.getFile(), "group_" + name + "_" + menuId);
                imagePath = photoSaver.savePhoto();
            }

            Group group = new Group()
                    .setName(name)
                    .setImage(imagePath)
                    .setTags(Tag.parseTags(tags))
                    .setMenu(new Menu().setId(menuId));

            groupRepository.create(group);
            writer.writeResponse(group);
        } catch (Exception e) {
            logger.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

}
