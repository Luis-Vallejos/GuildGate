<%-- 
    Document   : miperfil
    Created on : 16 jun. 2025, 16:37:26
    Author     : Juan - Luis
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="sub" class="com.guildgate.web.Bean.SessionUserBean" scope="session"/>

<jsp:include page="base.jsp">
    <jsp:param name="title" value="⚔ GuildGate ⚔"/>
    <jsp:param name="css" value="css/pages/mi-perfil.css?v=4"/>
    <jsp:param name="contenido" value="includes/content/contentMiPerfil.jspf"/>
    <jsp:param name="js" value="scriptMiPerfil.js?v=4"/>
</jsp:include>
