<%-- 
    Document   : navegador
    Created on : 26 jun. 2025, 21:26:33
    Author     : Juan - Luis
--%>
<jsp:useBean id="usuarioBean" class="com.guildgate.web.Bean.UsuarioBean" scope="session"/>
<!-- Navegador, Logo, Sección Cuenta -->
<header id="navegador">
    <div id="logo">
        <a href="index.jsp" id="link-logo">
            <img src="<%=(usuarioBean.getImagenGremio() != null && !usuarioBean.getImagenGremio().isEmpty()) ? usuarioBean.getImagenGremio() : "/GremiosYRaids/imagenes/Gremio/GuildPics/DefaultGuildPic.png" %>" alt="logoGremio" loading="lazy">
            <h1 id="nomGremio"><%=(usuarioBean.getGremioActual() != null && !usuarioBean.getGremioActual().isEmpty()) ? usuarioBean.getGremioActual() : "Sin Gremio" %></h1>
        </a>
    </div>
    <nav>
        <div id="nav-container">
            <ul id="nav-links">
                <li><a href="index.jsp" class="link-item" data-usuario="<%=usuarioBean.getUsuarioActual() %>">Inicio</a></li>
                <li class="nav-dropdown">
                    <a href="#" class="link-item" data-usuario="<%=usuarioBean.getUsuarioActual()%>">Gremios</a>
                    <div class="dropdown-content">
<% if (usuarioBean.getUsuarioActual() != null) { %>
<% if(usuarioBean.getGremioActual().equals("Sin Gremio")) { %>
                        <a href="gremio.jsp#container-buscarGremio" class="dropdown-item">Buscar un Gremio</a>
                        <a href="crear-gremio.jsp?user=<%=usuarioBean.getUsuarioActual()%>" class="dropdown-item">Crear un Gremio</a>
<% } else { %>
                        <a href="migremio.jsp" class="dropdown-item">Mi Gremio</a>
<% } %>
<% } %>
                    </div>
                </li>
                <li class="nav-dropdown">
                    <a href="#" class="link-item" data-usuario="<%=usuarioBean.getUsuarioActual()%>">Raids</a>
                    <div class="dropdown-content">
<% if (usuarioBean.getUsuarioActual() != null) { %>
<% if (usuarioBean.getGremioActual().equals("Sin Gremio")){ %>
                        <a href="index.jsp" class="dropdown-item">¡Necesitas crear/unirte a un <strong>GREMIO</strong> para acceder a esta sección!</a>
<% }else if(usuarioBean.isPermisoCrearRaids() == true){ %>
                        <a href="raids.jsp" class="dropdown-item">Raids del Gremio</a>
                        <a href="crear-raid.jsp" class="dropdown-item">Crear una Raid</a>
<% } else { %>
                        <a href="raids.jsp" class="dropdown-item">Raids del Gremio</a>
<% } %>
<% } %>
                    </div>
                </li>
                <li><a href="index.jsp#Info" class="link-item" data-usuario="<%=usuarioBean.getUsuarioActual() %>">Información</a></li>
            </ul>
        </div>
    </nav>
    <div id="nav-buttons">
<% if(usuarioBean.getUsuarioActual() == null){ %>
        <div id="nav-log-reg">
            <form action="SvLogin" id="login-form" method="GET">
                <button type="submit" class="btnLR" name="login">Iniciar Sesión</button>
            </form>
            <form action="SvRegister" id="register-form" method="GET">
                <button type="submit" class="btnLR" name="register">Registrarme</button>
            </form>
        </div>
<% } else { %>
        <div id="user-dropdown">
            <button id="btn-account" aria-haspopup="true" aria-expanded="false">
                <div id="avatar">
                    <img id="imgUsuario" src="<%=(usuarioBean.getImagenUsuario() != null && !usuarioBean.getImagenUsuario().isEmpty()) ? usuarioBean.getImagenUsuario() : "/GremiosYRaids/imagenes/Usuario/UserPics/DefaultUserPic.png" %>" alt="pfpUser" loading="lazy">
                </div>
            </button>
            <span id="nomUsuario"><%=(usuarioBean.getUsuarioActual() != null || !usuarioBean.getUsuarioActual().isEmpty()) ? usuarioBean.getUsuarioActual() : "Usuario" %></span>
            <div id="user-dropdown-content">
                <a href="miperfil.jsp" class="dropdown-item">Mi Perfil</a>
                <form action="SvLogout" method="POST">
                    <button id="logout-button" class="dropdown-item">
                        Cerrar Sesión
                    </button>
                </form>
            </div>
        </div>
<% } %>
    </div>
</header> 

