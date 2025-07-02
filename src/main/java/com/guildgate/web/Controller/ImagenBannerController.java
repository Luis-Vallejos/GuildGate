package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Persistence.exceptions.BannerNotFoundException;
import com.guildgate.web.Service.BannerService;
import com.guildgate.web.Service.UsuarioService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;

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
            bs.editarNuevoBanner(user.getId(), img);
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

    public ArrayList<ImagenBanner> traerListaBannersPredeterminadas() {
        return bs.traerListaBannersPredeterminados();
    }
}
