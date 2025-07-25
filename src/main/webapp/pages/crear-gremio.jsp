<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="sub" class="com.guildgate.web.Bean.SessionUserBean" scope="session"/>

<jsp:include page="base.jsp">
    <jsp:param name="title" value="⚔ GuildGate ⚔"/>
    <jsp:param name="css" value="css/pages/crear-gremio.css?v=4"/>
    <jsp:param name="contenido" value="includes/content/contentCrearGremio.jspf"/>
    <jsp:param name="js" value="crear-gremio.css?v=4"/>
</jsp:include>
