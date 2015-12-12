package com.waiter.server.servlets;

import com.waiter.server.response.IResponseWriter;
import com.waiter.server.response.JsonResponseWriter;
import com.waiter.server.utils.paramparser.BaseParser;
import com.waiter.server.utils.paramparser.IParamParser;
import com.waiter.server.utils.requests.YandexTranslatorApiManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Created by Admin on 11/20/2015.
 */
public class TestYandexTranslateServlet extends BaseServlet {
    private static final Logger LOG = Logger.getLogger(GetMenuServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(CONTEXT);
        IResponseWriter<String> writer = new JsonResponseWriter<>(resp.getWriter());
        IParamParser paramParser = new BaseParser(req);

        try {
            String text = paramParser.get("text");
            String lanFrom = paramParser.get("langFrom");
            String lanTo = paramParser.get("langTo");

            YandexTranslatorApiManager translator = new YandexTranslatorApiManager();
            String res = translator.translate(text, lanFrom, lanTo).toString();

            LOG.info(res);
            writer.writeResponse(res);

        } catch (Exception e) {
            LOG.error("something went wrong when adding tag to user. ", e);
            resp.sendError(SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
