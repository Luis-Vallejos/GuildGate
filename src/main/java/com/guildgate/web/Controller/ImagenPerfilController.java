package com.guildgate.web.Controller;

import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Persistence.exceptions.AvatarNotFoundException;
import com.guildgate.web.Service.PerfilService;
import com.guildgate.web.Service.UsuarioService;
import com.guildgate.web.Servlet.SvPerfil;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import com.guildgate.web.Utilities.Enum;
import com.guildgate.web.Utilities.Mensajes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class ImagenPerfilController {

    @Inject
    PerfilService ps;

    @Inject
    UsuarioService us;

    public ImagenPerfilController() {
        this.ps = new PerfilService();
        this.us = new UsuarioService();
    }

    public void cambioAvatarUsuario(String nomArchivo, int usuario) throws AvatarNotFoundException, Exception {
        Usuarios user = us.findById(usuario);

        try {
            ImagenPerfil img = ps.buscarImagenPorNombre(nomArchivo);
            if (img == null) {
                throw new AvatarNotFoundException("¡La imagen del avatar no fue encontrada!");
            }
            us.editarNuevoAvatar(user.getId(), img);
        } catch (AvatarNotFoundException e) {
            throw new Exception("Error al cambiar el avatar del usuario", e);
        }
    }

    public void cambioAvatarPersonalizadoUsuario(int usuario, String nomArchivo, String tipoArchivo, long tamanoArchivo, byte[] archivoByte) throws IOException {
        if (usuario <= 0 || SvUtils.isNullOrEmpty(nomArchivo)
                || SvUtils.isNullOrEmpty(tipoArchivo) || (tamanoArchivo == 0)) {
            throw new IllegalArgumentException("Argumentos inválidos proporcionados.");
        }

        int tamanoMaximo = 10485760; // 10MB en bytes

        if (tamanoArchivo > tamanoMaximo) {
            throw new IOException("El tamaño del archivo supera el límite de 10MB.");
        }

        ImagenPerfil imgNew;
        try {
            imgNew = SvUtils.obtenerOCrearPerfilSinPath(ps, nomArchivo, tipoArchivo, archivoByte, Enum.OrigenArchivo.USUARIO);
        } catch (IOException e) {
            throw new IOException("Error al obtener o crear la imagen de perfil.", e);
        }

        us.editarNuevoAvatar(usuario, imgNew);
    }

    public void manejarAvatarPredeterminado(HttpServletRequest request, HttpServletResponse response, String nomOriginalAvatar, int idUsuarioActual) throws IOException {
        String nomArchivo = request.getParameter("nomAvatarArchivo");

        if (nomArchivo == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
            return;
        } else if (idUsuarioActual == 0) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        } else if (nomArchivo.equalsIgnoreCase(nomOriginalAvatar)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.AVATAR_DUPLICADO);
            return;
        }

        try {
            cambioAvatarUsuario(nomArchivo, idUsuarioActual);
            processUpdateAvatarData(request, nomArchivo);
            SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.AVATAR_MODIFICADO_CON_EXITO);
        } catch (Exception ex) {
            Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void manejarAvatarPersonalizado(HttpServletRequest request, HttpServletResponse response, int idUsuarioActual) throws ServletException, IOException {
        Part filePart = request.getPart("fotoPerfilPersonalizada");
        long tamanoMaximo = 10485760; // 10MB en bytes

        String avatarName = SvUtils.obtenerNombreArchivo(filePart);
        String avatarTipo = filePart.getContentType();
        long avatarTamano = filePart.getSize();
        byte[] byteAvatar = SvUtils.leerBytesEntradaCadena(filePart.getInputStream(), avatarTamano);

        if (SvUtils.isNullOrEmpty(avatarName) || SvUtils.isNullOrEmpty(avatarTipo) || (avatarTamano == 0)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
            return;
        } else if (idUsuarioActual == 0) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        if (avatarTamano > tamanoMaximo) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_SUPERA_TAMANO);
            return;
        }

        try {
            cambioAvatarPersonalizadoUsuario(idUsuarioActual, avatarName, avatarTipo, avatarTamano, byteAvatar);
            processUpdateAvatarData(request, avatarName);
            SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.AVATAR_MODIFICADO_CON_EXITO);
        } catch (IOException ex) {
            Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_PROCESAMIENTO);
        }
    }

    public void processUpdateAvatarData(HttpServletRequest request, String nomImagen) throws IOException {
        ImagenPerfil img = ps.buscarImagenPorNombre(nomImagen);

        String urlAvatar = SvUtils.getImageDataUrl(img, ps);

        HttpSession sesion = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");

        usuarioBean.setNombreAvatar(img.getNomArchivo());
        usuarioBean.setImagenUsuario(urlAvatar);
    }

    public ImagenPerfil traerImagenPerfil(Long id) {
        return ps.findById(id);
    }

    public ArrayList<ImagenPerfil> traerListaImagenesPredeterminadas() {
        return ps.findAll();
    }

}
