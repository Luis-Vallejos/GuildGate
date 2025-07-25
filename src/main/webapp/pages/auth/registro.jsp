<%-- 
    Document   : registro
    Created on : 25 jun. 2025, 12:24:38
    Author     : Juan - Luis
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>⚔Registro⚔</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styleRegistro.css?v=4"/>
    </head>
    <body class="background-register">
        <!--Formulario Registro-->
        <div id="registro">
            <div id="imagenRegistro">
                <img class="chibi" id="imgChibi" src="/GremiosYRaids/imagenes/ChibiCharacters/Character (1).png" alt="Chibi Character"/>
            </div>
            <form id="registroForm" action="SvRegister" method="POST">
                <label for="nombre">Nombre de Usuario</label>
                <input type="text" id="nombre"  name="nombre" autocomplete="given-name" required>
                
                <label for="correo">Email</label>
                <input type="email" id="correo" name="correo" title="¡Debes ingresar un correo valido!" required>
            
                <label for="contra">Contraseña</label>
                <input type="password" id="contra" name="contra" minlength="6" maxlength="12" pattern=".{6,12}" title="La contraseña debe tener entre 6 y 12 caracteres." required>
                
                <button type="submit" id="btn-registro">Registrarme</button>
            </form>
            <div id="links-section">
                <p><a href="login.jsp">Ingresar</a>|<a href="#">¿Perdiste tu contraseña?</a></p>
                <a class="home" href="index.jsp">↞ Regresar al inicio</a>
            </div>
        </div>
        
<jsp:include page="mensajes.jsp"/>
        
        <script type="module" src="<%=request.getContextPath()%>/js/scriptRegistro.js?v=6"></script>
    </body>
</html>
