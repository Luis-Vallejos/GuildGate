<%-- 
    Document   : login
    Created on : 25 jun. 2025, 12:24:29
    Author     : Juan - Luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>⚔Ingreso⚔</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styleLogin.css?v=5"/>
    </head>
    <body class="background-login">
        
        <!--Formulario Login-->
        <div id="login">
            <form id="loginForm" action="SvLogin" method="POST">
                <div id="imagenLogin">
                    <img class="chibi" id="imgChibi" src="/GremiosYRaids/imagenes/ChibiCharacters/Character (1).png" alt="Chibi Character"/>
                </div>
                <label for="username">Nombre de Usuario o Email</label>
                <input type="text" id="username" name="username" title="¡Ingresa tu nombre de usuario!" required/>
            
                <label for="contra">Contraseña</label>
                <input type="password" id="contra" name="contra" minlength="6" maxlength="12" pattern=".{6,12}" title="La contraseña debe tener entre 6 y 12 caracteres." required/>
            
                <button type="submit" id="btn-login">Ingresar</button>
            </form>
            <div id="links-section">
                <p><a href="registro.jsp">Registrarme</a>|<a href="#">¿Perdiste tu contraseña?</a></p>
                <a class="home" href="index.jsp">↞ Regresar al inicio</a>
            </div>
        </div>
        
<jsp:include page="mensajes.jsp"/>
        
        <script type="module" src="<%=request.getContextPath()%>/js/scriptLogin.js?v=6"></script>
        
    </body>
</html>
