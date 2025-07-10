package com.guildgate.web.Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Service.impl.AvatarGremioServiceImpl;
import com.guildgate.web.Service.impl.BannerServiceImpl;
import com.guildgate.web.Service.impl.FondoGremioServiceImpl;
import com.guildgate.web.Service.impl.PerfilServiceImpl;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Lavender
 */
public class SvUtils {

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

    /**
     * Convierte una List<T> en un ArrayList<T>. Si la lista pasada es null,
     * retorna una lista vacía.
     *
     * @param <T> Tipo de elementos
     * @param list Lista a convertir
     * @return Un ArrayList con los mismos elementos
     */
    public static <T> ArrayList<T> toArrayList(List<? extends T> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(list);
    }

    public static Long parseLongParam(HttpServletRequest req, String name, HttpServletResponse resp)
            throws IOException {
        String s = req.getParameter(name);
        System.out.println(s);
        try {
            System.out.println(Long.valueOf(s));
            return Long.valueOf(s);
        } catch (NumberFormatException e) {
            respondWithJson(resp, SC_BAD_REQUEST, false, "ID inválido: " + e.getMessage() + s, null);
            return null;
        }
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
    public static String getImageDataUrl(ImagenPerfil img, PerfilServiceImpl ps) {
        ImagenPerfil imagen = ps.findById(img.getId());
        String imagenBase64 = Base64.getEncoder().encodeToString(imagen.getData());
        String tipoImagen = imagen.getTipoArchivo();
        return "data:image/" + tipoImagen + ";base64," + imagenBase64;
    }

    //Banner datos de su url
    public static String getBannerDataUrl(ImagenBanner imgB, BannerServiceImpl bs) {
        ImagenBanner imagenB = bs.findById(imgB.getId());
        String bannerBase64 = Base64.getEncoder().encodeToString(imagenB.getData());
        String tipoBanner = imagenB.getTipoArchivo();
        return "data:image/" + tipoBanner + ";base64," + bannerBase64;
    }

    public static String getAvatarGremioDataUrl(AvatarGremio ava, AvatarGremioServiceImpl ags) {
        AvatarGremio avatar = ags.findById(ava.getId());
        String avatarBase64 = Base64.getEncoder().encodeToString(avatar.getData());
        String tipoAvatar = avatar.getTipoArchivo();
        return "data:image/" + tipoAvatar + ";base64," + avatarBase64;
    }

    public static String getFondoGremioDataUrl(FondoGremio fondo, FondoGremioServiceImpl fgs) {
        FondoGremio fon = fgs.findById(fondo.getId());
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
    public static ImagenPerfil obtenerOCrearImagenPerfilConPath(PerfilServiceImpl ps, String nombreArchivo, String rutaArchivo, Enum.OrigenArchivo origenArchivo) throws IOException {
        ImagenPerfil img = ps.buscarImagenPorNombre(nombreArchivo);

        if (img == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            img = new ImagenPerfil();
            img.setNomArchivo(nombreArchivo);
            img.setTipoArchivo(tipoArchivo);
            img.setData(datos);
            img.setOrigenArchivo(origenArchivo);
            ps.create(img);
        }
        return img;
    }

    //Avatar usuario - Crear por primera vez con data y sin directorio
    public static ImagenPerfil obtenerOCrearPerfilSinPath(PerfilServiceImpl ps, String nombreArchivo, String tipoArchivo, byte[] data, Enum.OrigenArchivo origenArchivo) throws IOException {
        ImagenPerfil img = ps.buscarImagenPorNombre(nombreArchivo);

        if (img == null) {
            img = new ImagenPerfil();
            img.setNomArchivo(nombreArchivo);
            img.setTipoArchivo(tipoArchivo);
            img.setData(data);
            img.setOrigenArchivo(origenArchivo);
            ps.create(img);
        }

        return img;
    }

    //Banner usuario - Crear por primera vez sin data por directorio
    public static ImagenBanner obtenerOCrearImagenBannerConPath(BannerServiceImpl bs, String nombreArchivo, String rutaArchivo, Enum.OrigenArchivo origenArchivo) throws IOException {
        ImagenBanner img = bs.buscarBannerPorNombre(nombreArchivo);

        if (img == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            img = new ImagenBanner();
            img.setNomArchivo(nombreArchivo);
            img.setTipoArchivo(tipoArchivo);
            img.setData(datos);
            img.setOrigenArchivo(origenArchivo);
            bs.create(img);
        }
        return img;
    }

    //Banner usuario - Crear por primera vez con data y sin directorio
    public static ImagenBanner obtenerOCrearBannerSinPath(BannerServiceImpl bs, String nombreArchivo, String tipoArchivo, byte[] data, Enum.OrigenArchivo origenArchivo) throws IOException {
        ImagenBanner imgB = bs.buscarBannerPorNombre(nombreArchivo);

        if (imgB == null) {
            imgB = new ImagenBanner();
            imgB.setNomArchivo(nombreArchivo);
            imgB.setTipoArchivo(tipoArchivo);
            imgB.setData(data);
            imgB.setPosicionX("0");
            imgB.setPosicionY("50%");
            imgB.setOrigenArchivo(origenArchivo);
            bs.create(imgB);
        }
        return imgB;
    }

    //AvatarGremio - Crear por primera vez sin data por directorio
    public static AvatarGremio obtenerOCrearAvatarGremioConPath(AvatarGremioServiceImpl ags, String nombreArchivo, String rutaArchivo, Enum.OrigenArchivo origenArchivo) throws IOException {
        AvatarGremio ava = ags.buscarAvatarGremioPorNombre(nombreArchivo);

        if (ava == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            ava = new AvatarGremio();
            ava.setNomArchivo(nombreArchivo);
            ava.setTipoArchivo(tipoArchivo);
            ava.setData(datos);
            ava.setOrigenArchivo(origenArchivo);
            ags.create(ava);
        }
        return ava;
    }

    //AvatarGremio - Crear por primera vez con data y sin directorio
    public static AvatarGremio obtenerOCrearAvatarGremioSinPath(AvatarGremioServiceImpl ags, String nombreArchivo, String tipoArchivo, byte[] data, Enum.OrigenArchivo origenArchivo) throws IOException {
        AvatarGremio ava = ags.buscarAvatarGremioPorNombre(nombreArchivo);

        if (ava == null) {
            ava = new AvatarGremio();
            ava.setNomArchivo(nombreArchivo);
            ava.setTipoArchivo(tipoArchivo);
            ava.setData(data);
            ava.setOrigenArchivo(origenArchivo);
            ags.create(ava);
        }
        return ava;
    }

    //FondoGremio - Crear por primera vez sin data por directorio
    public static FondoGremio obtenerOCrearFondoGremioConPath(FondoGremioServiceImpl fgs, String nombreArchivo, String rutaArchivo, Enum.OrigenArchivo origenArchivo) throws IOException {
        FondoGremio fondo = fgs.buscarFondoGremioPorNombre(nombreArchivo);

        if (fondo == null) {
            Path path = Paths.get(rutaArchivo + nombreArchivo);
            byte[] datos = Files.readAllBytes(path);
            String tipoArchivo = Files.probeContentType(path);

            fondo = new FondoGremio();
            fondo.setNomArchivo(nombreArchivo);
            fondo.setTipoArchivo(tipoArchivo);
            fondo.setData(datos);
            fondo.setOrigenArchivo(origenArchivo);
            fgs.create(fondo);
        }
        return fondo;
    }

    public static FondoGremio obtenerOCrearFondoGremioSinPath(FondoGremioServiceImpl fgs, String nombreArchivo, String tipoArchivo, byte[] data, Enum.OrigenArchivo origenArchivo) throws IOException {
        FondoGremio fondo = fgs.buscarFondoGremioPorNombre(nombreArchivo);

        if (fondo == null) {
            fondo = new FondoGremio();
            fondo.setNomArchivo(nombreArchivo);
            fondo.setTipoArchivo(tipoArchivo);
            fondo.setData(data);
            fondo.setOrigenArchivo(origenArchivo);
            fgs.create(fondo);
        }
        return fondo;
    }

    /* Respuestas JSON */
    // Respuesta JSON - General - Sin data
    public static void respondWithJson(HttpServletResponse response, int statusCode, boolean success, String message, Object data) throws IOException {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("success", success);
        jsonResponse.put("message", message);
        jsonResponse.put("status", statusCode);
        if (data != null) {
            jsonResponse.put("data", data);
        }
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        String jsonResponseString = gson.toJson(jsonResponse);
        response.getWriter().write(jsonResponseString);
    }

    // Respuesta JSON - General - Con data
    public static void respondWithJsonList(HttpServletResponse respuesta, int codigoEstado, boolean checkSuccess, String mensaje, Object dataObjeto) throws IOException {
        Map<String, Object> responseInfo = new HashMap<>();

        responseInfo.put("success", checkSuccess);
        responseInfo.put("message", mensaje);
        responseInfo.put("data", dataObjeto instanceof Collection<?> ? dataObjeto : Collections.singletonList(dataObjeto));

        respuesta.setStatus(codigoEstado);
        respuesta.setContentType("application/json");
        respuesta.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseInfo);

        respuesta.getWriter().write(jsonResponse);
        System.out.println(jsonResponse);
    }

    // Respuesta JSON - General - Con objeto específico
    public static void respondWithJsonObject(HttpServletResponse respuesta, int codigoEstado, boolean checkSuccess, String mensaje, Map<String, Object> additionalData, Object mainData) throws IOException {
        Map<String, Object> responseInfo = new HashMap<>();

        responseInfo.put("success", checkSuccess);
        responseInfo.put("message", mensaje);

        responseInfo.put("data", mainData);

        if (additionalData != null && !additionalData.isEmpty()) {
            responseInfo.putAll(additionalData);
        }

        respuesta.setStatus(codigoEstado);
        respuesta.setContentType("application/json");
        respuesta.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseInfo);

        respuesta.getWriter().write(jsonResponse);
        System.out.println(jsonResponse);
    }

    // Respuesta JSON - Añadido - Data
    public static List<Map<String, Object>> anadirDataLista(List<?> listaOriginal, Map<String, Object> dataAdicional) {
        List<Map<String, Object>> listaEnriquecida = new ArrayList<>();

        listaOriginal.forEach(item -> {
            Map<String, Object> itemData = new HashMap<>();
            itemData.putAll(new ObjectMapper().convertValue(item, Map.class));
            itemData.putAll(dataAdicional);
            listaEnriquecida.add(itemData);
        });
        return listaEnriquecida;
    }

    // Respuesta JSON - Información - Éxito
    public static void respondWithSuccess(HttpServletResponse response, int statusCode, String message) throws IOException {
        respondWithJson(response, statusCode, true, message, null);
    }

    public static void respondWithSuccessDataObject(HttpServletResponse response, int statusCode, boolean checkSuccess, String mensaje, Map<String, Object> additionalData, Object mainData) throws IOException {
        respondWithJsonObject(response, statusCode, checkSuccess, mensaje, additionalData, mainData);
    }

    // Respuesta JSON - Información - Éxito con Datos
    public static void respondWithSuccessData(HttpServletResponse response, int statusCode, String message, Object data) throws IOException {
        respondWithJsonList(response, statusCode, true, message, data);
    }

    // Respuesta JSON - Información - Error
    public static void respondWithError(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        respondWithJson(response, statusCode, false, errorMessage, null);
    }
}
