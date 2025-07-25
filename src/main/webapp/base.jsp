<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="includes/meta.jspf"/>
        <title><c:out value="${param.title}" default="Gremios y Raids"/></title>
        <c:if test="${not empty param.css}">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/${param.css}"/>
        </c:if>
    </head>
    <body>
        <jsp:include page="includes/navbar.jspf"/>

        <jsp:include page="${param.contenido}"/>

        <jsp:include page="includes/footer.jspf"/>

        <c:if test="${not empty param.js}">
            <script type="module" src="${pageContext.request.contextPath}/js/${param.js}"></script>
        </c:if>
    </body>
</html>