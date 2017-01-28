<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.waiter.server.repository.CompanyDAO" %>
<%@ page import="com.waiter.server.repository.sql.CompanyRepository" %>
<%@ page import="com.waiter.server.utils.paramparser.IParamParser" %>
<%@ page import="com.waiter.server.utils.paramparser.ParserFactory" %>
<%@ page import="com.waiter.server.services.common.exception.APIException" %>
<%@ page import="com.waiter.server.repository.UserDAO" %>
<%--
  Created by IntelliJ IDEA.
  User: shahenpoghosyan
  Date: 11/2/15
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Validate Email</title>
</head>
<body>

<%
  ApplicationContext context = (ApplicationContext) pageContext.getServletContext().getAttribute("springContext");
  UserDAO userDAO = (UserDAO) context.getBean("userJDBCTemplate");
  IParamParser paramParser = new ParserFactory().newParser(request);
  String hash = paramParser.getString("hash", "");
  boolean validated = userDAO.validateEmail(hash);
  if(validated) {
    out.print("validated");
  } else {
    out.print("not validated");
  }

%>

</body>
</html>
