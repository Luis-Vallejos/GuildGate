<%-- 
    Document   : miperfil
    Created on : 16 jun. 2025, 16:37:26
    Author     : Juan - Luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="usuarioBean" class="com.guildgate.web.Bean.UsuarioBean" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>⚔Mi Perfil⚔</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styleMiPerfil.css?v=17"/>
    </head>
    <body>
        <jsp:include page="navegador.jsp"/>
        
        <!-- Sección Mi Perfil -->
        <div id="contenedor">
            <div id="banner-contenedor">
                <img src="<%=usuarioBean.getBanner() %>" alt="Banner" id="banner-imagen">
                <div id="banner-gradiente"></div>
                <div id="perfil-contenedor">
                    <div id="perfil">
                        <div id="perfil-imagen-contenedor">
                            <img src="<%=usuarioBean.getImagenUsuario() %>" alt="Perfil" id="perfil-imagen">
                            <div id="cambiar-imagen">
                                <button id="boton-cambiar-imagen">
                                    <svg id="icono-camara" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M14.5 4h-5L7 7H4a2 2 0 0 0-2 2v9a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2h-3l-2.5-3z" />
                                        <circle cx="12" cy="13" r="3" />
                                    </svg>
                                </button>
                            </div>
                        </div>
                        <div id="informacion-perfil">
                            <h1 id="nombre"><%=usuarioBean.getUsuarioActual() %></h1>
                            <div id="detalles-perfil">
                                <div id="nivel">Nivel <%=usuarioBean.getNivelUsuario() %></div>
                                <div id="clase"><%=(usuarioBean.getRolUsuario() != null) ? usuarioBean.getRolUsuario() : "Sin Rol"%></div>
                                <div id="gremio"><%=(usuarioBean.getGremioActual() != null) ? usuarioBean.getGremioActual() : "Sin Gremio"%></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="contenido">
                <div id="seccion-sobre">
                    <div id="titulo-sobre">
                        <h2>Bio</h2>
                        <form id="formDatosUsuario" action="SvUsuario" method="GET">
                            <button type="button" id="boton-editar-perfil">
                                <svg id="icono-editar" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M12 22h6a2 2 0 0 0 2-2V7l-5-5H6a2 2 0 0 0-2 2v10" />
                                    <path d="M14 2v4a2 2 0 0 0 2 2h4" />
                                    <path d="M10.4 12.6a2 2 0 1 1 3 3L8 21l-4 1 1-4Z" />
                                </svg>
                                Editar Perfil
                            </button>
                        </form>
                    </div>
                    <div id="descripcion-perfil">
                        <p>
<%=(usuarioBean.getBioUsuario() != null) ? usuarioBean.getBioUsuario() : "¡Esta es una bio salvaje!"%>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Overlay -->
        <div id="overlay" class="escondido"></div>
        
        <!-- Modal Avatar -->
        <div id="modal-pfp" class="escondido">
            <div id="encabezado">
                <h2>Cambiar foto de Perfil</h2>
                <p>¡Selecciona un avatar predefinido o sube tu propia imagen!</p>
                <button id="btnSalir-pfp">
                    <svg 
                    xmlns="http://www.w3.org/2000/svg" 
                    width="24" 
                    height="24" 
                    viewBox="0 0 24 24" 
                    fill="none" 
                    stroke="currentColor" 
                    stroke-width="2" 
                    stroke-linecap="round" 
                    stroke-linejoin="round"
                    >
                        <path d="M18 6L6 18"></path>
                        <path d="M6 6l12 12"></path>
                    </svg>
                </button>
            </div>
            <div id="seleccion">
                <form id="formPfps" action="SvPerfil" method="GET">
                    <button type="button" id="btnAvatarPredeterminado">
                        <div id="imgAvatar">
                            <img src="<%=usuarioBean.getImagenUsuario() %>" alt="Foto de Perfil" id="pfp">
                        </div>
                        <span>Seleccionar Avatar</span>
                    </button>
                </form>
                <button type="button" id="btnSubirAvatar">
                    <div id="iconSubir">
                        <svg 
                        id="iconoSubir" 
                        xmlns="http://www.w3.org/2000/svg" 
                        width="24" 
                        height="24" 
                        viewBox="0 0 24 24" 
                        fill="none" 
                        stroke="currentColor" 
                        stroke-width="2" 
                        stroke-linecap="round" 
                        stroke-linejoin="round"
                        >
                            <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                            <polyline points="17 8 12 3 7 8"></polyline>
                            <line x1="12" y1="3" x2="12" y2="15"></line>
                        </svg>
                    </div>
                    <span>Subir Foto</span>
                </button>
            </div>
        </div>

        <!-- Tarjeta Avatar Predeterminado -->
        <div id="tarjeta-avatar-predeterminado" class="escondido">
            <div id="encabezado-tarjeta">
                <h2 id="titulo-tarjeta">Cambiar Foto de Perfil</h2>
            </div>

            <div id="contenido-tarjeta">
                <div id="foto-perfil">
                    <img id="imgPreviewPred" src="<%=usuarioBean.getImagenUsuario() %>" alt="Foto de Perfil" />
                </div>

                <div id="galeria-fotos"></div>
            </div>

            <div id="pie-tarjeta">
                <div>
                    <button type="button" id="boton-cancelar">Cancelar</button>
                </div>
                
                <form id="formGuardarAvatarPre" action="SvPerfil" method="POST">
                    <input type="hidden" id="modalAvatarTipoPre" name="modalAvatarPre" value=""/> <!--AvatarPredeterminado-->
                    <input type="hidden" id="avatarFilename" name="nomAvatarArchivo" value=""/>
                    <button type="submit" id="boton-guardar">Guardar</button>
                </form>
            </div>
        </div>

        <!--Tarjeta Subir Avatar -->
        <div id="tarjeta-avatar-personalizado" class="escondido">
            <div id="encabezado-tarjeta-personalizado">
                <h2 id="titulo-tarjeta-personalizado">Cambiar Foto de Perfil</h2>
            </div>

            <div id="contenido-tarjeta-personalizado">
                <div id="foto-perfil-personalizado">
                    <img id="personalizableImg" src="<%=usuarioBean.getImagenUsuario() %>" alt="Foto de Perfil" />
                    <div id="superposicion-personalizado">
                        <button id="boton-subir-personalizado" aria-label="Subir Foto">
                            <svg 
                            id="icono-subir-personalizado" 
                            xmlns="http://www.w3.org/2000/svg"
                            width="24" 
                            height="24" 
                            viewBox="0 0 24 24" 
                            fill="none" 
                            stroke="currentColor" 
                            stroke-width="2" 
                            stroke-linecap="round" 
                            stroke-linejoin="round"
                            >
                                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
                                <polyline points="17 8 12 3 7 8" />
                                <line x1="12" x2="12" y1="3" y2="15" />
                            </svg>
                            <span id="subir-texto-personalizado">Subir Foto</span>
                        </button>
                    </div>
                </div>

                <div id="galeria-fotos-personalizado">
                    <button type="button">
                        <img id="foto-pequena" src="<%=usuarioBean.getImagenUsuario() %>" alt="Foto de Perfil" width="100" height="100" />
                    </button>
                </div>
            </div>

            <div id="pie-tarjeta-personalizado">
                <div>
                    <button type="button" id="boton-cancelar-personalizado">Cancelar</button>
                </div>
                
                <form id="formGuardarAvatarPer" action="SvPerfil" method="POST">
                    <input type="hidden" id="modalAvatarTipoPer" name="modalAvatarPer" value=""/> <!--AvatarPersonalizado-->
                    <input type="file" accept="image/*" name="fotoPerfilPersonalizada" id="input-archivo"/>
                    <button type="submit" id="boton-guardar-personalizado">Guardar</button>
                </form>
            </div>
        </div>

        <!-- Modal Banner -->
        <div id="modal-banner" class="escondido">
            <div id="banner-encabezado">
                <h2>Cambiar Banner</h2>
                <p>¡Selecciona entre un banner predeterminado o sube tu propia imagen!</p>
                <button id="btnSalir-banner">
                    <svg 
                    xmlns="http://www.w3.org/2000/svg" 
                    width="24" 
                    height="24" 
                    viewBox="0 0 24 24" 
                    fill="none" 
                    stroke="currentColor" 
                    stroke-width="2" 
                    stroke-linecap="round" 
                    stroke-linejoin="round"
                    >
                        <path d="M18 6L6 18"></path>
                        <path d="M6 6l12 12"></path>
                    </svg>
                </button>
            </div>
            <div id="seleccion-banner">
                <form id="formBanners" action="SvBanner" method="GET">
                    <button type="button" id="btnBannerPredeterminado">
                        <div id="imgBanner">
                            <img src="<%=usuarioBean.getBanner() %>" alt="" id="ban">
                        </div>
                        <span>Seleccionar Banner</span>
                    </button>
                </form>
                <button type="button" id="btnSubirBanner">
                    <div id="bannerIcon">
                        <svg 
                        id="iconoSubir" 
                        xmlns="http://www.w3.org/2000/svg" 
                        width="30" 
                        height="30" 
                        viewBox="0 0 30 30" 
                        fill="none" 
                        stroke="currentColor" 
                        stroke-width="2" 
                        stroke-linecap="round" 
                        stroke-linejoin="round"
                        >
                            <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                            <polyline points="17 8 12 3 7 8"></polyline>
                            <line x1="12" y1="3" x2="12" y2="15"></line>
                        </svg>
                    </div>
                    <span>Subir Foto</span>
                </button>
            </div>
        </div>

        <!-- Tarjeta Banner Predeterminado -->
        <div id="tarjeta-banner-predeterminado" class="escondido">
            <div id="encabezado-banner">
                <h2 id="titulo-banner">Cambiar Foto de tu Banner</h2>
            </div>

            <div id="contenido-banner">
                <div id="banner-perfil">
                    <img src="<%=usuarioBean.getBanner() %>" alt="Foto de Banner" id="bannerPreviewPred">
                </div>

                <div id="galeria-banners"></div>
            </div>

            <div id="pie-banner">
                <div>
                    <button type="button" id="btnCancelarBanner">Cancelar</button>
                </div>
                <form id="formGuardarBannerPre" action="SvBanner" method="POST">
                    <input type="hidden" id="modalBannerTipoPre" name="modalBannerPre" value=""/>
                    <input type="hidden" id="bannerFilename" name="nomBannerArchivo" value=""/>
                    <button type="submit" id="btnGuardarBanner">Guardar</button>
                </form>
            </div>
        </div>

        <!-- Tarjeta Banner Subir -->
        <div id="tarjeta-banner-personalizado" class="escondido">
            <div id="encabezado-banner">
                <h2 id="titleBanner">Cambiar Foto de tu Banner</h2>
            </div>

            <div id="contentBanner">
                <div id="bannerProfile">
                    <img src="<%=usuarioBean.getBanner() %>" alt="Foto de Banner" id="bannerPreviewPers">
                    <div id="super-banner">
                        <button id="btnUploadBanner" aria-label="Subir Banner">
                            <svg 
                            id="iconUploadBanner" 
                            xmlns="http://www.w3.org/2000/svg" 
                            width="24" 
                            height="24" 
                            viewBox="0 0 24 24" 
                            fill="none" 
                            stroke="currentColor" 
                            stroke-width="2" 
                            stroke-linecap="round" 
                            stroke-linejoin="round"
                            >
                                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
                                <polyline points="17 8 12 3 7 8" />
                                <line x1="12" x2="12" y1="3" y2="15" />
                            </svg>
                            <span id="uploadText">Subir Foto</span>
                        </button>
                    </div>
                </div>

                <div id="bannerUpload">
                    <button type="button">
                        <img id="banner-pequeno" src="<%=usuarioBean.getBanner() %>" alt="Banner">
                    </button>
                </div>
            </div>

            <div id="bannerFoot">
                <div>
                    <button type="button" id="btnCancelarBanner-Personalizado">Cancelar</button>
                </div>
                <form id="formGuardarBannerPer" action="SvBanner" method="POST" enctype="multipart/form-data">
                    <input type="hidden" id="modalBannerTipoPer" name="modalBannerPer" value=""/>
                    <input type="file" accept="image/*" name="fotoBanner" id="input-banner">
                    <button type="submit" id="btnGuardarBanner-Personalizado">Guardar</button>
                </form>
            </div>
        </div>
        
        <!-- Modal Editar Perfil -->
        <div id="modal-editar" class="escondido">
            <div id="seccion-cabecera">
                <h2>¡Edita tu perfil!</h2>
                <p>¡Realiza cambios a tu perfil aquí!</p>
                <button id="btnCerrar">
                    <svg 
                    xmlns="http://www.w3.org/2000/svg" 
                    width="24" 
                    height="24" 
                    viewBox="0 0 24 24" 
                    fill="none" 
                    stroke="currentColor" 
                    stroke-width="2" 
                    stroke-linecap="round" 
                    stroke-linejoin="round"
                    >
                        <path d="M18 6L6 18"></path>
                        <path d="M6 6l12 12"></path>
                    </svg>
                </button>
            </div>
            <form action="SvUsuario" method="POST" id="formPerfil">
                <div id="campoNombre" class="campoFormulario">
                    <label for="nomuser" class="etiqueta">Nombre de Usuario</label>
                    <input type="text" id="nomuser" class="entrada" name="nomusuario">
                </div>

                <div id="campoCorreo" class="campoFormulario">
                    <label for="correo" class="etiqueta">Correo Electrónico</label>
                    <input type="email" id="correo" class="entrada" name="correousuario">
                </div>

                <div id="campoContrasena" class="campoFormulario">
                    <label for="contra" class="etiqueta">Contraseña</label>
                    <div id="entradaContra">
                        <input type="password" id="contra" class="entrada" name="contrausuario">
                        <button type="button" id="btnMostrarContra">
                            <svg 
                                id="iconoOjoAbierto"
                                xmlns="http://www.w3.org/2000/svg" 
                                width="24" 
                                height="24" 
                                viewBox="0 0 24 24" 
                                fill="none" 
                                stroke="currentColor" 
                                stroke-width="2" 
                                stroke-linecap="round" 
                                stroke-linejoin="round"
                                style="display: block;"
                            >
                                <path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"></path>
                                <circle cx="12" cy="12" r="3"></circle>
                            </svg>
                            <svg 
                                id="iconoOjoCerrado"
                                xmlns="http://www.w3.org/2000/svg" 
                                width="24" 
                                height="24" 
                                viewBox="0 0 24 24" 
                                fill="none" 
                                stroke="currentColor" 
                                stroke-width="2" 
                                stroke-linecap="round" 
                                stroke-linejoin="round"
                                style="display: none;"
                            >
                                <path d="M17.94 17.94A10.5 10.5 0 0 1 12 19.5a10.5 10.5 0 0 1-7.93-3.64M3 3l18 18"></path>
                                <path d="M10.66 10.66A3 3 0 0 0 13.34 13.34"></path>
                                <path d="M15.66 8.34A10.5 10.5 0 0 0 12 4.5a10.5 10.5 0 0 0-7.93 3.64"></path>
                            </svg>
                        </button>
                    </div>
                </div>                

                <div id="campoBiografia" class="campoFormulario">
                    <label for="bio" class="etiqueta">Biografia</label>
                    <textarea id="bio" class="entrada" rows="5" name="biousuario"></textarea>
                </div>
                <button type="submit" id="btnGuardar">Guardar Cambios</button>
            </form>
        </div>
        
<jsp:include page="mensajes.jsp"/>
<jsp:include page="footer.jsp"/>
            
        <script type="module" src="<%=request.getContextPath()%>/js/scriptMiPerfil.js?v=15"></script>
    </body>
</html>
