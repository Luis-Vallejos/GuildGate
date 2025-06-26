<%-- 
    Document   : crearg
    Created on : 25 jun. 2025, 12:11:00
    Author     : Juan - Luis
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="usuarioBean" class="com.guildgate.web.Bean.UsuarioBean" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>⚔Creando Mi Gremio⚔</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styleCrearGremio.css?v=4"/>
    </head>
    <body>
        <!-- Sección Crear Gremio -->
        <div id="contenedor">
            <div id="imagenGremio">
                <img class="chibi" id="imgChibi" src="" alt="Chibi Character"/>
            </div>
            <form id="formGremio" action="SvGremio" method="POST">
                
                <label for="nombreGremio">Nombre del Gremio</label>
                <input type="text" id="nombreGremio" name="nomGremio" required/>
                
                <label for="textDescrip">Descripcion del Gremio</label>
                <textarea id="textDescrip" name="descGremio" rows="5" cols="10" required></textarea>
                
                <label for="regionOption">Región</label>
                <select id="regionOption" name="regionGremio">
                    <option value="">--Seleccione su región</option>
                    <option value="1">Norte América</option>
                    <option value="2">Europa</option>
                    <option value="3">Latinoamérica</option>
                    <option value="4">Oceanía</option>
                    <option value="5">Asia</option>
                </select>
                
                <label for="mundoOption">Mundo</label>
                <select id="mundoOption" name="mundoGremio">
                    <option value="">--Seleccione un mundo</option>
                    <option value="1">Mundo 1</option>
                    <option value="2">Mundo 2</option>
                    <option value="3">Mundo 3</option>
                </select>
                
                <button type="submit" id="btn-gremio">Crear Gremio</button>
            </form>
            <div id="links-section">
                <a class="home" href="index.jsp">↞ Regresar al inicio</a>
            </div>
        </div>
        
<jsp:include page="mensajes.jsp"/>
        
        <script type="module" src="<%=request.getContextPath()%>/js/scriptCrearGremio.js?v=4"></script>
    </body>
</html>