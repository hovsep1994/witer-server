package com.waiter.server.servlets;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import com.waiter.server.commons.EntityType;
import com.waiter.server.commons.TranslationType;
import com.waiter.server.commons.entities.*;
import com.waiter.server.repository.ProductDAO;
import com.waiter.server.repository.sql.ProductRepository;
import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.PhotoSaver;
import com.waiter.server.utils.paramparser.IParamParser;
import com.waiter.server.utils.paramparser.ParserFactory;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.waiter.server.commons.ErrorCodes.WRONG_REQUEST;
import static com.waiter.server.utils.FieldValidator.validRequiredFields;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Created by Admin on 10/28/2015.
 */
public class AddProductServlet extends BaseServlet {

    private static final Logger LOG = Logger.getLogger(AddProductServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        ProductDAO productJDBTemplate = (ProductRepository) context.getBean("productJDBTemplate");
        IResponseWriter<Product> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new ParserFactory().newParser(req);

        //todo add authentication
        try {
            String name = paramParser.get("name");
            String language = paramParser.get("language");
            String type = paramParser.getString("type", "main");

            String description = paramParser.get("description");
            List<String> tags = paramParser.getList("tags");
            double price = paramParser.getDouble("price");
            int group_id = paramParser.getInt("group_id");
            String image_path = "";

            if (paramParser.isFileExists()) {
                PhotoSaver photoSaver = new PhotoSaver(paramParser.getFile(), name);
                image_path = photoSaver.savePhoto();
            }

            if (!validRequiredFields(name))
                throw new APIException(SC_BAD_REQUEST,
                        new APIError(WRONG_REQUEST, "Wrong request parameters. "));

            Product product = new Product()
                    .setTags(Tag.parseTags(tags))
                    .setDescription(description)
                    .setPrice(price)
                    .setImage(image_path)
                    .setGroup(new Group().setId(group_id))
                    .addName(new Name().setName(name)
                            .setTranslationType(TranslationType.valueOf(type.toUpperCase()))
                            .setEntityType(EntityType.PRODUCT).setLanguage(new Language(language)));

            LOG.info(product.toString());

            productJDBTemplate.create(product);
            writer.writeResponse(product);
        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

}
