<%-- 
    Document   : gremio
    Created on : 25 jun. 2025, 21:18:55
    Author     : Juan - Luis
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="sub" class="com.guildgate.web.Bean.SessionUserBean" scope="session"/>

<jsp:include page="base.jsp">
    <jsp:param name="title" value="⚔ GuildGate ⚔"/>
    <jsp:param name="css" value="css/pages/gremio.css?v=4"/>
    <jsp:param name="contenido" value="includes/content/contentGremio.jspf"/>
    <jsp:param name="js" value="scriptGremio.js?v=4"/>
</jsp:include>
