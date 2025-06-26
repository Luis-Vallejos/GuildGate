<%-- 
    Document   : index
    Created on : 25 jun. 2025, 00:07:47
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
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styleIndex.css?v=4"/>
    </head>
    <body>
        <jsp:include page="navegador.jsp"/>
        
        <!--Carrusel de GIFs para la presentación-->
        <div id="PresentacionGT">
            <div class="carousel-container">
                <div class="carousel-images" id="carouselImages">
                    <div class="carousel-slide">
                        <img class="imagenGT" src="imagenes/Carrusel/EntranceGT.gif" alt="StarterGif">
                    </div>
                    <div class="carousel-slide">
                        <img class="imagenGT" src="imagenes/Carrusel/Season2GT.gif" alt="Season2">
                    </div>
                    <div class="carousel-slide">
                        <img class="imagenGT" src="imagenes/Carrusel/Season3GT.gif" alt="Season3">
                    </div>
                </div>
                <button class="carousel-control-left" id="prev">
                    🢀
                    <img class="Mayreel" id="imgM1" src="imagenes/Botones/Mayreel.gif" alt="Mayreel"/>
                </button>
                <button class="carousel-control-right" id="next">
                    🢂
                    <img class="Mayreel" id="imgM2" src="imagenes/Botones/MayreelRight.gif" alt="MayreelReverse"/>
                </button>
                <div class="carousel-indicators" id="carouselIndicators">
                    <span class="carousel-indicator active" data-index="0"></span>
                    <span class="carousel-indicator" data-index="1"></span>
                    <span class="carousel-indicator" data-index="2"></span>
                </div>
            </div>
        </div>
        
        <!--Boton Prox. Sección-->
        <div id="Container-Down">
            <a id="btn-down" href="#autor-sec">
                🢃
            </a>
        </div>
        
        <!--Sección Quien soy-->
        <section id="autor-sec">
            <div id="contenedor-texto">
                <h2>Sobre mi</h2>
                <p id="texto-autor">
                    Soy Joan Lavender, estudiante de Desarrollo de Software y Ciberseguridad 
                    en Senati. Me considero una persona honesta, determinada y con una gran 
                    pasión por aprender y mejorar continuamente. Mi deseo de ser autodidacta 
                    me impulsa a adquirir nuevos conocimientos y habilidades en mi campo de 
                    estudio.

                    Frecuentemente, asumo proyectos desafiantes como este para poner a prueba 
                    mis capacidades y seguir creciendo profesionalmente. Este proyecto, en 
                    particular, representa uno de esos desafíos que decidí emprender con 
                    entusiasmo. Si quieres saber más sobre mí, no dudes en consultar mi 
                    información de contacto en el pie de página.
                </p>
                <p id="texto-chequea">
                    ¡Chequea el botón de abajo para movilizarte mas rápido!
                </p>
                <button class="btn-sm" type="button"><a href="index.jsp#Contactos">Información de contacto</a></button>
            </div>
            <div id="contenedor-imagen">
                <img id="img-autor" src="/GremiosYRaids/imagenes/SobreMi/Person.png" alt="Autor Imagen"/>
            </div>
        </section>
        
        <!--Boton Prox. Sección-->
        <div id="Container-Down">
            <a id="btn-down" href="#gt-porque">
                🢃
            </a>
        </div>
        
        <!--Sección del Porque-->
        <section id="gt-porque">
            <div id="gt-texto">
                <h2>¿Por qué Guardian Tales?</h2>
                <p>
                    Bueno, comencé a jugar Guardian Tales hace bastantes años. Desde el 
                    principio, tuve experiencias muy positivas con el juego y, tras varios 
                    intentos, finalmente pude unirme a un gremio que resultó ser muy 
                    interesante.

                    Guardian Tales ha sido una parte significativa de mi vida estudiantil 
                    en el colegio y, posteriormente, también durante mi etapa académica en 
                    Senati. El juego no solo me brindó entretenimiento, sino que también 
                    me permitió hacer amigos y compartir momentos memorables.

                    Algunos de mis mejores recuerdos están ligados a Guardian Tales, y 
                    realmente solo puedo hacerles justicia si los describo en detalle, 
                    así que...
                </p>
            </div>
            <div id="gt-imagen">
                <img src="/GremiosYRaids/imagenes/PorQue-Memorias/GremioSaint.gif" alt="Saint"/>
            </div>
                <p id="letsgo">
                    !Aqui vamos!
                </p>
        </section>
        
        <!--Boton Prox. Sección-->
        <div id="Container-Down">
            <a id="btn-down" href="#gt-memoria">
                🢃
            </a>
        </div>
        
        <!--Sección Memorias-->
        <section id="gt-memoria">
            <article class="memorias" id="mem1" data-product="1">
                <img class="imgCaballera" src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Caballera"/>
                <h3 class="titulo-memoria"></h3>
            </article>
            <article class="memorias" id="mem2" data-product="2">
                <img class="imgCaballera" src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Caballera"/>
                <h3 class="titulo-memoria"></h3>
            </article>
            <article class="memorias" id="mem3" data-product="3">
                <img class="imgCaballera" src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Caballera"/>
                <h3 class="titulo-memoria"></h3>
            </article>
            <article class="memorias" id="mem4" data-product="4">
                <img class="imgCaballera" src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Caballera"/>
                <h3 class="titulo-memoria"></h3>
            </article>
            <article class="memorias" id="mem5" data-product="5">
                <img class="imgCaballera" src="/GremiosYRaids/imagenes/PorQue-Memorias/FemaleKnight.png" alt="Caballera"/>
                <h3 class="titulo-memoria"></h3>
            </article>
        </section>
        
        <!--Memorias, ventana emergente -->
        <div id="gt-Memorias-Modal" class="modal" style="display:none;">
            <div class="modal-content">
                <span class="close">&times;</span>
                <div id="Parte1">
                    <img class="modal-vid" src="" alt="1"/>
                    <p class="modal-text"></p>
                </div>
                <div id="Parte2">
                    <p class="modal-text"></p>
                    <img class="modal-vid" src="" alt="2"/>
                </div>
                <div id="Parte3">
                    <img class="modal-vid" src="" alt="3"/>
                    <p class="modal-text"></p>
                </div>
            </div>
        </div>
        
        <!--Boton Prox. Sección-->
        <div id="Container-Down">
            <a id="btn-down" href="#gt-registro">
                🢃
            </a>
        </div>
        
        <!--Sección Tengo Que Registrarme?-->
        <div id="gt-registro">
            <div class="carrusel-registro">
                <div class="carrusel-imgs" id="imgsCarrusel">
                    <div class="diapo-carrusel">
                        <p></p>
                        <img src="" alt="Registro0"/>
                    </div>
                    <div class="diapo-carrusel">
                        <p></p>
                        <img src="" alt="Registro1"/>
                    </div>
                    <div class="diapo-carrusel">
                        <p></p>
                        <img src="" alt="Registro2"/>
                    </div>
                    <div class="diapo-carrusel">
                        <p></p>
                        <img src="" alt="Registro3"/>
                    </div>
                </div>
                <div class="controles">
                    <button class="control-carrusel" id="izquierda">
                        🢀
                    </button>
                    <button class="control-carrusel" id="derecha">
                        🢂
                    </button>
                </div>
            </div>
        </div>
        
        <!--Boton Prox. Sección-->
        <div id="Container-Down">
            <a id="btn-down" href="#Contactos">
                🢃
            </a>
        </div>
        
<jsp:include page="mensajes.jsp"/>
        
<jsp:include page="footer.jsp"/>
        
        <script type="module" src="<%=request.getContextPath()%>/js/scriptIndex.js?v=6"></script>
        
    </body>
</html>