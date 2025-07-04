package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Service.PerfilService;
import com.guildgate.web.Service.UsuarioService;
import jakarta.inject.Inject;
import java.util.ArrayList;

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

    public ImagenPerfil traerImagenPerfil(Long id) {
        return ps.findById(id);
    }

    public ArrayList<ImagenPerfil> traerListaImagenesPredeterminadas() {
        return ps.findAll();
    }

}
