package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Service.PerfilService;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class ImagenPerfilController {

    @Inject
    PerfilService ps;

    public ImagenPerfilController() {
        this.ps = new PerfilService();
    }

    public ImagenPerfil traerImagenPerfil(Long id) {
        return ps.findById(id);
    }

    public ArrayList<ImagenPerfil> traerListaImagenesPredeterminadas() {
        return ps.findAll();
    }

}
