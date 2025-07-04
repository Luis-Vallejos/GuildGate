<%-- 
    Document   : migremio
    Created on : 25 jun. 2025, 18:11:22
    Author     : Juan - Luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="usuarioBean" class="com.guildgate.web.Bean.UsuarioBean" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>⚔Mi Gremio⚔</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styleMiGremio.css"/>
    </head>
    <body style="background-image: url('<%=usuarioBean.getImagenFondoGremio()%>')">
<jsp:include page="navegador.jsp"/>
        
        <!-- Sección Mi Gremio -->
    <div id="seccion-gremio">
        <div id="encabezado-gremio">
            <div id="gremio-imagen-contenedor">
                <img src="<%=usuarioBean.getImagenGremio()%>" alt="Imagen del Gremio"
                    id="imagen-gremio">
                <div id="cambiar-imagen">
                    <button id="boton-cambiar-imagen">
                        <svg id="icono-camara" xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                            viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                            stroke-linecap="round" stroke-linejoin="round">
                            <path
                                d="M14.5 4h-5L7 7H4a2 2 0 0 0-2 2v9a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2h-3l-2.5-3z" />
                            <circle cx="12" cy="13" r="3" />
                        </svg>
                    </button>
                </div>
            </div>
            <div id="info-gremio">
                <h1 id="nombre-gremio"><%=usuarioBean.getGremioActual()%></h1>
                <p id="descripcion-gremio"><%=usuarioBean.getDescripcionGremio()%></p>
            </div>
            <div id="gremio-fondo-contenedor">
                <div id="cambiar-fondo">
                    <button type="button" id="boton-cambiar-fondo" title="Cambiar Fondo">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="50" height="50"
                            id="icono-cambiar-fondo">
                            <circle cx="12" cy="12" r="11" fill="black" />
                            <path
                                d="M12 4.5l1.43 3.76 4.07.3-2.88 2.85 1.31 4.03L12 13.11l-3.44 2.33 1.31-4.04L7 8.55l4.08-.3L12 4.5z"
                                fill="white" />
                        </svg>
                    </button>
                </div>
            </div>
        </div>

        <div id="seccion-roles">
            <h2 id="titulo-roles">Tus Roles</h2>

            <div id="contenedor-roles">
                <!-- Usar como INNER HTML
                <div id="rol" class="tarjeta-rol">
                    <h3 id="nombre-rol">DUEÑO</h3>
                    <p id="descripcion-rol">Dueño del gremio, tiene todos los permisos de realizar cualquier cambio en
                        el gremio, ademas de, asignar roles a otros.</p>
                </div>
                <div id="rol" class="tarjeta-rol">
                    <h3 id="nombre-rol">Diseñador</h3>
                    <p id="descripcion-rol">Persona que diseña imagenes, logos, fondos y etc, para el gremio.</p>
                </div>
                -->
            </div>
        </div>

        <div id="contenedor-titulos-actividades">
            <div id="participaciones-gremio">
                <h2 id="titulo-parti" class="titulo-actividades">Participantes del Gremio</h2>
            </div>
            <div id="roles-gremio">
                <h2 id="titulo-rol" class="titulo-actividades">Roles del Gremio</h2>
            </div>
            <div id="raids-gremio">
                <h2 id="titulo-raids" class="titulo-actividades">Raids del Gremio</h2>
            </div>
        </div>

        <div id="contenedor-actividades">
            <div id="participantes">
                <!-- Usar como Inner HTML
                <ul class="contenedor-lista-participantes">
                    <li class="gremio-participantes">
                        <img class="foto-participante"
                            src="/SeccionMiGremio/imagenes/Usuario/MiPerfil/palworld-pal-world.gif" alt="Foto-Miembro">
                        <span class="nombre-participante">Lavendernara</span>
                        <span class="rol-participante">DUEÑO</span>
                    </li>
                    <li class="gremio-participantes">
                        <img class="foto-participante"
                            src="/SeccionMiGremio/imagenes/Usuario/UserPics/DefaultUserPic.png" alt="Foto-Miembro">
                        <span class="nombre-participante">Joan</span>
                        <span class="rol-participante">MIEMBRO</span>
                    </li>
                    
                    <li class="botones-accion">
                        <button type="button" class="boton-accion" title="¿Salirse del Gremio?">
                            <svg id="icono-salida-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M10 6L8.59 7.41 13.17 12H3v2h10.17l-4.58 4.59L10 18l6-6-6-6zM19 2h-6v2h6v14h-6v2h6c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2z"
                                    fill="currentColor" />
                            </svg>
                        </button>
                        <button type="button" class="boton-accion" title="Editar Miembro">
                            <svg id="icono-editar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0L15.47 5.19l3.75 3.75 1.49-1.49z"
                                    fill="currentColor" />
                            </svg>
                        </button>
                        <button type="button" class="boton-accion" title="Eliminar Miembro">
                            <svg id="icono-borrar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M3 6h18v2H3V6zm2 3h14v13a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9zm3 2v10h2V11H8zm4 0v10h2V11h-2zm4 0v10h2V11h-2zm2-7V4a2 2 0 0 0-2-2h-6a2 2 0 0 0-2 2v1H5v2h14V4h-2zM9 4V2h6v2H9z"
                                    fill="currentColor" />
                            </svg>
                        </button>
                    </li>   
                </ul>
                    -->
                <form action="SvMiGremio" method="GET"  id="formBtnObtenerParticipantes">
                    <button type="button" class="icono-cargar" id="icono-participantes" title="Cargar Participantes">
                        <img src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Logo" id="img-participantes" class="img-carga" loading="lazy">
                    </button>
                    <span id="cargaParticipantes" class="nomCarga">CARGAR PARTICIPANTES</span>
                </form>
            </div>

            <div id="roles">
                <!-- Usar como inner HTML
                <ul class="contenedor-lista-roles">
                    <li class="gremio-roles">
                        <span class="nombre-rol-gremio">DUEÑO</span>
                        <p class="rol-descripcion">Dueño del gremio, tiene todos los permisos de realizar cualquier
                            cambio en el gremio, ademas de, asignar roles a otros.</p>
                    </li>
                    <li class="gremio-roles">
                        <span class="nombre-rol-gremio">MIEMBRO</span>
                        <p class="rol-descripcion">Miembro del gremio, solo puede configurar su usuario en el gremio,
                            puede salir o entrar.</p>
                    </li>
                    
                    <li class="botones-accion">
                        <button class="boton-accion" type="button" title="Añadir Rol">
                            <svg id="icono-anadir-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6v-2z" fill="currentColor" />
                            </svg>
                        </button>
                        <button class="boton-accion" type="button" title="Editar Rol">
                            <svg id="icono-editar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0L15.47 5.19l3.75 3.75 1.49-1.49z"
                                    fill="currentColor" />
                            </svg>
                        </button>
                        <button class="boton-accion" type="button" title="Eliminar Rol">
                            <svg id="icono-borrar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M3 6h18v2H3V6zm2 3h14v13a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9zm3 2v10h2V11H8zm4 0v10h2V11h-2zm4 0v10h2V11h-2zm2-7V4a2 2 0 0 0-2-2h-6a2 2 0 0 0-2 2v1H5v2h14V4h-2zM9 4V2h6v2H9z"
                                    fill="currentColor" />
                            </svg>
                        </button>
                    </li>
                </ul>
                -->
                <form action="SvRoles" method="GET" id="formBtnObtenerRoles">
                    <button type="button" class="icono-cargar" id="icono-roles" title="Cargar Roles">
                        <img src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Logo" id="img-roles" class="img-carga" loading="lazy">
                    </button>
                    <span id="cargaRoles" class="nomCarga">CARGAR ROLES</span>
                </form>
            </div>

            <div id="raids">
                <!-- Aparece como Inner HTML
                <ul class="contenedor-lista-raids">
                    <li class="botones-accion">
                        <button class="boton-accion" type="button" title="Añadir Raid">
                            <svg id="icono-anadir-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6v-2z" fill="currentColor" />
                            </svg>
                        </button>
                        <button class="boton-accion" type="button" title="Editar Raid">
                            <svg id="icono-editar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0L15.47 5.19l3.75 3.75 1.49-1.49z"
                                    fill="currentColor" />
                            </svg>
                        </button>
                        <button class="boton-accion" type="button" title="Eliminar Raid">
                            <svg id="icono-borrar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M3 6h18v2H3V6zm2 3h14v13a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9zm3 2v10h2V11H8zm4 0v10h2V11h-2zm4 0v10h2V11h-2zm2-7V4a2 2 0 0 0-2-2h-6a2 2 0 0 0-2 2v1H5v2h14V4h-2zM9 4V2h6v2H9z"
                                    fill="currentColor" />
                            </svg>
                        </button>
                    </li>
                </ul>
                -->
                <form action="SvRaids" method="GET" id="formBtnObtenerRaids">
                    <button type="button" class="icono-cargar" id="icono-raids" title="Cargar Raids">
                        <img src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Logo" id="img-raids" class="img-carga" loading="lazy">
                    </button>
                    <span id="cargaRaids" class="nomCarga">CARGAR RAIDS</span>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal de Cambiar Fondo / Editar Información del Gremio -->
    <!-- Overlay -->
    <div id="overlay" class="escondido"></div>

    <!-- Modal Avatar -->
    <div id="modal-pfp" class="escondido">
        <div id="encabezado">
            <h2>Cambiar foto de Perfil</h2>
            <p>¡Selecciona un avatar predefinido o sube tu propia imagen!</p>
            <button id="btnSalir-pfp">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M18 6L6 18"></path>
                    <path d="M6 6l12 12"></path>
                </svg>
            </button>
        </div>
        <div id="seleccion">
            <form id="formPfps" action="SvPerfil" method="GET">
                <button type="button" id="btnAvatarPredeterminado">
                    <div id="imgAvatar">
                        <img src="" alt="Foto de Perfil" id="pfp">
                    </div>
                    <span>Seleccionar Avatar</span>
                </button>
            </form>
            <button type="button" id="btnSubirAvatar">
                <div id="iconSubir">
                    <svg id="iconoSubir" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                        fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                        stroke-linejoin="round">
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
                <img id="imgPreviewPred" src=""
                    alt="Foto de Perfil" />
            </div>

            <div id="galeria-fotos"></div>
        </div>

        <div id="pie-tarjeta">
            <div>
                <button type="button" id="boton-cancelar">Cancelar</button>
            </div>

            <form id="formGuardarAvatarPre" action="SvAvatarGremio" method="POST">
                <input type="hidden" id="modalAvatarTipoPre" name="modalAvatarPre" value="" />
                <!--AvatarPredeterminado-->
                <input type="hidden" id="avatarFilename" name="nomAvatarArchivo" value="" />
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
                <img id="personalizableImg" src=""
                    alt="Foto de Perfil" />
                <div id="superposicion-personalizado">
                    <button id="boton-subir-personalizado" aria-label="Subir Foto">
                        <svg id="icono-subir-personalizado" xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                            viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                            stroke-linecap="round" stroke-linejoin="round">
                            <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
                            <polyline points="17 8 12 3 7 8" />
                            <line x1="12" x2="12" y1="3" y2="15" />
                        </svg>
                        <span id="subir-texto-personalizado">Subir Foto</span>
                    </button>
                </div>
            </div>

            <div id="galeria-fotos-personalizado"></div>
        </div>

        <div id="pie-tarjeta-personalizado">
            <div>
                <button type="button" id="boton-cancelar-personalizado">Cancelar</button>
            </div>

            <form id="formGuardarAvatarPer" action="SvAvatarGremio" method="POST">
                <input type="hidden" id="modalAvatarTipoPer" name="modalAvatarPer" value="" />
                <!--AvatarPersonalizado-->
                <input type="file" accept="image/*" name="fotoPerfilPersonalizada" id="input-archivo" />
                <button type="submit" id="boton-guardar-personalizado">Guardar</button>
            </form>
        </div>
    </div>

    <!-- Modal Editar Info/Editar Fondo -->
    <div id="modal-editar-fondo" class="escondido">
        <div id="encabezado-editar-fondo">
            <h2>Edita | Borra | Cambia</h2>
            <p>¡Selecciona un fondo o actualiza la información de tu gremio!</p>
            <button id="boton-salir-fondo">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M18 6L6 18"></path>
                    <path d="M6 6l12 12"></path>
                </svg>
            </button>
        </div>
        <div id="seleccion-fondo">
            <form id="form-fondo" action="SvAccionGremio" method="GET">
                <button type="button" id="boton-editar-gremio">
                    <svg id="icono-editar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                        xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0L15.47 5.19l3.75 3.75 1.49-1.49z"
                            fill="currentColor" />
                    </svg>
                    <span>Editar Gremio</span>
                </button>
            </form>
            <form action="SvAccionGremio" id="form-borrar" method="POST">
                <button type="button" id="boton-borrar-gremio">
                    <svg id="icono-borrar-gremio" width="24" height="24" viewBox="0 0 24 24" fill="none"
                        xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M3 6h18v2H3V6zm2 3h14v13a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V9zm3 2v10h2V11H8zm4 0v10h2V11h-2zm4 0v10h2V11h-2zm2-7V4a2 2 0 0 0-2-2h-6a2 2 0 0 0-2 2v1H5v2h14V4h-2zM9 4V2h6v2H9z"
                            fill="currentColor" />
                    </svg>
                    <span>Eliminar Gremio</span>
                </button>
            </form>
            <form action="SvFondoGremio" id="form-fondo" method="GET">
                <button type="button" id="boton-fondo">
                    <div id="fondo-gremio">
                        <img src="/SeccionMiGremio/imagenes/Gremio/MiGremio/DefaultGuildBackground.jpg"
                            alt="Fondo Gremio" id="img-fondo">
                    </div>
                    <span>Cambiar Fondo del Gremio</span>
                </button>
            </form>
        </div>
    </div>

    <!-- Tarjeta Edición del Gremio -->
    <div id="modal-editar" class="escondido">
        <div id="seccion-cabecera">
            <h2>¡Edita tu gremio!</h2>
            <p>¡Realiza cambios al gremio desde aquí!</p>
            <button id="btnCerrar">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M18 6L6 18"></path>
                    <path d="M6 6l12 12"></path>
                </svg>
            </button>
        </div>
        <form action="SvAccionGremio" method="POST" id="formPerfil">
            <div id="campoNombre" class="campoFormulario">
                <label for="nomuser" class="etiqueta">Nombre del Gremio</label>
                <input type="text" id="nomuser" class="entrada" name="nomusuario">
            </div>

            <div id="campoBiografia" class="campoFormulario">
                <label for="bio" class="etiqueta">Descripción</label>
                <textarea id="bio" class="entrada" rows="5" name="biousuario"></textarea>
            </div>
            <button type="submit" id="btnGuardar">Guardar Cambios</button>
        </form>
    </div>

    <!-- Tarjeta Fondo Predeterminado -->
    <div id="tarjeta-fondo-gremio" class="escondido">
        <div id="encabezado-tarjeta-fondo-gremio">
            <h2 id="titulo-tarjeta-fondo-gremio">Cambiar Foto de Perfil</h2>
        </div>

        <div id="contenido-tarjeta-fondo-gremio">
            <div id="foto-perfil-fondo-gremio">
                <img id="imgPreviewBackground"
                    src="" alt="Fondo del Gremio" />
            </div>

            <div id="galeria-fotos-fondo-gremio"></div>
        </div>

        <div id="pie-tarjeta-fondo-gremio">
            <div>
                <button type="button" id="boton-cancelar-fondo-gremio">Cancelar</button>
            </div>

            <form id="form-guardar-fondo-gremio" action="SvFondoGremio" method="POST">
                <input type="hidden" id="fondoFilename" name="nomFondoArchivo" value="" />
                <button type="submit" id="boton-guardar-fondo-gremio">Guardar</button>
            </form>
        </div>
    </div>
    
    <script type="module" src="<%=request.getContextPath()%>/js/scriptMiGremio.js"></script>
<jsp:include page="mensajes.jsp"/>
<jsp:include page="footer.jsp"/>
    </body>
</html>
