package com.guildgate.web.Controller;

import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Persistence.exceptions.BannerNotFoundException;
import com.guildgate.web.Service.BannerService;
import com.guildgate.web.Service.UsuarioService;
import com.guildgate.web.Servlet.SvPerfil;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class ImagenBannerController {

    @Inject
    BannerService bs;

    @Inject
    UsuarioService us;

    public ImagenBannerController() {
        this.bs = new BannerService();
        this.us = new UsuarioService();
    }

    public ImagenBanner traerImagenBanner(long id) {
        return bs.findById(id);
    }

    public void cambioBannerUsuario(String nomArchivo, int usuario) throws BannerNotFoundException, Exception {
        Usuarios user = us.findById(usuario);

        try {
            ImagenBanner img = bs.buscarBannerPorNombre(nomArchivo);
            if (img == null) {
                throw new BannerNotFoundException("¡La imagen del banner no fue encontrada!");
            }
            us.editarNuevoBanner(user.getId(), img);
        } catch (BannerNotFoundException e) {
            throw new Exception("Error al cambiar el banner del usuario", e);
        }
    }

    public void cambioBannerPersonalizadoUsuario(int usuario, String nomArchivo, String tipoArchivo, long tamanoArchivo, byte[] byteArchivo) throws IOException {
        if (usuario <= 0 || SvUtils.isNullOrEmpty(nomArchivo)
                || SvUtils.isNullOrEmpty(tipoArchivo) || (tamanoArchivo == 0)) {
            throw new IllegalArgumentException("Argumentos inválidos proporcionados.");
        }

        int tamanoMaximo = 10485760; // 10MB en bytes

        if (tamanoArchivo > tamanoMaximo) {
            throw new IOException("El tamaño del archivo supera el límite de 10MB.");
        }

        ImagenBanner imgBNew;
        try {
            imgBNew = SvUtils.obtenerOCrearBannerSinPath(bs, nomArchivo, tipoArchivo, byteArchivo, com.guildgate.web.Utilities.Enum.OrigenArchivo.USUARIO);
        } catch (IOException e) {
            throw new IOException("Error al obtener o crear la imagen del banner.", e);
        }

        bs.editarNuevoBanner(usuario, imgBNew);
    }

    public void manejarBannerPredeterminado(HttpServletRequest request, HttpServletResponse response, String nomOriginalBanner, int idUsuarioActual) throws IOException {
        String nomBanner = request.getParameter("nomBannerArchivo");

        if (nomBanner == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
            return;
        } else if (idUsuarioActual == 0) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        } else if (nomBanner.equalsIgnoreCase(nomOriginalBanner)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.BANNER_DUPLICADO);
            return;
        }

        try {
            cambioBannerUsuario(nomBanner, idUsuarioActual);
            processUpdateBannerData(request, nomBanner);
            SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.BANNER_MODIFICADO_CON_EXITO);
        } catch (Exception ex) {
            Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void manejarBannerPersonalizado(HttpServletRequest request, HttpServletResponse response, int idUsuarioActual) throws IOException, ServletException {
        Part filePart = request.getPart("fotoBanner");
        long tamanoMaximo = 10485760; // 10MB en bytes

        String bannerName = SvUtils.obtenerNombreArchivo(filePart);
        String bannerTipo = filePart.getContentType();
        long bannerTamano = filePart.getSize();
        byte[] byteArchivo = SvUtils.leerBytesEntradaCadena(filePart.getInputStream(), bannerTamano);

        if (SvUtils.isNullOrEmpty(bannerName) || SvUtils.isNullOrEmpty(bannerTipo) || (bannerTamano == 0)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ESCOGIDA);
            return;
        } else if (idUsuarioActual == 0) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        if (bannerTamano > tamanoMaximo) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_SUPERA_TAMANO);
            return;
        }

        try {
            cambioBannerPersonalizadoUsuario(idUsuarioActual, bannerName, bannerTipo, bannerTamano, byteArchivo);
            processUpdateBannerData(request, bannerName);
            SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.BANNER_MODIFICADO_CON_EXITO);
        } catch (IOException ex) {
            Logger.getLogger(SvPerfil.class.getName()).log(Level.SEVERE, null, ex);
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_PROCESAMIENTO);
        }
    }

    public void processUpdateBannerData(HttpServletRequest request, String nomBanner) throws IOException {
        ImagenBanner imgB = bs.buscarBannerPorNombre(nomBanner);

        String urlAvatar = SvUtils.getBannerDataUrl(imgB, bs);

        HttpSession sesion = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");

        usuarioBean.setNombreBanner(imgB.getNomArchivo());
        usuarioBean.setBanner(urlAvatar);
    }

    public ArrayList<ImagenBanner> traerListaBannersPredeterminadas() {
        return bs.traerListaBannersPredeterminados();
    }
}
