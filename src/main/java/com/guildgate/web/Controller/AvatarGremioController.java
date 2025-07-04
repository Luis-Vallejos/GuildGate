package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Service.AvatarGremioService;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class AvatarGremioController {

    @Inject
    AvatarGremioService ags;

    public AvatarGremioController() {
        this.ags = new AvatarGremioService();
    }

    public boolean creacion(AvatarGremio av) {
        boolean estado = ags.create(av);
        return estado;
    }

    public boolean edicion(AvatarGremio av) {
        boolean estado = ags.edit(av);
        return estado;
    }

    public boolean eliminar(Long id) {
        boolean estado = ags.delete(id);
        return estado;
    }

    public AvatarGremio buscarPorId(Long id) {
        return ags.findById(id);
    }

    public ArrayList<AvatarGremio> buscarTodos() {
        return ags.findAll();
    }
    
    
}
