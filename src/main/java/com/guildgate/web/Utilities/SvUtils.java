package com.guildgate.web.Utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Modelo.Controladora;
import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Bean.UsuarioBean;
import persistencia.ControladoraPersistencia;
import com.guildgate.web.Servlet.SvPerfil;
import com.guildgate.web.Utilities.Mensajes;

/**
 *
 * @author Lavender
 */
public class SvUtils {

    ControladoraPersistencia cors = new ControladoraPersistencia();

    /*Metodos para los campos*/
    //Método para campos vacios
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    // Método para parsear un entero con un valor predeterminado
    public static int parseIntOrDefault(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /*Métodos para listas*/
    //Metodo para encontrar un gremio por su nombre
    public static Optional<Gremio> findGremioByName(String nombre, ArrayList<Gremio> listaGremios) {
        return listaGremios.stream()
                .filter(gre -> nombre.equalsIgnoreCase(gre.getNombre()))
                .findFirst();
    }

    //Método para encontrar un usuario por su nombre de usuario o email con dos parametros
    public static Optional<Usuarios> findUserByUsernameOrEmail(String nomUser, ArrayList<Usuarios> listaUsuarios) {
        return listaUsuarios.stream()
                .filter(user -> nomUser.equalsIgnoreCase(user.getNombre()) || nomUser.equalsIgnoreCase(user.getCorreo()))
                .findFirst();
    }

    //Método para encontrar un usuario por su nombre de usuario o email con tres parametros
    public static Optional<Usuarios> findUsersByUsernameOrEmail(String nomUser, String email, ArrayList<Usuarios> listaUsuarios) {
        return listaUsuarios.stream()
                .filter(user -> (nomUser.equalsIgnoreCase(user.getNombre())) || (email.equalsIgnoreCase(user.getCorreo())))
                .findFirst();
    }

    //Método para encontrar un Gremio por su Id
    public static Optional<Gremio> findGremioById(int id, ArrayList<Gremio> listaGremios) {
        return listaGremios.stream()
                .filter(gre -> gre.getId() == id)
                .findFirst();
    }

    //Método para encontrar un gremio por región y mundo
    public static Optional<Gremio> findGremioByRegionAndMundo(int idRegion, int idMundo, ArrayList<Gremio> listaGremios) {
        return listaGremios.stream()
                .filter(g -> idRegion == g.getGremioregion().getId() && idMundo == g.getGremiomundo().getId())
                .findFirst();
    }

    //Método para encontrar una Región por su Id
    public static Optional<Region> findRegionById(int id, ArrayList<Region> listaRegion) {
        return listaRegion.stream()
                .filter(r -> id == r.getId())
                .findFirst();
    }

    //Método para encontrar un Mundo por su Id
    public static Optional<Mundos> findMundoById(int id, ArrayList<Mundos> listaMundos) {
        return listaMundos.stream()
                .filter(m -> id == m.getId())
                .findFirst();
    }

    //Método para encontrar una Rol por su Id
    public static Optional<Roles> findRolById(int id, ArrayList<Roles> listaRoles) {
        return listaRoles.stream()
                .filter(r -> id == r.getId())
                .findFirst();
    }

    //Método para encontrar un Usuario por su Nombre
    public static Optional<Usuarios> findUserByUsername(String username, ArrayList<Usuarios> listaUsuarios) {
        return listaUsuarios.stream()
                .filter(j -> username.equalsIgnoreCase(j.getNombre()))
                .findFirst();
    }

    public static Optional<Roles> obtenerObjetoRolPorUsuario(Usuarios user) {
        return user.getListaUsuariosRol().stream()
                .map(UsuarioRoles::getRoluserrol)
                .findFirst();
    }
    
    public static Optional<Roles> obtenerObjetoRolPorId(int id, ArrayList<Roles> listaRoles) {
        return listaRoles.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }

    
    public static String obtenerRolUser(Usuarios usuario) {
        return usuario.getListaUsuariosRol().stream()
                .map(UsuarioRoles::getRoluserrol)
                .map(Roles::getNombre)
                .findFirst()
                .orElse("Rol Desconocido");
    }

    //Método para encontrar una ImagenPerfil por su Nombre
    public Optional<ImagenPerfil> findImagenByNombre(String nombre) {
        return Optional.ofNullable(cors.buscarImagenPorNombre(nombre));
    }
    
    public Optional<AvatarGremio> findAvatarGremioByNombre(String nombre) {
        return Optional.ofNullable(cors.buscarAvatarGremioPorNombre(nombre));
    }
    
    public Optional<FondoGremio> findFondoGremioByNombre(String nombre) {
        return Optional.ofNullable(cors.buscarFondoGremioPorNombre(nombre));
    }

    /*Metodos para procesar una gran cantidad de datos*/
    //Metodo para procesar el login y asignar variables a la sesión
    @SuppressWarnings("null")
    public static void processSuccessfulLogin(HttpServletResponse response, HttpServletRequest request, Usuarios user, Controladora control) throws IOException {
        Gremio gremioUsuario = user.getGremiousuario();
        Optional<Gremio> optGremios = gremioUsuario != null ? findGremioById(gremioUsuario.getId(), control.traerListaGremios()) : Optional.empty();
        Gremio gre = optGremios.orElse(null);

        Optional<Roles> optRoles = obtenerObjetoRolPorUsuario(user);
        Roles roles = optRoles.orElse(null);

        String imagenBase64DataUrl = user.getImg() != null ? getImageDataUrl(user.getImg(), control) : null;
        String imagenBase64DataUrlG = (gre != null && gre.getImg() != null) ? getAvatarGremioDataUrl(gre.getImg(), control) : null;
        String imagenBase64DataUrlBanner = user.getImgB() != null ? getBannerDataUrl(user.getImgB(), control) : null;
        String imagenBase64DataUrlGFondo = (gre != null && gre.getImgF() != null) ? getFondoGremioDataUrl(gre.getImgF(), control) : null;
        String nombreGremio = (gremioUsuario != null) ? gremioUsuario.getNombre() : "Sin Gremio";
        String descripcionGremio = (gremioUsuario != null) ? gremioUsuario.getDescripcion() : "No contienen descripción...";
        String bioUsuario = user.getBio() != null ? user.getBio() : "¡Esta es una bio salvaje!";
        int nivelUsuario = user.getNivel() != 0 ? user.getNivel() : 1;
        String nombreRol = roles != null ? roles.getNombre() : "Sin Rol";
        String contraUsuario = user.getContrasena() != null ? user.getContrasena() : null;
        String emailUsuario = user.getCorreo() != null ? user.getCorreo() : null;

        HttpSession sesion = request.getSession(false);

        UsuarioBean usuarioBean = new UsuarioBean();
        usuarioBean.setId(user.getId());
        usuarioBean.setUsuarioActual(user.getNombre());
        usuarioBean.setDescripcionGremio(descripcionGremio);
        usuarioBean.setGremioActual(nombreGremio);
        usuarioBean.setRolUsuario(nombreRol);
        usuarioBean.setBioUsuario(bioUsuario);
        usuarioBean.setNivelUsuario(nivelUsuario);
        usuarioBean.setNombreAvatar(user.getImg().getNomArchivo());
        usuarioBean.setImagenUsuario(imagenBase64DataUrl);
        usuarioBean.setNombreAvatarGremio(gre != null ? gre.getImg().getNomArchivo() : null);
        usuarioBean.setImagenGremio(imagenBase64DataUrlG);
        usuarioBean.setNombreFondoGremio(gre != null ? gre.getImgF().getNomArchivo() : null);
        usuarioBean.setImagenFondoGremio(imagenBase64DataUrlGFondo);
        usuarioBean.setNombreBanner(user.getImgB().getNomArchivo());
        usuarioBean.setBanner(imagenBase64DataUrlBanner);
        usuarioBean.setPosicionXBanner(user.getImgB().getPosicionX());
        usuarioBean.setPosicionYBanner(user.getImgB().getPosicionY());
        usuarioBean.setContra(contraUsuario);
        usuarioBean.setCorreo(emailUsuario);

        // Permisos por defecto en caso de que no haya rol
        usuarioBean.setPermisoCambiarAvatarGremio(roles != null && roles.isPermisoCambiarAvatarGremio());
        usuarioBean.setPermisoCambiarFondoGremio(roles != null && roles.isPermisoCambiarFondoGremio());
        usuarioBean.setPermisoCambiarNombreGremio(roles != null && roles.isPermisoCambiarNombreGremio());
        usuarioBean.setPermisoCambiarDescripcionGremio(roles != null && roles.isPermisoCambiarDescripcionGremio());
        usuarioBean.setPermisoCrearRaids(roles != null && roles.isPermisoCrearRaids());
        usuarioBean.setPermisoVisualizarRaids(roles != null && roles.isPermisoVisualizarRaids());
        usuarioBean.setPermisoEditarRaids(roles != null && roles.isPermisoEditarRaids());
        usuarioBean.setPermisoCrearRoles(roles != null && roles.isPermisoCrearRoles());
        usuarioBean.setPermisoEditarRoles(roles != null && roles.isPermisoEditarRoles());
        usuarioBean.setPermisoVisualizarRoles(roles != null && roles.isPermisoVisualizarRoles());
        usuarioBean.setPermisoBotarMiembros(roles != null && roles.isPermisoBotarMiembros());
        usuarioBean.setPermisoSalirGremio(roles != null && roles.isPermisoSalirGremio());
        usuarioBean.setPermisoEliminarGremio(roles != null && roles.isPermisoEliminarGremio());

        sesion.setAttribute("usuarioBean", usuarioBean);
    }

    //Metodo para asignar los variables como la imagen del gremio y el nombre a la sesión del usuario
    public static void processSuccesfulGuildCreation(HttpServletResponse response, HttpServletRequest request, Gremio gre, Usuarios user, Controladora control) throws IOException {
        Optional<Gremio> optGremio = findGremioById(gre.getId(), control.traerListaGremios());
        
        if (!optGremio.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.GREMIO_NO_ENCONTRADO);
            return;
        }

        Gremio gremio = optGremio.get();
        Optional<Roles> optRoles = obtenerObjetoRolPorId(1, control.traerListaRoles());
        Roles roles = optRoles.get();

        try {
            Optional<AvatarGremio> optAvatarGremio = Optional.ofNullable(control.traerAvatarGremio(gre.getImg().getId()));
            Optional<FondoGremio> optFondoGremio = Optional.ofNullable(control.traerFondoGremio(gre.getImgF().getId()));

            if (!optAvatarGremio.isPresent() || !optFondoGremio.isPresent()) {
                respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
                return;
            }

            AvatarGremio ava = optAvatarGremio.get();
            FondoGremio fondo = optFondoGremio.get();
            String imagenBase64DataUrl = encodeAvatarGremioToBase64(ava);
            String fondoBase64DataUrl = encodeFondoGremioToBase64(fondo);
            String descripcionGremio = (gremio != null) ? gremio.getDescripcion() : "No contienen descripción...";

            HttpSession session = request.getSession(false);
            UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("usuarioBean");

            if (usuarioBean == null) {
                usuarioBean = new UsuarioBean();
                session.setAttribute("usuarioBean", usuarioBean);
            }

            usuarioBean.setGremioActual(gremio.getNombre());
            usuarioBean.setDescripcionGremio(descripcionGremio);
            usuarioBean.setRolUsuario(roles.getNombre());
            // Permisos por defecto en caso de que no haya rol
            usuarioBean.setPermisoCambiarAvatarGremio(roles != null && roles.isPermisoCambiarAvatarGremio());
            usuarioBean.setPermisoCambiarFondoGremio(roles != null && roles.isPermisoCambiarFondoGremio());
            usuarioBean.setPermisoCambiarNombreGremio(roles != null && roles.isPermisoCambiarNombreGremio());
            usuarioBean.setPermisoCambiarDescripcionGremio(roles != null && roles.isPermisoCambiarDescripcionGremio());
            usuarioBean.setPermisoCrearRaids(roles != null && roles.isPermisoCrearRaids());
            usuarioBean.setPermisoVisualizarRaids(roles != null && roles.isPermisoVisualizarRaids());
            usuarioBean.setPermisoEditarRaids(roles != null && roles.isPermisoEditarRaids());
            usuarioBean.setPermisoCrearRoles(roles != null && roles.isPermisoCrearRoles());
            usuarioBean.setPermisoEditarRoles(roles != null && roles.isPermisoEditarRoles());
            usuarioBean.setPermisoVisualizarRoles(roles != null && roles.isPermisoVisualizarRoles());
            usuarioBean.setPermisoBotarMiembros(roles != null && roles.isPermisoBotarMiembros());
            usuarioBean.setPermisoSalirGremio(roles != null && roles.isPermisoSalirGremio());
            usuarioBean.setPermisoEliminarGremio(roles != null && roles.isPermisoEliminarGremio());
            
            usuarioBean.setNombreBanner(ava.getNomArchivo());
            usuarioBean.setImagenGremio(imagenBase64DataUrl);
            usuarioBean.setNombreFondoGremio(fondo.getNomArchivo());
            usuarioBean.setImagenFondoGremio(fondoBase64DataUrl);
        } catch (IOException e) {
            respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
        }
    }

    //Método para procesar la lista de gremios
    public static void processGuildLists(HttpServletResponse response, HttpServletRequest request, Gremio gre, Controladora control) throws IOException {
        Map<String, Object> gremioInfo = new HashMap<>();
        gremioInfo.put("id", gre.getId());
        gremioInfo.put("nombre", gre.getNombre());

        Optional<AvatarGremio> optAvatarGremio;
        try {
            optAvatarGremio = Optional.ofNullable(control.traerAvatarGremio(gre.getImg().getId()));
            if (optAvatarGremio.isPresent()) {
                AvatarGremio ava = optAvatarGremio.get();
                String imagenBase64DataUrl = encodeAvatarGremioToBase64(ava);
                gremioInfo.put("Imagen", imagenBase64DataUrl);
            }
        } catch (Exception e) {
            respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
            return;
        }

        ArrayList<Map<String, Object>> gremiosFiltrados = new ArrayList<>();
        gremiosFiltrados.add(gremioInfo);

        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonGremiosFiltrados = gson.toJson(gremiosFiltrados);
        response.getWriter().write(jsonGremiosFiltrados);
    }

    //Método para traer la información de un solo gremio
    public static void processGuildInfo(HttpServletResponse response, HttpServletRequest request, Gremio gre, Controladora control) throws IOException {
        Map<String, Object> gremioInfo = new HashMap<>();
        gremioInfo.put("id", gre.getId());
        gremioInfo.put("nombre", gre.getNombre());
        gremioInfo.put("descripcion", gre.getDescripcion());
        gremioInfo.put("miembros", control.contarUsuarioPorGremio(gre.getId()));
        gremioInfo.put("limiteMiembros", gre.getCantidad());

        AvatarGremio img = gre.getImg();
        if (img != null) {
            String imagenBase64DataUrl = encodeAvatarGremioToBase64(img);
            gremioInfo.put("Imagen", imagenBase64DataUrl);
        } else {
            respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ENCONTRADA);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new Gson();
        String jsonGremio = gson.toJson(gremioInfo);
        response.getWriter().write(jsonGremio);
    }

    //Método para unirse a un gremio
    public static void processJoinGuild(HttpServletResponse response, HttpServletRequest request, String usuario, int gremio, String rol, Controladora control) throws IOException {
        control.asociarGremioaUsuario(usuario, gremio, rol);

        Gremio gre = control.traerGremio(gremio);
        String nomGremio = gre.getNombre();
        String descripcionGremio = (gre != null) ? gre.getDescripcion() : "No contienen descripción...";

        Optional<Roles> optRol = obtenerObjetoRolPorId(2, control.traerListaRoles());
        Roles roles = optRol.get();

        HttpSession sesion = request.getSession();
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");

        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
            sesion.setAttribute("usuarioBean", usuarioBean);
        }

        usuarioBean.setGremioActual(nomGremio);
        usuarioBean.setDescripcionGremio(descripcionGremio);
        usuarioBean.setRolUsuario(roles.getNombre());
        usuarioBean.setPermisoCambiarAvatarGremio(roles.isPermisoCambiarAvatarGremio());
        usuarioBean.setPermisoCambiarFondoGremio(roles.isPermisoCambiarFondoGremio());
        usuarioBean.setPermisoCambiarNombreGremio(roles.isPermisoCambiarNombreGremio());
        usuarioBean.setPermisoCambiarDescripcionGremio(roles.isPermisoCambiarDescripcionGremio());
        usuarioBean.setPermisoCrearRaids(roles.isPermisoCrearRaids());
        usuarioBean.setPermisoVisualizarRaids(roles.isPermisoVisualizarRaids());
        usuarioBean.setPermisoEditarRaids(roles.isPermisoEditarRaids());
        usuarioBean.setPermisoCrearRoles(roles.isPermisoCrearRoles());
        usuarioBean.setPermisoEditarRoles(roles.isPermisoEditarRoles());
        usuarioBean.setPermisoVisualizarRoles(roles.isPermisoVisualizarRoles());
        usuarioBean.setPermisoBotarMiembros(roles.isPermisoBotarMiembros());
        usuarioBean.setPermisoSalirGremio(roles.isPermisoSalirGremio());
        usuarioBean.setPermisoEliminarGremio(roles.isPermisoEliminarGremio());

        Optional<AvatarGremio> optAvatar = Optional.ofNullable(control.traerAvatarGremio(gre.getImg().getId()));
        Optional<FondoGremio> optFondo = Optional.ofNullable(control.traerFondoGremio(gre.getImgF().getId()));

        if (optAvatar.isPresent() || optFondo.isPresent()) {
            AvatarGremio ava = optAvatar.get();
            FondoGremio fon = optFondo.get();
            
            String imagenBase64DataUrl = encodeAvatarGremioToBase64(ava);
            String fondoBase64DataUrl = encodeFondoGremioToBase64(fon);
            
            usuarioBean.setNombreAvatarGremio(ava.getNomArchivo());
            usuarioBean.setImagenGremio(imagenBase64DataUrl);
            usuarioBean.setNombreFondoGremio(fon.getNomArchivo());
            usuarioBean.setImagenFondoGremio(fondoBase64DataUrl);
            respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.USUARIO_UNIDO_GREMIO + nomGremio + "!");
        } else {
            respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
        }
    }

    public static void manejarAvatarPredeterminado(HttpServletRequest request, HttpServletResponse response, String nomOriginalAvatar, int idUsuarioActual, Controladora control) throws IOException {
            String nomArchivo = request.getParameter("nomAvatarArchivo");
            SvUtils su = new SvUtils();
            if(nomArchivo == null) {
                respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
                return;
            }else if(idUsuarioActual == 0) {
                respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
                return;
            }else if(nomArchivo.equalsIgnoreCase(nomOriginalAvatar)) {
                respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.AVATAR_DUPLICADO);
                return;
            }

            try {
                control.cambioAvatarUsuario(nomArchivo, idUsuarioActual);
                su.processUpdateAvatarData(request, nomArchivo, control);
                respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.AVATAR_MODIFICADO_CON_EXITO);
            } catch (Exception ex) {
                Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void manejarAvatarPersonalizado(HttpServletRequest request, HttpServletResponse response, int idUsuarioActual, Controladora control) throws ServletException, IOException {
        Part filePart = request.getPart("fotoPerfilPersonalizada");
        long tamanoMaximo = 10485760; // 10MB en bytes
        
        String avatarName = obtenerNombreArchivo(filePart);
        String avatarTipo = filePart.getContentType();
        long avatarTamano = filePart.getSize();
        byte[] byteAvatar = leerBytesEntradaCadena(filePart.getInputStream(), avatarTamano);
        
        SvUtils su = new SvUtils();
        
        if (isNullOrEmpty(avatarName) || isNullOrEmpty(avatarTipo) || (avatarTamano == 0)) {
            respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
            return;
        } else if (idUsuarioActual == 0) {
            respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }
        
        if (avatarTamano > tamanoMaximo) {
            respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_SUPERA_TAMANO);
            return;
        }
        
        try {
            control.cambioAvatarPersonalizadoUsuario(idUsuarioActual, avatarName, avatarTipo, avatarTamano, byteAvatar);
            su.processUpdateAvatarData(request, avatarName, control);
            respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.AVATAR_MODIFICADO_CON_EXITO);
        } catch (IOException ex) {
            Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
            respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_PROCESAMIENTO);
        }
    }
    
    public void processUpdateAvatarData(HttpServletRequest request, String nomImagen, Controladora control) throws IOException {
        ImagenPerfil img = cors.buscarImagenPorNombre(nomImagen);
        
        String urlAvatar = getImageDataUrl(img, control);
        
        HttpSession sesion = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");
        
        usuarioBean.setNombreAvatar(img.getNomArchivo());
        usuarioBean.setImagenUsuario(urlAvatar);
    }
    
    public static void manejarBannerPredeterminado(HttpServletRequest request, HttpServletResponse response, String nomOriginalBanner, int idUsuarioActual, Controladora control) throws IOException {
        String nomBanner = request.getParameter("nomBannerArchivo");
        SvUtils su = new SvUtils();
        if(nomBanner == null) {
            respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
            return;
        }else if(idUsuarioActual == 0) {
            respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        } else if(nomBanner.equalsIgnoreCase(nomOriginalBanner)) {
            respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.BANNER_DUPLICADO);
            return;
        }
        
        try {
            control.cambioBannerUsuario(nomBanner, idUsuarioActual);
            su.processUpdateBannerData(request, nomBanner, control);
            respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.BANNER_MODIFICADO_CON_EXITO);
        } catch (Exception ex) {
            Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void manejarBannerPersonalizado(HttpServletRequest request, HttpServletResponse response, int idUsuarioActual, Controladora control) throws IOException, ServletException {
        Part filePart = request.getPart("fotoBanner");
        long tamanoMaximo = 10485760; // 10MB en bytes

        String bannerName = obtenerNombreArchivo(filePart);
        String bannerTipo = filePart.getContentType();
        long bannerTamano = filePart.getSize();
        byte[] byteArchivo = leerBytesEntradaCadena(filePart.getInputStream(), bannerTamano);

        SvUtils su = new SvUtils();

        if (isNullOrEmpty(bannerName) || isNullOrEmpty(bannerTipo) || (bannerTamano == 0)) {
            respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
            return;
        } else if (idUsuarioActual == 0) {
            respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        if (bannerTamano > tamanoMaximo) {
            respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_SUPERA_TAMANO);
            return;
        }

        try {
            control.cambioBannerPersonalizadoUsuario(idUsuarioActual, bannerName, bannerTipo, bannerTamano, byteArchivo);
            su.processUpdateBannerData(request, bannerName, control);
            respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.BANNER_MODIFICADO_CON_EXITO);
        } catch (IOException ex) {
            Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
            respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_PROCESAMIENTO);
        }
    }
    
    public void processUpdateBannerData(HttpServletRequest request, String nomBanner, Controladora control) throws IOException {
        ImagenBanner imgB = cors.buscarBannerPorNombre(nomBanner);
        
        String urlAvatar = getBannerDataUrl(imgB, control);
        
        HttpSession sesion = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");
        
        usuarioBean.setNombreBanner(imgB.getNomArchivo());
        usuarioBean.setBanner(urlAvatar);
    }
    
    /*Métodos para Imagenes*/
    public static String obtenerNombreArchivo(Part part) {
        for (String contenido : part.getHeader("content-disposition").split(";")) {
            if (contenido.trim().startsWith("filename")) {
                return contenido.substring(contenido.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static byte[] leerBytesEntradaCadena(InputStream inpStream, long fileSize) throws IOException {
        byte[] bytesArchivo = new byte[(int) fileSize];
        try (BufferedInputStream bis = new BufferedInputStream(inpStream)) {
            bis.read(bytesArchivo, 0, bytesArchivo.length);
        }
        return bytesArchivo;
    }
    
    //CON CONTROL
    
    //Método para obtener la url de una imagen de la base de datos por su Data
    public static String getImageDataUrl(ImagenPerfil img, Controladora control) {
        ImagenPerfil imagen = control.traerImagenPerfil(img.getId());
        String imagenBase64 = Base64.getEncoder().encodeToString(imagen.getData());
        String tipoImagen = imagen.getTipoArchivo();
        return "data:image/" + tipoImagen + ";base64," + imagenBase64;
    }

    //Banner datos de su url
    public static String getBannerDataUrl(ImagenBanner imgB, Controladora control) {
        ImagenBanner imagenB = control.traerImagenBanner(imgB.getId());
        String bannerBase64 = Base64.getEncoder().encodeToString(imagenB.getData());
        String tipoBanner = imagenB.getTipoArchivo();
        return "data:image/" + tipoBanner + ";base64," + bannerBase64;
    }
    
    public static String getAvatarGremioDataUrl(AvatarGremio ava, Controladora control) {
        AvatarGremio avatar = control.traerAvatarGremio(ava.getId());
        String avatarBase64 = Base64.getEncoder().encodeToString(avatar.getData());
        String tipoAvatar = avatar.getTipoArchivo();
        return "data:image/" + tipoAvatar + ";base64," + avatarBase64;
    }

    public static String getFondoGremioDataUrl(FondoGremio fondo, Controladora control) {
        FondoGremio fon = control.traerFondoGremio(fondo.getId());
        String fondoBase64 = Base64.getEncoder().encodeToString(fon.getData());
        String tipoFondo = fon.getTipoArchivo();
        return "data:image/" + tipoFondo + ";base64," + fondoBase64;
    }
    
    //SIN CONTROL
    
    //Método para codificar imagen a Base64
    public static String encodeImageToBase64(ImagenPerfil imagen) {
        String imagenBase64 = Base64.getEncoder().encodeToString(imagen.getData());
        String tipoArchivo = imagen.getTipoArchivo();
        return "data:image/" + tipoArchivo + ";base64," + imagenBase64;
    }
    
    //AvatarGremio a Base64
    public static String encodeAvatarGremioToBase64(AvatarGremio ava) {
        String avatarBase64 = Base64.getEncoder().encodeToString(ava.getData());
        String tipoArchivo = ava.getTipoArchivo();
        return "data:image/" + tipoArchivo + ";base64," + avatarBase64;
    }

    public static String encodeFondoGremioToBase64(FondoGremio fondo) {
        String fondoBase64 = Base64.getEncoder().encodeToString(fondo.getData());
        String tipoArchivo = fondo.getTipoArchivo();
        return "data:image/" + tipoArchivo + ";base64," + fondoBase64;
    }

    
    // Método para obtener o crear una imagen
    //Avatar usuario - Crear por primera vez sin data por directorio
    public static ImagenPerfil obtenerOCrearImagenPerfilConPath(ControladoraPersistencia cors, String nombreArchivo, String rutaArchivo, ImagenPerfil.OrigenArchivo origenArchivo) throws IOException {
        ImagenPerfil img = cors.buscarImagenPorNombre(nombreArchivo);

        if (img == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            img = new ImagenPerfil();
            img.setNomArchivo(nombreArchivo);
            img.setTipoArchivo(tipoArchivo);
            img.setData(datos);
            img.setOrigenArchivo(origenArchivo);
            cors.guardarImagen(img);
        }
        return img;
    }

    //Avatar usuario - Crear por primera vez con data y sin directorio
    public static ImagenPerfil obtenerOCrearPerfilSinPath(ControladoraPersistencia cors, String nombreArchivo, String tipoArchivo, byte[] data, ImagenPerfil.OrigenArchivo origenArchivo) throws IOException {
        ImagenPerfil img = cors.buscarImagenPorNombre(nombreArchivo);
        
        if(img == null) {
            img = new ImagenPerfil();
            img.setNomArchivo(nombreArchivo);
            img.setTipoArchivo(tipoArchivo);
            img.setData(data);
            img.setOrigenArchivo(origenArchivo);
            cors.guardarImagen(img);
        }
        
        return img;
    }
    
    //Banner usuario - Crear por primera vez sin data por directorio
    public static ImagenBanner obtenerOCrearImagenBannerConPath(ControladoraPersistencia cors, String nombreArchivo, String rutaArchivo, ImagenBanner.OrigenArchivo origenArchivo) throws IOException {
        ImagenBanner img = cors.buscarBannerPorNombre(nombreArchivo);

        if (img == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            img = new ImagenBanner();
            img.setNomArchivo(nombreArchivo);
            img.setTipoArchivo(tipoArchivo);
            img.setData(datos);
            img.setOrigenArchivo(origenArchivo);
            cors.guardarBanner(img);
        }
        return img;
    }
    
    //Banner usuario - Crear por primera vez con data y sin directorio
    public static ImagenBanner obtenerOCrearBannerSinPath(ControladoraPersistencia cors, String nombreArchivo, String tipoArchivo, byte[] data, ImagenBanner.OrigenArchivo origenArchivo) throws IOException {
        ImagenBanner imgB = cors.buscarBannerPorNombre(nombreArchivo);

        if (imgB == null) {
            imgB = new ImagenBanner();
            imgB.setNomArchivo(nombreArchivo);
            imgB.setTipoArchivo(tipoArchivo);
            imgB.setData(data);
            imgB.setPosicionX("0");
            imgB.setPosicionY("50%");
            imgB.setOrigenArchivo(origenArchivo);
            cors.guardarBanner(imgB);
        }
        return imgB;
    }
    
    //AvatarGremio - Crear por primera vez sin data por directorio
    public static AvatarGremio obtenerOCrearAvatarGremioConPath(ControladoraPersistencia cors, String nombreArchivo, String rutaArchivo, AvatarGremio.OrigenArchivo origenArchivo) throws IOException {
        AvatarGremio ava = cors.buscarAvatarGremioPorNombre(nombreArchivo);

        if (ava == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            ava = new AvatarGremio();
            ava.setNomArchivo(nombreArchivo);
            ava.setTipoArchivo(tipoArchivo);
            ava.setData(datos);
            ava.setOrigenArchivo(origenArchivo);
            cors.guardarAvatarGremio(ava);
        }
        return ava;
    }
    
    //AvatarGremio - Crear por primera vez con data y sin directorio
    public static AvatarGremio obtenerOCrearAvatarGremioSinPath(ControladoraPersistencia cors, String nombreArchivo, String tipoArchivo, byte[] data, AvatarGremio.OrigenArchivo origenArchivo) throws IOException {
        AvatarGremio ava = cors.buscarAvatarGremioPorNombre(nombreArchivo);
        
        if(ava == null) {
            ava = new AvatarGremio();
            ava.setNomArchivo(nombreArchivo);
            ava.setTipoArchivo(tipoArchivo);
            ava.setData(data);
            ava.setOrigenArchivo(origenArchivo);
            cors.guardarAvatarGremio(ava);
        }
        return ava;
    }
    
    //FondoGremio - Crear por primera vez sin data por directorio
    public static FondoGremio obtenerOCrearFondoGremioConPath(ControladoraPersistencia cors, String nombreArchivo, String rutaArchivo, FondoGremio.OrigenArchivo origenArchivo) throws IOException {
        FondoGremio fondo = cors.buscarFondoGremioPorNombre(nombreArchivo);

        if (fondo == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            fondo = new FondoGremio();
            fondo.setNomArchivo(nombreArchivo);
            fondo.setTipoArchivo(tipoArchivo);
            fondo.setData(datos);
            fondo.setOrigenArchivo(origenArchivo);
            cors.guardarFondoGremio(fondo);
        }
        return fondo;
    }
    
    public static FondoGremio obtenerOCrearFondoGremioSinPath(ControladoraPersistencia cors, String nombreArchivo, String tipoArchivo, byte[] data, FondoGremio.OrigenArchivo origenArchivo) throws IOException {
        FondoGremio fondo = cors.buscarFondoGremioPorNombre(nombreArchivo);
        
        if(fondo == null) {
            fondo = new FondoGremio();
            fondo.setNomArchivo(nombreArchivo);
            fondo.setTipoArchivo(tipoArchivo);
            fondo.setData(data);
            fondo.setOrigenArchivo(origenArchivo);
            cors.guardarFondoGremio(fondo);
        }
        return fondo;
    }
    
    /*Respuestas JSON*/
    //Respuesta JSON - General
    public static void respondWithJson(HttpServletResponse response, int statusCode, boolean success, String message) throws IOException {
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put(success ? "message" : "error", message);
        response.setStatus(statusCode);
        Gson gson = new Gson();
        String jsonResponseString = gson.toJson(jsonResponse);
        response.getWriter().write(jsonResponseString);
    }

    //Respuesta JSON - Error   
    public static void respondWithError(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        respondWithJson(response, statusCode, false, errorMessage);
    }

    //Respuesta JSON - Éxito
    public static void respondWithSuccess(HttpServletResponse response, int statusCode, String message) throws IOException {
        respondWithJson(response, statusCode, true, message);
    }
}
