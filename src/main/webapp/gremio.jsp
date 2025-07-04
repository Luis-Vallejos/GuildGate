<%-- 
    Document   : gremio
    Created on : 25 jun. 2025, 21:18:55
    Author     : Juan - Luis
--%>

<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="usuarioBean" class="com.guildgate.web.Bean.UsuarioBean" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>⚔Gremios⚔</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styleGremio.css?v=13"/>
    </head>
    <body>
        <jsp:include page="navegador.jsp"/>
           
        <!--Sección Buscar Gremio-->
        <div id="container-buscarGremio">
            <video id="backgroundVideo" loop>
                <source id="srcVid" src="/GremiosYRaids/imagenes/Gremio/VisualizarGremios/GT_Entrance.mp4" type="video/mp4"/>
            </video>
            <button type="button" id="btn-empezar">¡Empezar a buscar!</button>
            <div id="controles" style="display: none;">
                <span id="noVolume" style="display: none;">🔇</span>
                <span id="volumeUp" style="display: inline-block;">🔊</span>
                <span id="rP" style="display: none;">▶</span>
                <span id="pS" style="display: inline-block;">⏸</span>
            </div>
            <form class="contenedor-formulario" id="formBGremio" action="SvGremio" method="GET" style="display: none;">
              <div class="fila-selects">
                <div class="grupo-formulario">
                  <label for="region" class="etiqueta-formulario">Región</label>
                  <select id="region" name="region" class="select-formulario">
                    <option value="">Seleccione una región</option>
                    <option value="1">Norte América</option>
                    <option value="2">Europa</option>
                    <option value="3">LatinoAmérica</option>
                    <option value="4">Oceania</option>
                    <option value="5">Asia</option>
                  </select>
                </div>
                <div class="grupo-formulario">
                  <label for="mundo" class="etiqueta-formulario">Mundo</label>
                  <select id="mundo" name="mundo" class="select-formulario">
                    <option value="">Seleccione un mundo</option>
                    <option value="1">Mundo 1</option>
                    <option value="2">Mundo 2</option>
                    <option value="3">Mundo 3</option>
                  </select>
                </div>
              </div>
              <button id="btn-buscar">Buscar</button>
            </form>
        </div>

        <!-- Tabla Gremios -->
        <div id="container-tablaGremios" style="display: none;">
            <table id="tablaGremios">
                <tbody>
                    
                </tbody>
            </table>
        </div>

        <!-- Overlay y tarjeta de gremio -->
        <div id="overlay" class="hidden">
            <div class="tarjeta">
                <div class="imagen-relativa">
                    <img id="imagenGremio" src="" alt="Imagen del gremio" class="imagen-gremio">
                    <div id="etiquetaGremio" class="etiqueta-gremio"></div>
                </div>
                <div class="contenido-tarjeta">
                    <h3 id="nombreGremio"></h3>
                    <p id="descripcionGremio"></p>
                    <div class="footer-tarjeta">
                        <div class="info-miembros">
                            <img src="/GremiosYRaids/imagenes/Usuario/UserPics/DefaultUserPic.png" alt="Icono usuarios" class="icono-usuarios">
                            <span id="miembrosGremio"></span>
                        </div>
                        <form id="formUnirse" action="SvDatosGremio" method="POST">
                            <input type="hidden" id="gremioId" name="gremioId" value=""/>
                            <button type="submit" id="botonUnirse" class="boton-unirse"></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
<jsp:include page="mensajes.jsp"/>
    
<jsp:include page="footer.jsp"/>
        
        <script type="module" src="<%=request.getContextPath()%>/js/scriptGremio.js?v=7"></script>    
    </body>
</html>